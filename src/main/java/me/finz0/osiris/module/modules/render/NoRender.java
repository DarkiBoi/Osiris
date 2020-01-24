package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

public class NoRender extends Module {
    public NoRender() {
        super("NoRender", Category.RENDER);
    }

    public Setting armor;
    Setting fire;
    Setting blind;
    Setting nausea;
    public Setting hurtCam;

    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(armor = new Setting("Armor", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(fire = new Setting("Fire", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(blind = new Setting("Blindness", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(nausea = new Setting("Nausea", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(hurtCam = new Setting("HurtCam", this, false));
    }

    public void onUpdate(){
        if(blind.getValBoolean() && mc.player.isPotionActive(MobEffects.BLINDNESS)) mc.player.removePotionEffect(MobEffects.BLINDNESS);
        if(nausea.getValBoolean() && mc.player.isPotionActive(MobEffects.NAUSEA)) mc.player.removePotionEffect(MobEffects.NAUSEA);
    }

    @EventHandler
    public Listener<RenderBlockOverlayEvent> blockOverlayEventListener = new Listener<>(event -> {
        if(fire.getValBoolean() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) event.setCanceled(true);
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }
}
