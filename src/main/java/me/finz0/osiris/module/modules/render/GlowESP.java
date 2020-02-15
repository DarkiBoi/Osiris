package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class GlowESP extends Module {
    public GlowESP() {
        super("GlowESP", Category.RENDER);
    }

    Setting players;
    Setting passive;
    Setting monsters;
    Setting items;
    Setting xpBottles;
    Setting crystals;

    List<Entity> entities;

    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(players = new Setting("gPlayers", this, true));
        OsirisMod.getInstance().settingsManager.rSetting(passive = new Setting("gPassive", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(monsters = new Setting("gMonsters", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(items = new Setting("gItems", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(crystals = new Setting("gCrystals", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(xpBottles = new Setting("gXpBottles", this, false));
    }

    public void onUpdate(){
        entities = mc.world.loadedEntityList.stream()
                .filter(e -> e != mc.player)
                .collect(Collectors.toList());
        entities.forEach(e -> {
            if(e instanceof EntityPlayer && players.getValBoolean())
                e.setGlowing(true);

            if(isPassive(e) && passive.getValBoolean())
                e.setGlowing(true);

            if(e instanceof EntityExpBottle && xpBottles.getValBoolean())
                e.setGlowing(true);

            if(isMonster(e) && monsters.getValBoolean())
                e.setGlowing(true);

            if(e instanceof EntityItem && items.getValBoolean())
                e.setGlowing(true);

            if(e instanceof EntityEnderCrystal && crystals.getValBoolean())
                e.setGlowing(true);
        });
    }

    public void onDisable(){
        entities.forEach(p -> p.setGlowing(false));
    }

    public static boolean isPassive(Entity e) {
        if (e instanceof EntityWolf && ((EntityWolf) e).isAngry()) return false;
        if (e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid)
            return true;
        if (e instanceof EntityIronGolem && ((EntityIronGolem) e).getRevengeTarget() == null) return true;
        return false;
    }

    public static boolean isMonster(Entity entity) {
        return entity.isCreatureType(EnumCreatureType.MONSTER, false);
    }

}
