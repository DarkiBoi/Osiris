package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class Fps extends Module {
    public Fps() {
        super("FPS", Category.GUI);
        setDrawn(false);
    }

    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;

    public void setup(){
        red = new Setting("FpsRed", this, 255, 0, 255, true);
        green = new Setting("FpsGreen", this, 255, 0, 255, true);
        blue = new Setting("FpsBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("FpsX", this, 2, 0, 1000, true);
        y = new Setting("FpsY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("fpsRainbow", this, false));
    }

    public void onRender(){
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        if(!rainbow.getValBoolean())
            mc.fontRenderer.drawStringWithShadow(Minecraft.getDebugFPS() + " FPS", (int)x.getValDouble(), (int)y.getValDouble(), c.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(Minecraft.getDebugFPS() + " FPS", (int)x.getValDouble(), (int)y.getValDouble(), Rainbow.getInt());
    }
}
