package me.finz0.osiris.module.modules.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.TpsUtils;
import net.minecraft.client.resources.I18n;

import java.text.DecimalFormat;

public class PotionEffects extends Module {
    public PotionEffects() {
        super("PotionEffects", Category.GUI);
        setDrawn(false);
    }

    int count;
    Setting x;
    Setting y;
    Setting sortUp;
    Setting right;
    DecimalFormat format1 = new DecimalFormat("0");
    DecimalFormat format2 = new DecimalFormat("00");

    public void setup(){
        x = new Setting("fxX", this, 2, 0, 1000, true);
        y = new Setting("fxY", this, 12, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        sortUp = new Setting("fxSortUp", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(sortUp);
        right = new Setting("fxAlignRight", this, false);
        OsirisMod.getInstance().settingsManager.rSetting(right);
    }

    public void onRender(){
        count = 0;
        try {
            mc.player.getActivePotionEffects().forEach(effect -> {
                String name = I18n.format(effect.getPotion().getName());
                double duration = effect.getDuration() / TpsUtils.getTickRate();
                int amplifier = effect.getAmplifier() + 1;
                int color = effect.getPotion().getLiquidColor();
                double p1 = duration % 60;
                double p2 = duration / 60;
                double p3 = p2 % 60;
                String minutes = format1.format(p3);
                String seconds = format2.format(p1);
                String s = name + " " + amplifier + ChatFormatting.GRAY + " " + minutes + ":" + seconds;
                if (sortUp.getValBoolean()) {
                    if (right.getValBoolean()) {
                        mc.fontRenderer.drawStringWithShadow(s, (int) x.getValDouble() - mc.fontRenderer.getStringWidth(s), (int) y.getValDouble() + (count * 10), color);
                    } else {
                        mc.fontRenderer.drawStringWithShadow(s, (int) x.getValDouble(), (int) y.getValDouble() + (count * 10), color);
                    }
                    count++;
                } else {
                    if (right.getValBoolean()) {
                        mc.fontRenderer.drawStringWithShadow(s, (int) x.getValDouble() - mc.fontRenderer.getStringWidth(s), (int) y.getValDouble() + (count * -10), color);
                    } else {
                        mc.fontRenderer.drawStringWithShadow(s, (int) x.getValDouble(), (int) y.getValDouble() + (count * -10), color);
                    }
                    count++;
                }
            });
        } catch(NullPointerException e){e.printStackTrace();}
    }
}
