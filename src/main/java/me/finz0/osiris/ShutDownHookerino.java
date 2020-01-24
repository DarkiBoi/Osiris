package me.finz0.osiris;

public class ShutDownHookerino extends Thread {
    @Override
    public void run(){
        OsirisMod.getInstance().osirisConfig.saveMods();
        OsirisMod.getInstance().osirisConfig.saveSettingsList();
        OsirisMod.getInstance().osirisConfig.saveBinds();
        OsirisMod.getInstance().osirisConfig.saveDrawn();
        OsirisMod.getInstance().osirisConfig.saveFriends();
        OsirisMod.getInstance().osirisConfig.saveGui();
        OsirisMod.getInstance().osirisConfig.savePrefix();
        OsirisMod.getInstance().osirisConfig.saveRainbow();
        OsirisMod.getInstance().osirisConfig.saveMacros();
    }
}
