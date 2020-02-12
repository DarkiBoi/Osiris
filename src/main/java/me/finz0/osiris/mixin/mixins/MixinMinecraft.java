package me.finz0.osiris.mixin.mixins;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.GuiScreenDisplayedEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow
    WorldClient world;
    @Shadow
    EntityPlayerSP player;
    @Shadow
    GuiScreen currentScreen;
    @Shadow
    GameSettings gameSettings;
    @Shadow
    GuiIngame ingameGUI;
    @Shadow
    boolean skipRenderWorld;
    @Shadow
    SoundHandler soundHandler;

    @Inject(method = "displayGuiScreen", at = @At("HEAD"), cancellable = true)
    public void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo info) {
        try {
            GuiScreenDisplayedEvent screenEvent = new GuiScreenDisplayedEvent(guiScreenIn);
            OsirisMod.EVENT_BUS.post(screenEvent);
            guiScreenIn = screenEvent.getScreen();

            if (guiScreenIn == null && this.world == null) {
                guiScreenIn = new GuiMainMenu();
            } else if (guiScreenIn == null && this.player.getHealth() <= 0.0F) {
                guiScreenIn = new GuiGameOver(null);
            }

            GuiScreen old = this.currentScreen;
            net.minecraftforge.client.event.GuiOpenEvent event = new net.minecraftforge.client.event.GuiOpenEvent(guiScreenIn);

            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;

            guiScreenIn = event.getGui();
            if (old != null && guiScreenIn != old) {
                old.onGuiClosed();
            }

            if (guiScreenIn instanceof GuiMainMenu || guiScreenIn instanceof GuiMultiplayer) {
                this.gameSettings.showDebugInfo = false;
                this.ingameGUI.getChatGUI().clearChatMessages(true);
            }

            this.currentScreen = guiScreenIn;

            if (guiScreenIn != null) {
                Minecraft.getMinecraft().setIngameNotInFocus();
                KeyBinding.unPressAllKeys();

                while (Mouse.next()) {
                }

                while (Keyboard.next()) {
                }

                ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
                int i = scaledresolution.getScaledWidth();
                int j = scaledresolution.getScaledHeight();
                guiScreenIn.setWorldAndResolution(Minecraft.getMinecraft(), i, j);
                this.skipRenderWorld = false;
            } else {
                this.soundHandler.resumeSounds();
                Minecraft.getMinecraft().setIngameFocus();
            }

            info.cancel();
        } catch(Exception e){}
    }
}
