package me.finz0.osiris.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

public class MiddleClickFriends extends Module {
    public MiddleClickFriends() {
        super("MCF", Category.MISC);
    }

    @EventHandler
    private Listener<InputEvent.MouseInputEvent> listener = new Listener<>(event -> {
        if (mc.objectMouseOver.typeOfHit.equals(RayTraceResult.Type.ENTITY) && mc.objectMouseOver.entityHit instanceof EntityPlayer && Mouse.getEventButton() == 2) {
            if (OsirisMod.getInstance().friends.isFriend(mc.objectMouseOver.entityHit.getName())) {
                OsirisMod.getInstance().friends.delFriend(mc.objectMouseOver.entityHit.getName());
                Command.sendClientMessage(ChatFormatting.RED + "Removed " + mc.objectMouseOver.entityHit.getName() + " from friends list");
            } else {
                OsirisMod.getInstance().friends.addFriend(mc.objectMouseOver.entityHit.getName());
                Command.sendClientMessage(ChatFormatting.GREEN + "Added " + mc.objectMouseOver.entityHit.getName() + " to friends list");
            }
        }
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }
}
