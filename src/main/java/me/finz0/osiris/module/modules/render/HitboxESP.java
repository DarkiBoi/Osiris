package me.finz0.osiris.module.modules.render;

import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class HitboxESP extends Module {
    public HitboxESP() {
        super("HitboxESP", Category.RENDER);
    }

    public void onWorldRender(RenderEvent event){
        mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> entity instanceof EntityPlayer)
                .forEach(entity -> {
                    OsirisTessellator.prepareGL();
                    if(Friends.isFriend(entity.getName()))
                        OsirisTessellator.drawBoundingBox(entity.getEntityBoundingBox(), 1, Color.CYAN.getRGB());
                    else
                        OsirisTessellator.drawBoundingBox(entity.getEntityBoundingBox(), 1, Color.RED.getRGB());
                    OsirisTessellator.releaseGL();
                });
    }
}
