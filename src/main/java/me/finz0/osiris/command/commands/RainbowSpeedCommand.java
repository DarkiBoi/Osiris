package me.finz0.osiris.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.ForgeEventProcessor;

public class RainbowSpeedCommand extends Command {
    @Override
    public String getAlias() {
        return "rainbowspeed";
    }

    @Override
    public String getSyntax() {
        return "rainbowspeed <speed>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        if(args.length == 1){
            int i = Integer.parseInt(args[0]);
            if(i <= 0) {
                ForgeEventProcessor.INSTANCE.setRainbowSpeed(0);
            } else {
                ForgeEventProcessor.INSTANCE.setRainbowSpeed(i);
            }
            Command.sendClientMessage("Rainbow speed set to " + i);
        } else {
            Command.sendClientMessage(ChatFormatting.RED + this.getSyntax());
        }
    }
}
