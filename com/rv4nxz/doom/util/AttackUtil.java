package com.rv4nxz.doom.util;

import com.rv4nxz.doom.Doom;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class AttackUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean canAttack(EntityLivingBase player) {
        if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager || player instanceof EntityArmorStand) {
            if (player instanceof EntityPlayer && !Doom.instance.settingsManager.getSettingByName("Players").getValBoolean())
                return false;
            if (player instanceof EntityAnimal && !Doom.instance.settingsManager.getSettingByName("Animals").getValBoolean())
                return false;
            if (player instanceof EntityMob && !Doom.instance.settingsManager.getSettingByName("Monsters").getValBoolean())
                return false;
            if (player instanceof EntityVillager && !Doom.instance.settingsManager.getSettingByName("Villagers").getValBoolean())
                return false;
            if (player instanceof EntityArmorStand && !Doom.instance.settingsManager.getSettingByName("Armor Stand").getValBoolean())
                return false;
        }
        if (player.isOnSameTeam(mc.thePlayer) && Doom.instance.settingsManager.getSettingByName("Teams").getValBoolean())
            return false;
        if (player.isInvisible() && !Doom.instance.settingsManager.getSettingByName("Invisibles").getValBoolean())
            return false;
        if (!isInFOV(player, Doom.instance.settingsManager.getSettingByName("FOV").getValDouble()))
            return false;
        return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= mc.playerController.getBlockReachDistance() && mc.thePlayer.getDistanceToEntity(player) < 3 || mc.playerController.getBlockReachDistance() < 3 && player.ticksExisted > Doom.instance.settingsManager.getSettingByName("Existed").getValDouble();
    }

    public static boolean isInFOV(EntityLivingBase entity, double angle) {
        angle *= .5D;
        double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity)[0]);
        return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
    }

    public static float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360F;
        return f > 180F ? 360F - f : f;
    }

    public static float[] getRotations(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees (-Math.atan (deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees (Math.atan (deltaY / distance));

        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90+ Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        return new float[]{yaw, pitch};
    }
}
