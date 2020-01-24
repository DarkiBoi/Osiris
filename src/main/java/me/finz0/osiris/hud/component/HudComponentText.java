package me.finz0.osiris.hud.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class HudComponentText {
    public String title;
    public String text;
    public double x;
    public double y;
    private double x2;
    private double y2;
    public double width;
    public double height;
    public boolean dragging;
    public boolean visible;
    public boolean pinned;
    public int textRed;
    public int textGreen;
    public int textBlue;

    /*
     * Konstrukor
     */
    public HudComponentText(String iTitle, double ix, double iy, double iWidth, double iHeight) {
        this.title = iTitle;
        this.text = "";
        this.x = ix;
        this.y = iy;
        this.width = iWidth;
        this.height = iHeight;
        this.dragging = false;
        this.visible = true;
        this.pinned = false;
        textRed = 255;
        textGreen = 255;
        textBlue = 255;
        setup();
    }

    /*
     * Wird in ClickGUI berschrieben, sodass auch ModuleButtons hinzugefgt werden knnen :3
     */
    public void setup() {}

    /*
     * Rendern des Elements.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!this.visible)
            return;

        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        Color c = new Color(0, 0, 0, 100);
        Color tc = new Color(textRed, textGreen, textBlue);
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, (float)x, (float)y, tc.getRGB());
    }

    /*
     * Zum Bewegen und Extenden des Panels
     * usw.
     */
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible) {
            return false;
        }
        if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
            x2 = this.x - mouseX;
            y2 = this.y - mouseY;
            dragging = true;
            return true;
        }
        return false;
    }

    /*
     * Damit das Panel auch losgelassen werden kann
     */
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (!this.visible) {
            return;
        }
        if (state == 0) {
            this.dragging = false;
        }
    }

    /*
     * HoverCheck
     */
    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void setText(String t){
        text = t;
    }

    public void setTextRed(int r){
        textRed = r;
    }
    public void setTextGreen(int g){
        textGreen = g;
    }
    public void setTextBlue(int b){
        textBlue = b;
    }
}
