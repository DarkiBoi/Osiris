package me.finz0.osiris.module.modules.misc;

import me.finz0.osiris.OsirisRPC;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;

public class RpcModule extends Module {
    public RpcModule() {
        super("DiscordRPC", Category.MISC);
        setDrawn(false);
    }

    public void onEnable(){
        OsirisRPC.init();
        if(mc.player != null)
            Command.sendClientMessage("discord rpc started");
    }

    public void onDisable(){
        Command.sendClientMessage("you need to restart your game disable rpc");
    }
}
