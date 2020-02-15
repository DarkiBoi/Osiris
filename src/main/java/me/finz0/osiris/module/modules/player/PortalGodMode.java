package me.finz0.osiris.module.modules.player;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketConfirmTeleport;

public class PortalGodMode extends Module {
    public PortalGodMode() {
        super("PortalGodmode", Category.PLAYER);
    }

    public void onEnable() {
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    private Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketConfirmTeleport)
            event.cancel();
    });
}
