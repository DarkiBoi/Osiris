package me.finz0.osiris.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.util.OsirisConfig;

public class ConfigCommand extends Command {
    @Override
    public String getAlias() {
        return "saveconfig";
    }

    @Override
    public String getSyntax() {
        return "saveconfig";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
            OsirisMod.getInstance().osirisConfig.saveMods();
            OsirisMod.getInstance().osirisConfig.saveSettingsList();
            OsirisMod.getInstance().osirisConfig.saveBinds();
            OsirisMod.getInstance().osirisConfig.saveDrawn();
            OsirisMod.getInstance().osirisConfig.saveFriends();
            OsirisMod.getInstance().osirisConfig.saveGui();
            OsirisMod.getInstance().osirisConfig.savePrefix();
            OsirisMod.getInstance().osirisConfig.saveRainbow();
            OsirisMod.getInstance().osirisConfig.saveMacros();
            Command.sendClientMessage("Config saved");
    }
}
