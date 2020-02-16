package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

public class Fps extends Module {
    public Fps() {
        super("FPS", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;

    public void setup(){
        red = new Setting("FpsRed", this, 255, 0, 255, true);
        green = new Setting("FpsGreen", this, 255, 0, 255, true);
        blue = new Setting("FpsBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("fpsRainbow", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("fpsCFont", this, false));
    }

    public void onEnable(){
        disable();
    }
}
