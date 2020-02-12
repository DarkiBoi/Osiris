package me.finz0.osiris.module.modules.misc;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.GuiScreenDisplayedEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.waypoint.Waypoint;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiGameOver;

import java.awt.*;

public class DeathWaypoint extends Module {
    public DeathWaypoint() {
        super("DeathWaypoint", Category.MISC);
    }

    @EventHandler
    private Listener<GuiScreenDisplayedEvent> listener = new Listener<>(event -> {
        if (event.getScreen() instanceof GuiGameOver) {
            OsirisMod.getInstance().waypointManager.delWaypoint( OsirisMod.getInstance().waypointManager.getWaypointByName("Last Death"));
            OsirisMod.getInstance().waypointManager.addWaypoint(new Waypoint("Last Death", mc.player.posX, mc.player.posY, mc.player.posZ, Color.RED.getRGB()));
        }
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }

}
