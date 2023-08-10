package dev.doom.module.movement;

import dev.doom.event.EventTarget;
import dev.doom.event.events.EventUpdate;
import dev.doom.module.Category;
import dev.doom.module.Module;
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
