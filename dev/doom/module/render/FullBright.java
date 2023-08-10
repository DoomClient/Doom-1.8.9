package dev.doom.module.render;

import dev.doom.event.EventTarget;
import dev.doom.event.events.EventUpdate;
import dev.doom.module.Category;
import dev.doom.module.Module;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {
    private float oldBrightness;

    public FullBright() {
        super("FullBright", Keyboard.KEY_NONE, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        oldBrightness = mc.gameSettings.gammaSetting;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.gameSettings.gammaSetting = 10F;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.gameSettings.gammaSetting = oldBrightness;
    }

}
