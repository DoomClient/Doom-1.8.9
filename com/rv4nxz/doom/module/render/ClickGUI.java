package com.rv4nxz.doom.module.render;

import com.rv4nxz.doom.Doom;
import com.rv4nxz.doom.module.Category;
import com.rv4nxz.doom.module.Module;
import org.lwjgl.input.Keyboard;


public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    @Override
    public void setup() {
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.displayGuiScreen(Doom.instance.clickGUI);
        toggle();
    }
}
