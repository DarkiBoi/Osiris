package me.finz0.osiris.module.modules.gui;
import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

public class ModList extends Module {
    public ModList() {
        super("ModList", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting sortUp;
    public Setting right;
    public Setting rainbow;
    public Setting customFont;

    public void setup(){
        red = new Setting("ModListRed", this, 255, 0, 255, true);
        green = new Setting("ModListGreen", this, 255, 0, 255, true);
        blue = new Setting("ModListBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        sortUp = new Setting("mlSortUp", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(sortUp);
        right = new Setting("mlAlignRight", this, false);
        OsirisMod.getInstance().settingsManager.rSetting(right);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("modlistRainbow", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("modlistCFont", this, false));
    }
}
