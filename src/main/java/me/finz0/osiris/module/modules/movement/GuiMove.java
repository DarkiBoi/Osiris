package me.finz0.osiris.module.modules.movement;

import me.finz0.osiris.module.Module;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;

public class GuiMove extends Module {
    public GuiMove() {
        super("GuiMove", Category.MOVEMENT);
    }

    public void onUpdate(){
        if(mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(Integer.valueOf(200).intValue())) {
                mc.player.rotationPitch -= 5;

            }
            if (Keyboard.isKeyDown(Integer.valueOf(208).intValue())) {
                mc.player.rotationPitch += 5;
            }
            if (Keyboard.isKeyDown(Integer.valueOf(205).intValue())) {
                mc.player.rotationYaw += 5;
            }
            if (Keyboard.isKeyDown(Integer.valueOf(203).intValue())) {
                mc.player.rotationYaw -= 5;
            }
            if (mc.player.rotationPitch > 90) mc.player.rotationPitch = 90;
            if (mc.player.rotationPitch < -90) mc.player.rotationPitch = -90;
        }
    }
}
