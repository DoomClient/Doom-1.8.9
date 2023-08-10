package com.rv4nxz.doom.module.movement;

import com.rv4nxz.doom.event.EventTarget;
import com.rv4nxz.doom.event.events.EventUpdate;
import com.rv4nxz.doom.module.Category;
import com.rv4nxz.doom.module.Module;
import org.lwjgl.input.Keyboard;

public class Step extends Module {
    public Step() {
        super("Step", Keyboard.KEY_N, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.thePlayer.stepHeight = 1.5F;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.thePlayer.stepHeight = .5F;
    }
}
