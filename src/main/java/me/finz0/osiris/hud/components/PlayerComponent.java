package me.finz0.osiris.hud.components;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class PlayerComponent extends Panel {
    public PlayerComponent(double ix, double iy, ClickGUI parent) {
        super("Player", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;
    }




    Color c;
    float mouseXX;
    float mouseYY;


    public void drawHud(){
        mouseXX = mc.getRenderManager().playerViewY / 5 * -1;
        mouseYY = mc.getRenderManager().playerViewX * -1;
        //TODO: fix
        if(mouseXX < -10) mouseXX = -10;
        if(mouseXX > 10) mouseXX = 10;
        GuiInventory.drawEntityOnScreen((int)x + 21, (int)y + 60, 30, mouseXX, mouseYY, mc.player);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        c = new Color(50, 50, 50, 100);
        if(isHudComponentPinned) c = new Color(ColorUtil.getClickGUIColor().darker().getRed(), ColorUtil.getClickGUIColor().darker().getGreen(), ColorUtil.getClickGUIColor().darker().getBlue(), 100);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        width = 43;
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        FontUtil.drawStringWithShadow(title, x, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffffffff);

        if(extended) {
            double startY = y + height;
            Gui.drawRect((int) x, (int) startY, (int) x + (int) width, (int) startY + (int) height + 45, c.getRGB());
            GlStateManager.color(1, 1, 1, 1);
            GuiInventory.drawEntityOnScreen((int)x + 21, (int)y + 60, 30, (float)(x + 51) - mouseX, (float)(y + 75 - 50) - mouseY, mc.player);
        }
    }
}
