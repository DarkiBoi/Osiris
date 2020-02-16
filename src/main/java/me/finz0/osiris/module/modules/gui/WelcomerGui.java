package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

import java.util.ArrayList;


public class WelcomerGui extends Module {
    public WelcomerGui() {
        super("Welcome", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting message;
    public Setting customFont;
    ArrayList<String> messages;

    public void setup(){
        messages = new ArrayList<>();
        messages.add("Welcome1");
        messages.add("Welcome2");
        messages.add("Hello1");
        messages.add("Hello2");
        red = new Setting("welRed", this, 255, 0, 255, true);
        green = new Setting("welGreen", this, 255, 0, 255, true);
        blue = new Setting("welBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        rainbow = new Setting("welRainbow", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow);
        OsirisMod.getInstance().settingsManager.rSetting(message = new Setting("welMessage", this, "Welcome1", messages));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("welCFont", this, false));
    }

    public void onEnable(){
        disable();
    }
}
