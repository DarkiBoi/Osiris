package me.finz0.osiris;

public class ShutDownHookerino extends Thread {
    @Override
    public void run(){
        saveConfig();
    }

    public static void saveConfig(){
        OsirisMod.getInstance().osirisConfig.saveMods();
        OsirisMod.getInstance().osirisConfig.saveSettingsList();
        OsirisMod.getInstance().osirisConfig.saveBinds();
        OsirisMod.getInstance().osirisConfig.saveDrawn();
        OsirisMod.getInstance().osirisConfig.saveFriends();
        OsirisMod.getInstance().osirisConfig.saveGui();
        OsirisMod.getInstance().osirisConfig.savePrefix();
        OsirisMod.getInstance().osirisConfig.saveRainbow();
        OsirisMod.getInstance().osirisConfig.saveMacros();
        OsirisMod.getInstance().osirisConfig.saveMsgs();
        OsirisMod.getInstance().osirisConfig.saveAutoGG();
        OsirisMod.getInstance().osirisConfig.saveSpammer();
        OsirisMod.getInstance().osirisConfig.saveAutoReply();
        OsirisMod.getInstance().osirisConfig.saveAnnouncer();
        OsirisMod.getInstance().osirisConfig.saveWaypoints();
    }
}
