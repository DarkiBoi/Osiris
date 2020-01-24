package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class Exp extends Module {
    public Exp() {
        super("Exp", Category.GUI);
        setDrawn(false);
    }

    int xp;
    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;

    public void setup(){
        red = new Setting("ExpRed", this, 255, 0, 255, true);
        green = new Setting("ExpGreen", this, 255, 0, 255, true);
        blue = new Setting("ExpBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("ExpX", this, 2, 0, 1000, true);
        y = new Setting("ExpY", this, 2, 0, 500, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("expRainbow", this, false));
    }

    public void onRender(){
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        xp = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.EXPERIENCE_BOTTLE).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) xp += mc.player.getHeldItemOffhand().getCount();
        if(!rainbow.getValBoolean())
            mc.fontRenderer.drawStringWithShadow(xp + " EXP", (int)x.getValDouble(), (int)y.getValDouble(), c.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(xp + " EXP", (int)x.getValDouble(), (int)y.getValDouble(), Rainbow.getInt());
    }
}
