package me.finz0.osiris.module.modules.misc;

import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.PlayerJoinEvent;
import me.finz0.osiris.event.events.PlayerLeaveEvent;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.module.Module;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.UUID;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class LogoutSpots extends Module {
    public LogoutSpots() {
        super("LogoutSpots", Category.MISC);
    }
    private final Set<LogoutPos> spots = Sets.newHashSet();


    private void reset() {
        synchronized (spots) {
            spots.clear();
        }
    }

    private void printWarning(String fmt, Object... args) {
        //if (print_message.get())
        Command.sendClientMessage(args + fmt);
    }

    @Override
    protected void onDisable() {
        reset();
    }

    @EventHandler
    private Listener<PlayerJoinEvent> playerJoinEventListener = new Listener<>(event -> {
        synchronized (spots) {
            if (spots.removeIf(spot -> spot.getName().equals(event.getName()))) {
                printWarning(" has joined!", event.getName());
            }
        }
    });

    @EventHandler
    private Listener<PlayerLeaveEvent> playerLeaveEventListener = new Listener<>(event -> {
        if (mc.world == null) {
            return;
        }

        EntityPlayer player = mc.world.getPlayerEntityByName(event.getName());
        if (player != null && mc.player != null && !mc.player.equals(player)) {
            AxisAlignedBB bb = player.getEntityBoundingBox();
            synchronized (spots) {
                if (spots.add(
                        new LogoutPos(
                                event.getName(),
                                new Vec3d(bb.maxX, bb.maxY, bb.maxZ),
                                new Vec3d(bb.minX, bb.minY, bb.minZ)))) {
                    printWarning(" has disconnected!", event.getName());
                }
            }
        }
    });

    public void onRender() {
        synchronized (spots) {
            spots.forEach(
                    spot -> {

                    });
        }
    }

    public void onWorldRender(RenderEvent event) {

        //event.getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

        synchronized (spots) {
            spots.forEach(
                    spot ->{

                    });
        }

        //event.getTessellator().draw();
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        reset();
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        reset();
    }

    private class LogoutPos {

        final String name;
        final Vec3d maxs;
        final Vec3d mins;

        private LogoutPos(String name, Vec3d maxs, Vec3d mins) {
            this.name = name;
            this.maxs = maxs;
            this.mins = mins;
        }


        public String getName() {
            return name;
        }

        public Vec3d getMaxs() {
            return maxs;
        }

        public Vec3d getMins() {
            return mins;
        }

        public Vec3d getTopVec() {
            return new Vec3d(
                    (getMins().x + getMaxs().x) / 2.D, getMaxs().y, (getMins().z + getMaxs().z) / 2.D);
        }

        @Override
        public boolean equals(Object other) {
            return this == other
                    || (other instanceof LogoutPos && getName().equals(((LogoutPos) other).getName()));
        }

    }
}
