package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.EnumHand;

public class LowHands extends Module {
    public LowHands() {
        super("LowOffhand", Category.RENDER);
    }
    Setting off;
    ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;

    public void setup(){
        off = new Setting("Height", this, 0.5, 0, 1, false);
        OsirisMod.getInstance().settingsManager.rSetting(off);
    }

    public void onUpdate(){
        itemRenderer.equippedProgressOffHand = (float)off.getValDouble();
    }
}
