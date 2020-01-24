package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class Crystals extends Module {
    public Crystals() {
        super("Crystals", Category.GUI);
        setDrawn(false);
    }

    int crystals;
    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;

    public void setup(){
        red = new Setting("CrystalsRed", this, 255, 0, 255, true);
        green = new Setting("CrystalsGreen", this, 255, 0, 255, true);
        blue = new Setting("CrystalsBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("CrystalsX", this, 2, 0, 1000, true);
        y = new Setting("CrystalsY", this, 2, 0, 500, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("cryRainbow", this, false));
    }

    public void onRender(){
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        crystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) crystals += mc.player.getHeldItemOffhand().getCount();
        if(!rainbow.getValBoolean())
            mc.fontRenderer.drawStringWithShadow(crystals + " CRY", (int)x.getValDouble(), (int)y.getValDouble(), c.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(crystals + " CRY", (int)x.getValDouble(), (int)y.getValDouble(), Rainbow.getInt());
    }
}
