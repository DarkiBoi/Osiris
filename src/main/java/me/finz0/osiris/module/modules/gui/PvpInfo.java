package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

public class PvpInfo extends Module {
    public PvpInfo() {
        super("PvpInfo", Category.GUI);
        setDrawn(false);
    }

    public Setting offRainbow;
    public Setting offR;
    public Setting offG;
    public Setting offB;
    public Setting onRainbow;
    public Setting onR;
    public Setting onG;
    public Setting onB;
    public Setting customFont;

    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(offRainbow = new Setting("piOffRainbow", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(offR = new Setting("piOffR", this, 255, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(offG = new Setting("piOffG", this, 0, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(offB = new Setting("piOffB", this, 0, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(onRainbow = new Setting("piOnRainbow", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(onR = new Setting("piOnR", this, 0, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(onG = new Setting("piOnG", this, 255, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(onB = new Setting("piOnB", this, 0, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("piCFont", this, false));
    }

    public void onEnable(){
        disable();
    }
}
