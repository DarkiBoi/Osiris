package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.awt.*;

public class Totems extends Module {
    public Totems() {
        super("Totems", Category.GUI);
        setDrawn(false);
    }

    int totems;
    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;

    public void setup(){
        red = new Setting("TotemsRed", this, 255, 0, 255, true);
        green = new Setting("TotemsGreen", this, 255, 0, 255, true);
        blue = new Setting("TotemsBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("TotemsX", this, 2, 0, 1000, true);
        y = new Setting("TotemsY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("totRainbow", this, false));
    }

    public void onRender(){
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) totems++;
        if(!rainbow.getValBoolean())
            mc.fontRenderer.drawStringWithShadow(totems + " TOT", (int)x.getValDouble(), (int)y.getValDouble(), c.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(totems + " TOT", (int)x.getValDouble(), (int)y.getValDouble(), Rainbow.getInt());
    }
}
