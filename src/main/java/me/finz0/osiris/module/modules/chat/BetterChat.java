package me.finz0.osiris.module.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.friends.Friends;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BetterChat extends Module {
    public BetterChat() {
        super("BetterChat", Category.CHAT);
    }

    public Setting clearBkg;
    Setting timeStamps;
    Setting suffix;
    Setting nameHighlight;
    Setting friendHighlight;

    public void setup(){
        clearBkg = new Setting("Clear", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(clearBkg);
        timeStamps = new Setting("TimeStamps", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(timeStamps);
        suffix = new Setting("Suffix", this, false);
        OsirisMod.getInstance().settingsManager.rSetting(suffix);
        OsirisMod.getInstance().settingsManager.rSetting(nameHighlight = new Setting("NameHighlight", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(friendHighlight = new Setting("FriendHighlight", this, false));
    }

    @EventHandler
    private Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketChatMessage && suffix.getValBoolean()){
            if(((CPacketChatMessage) event.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix())) return;
            String old = ((CPacketChatMessage) event.getPacket()).getMessage();
            String suffix = " \u300b\u1d0f\ua731\u026a\u0280\u026a\ua731";
            String s = old + suffix;
            if(s.length() > 255) return;
            ((CPacketChatMessage) event.getPacket()).message = s;
        }
    });

    @EventHandler
    private Listener<ClientChatReceivedEvent> chatReceivedEventListener = new Listener<>(event -> {
        if(mc.player == null || mc.player.getName() == null) return;
        ITextComponent msg = event.getMessage();
        String name = mc.player.getName().toLowerCase();
        if(friendHighlight.getValBoolean()){
            TextComponentString aqua = new TextComponentString(ChatFormatting.AQUA + "");
            if(!event.getMessage().getUnformattedText().startsWith("<"+mc.player.getName()+">")){
                Friends.getFriends().forEach(f -> {
                    if(event.getMessage().getUnformattedText().contains(f.getName())){
                        event.getMessage().setStyle(event.getMessage().getStyle().setColor(TextFormatting.AQUA));
                    }
                });
            }
        }
        if(nameHighlight.getValBoolean()){
            TextComponentString gold = new TextComponentString(ChatFormatting.GOLD + "");
            if(!event.getMessage().getUnformattedText().startsWith("<"+mc.player.getName()+">")
                && event.getMessage().getUnformattedText().toLowerCase().contains(name))
                  event.getMessage().setStyle(event.getMessage().getStyle().setParentStyle(event.getMessage().getStyle().setColor(TextFormatting.GOLD)));
        }
        if(timeStamps.getValBoolean()) {
            String date = new SimpleDateFormat("k:mm").format(new Date());
            TextComponentString newMsg = new TextComponentString("\2477[" + date + "]\247r");
            event.setMessage(newMsg.appendSibling(event.getMessage()));
        }
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }
}
