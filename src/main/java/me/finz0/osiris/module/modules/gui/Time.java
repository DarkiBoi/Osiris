package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time extends Module {
    public Time() {
        super("SystemTime", Category.GUI);
        setDrawn(false);
    }

    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;

    public void setup(){
        red = new Setting("TimeRed", this, 255, 0, 255, true);
        green = new Setting("TimeGreen", this, 255, 0, 255, true);
        blue = new Setting("TimeBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("TimeX", this, 2, 0, 1000, true);
        y = new Setting("TimeY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("timeRainbow", this, false));
    }

    public void onRender(){
        String date = new SimpleDateFormat("k:mm").format(new Date());
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        if(!rainbow.getValBoolean())
            mc.fontRenderer.drawStringWithShadow(date + "", (int)x.getValDouble(), (int)y.getValDouble(), c.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(date + "", (int)x.getValDouble(), (int)y.getValDouble(), Rainbow.getInt());
    }
}
