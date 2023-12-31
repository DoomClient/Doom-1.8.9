package dev.doom.ui.ingame;

import dev.doom.Doom;
import dev.doom.module.Module;
//import dev.doom.util.TTFFontRendererUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

public class HUD {
    public Minecraft mc = Minecraft.getMinecraft();

    public static class ModuleComparator implements Comparator<Module> {
        @Override
        public int compare(Module o1, Module o2) {
            if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(o1.getDisplayName()) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(o2.getDisplayName())) {
                return -1;
            }
            if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(o1.getDisplayName()) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(o2.getDisplayName())) {
                return 1;
            }
            return 0;
        }
    }

    public void draw() throws IOException, FontFormatException {
        ScaledResolution sr = new ScaledResolution(mc);
        //TTFFontRendererUtil fr = new TTFFontRendererUtil();
        FontRenderer fr = mc.fontRendererObj;

        Collections.sort(Doom.instance.moduleManager.getModules(), new ModuleComparator());

        GlStateManager.translate(4, 4, 0);
        GlStateManager.scale(2, 2, 0);
        GlStateManager.translate(-4, -4, 0);
        fr.drawString(Doom.instance.name, 4, 4, 0xffff0000);
        GlStateManager.translate(4, 4, 0);
        GlStateManager.scale(.5, .5, 0);
        GlStateManager.translate(-4, -4, 0);

        int count = 0;
        for(Module m : Doom.instance.moduleManager.getModules()) {
            if(!m.isToggled())
                continue;

            double offset = count*(fr.FONT_HEIGHT + 6);

            // Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 10, offset, sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 8, 6 + fr.FONT_HEIGHT + offset, 0xffff0000);
            Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 8, offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, 0x50000000);
            fr.drawString(m.getDisplayName(), sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 4, (float) (4 + offset), -1);

            count++;
        }
    }
}