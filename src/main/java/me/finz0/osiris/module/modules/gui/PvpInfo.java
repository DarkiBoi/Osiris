package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.util.Rainbow;

import java.awt.*;

public class PvpInfo extends Module {
    public PvpInfo() {
        super("PvpInfo", Category.GUI);
        setDrawn(false);
    }

    Setting x;
    Setting y;
    Setting offRainbow;
    Setting offR;
    Setting offG;
    Setting offB;
    Setting onRainbow;
    Setting onR;
    Setting onG;
    Setting onB;

    public void setup(){
        x = new Setting("piX", this, 2, 0, 1000, true);
        y = new Setting("piY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(offRainbow = new Setting("piOffRainbow", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(offR = new Setting("piOffR", this, 255, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(offG = new Setting("piOffG", this, 0, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(offB = new Setting("piOffB", this, 0, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(onRainbow = new Setting("piOnRainbow", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(onR = new Setting("piOnR", this, 0, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(onG = new Setting("piOnG", this, 255, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(onB = new Setting("piOnB", this, 0, 0, 255, true));
    }

    public void onRender(){
        Color off = new Color(offR.getValInt(), offG.getValInt(), offB.getValInt());
        Color on = new Color(onR.getValInt(), onG.getValInt(), onB.getValInt());
        if(offRainbow.getValBoolean()) off = Rainbow.getColor();
        if(onRainbow.getValBoolean()) on = Rainbow.getColor();
        if(ModuleManager.isModuleEnabled("AutoCrystal")){
            mc.fontRenderer.drawStringWithShadow("CA ON", (int)x.getValDouble(), (int)y.getValDouble(), on.getRGB());
        } else{
            mc.fontRenderer.drawStringWithShadow("CA OFF", (int)x.getValDouble(), (int)y.getValDouble(), off.getRGB());
        }
        if(ModuleManager.isModuleEnabled("KillAura")){
            mc.fontRenderer.drawStringWithShadow("KA ON", (int)x.getValDouble(), (int)y.getValDouble() + 10, on.getRGB());
        } else{
            mc.fontRenderer.drawStringWithShadow("KA OFF", (int)x.getValDouble(), (int)y.getValDouble() + 10, off.getRGB());
        }
        if(ModuleManager.isModuleEnabled("Surround")){
            mc.fontRenderer.drawStringWithShadow("SU ON", (int)x.getValDouble(), (int)y.getValDouble() + 20, on.getRGB());
        } else{
            mc.fontRenderer.drawStringWithShadow("SU OFF", (int)x.getValDouble(), (int)y.getValDouble() + 20, off.getRGB());
        }
        if(ModuleManager.isModuleEnabled("AutoTrap")){
            mc.fontRenderer.drawStringWithShadow("AT ON", (int)x.getValDouble(), (int)y.getValDouble() + 30, on.getRGB());
        } else{
            mc.fontRenderer.drawStringWithShadow("AT OFF", (int)x.getValDouble(), (int)y.getValDouble() + 30, off.getRGB());
        }
        if(ModuleManager.isModuleEnabled("HoleFill")){
            mc.fontRenderer.drawStringWithShadow("HF ON", (int)x.getValDouble(), (int)y.getValDouble() + 40, on.getRGB());
        } else{
            mc.fontRenderer.drawStringWithShadow("HF OFF", (int)x.getValDouble(), (int)y.getValDouble() + 40, off.getRGB());
        }
    }
}
