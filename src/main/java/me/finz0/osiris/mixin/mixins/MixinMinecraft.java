package me.finz0.osiris.mixin.mixins;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.GuiScreenDisplayedEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "displayGuiScreen", at = @At("HEAD"), cancellable = true)
    public void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo info) {
        try {
            GuiScreenDisplayedEvent screenEvent = new GuiScreenDisplayedEvent(guiScreenIn);
            OsirisMod.EVENT_BUS.post(screenEvent);
            guiScreenIn = screenEvent.getScreen();
        } catch (Exception e){}
    }
}
