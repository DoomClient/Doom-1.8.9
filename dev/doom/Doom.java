package dev.doom;

import dev.doom.clickgui.ClickGUI;
import dev.doom.event.EventManager;
import dev.doom.event.EventTarget;
import dev.doom.event.events.EventKey;
import dev.doom.module.Module;
import dev.doom.module.ModuleManager;
import dev.doom.settings.SettingsManager;
import dev.doom.ui.ingame.HUD;
import org.lwjgl.opengl.Display;

public class Doom {
    public String name = "Doom", version = "1.0b3", author = "rV4nxZ";

    public static Doom instance = new Doom();

    public SettingsManager settingsManager;
    public EventManager eventManager;
    public ModuleManager moduleManager;
    public HUD hud;
    public ClickGUI clickGUI;

    public void startClient() {
        settingsManager = new SettingsManager();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        hud = new HUD();
        clickGUI = new ClickGUI();

        System.out.println("[" + name + "] Starting client by [" + author + "]...");
        Display.setTitle(name + " " + version);

        eventManager.register(this);
    }

    public void stopClient() {
        eventManager.unregister(this);
    }

    @EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(Module::toggle);
    }
}
