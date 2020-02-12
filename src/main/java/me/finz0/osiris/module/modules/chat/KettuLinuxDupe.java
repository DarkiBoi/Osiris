package me.finz0.osiris.module.modules.chat;

import me.finz0.osiris.module.Module;

import java.util.Random;

public class KettuLinuxDupe extends Module {
    public KettuLinuxDupe() {
        super("KettuLinuxDupe", Category.CHAT);
    }

    public void onEnable(){
        if(mc.player != null)
            mc.player.sendChatMessage("I just used the Kettu Linux Dupe and got " + new Random().nextInt(15) + " shulkers! Powered by Osiris\u2122");
        disable();
    }
}
