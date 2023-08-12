package dev.doom.module.combat;

import dev.doom.Doom;
import dev.doom.event.EventTarget;
import dev.doom.event.events.EventReceivePacket;
import dev.doom.event.events.EventUpdate;
import dev.doom.module.Category;
import dev.doom.module.Module;
import dev.doom.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class AntiBot extends Module {
    public AntiBot() {
        super("Antibot", "Doesn't attack bots.", Keyboard.KEY_NONE, Category.COMBAT);
    }
    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Advanced");
        options.add("Watchdog");
        Doom.instance.settingsManager.rSetting(new Setting("AntiBot Mode", this, "Advanced", options));
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        String mode = Doom.instance.settingsManager.getSettingByName("AntiBot Mode").getValString();
        if(mode.equalsIgnoreCase("Advanced") && event.getPacket() instanceof S0CPacketSpawnPlayer) {
            S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) event.getPacket();
            double posX = packet.getX() / 32D;
            double posY = packet.getY() / 32D;
            double posZ = packet.getZ() / 32D;

            double diffX = mc.thePlayer.posX - posX;
            double diffY = mc.thePlayer.posY - posY;
            double diffZ = mc.thePlayer.posZ - posZ;

            double dist = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

            if(dist <= 17D && posX != mc.thePlayer.posX && posY != mc.thePlayer.posY && posZ != mc.thePlayer.posZ)
                event.setCancelled(true);
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String mode = Doom.instance.settingsManager.getSettingByName("AntiBot Mode").getValString();
        String capitalizedMode = mode.substring(0, 1).toUpperCase() + mode.substring(1);
        this.setDisplayName("AntiBot §4" + capitalizedMode);

        if(mode.equalsIgnoreCase("Watchdog"))
            for(Object entity : mc.theWorld.loadedEntityList)
                if(((Entity) entity).isInvisible() && entity != mc.thePlayer)
                    mc.theWorld.removeEntity((Entity) entity);
    }
}
