package me.finz0.osiris.command.commands;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;

public class FriendCommand extends Command {
    @Override
    public String getAlias() {
        return "friend";
    }

    @Override
    public String getSyntax() {
        return "friend <add | del> <Name>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        if(args[0].equalsIgnoreCase("add")){
            if(OsirisMod.getInstance().friends.isFriend(args[1])) {
                Command.sendClientMessage(args[1] + " is already a friend!");
                return;
            }
            if(!OsirisMod.getInstance().friends.isFriend(args[1])){
                OsirisMod.getInstance().friends.addFriend(args[1]);
                Command.sendClientMessage("Added " + args[1] + " to friends list");
            }
        }
        if(args[0].equalsIgnoreCase("del")){
            if(!OsirisMod.getInstance().friends.isFriend(args[1])){
                Command.sendClientMessage(args[1] + " is not a friend!");
                return;
            }
            if(OsirisMod.getInstance().friends.isFriend(args[1])){
                OsirisMod.getInstance().friends.delFriend(args[1]);
                Command.sendClientMessage("Removed " + args[1] + " from friends list");
            }
        }
    }
}
