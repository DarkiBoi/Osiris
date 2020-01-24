package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class Gapples extends Module {
    public Gapples() {
        super("Gapples", Category.GUI);
        setDrawn(false);
    }

    int gapples;
    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;

    public void setup(){
        red = new Setting("GapplesRed", this, 255, 0, 255, true);
        green = new Setting("GapplesGreen", this, 255, 0, 255, true);
        blue = new Setting("GapplesBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("GapplesX", this, 2, 0, 1000, true);
        y = new Setting("GapplesY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("gapRainbow", this, false));
    }

    public void onRender(){
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        gapples = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) gapples += mc.player.getHeldItemOffhand().getCount();
        if(!rainbow.getValBoolean())
            mc.fontRenderer.drawStringWithShadow(gapples + " GAP", (int)x.getValDouble(), (int)y.getValDouble(), c.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(gapples + " GAP", (int)x.getValDouble(), (int)y.getValDouble(), Rainbow.getInt());
    }
}
