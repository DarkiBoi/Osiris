package me.finz0.osiris.module.modules.chat;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.PlayerJoinEvent;
import me.finz0.osiris.event.events.PlayerLeaveEvent;
import me.finz0.osiris.module.Module;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class Welcomer extends Module {
    public Welcomer() {
        super("Welcomer", Category.CHAT);
    }

    @EventHandler
    private Listener<PlayerJoinEvent> listener1 = new Listener<>(event -> {
        Command.sendClientMessage(event.getName() + " joined the game");
    });

    @EventHandler
    private Listener<PlayerLeaveEvent> listener2 = new Listener<>(event2 -> {
        Command.sendClientMessage(event2.getName() + " left the game");
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }
}
