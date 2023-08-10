package dev.doom.module;

import dev.doom.module.combat.AimBot;
import dev.doom.module.combat.AntiBot;
import dev.doom.module.combat.KillAura;
import dev.doom.module.combat.Velocity;
import dev.doom.module.movement.Scaffold;
import dev.doom.module.movement.Sprint;
import dev.doom.module.movement.Step;
import dev.doom.module.player.NoFall;
import dev.doom.module.render.ClickGUI;
import dev.doom.module.render.FullBright;
import com.rv4nxz.doom.module.combat.*;
import com.rv4nxz.doom.module.movement.*;
import com.rv4nxz.doom.module.player.*;
import com.rv4nxz.doom.module.render.*;

import java.util.ArrayList;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        // COMBAT
        modules.add(new AimBot());
        modules.add(new AntiBot());
        modules.add(new KillAura());
        modules.add(new Velocity());

        // MOVEMENT
        modules.add(new Sprint());
        modules.add(new Step());
        modules.add(new Scaffold());

        // RENDER
        modules.add(new FullBright());
        modules.add(new ClickGUI());

        // PLAYER
        modules.add(new NoFall());

        // MISC

        // NONE
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}