package dev.doom.clickgui.elements;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import dev.doom.Doom;
import dev.doom.clickgui.elements.menu.ElementComboBox;
import dev.doom.clickgui.elements.menu.ElementSlider;
import dev.doom.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import org.lwjgl.input.Keyboard;

import dev.doom.clickgui.Panel;
import dev.doom.clickgui.elements.menu.ElementCheckBox;
import dev.doom.clickgui.util.ColorUtil;
import dev.doom.clickgui.util.FontUtil;
import dev.doom.settings.Setting;

public class ModuleButton {
	public Module mod;
	public ArrayList<Element> menuelements;
	public Panel parent;
	public double x;
	public double y;
	public double width;
	public double height;
	public boolean extended = false;
	public boolean listening = false;

	public ModuleButton(Module imod, Panel pl) {
		mod = imod;
		height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
		parent = pl;
		menuelements = new ArrayList<>();

		if (Doom.instance.settingsManager.getSettingsByMod(imod) != null)
			for (Setting s : Doom.instance.settingsManager.getSettingsByMod(imod)) {
				if (s.isCheck()) {
					menuelements.add(new ElementCheckBox(this, s));
				} else if (s.isSlider()) {
					menuelements.add(new ElementSlider(this, s));
				} else if (s.isCombo()) {
					menuelements.add(new ElementComboBox(this, s));
				}
			}

	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();

		int textcolor = 0xffafafaf;
		if (mod.isToggled()) {
			Gui.drawRect(x - 2, y, x + width + 2, y + height + 1, color);
			textcolor = 0xffefefef;
		}

		if (isHovered(mouseX, mouseY)) {
			Gui.drawRect(x - 2, y, x + width + 2, y + height + 1, 0x55111111);
		}

		FontUtil.drawTotalCenteredStringWithShadow(mod.getName(), x + width / 2, y + 1 + height / 2, textcolor);
	}

	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!isHovered(mouseX, mouseY))
			return false;
		if (mouseButton == 0) {
			mod.toggle();
			
//			if(Doom.instance.settingsManager.getSettingByName("Sound").getValBoolean())
//			Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.5f, 0.5f);
		} else if (mouseButton == 1) {
			if (menuelements != null && menuelements.size() > 0) {
				boolean b = !this.extended;
				Doom.instance.clickGUI.closeAllSettings();
				this.extended = b;
				
//				if(Doom.instance.settingsManager.getSettingByName("Sound").getValBoolean())
//				if(extended)Minecraft.getMinecraft().thePlayer.playSound("tile.piston.out", 1f, 1f);else Minecraft.getMinecraft().thePlayer.playSound("tile.piston.in", 1f, 1f);
			}
		} else if (mouseButton == 2) {
			listening = true;
		}
		return true;
	}

	public boolean keyTyped(char typedChar, int keyCode) throws IOException {
		if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				//Doom.sendChatMessage("Bound '" + mod.getName() + "'" + " to '" + Keyboard.getKeyName(keyCode) + "'");
				mod.setKey(keyCode);
			} else {
				//Doom.sendChatMessage("Unbound '" + mod.getName() + "'");
				mod.setKey(Keyboard.KEY_NONE);
			}
			listening = false;
			return true;
		}
		return false;
	}

	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

}
