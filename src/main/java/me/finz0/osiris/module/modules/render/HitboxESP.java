package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class HitboxESP extends Module {
    public HitboxESP() {
        super("HitboxESP", Category.RENDER);
        OsirisMod.getInstance().settingsManager.rSetting(players = new Setting("hePlayers", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(passive = new Setting("hePassive", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(mobs = new Setting("heMobs", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(exp = new Setting("heXpBottles", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(epearls = new Setting("heEpearls", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(crystals = new Setting("heCrystals", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(items = new Setting("heItems", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(r = new Setting("heRed", this, 0, 1, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(g = new Setting("heGreen", this, 0, 1, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(b = new Setting("heBlue", this, 0, 1, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(a = new Setting("heAlpha", this, 0, 1, 255, true));

    }

    Setting players;
    Setting passive;
    Setting mobs;
    Setting exp;
    Setting epearls;
    Setting crystals;
    Setting items;

    Setting r;
    Setting g;
    Setting b;
    Setting a;

    public void onWorldRender(RenderEvent event){
        Color c = new Color(r.getValInt(), g.getValInt(), b.getValInt(), a.getValInt());
        mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .forEach(e -> {
                    OsirisTessellator.prepareGL();
                    if(players.getValBoolean() && e instanceof EntityPlayer) {
                        if (Friends.isFriend(e.getName())) OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, Color.CYAN.getRGB());
                        else OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, Color.RED.getRGB());
                    }
                    if(mobs.getValBoolean() && GlowESP.isMonster(e)){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(passive.getValBoolean() && GlowESP.isPassive(e)){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(exp.getValBoolean() && e instanceof EntityExpBottle){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(epearls.getValBoolean() && e instanceof EntityEnderPearl){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(crystals.getValBoolean() && e instanceof EntityEnderCrystal){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(items.getValBoolean() && e instanceof EntityItem){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    OsirisTessellator.releaseGL();
                });
    }
}
