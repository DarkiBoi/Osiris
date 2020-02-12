package me.finz0.osiris.module.modules.chat;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketChat;

public class AutoReply extends Module {
    public AutoReply() {
        super("AutoReply", Category.CHAT);
    }

    private static String reply = "fuck off";

    @EventHandler
    private Listener<PacketEvent.Receive> listener = new Listener<>(event ->{
        if(event.getPacket() instanceof SPacketChat
                && ((SPacketChat)event.getPacket()).getChatComponent().getUnformattedText().contains("whispers: ")
                && !((SPacketChat)event.getPacket()).getChatComponent().getUnformattedText().startsWith(mc.player.getName())){
            mc.player.sendChatMessage("/r " + reply);
        }
    });

    public static String getReply(){
        return reply;
    }

    public static void setReply(String r){
        reply = r;
    }

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }
}
