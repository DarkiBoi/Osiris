package me.finz0.osiris.module.modules.combat;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.friends.Friends;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {
    public KillAura() {
        super("KillAura", Category.COMBAT);
    }

    private Setting range;
    private Setting swordOnly;
    private Setting caCheck;
    private Setting criticals;
    public void setup(){
        range = new Setting("kaRange", this, 5.0, 0.0, 10.0, false);
        OsirisMod.getInstance().settingsManager.rSetting(range);
        OsirisMod.getInstance().settingsManager.rSetting(criticals = new Setting("kaCriticals", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(swordOnly = new Setting("kaSwordOnly", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(caCheck = new Setting("CAuraCheck", this, false));

    }

    private boolean isAttacking = false;

    public void onUpdate(){
        if(mc.player == null || mc.player.isDead) return;
        List<Entity> targets = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= range.getValDouble())
                .filter(entity -> !entity.isDead)
                .filter(entity -> entity instanceof EntityPlayer)
                .filter(entity -> ((EntityPlayer) entity).getHealth() > 0)
                .filter(entity -> !Friends.isFriend(entity.getName()))
                .sorted(Comparator.comparing(e->mc.player.getDistance(e)))
                .collect(Collectors.toList());

        targets.forEach(target ->{
            if(swordOnly.getValBoolean())
                if(!(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) return;

            if(caCheck.getValBoolean())
                if(((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).isActive) return;

            attack(target);
        });
    }

    @EventHandler
    private Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketUseEntity) {
            if(criticals.getValBoolean() && !ModuleManager.isModuleEnabled("Criticals") && ((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround && isAttacking) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            }
        }
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }

    public void attack(Entity e){
        if(mc.player.getCooledAttackStrength(0) >= 1) {
            isAttacking = true;
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            isAttacking = false;
        }
    }
}
