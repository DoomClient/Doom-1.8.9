package dev.doom.clickgui.elements.menu;

import java.awt.Color;

import dev.doom.clickgui.elements.Element;
import dev.doom.clickgui.elements.ModuleButton;
import net.minecraft.client.gui.Gui;
import dev.doom.clickgui.util.ColorUtil;
import dev.doom.clickgui.util.FontUtil;
import dev.doom.settings.Setting;

public class ElementCheckBox extends Element {
	public ElementCheckBox(ModuleButton iparent, Setting iset) {
		parent = iparent;
		set = iset;
		super.setup();
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 200).getRGB();

		Gui.drawRect(x, y, x + width, y + height, 0xff1a1a1a);

		FontUtil.drawString(setstrg, x + width - FontUtil.getStringWidth(setstrg), y + FontUtil.getFontHeight() / 2 - 0.5, 0xffffffff);
		Gui.drawRect(x + 1, y + 2, x + 12, y + 13, set.getValBoolean() ? color : 0xff000000);
		if (isCheckHovered(mouseX, mouseY))
			Gui.drawRect(x + 1, y + 2, x + 12, y + 13, 0x55111111);
	}

	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && isCheckHovered(mouseX, mouseY)) {
			set.setValBoolean(!set.getValBoolean());
			return true;
		}
		
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public boolean isCheckHovered(int mouseX, int mouseY) {
		return mouseX >= x + 1 && mouseX <= x + 12 && mouseY >= y + 2 && mouseY <= y + 13;
	}
}
