package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;

import java.awt.*;

public class Ping extends Module {
    public Ping() {
        super("Ping", Category.GUI);
        setDrawn(false);
    }

    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;

    public void setup(){
        red = new Setting("PingRed", this, 255, 0, 255, true);
        green = new Setting("PingGreen", this, 255, 0, 255, true);
        blue = new Setting("PingBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("PingX", this, 2, 0, 1000, true);
        y = new Setting("PingY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("pingRainbow", this, false));
    }

    public void onRender(){
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        if(!rainbow.getValBoolean())
            mc.fontRenderer.drawStringWithShadow(getPing() + "ms", (int)x.getValDouble(), (int)y.getValDouble(), c.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(getPing() + "ms", (int)x.getValDouble(), (int)y.getValDouble(), Rainbow.getInt());
    }

    public int getPing(){
        int p = -1;
        if(mc.player == null || mc.getConnection() == null || mc.getConnection().getPlayerInfo(mc.player.getName()) == null || mc.player.getName() == null){
            p = -1;
        } else {
            p = mc.getConnection().getPlayerInfo(mc.player.getName()).getResponseTime();
        }
        return p;
    }
}
