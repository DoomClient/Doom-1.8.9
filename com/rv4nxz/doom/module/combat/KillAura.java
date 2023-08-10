package com.rv4nxz.doom.module.combat;

import com.rv4nxz.doom.Doom;
import com.rv4nxz.doom.event.EventTarget;
import com.rv4nxz.doom.event.events.EventPostMotionUpdate;
import com.rv4nxz.doom.event.events.EventPreMotionUpdate;
import com.rv4nxz.doom.module.Category;
import com.rv4nxz.doom.module.Module;
import com.rv4nxz.doom.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import com.rv4nxz.doom.util.AttackUtil;

import java.util.ArrayList;

import static com.rv4nxz.doom.util.AttackUtil.getRotations;

public class KillAura extends Module {
    private EntityLivingBase target;
    private long current, last;
    private float yaw, pitch;
    private boolean others;
    private boolean isBlocking;

    public KillAura() {
        super("KillAura", Keyboard.KEY_R, Category.COMBAT);
    }

        @Override
        public void setup() {
            ArrayList<String> options = new ArrayList<>();
            options.add("New");
            options.add("Old");
//            Doom.instance.settingsManager.rSetting(new Setting("Crack Size", this, 5, 0, 15, true));
            Doom.instance.settingsManager.rSetting(new Setting("Existed", this, 30, 0, 500, true));
            Doom.instance.settingsManager.rSetting(new Setting("Delay", this, 8, 0, 12, true));
            Doom.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, false));
            Doom.instance.settingsManager.rSetting(new Setting("Rotations", this, "New", options));
            Doom.instance.settingsManager.rSetting(new Setting("Speed", this, 1, 0, 10, true));
            Doom.instance.settingsManager.rSetting(new Setting("AutoBlock", this, false));
            Doom.instance.settingsManager.rSetting(new Setting("KeepSprint", this, false));
            Doom.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
            Doom.instance.settingsManager.rSetting(new Setting("Players", this, true));
            Doom.instance.settingsManager.rSetting(new Setting("Animals", this, false));
            Doom.instance.settingsManager.rSetting(new Setting("Monsters", this, false));
            Doom.instance.settingsManager.rSetting(new Setting("Villagers", this, false));
            Doom.instance.settingsManager.rSetting(new Setting("Armor Stand", this, false));
            Doom.instance.settingsManager.rSetting(new Setting("Teams", this, false));
        }

    @EventTarget
    public void onPre(EventPreMotionUpdate event) {
        target = getClosest(mc.playerController.getBlockReachDistance());
        if (target == null)
            return;
        updateTime();

//        yaw = mc.thePlayer.rotationYaw;
//        pitch = mc.thePlayer.rotationPitch;

        boolean block = target != null && Doom.instance.settingsManager.getSettingByName("AutoBlock").getValBoolean() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
        if (block && target.getDistanceToEntity(mc.thePlayer) < 8F) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
            isBlocking = true;
        }
        MovingObjectPosition rayTraceResult = mc.theWorld.rayTraceBlocks(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ), new Vec3(target.posX, target.posY + target.getEyeHeight(), target.posZ), false, true, false);

        if (rayTraceResult == null || rayTraceResult.entityHit == target) {
            if (Doom.instance.settingsManager.getSettingByName("Rotations").getValString().equalsIgnoreCase("New")) {
//                float targetYaw = AttackUtil.getRotations(target)[0];
//                float angleDiff = AttackUtil.getAngleDifference(yaw, targetYaw);
//                float speed = (float) Doom.instance.settingsManager.getSettingByName("Speed").getValDouble();
//                yaw += angleDiff * speed;
//                mc.thePlayer.rotationYaw = yaw;

                yaw = AttackUtil.getRotations(target)[0];
                pitch = AttackUtil.getRotations(target)[1];
                mc.thePlayer.rotationYawHead = yaw;
                mc.thePlayer.renderYawOffset = yaw;
                mc.thePlayer.rotationPitchHead = pitch;

                event.setYaw(yaw);
                event.setPitch(pitch);
            } else
                return;
            if (current - last > 1000 / Doom.instance.settingsManager.getSettingByName("Delay").getValDouble()) {
                if (isBlocking) {
                    mc.playerController.onStoppedUsingItem(mc.thePlayer);
                }
                if (!Doom.instance.settingsManager.getSettingByName("KeepSprint").getValBoolean()) {
                    mc.thePlayer.setSprinting(false);
                }
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(event.x, event.y, event.z, event.getYaw(), event.getPitch(), event.onGround()));
                attack(target);
                resetTime();
            }
        }
    }

    @EventTarget
    public void onPost(EventPostMotionUpdate event) {
        if (target == null)
            return;
        mc.thePlayer.rotationYaw = yaw;
        mc.thePlayer.rotationPitch = pitch;
    }

    private void attack(EntityLivingBase entity) {
        mc.playerController.attackEntity(mc.thePlayer, entity);
        mc.thePlayer.swingItem();
    }

    private void updateTime() {
        current = System.nanoTime() / 1000000L;
    }

    private void resetTime() {
        last = System.nanoTime() / 1000000L;
    }

    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) entity;
                if (AttackUtil.canAttack(player)) {
                    double currentDist = mc.thePlayer.getDistanceToEntity(player);
                    if (currentDist <= dist) {
                        dist = currentDist;
                        target = player;
                    }
                }
            }
        }
        return target;
    }
}