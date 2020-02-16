package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

public class Bps extends Module {
    public Bps() {
        super("BPS", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;

    public void setup(){
        red = new Setting("bpsRed", this, 255, 0, 255, true);
        green = new Setting("bpsGreen", this, 255, 0, 255, true);
        blue = new Setting("bpsBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        rainbow = new Setting("bpsRainbow", this, false);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow);
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("bpsCFont", this, false));
    }

    public void onEnable(){
        disable();
    }


}
