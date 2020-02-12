package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;

import java.awt.*;
import java.text.DecimalFormat;

import me.finz0.osiris.util.TpsUtils;

public class Tps extends Module {
    public static Tps INSTANCE;
    public Tps() {
        super("TPS", Category.GUI);
        setDrawn(false);
        INSTANCE = this;
    }



    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;
    DecimalFormat decimalFormat = new DecimalFormat("##.#");

    public void setup(){
        red = new Setting("TpsRed", this, 255, 0, 255, true);
        green = new Setting("TpsGreen", this, 255, 0, 255, true);
        blue = new Setting("TpsBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("TpsX", this, 2, 0, 1000, true);
        y = new Setting("TpsY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("tpsRainbow", this, false));
    }

    public void onRender(){
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        if(!rainbow.getValBoolean())
            mc.fontRenderer.drawStringWithShadow(decimalFormat.format(TpsUtils.getTickRate()) + " TPS", (int)x.getValDouble(), (int)y.getValDouble(), c.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(decimalFormat.format(TpsUtils.getTickRate()) + " TPS", (int)x.getValDouble(), (int)y.getValDouble(), Rainbow.getInt());
    }

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }

    //Credit 086




}
