package com.rv4nxz.doom.module.combat;

import com.rv4nxz.doom.event.EventTarget;
import com.rv4nxz.doom.event.events.EventReceivePacket;
import com.rv4nxz.doom.module.Category;
import com.rv4nxz.doom.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
    public Minecraft mc = Minecraft.getMinecraft();

    public Velocity() {
        super("Velocity", Keyboard.KEY_X, Category.COMBAT);
    }

    @EventTarget
    public void onSendPacket(EventReceivePacket event) {
        Packet p = event.getPacket();
        if (p instanceof S12PacketEntityVelocity || p instanceof S27PacketExplosion){
            event.setCancelled(true);
        }
    }
}
