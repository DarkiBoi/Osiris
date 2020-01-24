package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;

import java.awt.*;

public class Watermark extends Module {
    public static Watermark INSTANCE;
    public Watermark() {
        super("Watermark", Category.GUI);
        setDrawn(false);
        INSTANCE = this;
    }

    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;

    public void setup(){
        red = new Setting("MarkRed", this, 255, 0, 255, true);
        green = new Setting("MarkGreen", this, 255, 0, 255, true);
        blue = new Setting("MarkBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("MarkX", this, 2, 0, 1000, true);
        y = new Setting("MarkY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        rainbow = new Setting("MarkRainbow", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow);
    }

    public void onRender(){
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        if(rainbow.getValBoolean()){
            mc.fontRenderer.drawStringWithShadow(OsirisMod.MODNAME + " " + OsirisMod.MODVER, (int) x.getValDouble(), (int) y.getValDouble(), Rainbow.getInt());
        } else {
            mc.fontRenderer.drawStringWithShadow(OsirisMod.MODNAME + " " + OsirisMod.MODVER, (int) x.getValDouble(), (int) y.getValDouble(), c.getRGB());
        }
    }
}
