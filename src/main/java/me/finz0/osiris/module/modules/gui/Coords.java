package me.finz0.osiris.module.modules.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;

import java.awt.*;

public class Coords extends Module {
    public Coords() {
        super("Coordinates", Category.GUI);
        setDrawn(false);
    }

    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    String coords;
    Setting rainbow;

    public void setup(){
        red = new Setting("CoordsRed", this, 255, 0, 255, true);
        green = new Setting("CoordsGreen", this, 255, 0, 255, true);
        blue = new Setting("CoordsBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("CoordsX", this, 2, 0, 1000, true);
        y = new Setting("CoordsY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("coordsRainbow", this, false));
    }

    public void onRender() {
        if (mc.player.dimension == -1) {
            coords = mc.player.getPosition().getX() + ", " + mc.player.getPosition().getY() + ", " + mc.player.getPosition().getZ() +
                    ChatFormatting.GRAY + "[" + ChatFormatting.RESET + mc.player.getPosition().getX() * 8 + ", " + mc.player.getPosition().getZ() * 8 + ChatFormatting.GRAY + "]";
        } else {
            coords = mc.player.getPosition().getX() + ", " + mc.player.getPosition().getY() + ", " + mc.player.getPosition().getZ() +
                    ChatFormatting.GRAY + "[" + ChatFormatting.RESET + mc.player.getPosition().getX() / 8 + ", " + mc.player.getPosition().getZ() / 8 + ChatFormatting.GRAY + "]";
        }
        Color c = new Color((int) red.getValDouble(), (int) green.getValDouble(), (int) blue.getValDouble());
        if(rainbow.getValBoolean())
            mc.fontRenderer.drawStringWithShadow(coords, (int)x.getValDouble(), (int)y.getValDouble(), Rainbow.getInt());
        else
            mc.fontRenderer.drawStringWithShadow(coords, (int) x.getValDouble(), (int) y.getValDouble(), c.getRGB());
    }
}
