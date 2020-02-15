package me.finz0.osiris.hud.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.Coords;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class CoordsComponent extends Panel {
    public CoordsComponent(double ix, double iy, ClickGUI parent) {
        super("Coordinates", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;
    }



    Coords mod = ((Coords) ModuleManager.getModuleByName("Coordinates"));

    Color c;
    boolean font;
    Color text;
    Color color;


    public void drawHud(){
        doStuff();

        Color c = new Color((int) mod.red.getValDouble(), (int) mod.green.getValDouble(), (int) mod.blue.getValDouble());
        if(mod.rainbow.getValBoolean()) c = Rainbow.getColor();
        if(font)
            OsirisMod.fontRenderer.drawStringWithShadow(getCoords(), (int) x, (int) y, c.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(getCoords(), (int) x, (int) y, c.getRGB());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        doStuff();
        double w = mc.fontRenderer.getStringWidth(getCoords()) + 2;
        c = new Color(50, 50, 50, 100);
        if(isHudComponentPinned) c = new Color(ColorUtil.getClickGUIColor().darker().getRed(), ColorUtil.getClickGUIColor().darker().getGreen(), ColorUtil.getClickGUIColor().darker().getBlue(), 100);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        this.width = w;
        this.height = FontUtil.getFontHeight() + 2;
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        FontUtil.drawStringWithShadow(title, x, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffffffff);

        if(extended) {
            double startY = y + height;
            Gui.drawRect((int) x, (int) startY, (int) x + (int) width, (int) startY + (int) height, c.getRGB());
            if (font) OsirisMod.fontRenderer.drawStringWithShadow(getCoords(), (float) x, (float) startY, text.getRGB());
            else mc.fontRenderer.drawStringWithShadow(getCoords(), (float) x, (float) startY, text.getRGB());
        }
    }

    private void doStuff() {
        color = new Color(mod.red.getValInt(), mod.green.getValInt(), mod.blue.getValInt());
        text = mod.rainbow.getValBoolean() ? Rainbow.getColor() : color;
        font = mod.customFont.getValBoolean();
    }

    private String getCoords(){
        String coords;
        if (mc.player.dimension == -1) {
            coords = mc.player.getPosition().getX() + ", " + mc.player.getPosition().getY() + ", " + mc.player.getPosition().getZ() +
                    ChatFormatting.GRAY + "[" + ChatFormatting.RESET + mc.player.getPosition().getX() * 8 + ", " + mc.player.getPosition().getZ() * 8 + ChatFormatting.GRAY + "]";
        } else {
            coords = mc.player.getPosition().getX() + ", " + mc.player.getPosition().getY() + ", " + mc.player.getPosition().getZ() +
                    ChatFormatting.GRAY + "[" + ChatFormatting.RESET + mc.player.getPosition().getX() / 8 + ", " + mc.player.getPosition().getZ() / 8 + ChatFormatting.GRAY + "]";
        }
        return coords;
    }
}
