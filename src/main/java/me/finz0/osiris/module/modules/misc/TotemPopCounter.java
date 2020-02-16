package me.finz0.osiris.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;

import java.util.concurrent.ConcurrentHashMap;

public class TotemPopCounter extends Module {
    public TotemPopCounter() {
        super("TotemPopCounter", Category.MISC);
    }

    ConcurrentHashMap<Entity, Integer> players;

    //credit to Darki for the event
    @EventHandler
    private Listener<PacketEvent.Receive> listener = new Listener<>(event -> {
        if(event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if(packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(mc.world);
                if(players.containsKey(entity)){
                    players.forEach((ent, count) -> {
                        if(!entity.isDead) {
                            players.put(ent, count + 1);
                            Command.sendClientMessage(ChatFormatting.GREEN + ent.getName() + ChatFormatting.RESET + " just popped " + ChatFormatting.GREEN + count + ChatFormatting.RESET + " totems!");
                        } else {
                            try {
                                players.remove(ent, count);
                                Command.sendClientMessage(ChatFormatting.RED + ent.getName() + ChatFormatting.RESET + " just died after popping " + ChatFormatting.RED + count + ChatFormatting.RESET + " totems!");
                            } catch(Exception e){}
                        }
                    });
                } else {
                    players.put(entity, 1);
                    Command.sendClientMessage(ChatFormatting.GREEN + entity.getName() + ChatFormatting.RESET  + " just popped" + ChatFormatting.GREEN +  "1" + ChatFormatting.RESET  +  "totem!");
                }
            }
        }
    });

    public void onUpdate(){
        try {
            players.forEach((e, count) -> {
                if (e.isDead) {
                    players.remove(e, count);
                    Command.sendClientMessage(ChatFormatting.RED + e.getName() + ChatFormatting.RESET + " just died after popping " + ChatFormatting.RED + count + ChatFormatting.RESET + " totems!");
                }
            });
        } catch(Exception e){}
    }

    public void onEnable(){
        players = new ConcurrentHashMap<>();
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }
}
