package me.finz0.osiris.command.commands;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;

public class LoadAnnouncerCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{
                "loadannouncer"
        };
    }

    @Override
    public String getSyntax() {
        return "loadannouncer";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        OsirisMod.getInstance().configUtils.loadAnnouncer();
        sendClientMessage("Loaded Announcer file");
    }
}
