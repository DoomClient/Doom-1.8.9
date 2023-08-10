package com.rv4nxz.doom.module.player;

import com.rv4nxz.doom.event.EventTarget;
import com.rv4nxz.doom.event.events.EventUpdate;
import com.rv4nxz.doom.module.Category;
import com.rv4nxz.doom.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_B, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.fallDistance > 2F)
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
    }
}
