package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;

import java.awt.*;
import java.util.ArrayList;


public class WelcomerGui extends Module {
    public WelcomerGui() {
        super("Welcome", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting x;
    public Setting y;
    public Setting rainbow;
    public Setting message;
    ArrayList<String> messages;
    public String text = "";

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
        x = new Setting("welX", this, 2, 0, 1000, true);
        y = new Setting("welY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        rainbow = new Setting("welRainbow", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow);
        OsirisMod.getInstance().settingsManager.rSetting(message = new Setting("welMessage", this, "Welcome1", messages));
    }

    public void onRender(){
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        if(message.getValString().equalsIgnoreCase("Welcome1"))
            text = "Welcome, " + mc.player.getName();
        if(message.getValString().equalsIgnoreCase("Welcome2"))
            text = "Welcome, " + mc.player.getName() + " ^_^";
        if(message.getValString().equalsIgnoreCase("Hello1"))
            text = "Hello " + mc.player.getName();
        if(message.getValString().equalsIgnoreCase("Hello2"))
            text = "Hello " + mc.player.getName() + " ^_^";
        if(rainbow.getValBoolean()){
            //mc.fontRenderer.drawStringWithShadow(text, (int) x.getValDouble(), (int) y.getValDouble(), Rainbow.getInt());
        } else {
            //mc.fontRenderer.drawStringWithShadow(text, (int) x.getValDouble(), (int) y.getValDouble(), c.getRGB());
        }
    }
}
