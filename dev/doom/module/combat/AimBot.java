package dev.doom.module.combat;

import dev.doom.Doom;
import dev.doom.event.EventTarget;
import dev.doom.event.events.EventPostMotionUpdate;
import dev.doom.module.Category;
import dev.doom.module.Module;
import dev.doom.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class AimBot extends Module {
    private EntityLivingBase target;

    public AimBot() {
        super("AimBot", "Aim automatically to the enemy.", 0, Category.COMBAT);
    }

    @Override
    public void setup() {
        Doom.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
    }

    @EventTarget
    public void onPost(EventPostMotionUpdate event) {
        if(target == null)
            return;

        mc.thePlayer.rotationYaw = getRotations(target)[0];
        mc.thePlayer.rotationPitch = getRotations(target)[1];
    }

    public float[] getRotations (Entity e) {
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

        return new float[] {yaw, pitch};
    }
}
