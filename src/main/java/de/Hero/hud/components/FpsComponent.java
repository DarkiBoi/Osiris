package de.Hero.hud.components;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class FpsComponent extends Panel {
    public FpsComponent(double ix, double iy, ClickGUI parent) {
        super("FPS", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;

    }



    Color c;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        String fps = Minecraft.getDebugFPS() + " FPS";
        this.hudComponentText = fps;
        double w = mc.fontRenderer.getStringWidth(fps) + 2;
        c = new Color(50, 50, 50, 150);
        if(isHudComponentPinned) c = new Color(50, 150, 50, 50);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        this.width = w;
        this.height = FontUtil.getFontHeight() + 2;
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        FontUtil.drawStringWithShadow(title, x, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffffffff);

        double startY = y + height;
        Gui.drawRect((int)x, (int)startY, (int)x + (int)width, (int)startY + (int)height, c.getRGB());
        mc.fontRenderer.drawStringWithShadow(fps, (float)x + 2, (float)startY + 2, 0xffffffff);
    }
}
