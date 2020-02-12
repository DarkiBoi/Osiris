package me.finz0.osiris.module.modules.movement;

import me.finz0.osiris.module.Module;

public class Jesus extends Module {
    public Jesus() {
        super("Jesus", Category.MOVEMENT);
    }

    public void onUpdate(){
        if(mc.player.isInWater() && !mc.player.isSneaking()){
            mc.player.motionY = 0.1;
        }
    }
}
