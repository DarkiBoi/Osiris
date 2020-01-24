package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.text.DecimalFormat;

public class Bps extends Module {
    public Bps() {
        super("BPS", Category.GUI);
        setDrawn(false);
    }

    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;
    final DecimalFormat df = new DecimalFormat("0.0");

    public void setup(){
        red = new Setting("bpsRed", this, 255, 0, 255, true);
        green = new Setting("bpsGreen", this, 255, 0, 255, true);
        blue = new Setting("bpsBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("bpsX", this, 2, 0, 1000, true);
        y = new Setting("bpsY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        rainbow = new Setting("bpsRainbow", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow);
    }

    public void onRender(){
        final double deltaX = mc.player.posX - mc.player.prevPosX;
        final double deltaZ = mc.player.posZ - mc.player.prevPosZ;
        final float tickRate = (mc.timer.tickLength / 1000.0f);
        final String bps = df.format((MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ) / tickRate)) + " BPS";
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        if(rainbow.getValBoolean()){
            mc.fontRenderer.drawStringWithShadow(bps, (int) x.getValDouble(), (int) y.getValDouble(), Rainbow.getInt());
        } else {
            mc.fontRenderer.drawStringWithShadow(bps, (int) x.getValDouble(), (int) y.getValDouble(), c.getRGB());
        }
    }


}
