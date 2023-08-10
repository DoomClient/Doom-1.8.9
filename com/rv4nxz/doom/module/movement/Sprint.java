package com.rv4nxz.doom.module.movement;

import com.rv4nxz.doom.event.EventTarget;
import com.rv4nxz.doom.event.events.EventUpdate;
import com.rv4nxz.doom.module.Category;
import com.rv4nxz.doom.module.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Keyboard.KEY_I, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0)
            mc.thePlayer.setSprinting(true);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.thePlayer.setSprinting(false);
    }
}
