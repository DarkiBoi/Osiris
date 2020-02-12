package me.finz0.osiris.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.ShutDownHookerino;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.util.OsirisConfig;

public class ConfigCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"saveconfig", "savecfg"};
    }

    @Override
    public String getSyntax() {
        return "saveconfig";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        ShutDownHookerino.saveConfig();
        Command.sendClientMessage("Config saved");
    }
}
