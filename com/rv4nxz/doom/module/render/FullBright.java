package com.rv4nxz.doom.module.render;

import com.rv4nxz.doom.event.EventTarget;
import com.rv4nxz.doom.event.events.EventUpdate;
import com.rv4nxz.doom.module.Category;
import com.rv4nxz.doom.module.Module;
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
