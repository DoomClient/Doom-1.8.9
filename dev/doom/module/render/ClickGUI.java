package dev.doom.module.render;

import dev.doom.Doom;
import dev.doom.module.Category;
import dev.doom.module.Module;
import org.lwjgl.input.Keyboard;


public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
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
