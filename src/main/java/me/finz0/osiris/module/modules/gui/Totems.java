package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import java.util.ArrayList;

public class Totems extends Module {
    public Totems() {
        super("Totems", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;
    public Setting mode;

    public void setup(){
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Short");
        modes.add("Full");
        red = new Setting("TotemsRed", this, 255, 0, 255, true);
        green = new Setting("TotemsGreen", this, 255, 0, 255, true);
        blue = new Setting("TotemsBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("totRainbow", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("totCFont", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(mode = new Setting("totText", this, "Short", modes));
    }

    public void onEnable(){
        disable();
    }
}
