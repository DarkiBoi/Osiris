package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;

import java.awt.*;

public class PvpInfo extends Module {
    public PvpInfo() {
        super("PvpInfo", Category.GUI);
        setDrawn(false);
    }

    Setting x;
    Setting y;

    public void setup(){
        x = new Setting("piX", this, 2, 0, 1000, true);
        y = new Setting("piY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
    }

    public void onRender(){
        Color red = new Color(255, 0, 0);
        Color green = new Color(0, 255, 0);
        if(ModuleManager.isModuleEnabled("AutoCrystal")){
            mc.fontRenderer.drawStringWithShadow("CA ON", (int)x.getValDouble(), (int)y.getValDouble(), green.getRGB());
        } else{
            mc.fontRenderer.drawStringWithShadow("CA OFF", (int)x.getValDouble(), (int)y.getValDouble(), red.getRGB());
        }
        if(ModuleManager.isModuleEnabled("KillAura")){
            mc.fontRenderer.drawStringWithShadow("KA ON", (int)x.getValDouble(), (int)y.getValDouble() + 10, green.getRGB());
        } else{
            mc.fontRenderer.drawStringWithShadow("KA OFF", (int)x.getValDouble(), (int)y.getValDouble() + 10, red.getRGB());
        }
        if(ModuleManager.isModuleEnabled("Surround")){
            mc.fontRenderer.drawStringWithShadow("SU ON", (int)x.getValDouble(), (int)y.getValDouble() + 20, green.getRGB());
        } else{
            mc.fontRenderer.drawStringWithShadow("SU OFF", (int)x.getValDouble(), (int)y.getValDouble() + 20, red.getRGB());
        }
        if(ModuleManager.isModuleEnabled("AutoTrap")){
            mc.fontRenderer.drawStringWithShadow("AT ON", (int)x.getValDouble(), (int)y.getValDouble() + 30, green.getRGB());
        } else{
            mc.fontRenderer.drawStringWithShadow("AT OFF", (int)x.getValDouble(), (int)y.getValDouble() + 30, red.getRGB());
        }
    }
}
