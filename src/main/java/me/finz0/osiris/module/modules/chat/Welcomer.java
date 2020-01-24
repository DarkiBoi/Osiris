package me.finz0.osiris.module.modules.chat;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.PlayerJoinEvent;
import me.finz0.osiris.event.events.PlayerLeaveEvent;
import me.finz0.osiris.module.Module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class Welcomer extends Module {
    public Welcomer() {
        super("Welcomer", Category.CHAT);
    }

    static ArrayList<NetworkPlayerInfo> playerMap;
    static int cachePlayerCount;
    boolean isOnServer;
    public static ArrayList<String> greets;
    public static ArrayList<String> goodbyes;

    public static ArrayList<String> joinMessages = new ArrayList<>();
    public static ArrayList<String> leaveMessages = new ArrayList<>();

    static {
        playerMap = new ArrayList<NetworkPlayerInfo>();
        greets = new ArrayList<String>();
        goodbyes = new ArrayList<String>();
    }

    public void onUpdate() {

        if (mc.player != null) {
            if (mc.player.ticksExisted % 10 == 0) {
                checkPlayers();
            }
        }

    }

    public void onEnable(){
        onJoinServer();
    }

    private void checkPlayers() {
        final ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(
                Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
        final int currentPlayerCount = infoMap.size();
        if (currentPlayerCount != cachePlayerCount) {
            final ArrayList<NetworkPlayerInfo> currentInfoMap = (ArrayList<NetworkPlayerInfo>) infoMap.clone();
            currentInfoMap.removeAll(playerMap);
            if (currentInfoMap.size() > 5) {
                cachePlayerCount = playerMap.size();
                this.onJoinServer();
                return;
            }
            final ArrayList<NetworkPlayerInfo> playerMapClone = (ArrayList<NetworkPlayerInfo>) playerMap.clone();
            playerMapClone.removeAll(infoMap);
            for (final NetworkPlayerInfo npi : currentInfoMap) {
                this.playerJoined(npi);
            }
            for (final NetworkPlayerInfo npi : playerMapClone) {
                this.playerLeft(npi);
            }
            cachePlayerCount = playerMap.size();
            this.onJoinServer();
        }
    }

    private void onJoinServer() {
        playerMap = new ArrayList<NetworkPlayerInfo>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
        cachePlayerCount = playerMap.size();
        this.isOnServer = true;
    }

    protected void playerJoined(final NetworkPlayerInfo playerInfo) {
        PlayerJoinEvent event1 = new PlayerJoinEvent(playerInfo.getGameProfile().getName());
        OsirisMod.EVENT_BUS.post(event1);
        Command.sendClientMessage(playerInfo.getGameProfile().getName() + " joined the game");
        //mc.player.sendChatMessage(joinMessages.get(new Random().nextInt(joinMessages.size())));
    }

    protected void playerLeft(final NetworkPlayerInfo playerInfo) {
        PlayerLeaveEvent event2 = new PlayerLeaveEvent(playerInfo.getGameProfile().getName());
        OsirisMod.EVENT_BUS.post(event2);
        Command.sendClientMessage(playerInfo.getGameProfile().getName() + " left the game");
        //mc.player.sendChatMessage(leaveMessages.get(new Random().nextInt(leaveMessages.size())));
    }
}
