package dev.doom.module;

import dev.doom.Doom;
import net.minecraft.client.Minecraft;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();

    private String name, description, displayName;
    private int key;
    private Category category;
    private boolean toggled;

    public Module(String name, String description, int key, Category category) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.category = category;
        toggled = false;

        setup();
    }

    public void onEnable() {
        Doom.instance.eventManager.register(this);
    }
    public void onDisable() {
        Doom.instance.eventManager.unregister(this);
    }
    public void onToggle() {}
    public void toggle() {
        toggled = !toggled;
        onToggle();
        if(toggled)
            onEnable();
        else
            onDisable();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public boolean isToggled( ) {
        return toggled;
    }
    public String getDisplayName() {
        if (displayName == null) {
            return name;
        } else {
            return Character.toUpperCase(displayName.charAt(0)) + displayName.substring(1);
        }
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setup() {}
}