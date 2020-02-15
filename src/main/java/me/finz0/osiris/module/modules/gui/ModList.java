package me.finz0.osiris.module.modules.gui;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.util.Rainbow;

import java.awt.*;
import java.util.Comparator;

public class ModList extends Module {
    public ModList() {
        super("ModList", Category.GUI);
        setDrawn(false);
    }

    int modCount;
    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    int sort;
    Setting sortUp;
    Setting right;
    Setting rainbow;
    Setting customFont;
    Color c;

    public void setup(){
        red = new Setting("ModListRed", this, 255, 0, 255, true);
        green = new Setting("ModListGreen", this, 255, 0, 255, true);
        blue = new Setting("ModListBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("ModListX", this, 2, 0, 1000, true);
        y = new Setting("ModListY", this, 12, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        sortUp = new Setting("SortUp", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(sortUp);
        right = new Setting("AlignRight", this, false);
        OsirisMod.getInstance().settingsManager.rSetting(right);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("modlistRainbow", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(customFont = new Setting("modlistCFont", this, false));
    }

    public void onRender(){
        if(rainbow.getValBoolean())
            c = Rainbow.getColor();
        else
            c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());

        if(sortUp.getValBoolean()){ sort = -1;
        } else { sort = 1; }
        modCount = 0;
            ModuleManager.getModules()
                    .stream()
                    .filter(Module::isEnabled)
                    .filter(Module::isDrawn)
                    .sorted(Comparator.comparing(module -> mc.fontRenderer.getStringWidth(module.getName() + ChatFormatting.GRAY + " " + module.getHudInfo()) * (-1)))
                    .forEach(m -> {
                        if(sortUp.getValBoolean()) {
                            if (right.getValBoolean()) {
                                drawStringWithShadow(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), (int) x.getValDouble() - mc.fontRenderer.getStringWidth(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo()), (int) y.getValDouble() + (modCount * 10), c.getRGB());
                            } else {
                                drawStringWithShadow(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), (int) x.getValDouble(), (int) y.getValDouble() + (modCount * 10), c.getRGB());
                            }
                            modCount++;
                        } else {
                            if (right.getValBoolean()) {
                                drawStringWithShadow(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), (int) x.getValDouble() - mc.fontRenderer.getStringWidth(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo()), (int) y.getValDouble() + (modCount * -10), c.getRGB());
                            } else {
                                drawStringWithShadow(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), (int) x.getValDouble(), (int) y.getValDouble() + (modCount * -10), c.getRGB());
                            }
                            modCount++;
                        }
                    });
    }

    private void drawStringWithShadow(String text, int x, int y, int color){
        if(customFont.getValBoolean())
            OsirisMod.fontRenderer.drawStringWithShadow(text, x, y, color);
        else
            mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }
}
