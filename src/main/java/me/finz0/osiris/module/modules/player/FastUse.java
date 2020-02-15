package me.finz0.osiris.module.modules.player;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

import net.minecraft.init.Items;

public class FastUse extends Module {
    public FastUse() {
        super("FastUse", Category.PLAYER);
    }

    Setting xp;
    Setting crystals;
    Setting all;
    Setting breakS;

    public void setup(){
        xp = new Setting( "fuEXP", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(xp);
        crystals = new Setting("fuCrystals", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(crystals);
        all = new Setting("fuEverything", this, false);
        OsirisMod.getInstance().settingsManager.rSetting(all);
        breakS = new Setting("fuFastBreak", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(breakS);
    }

    public void onUpdate() {
        if(xp.getValBoolean()) {
            if (mc.player != null && (mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE)) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if(crystals.getValBoolean()) {
            if (mc.player != null && (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)) {
                mc.rightClickDelayTimer = 0;
            }
        }

        if(all.getValBoolean()) {
            mc.rightClickDelayTimer = 0;
        }

        if(breakS.getValBoolean()){
            mc.playerController.blockHitDelay = 0;
        }
    }
}
