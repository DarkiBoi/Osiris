package me.finz0.osiris.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.command.CommandManager;
import me.finz0.osiris.macro.MacroManager;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ForgeEventProcessor {
    public static ForgeEventProcessor INSTANCE;
    Minecraft mc = Minecraft.getMinecraft();
    CommandManager commandManager = new CommandManager();
    float hue = 0;
    Color c;
    int rgb;
    int speed = 2;

    public ForgeEventProcessor(){
        INSTANCE = this;
    }

    public int getRgb(){
        return rgb;
    }

    public Color getC(){
        return c;
    }

    public void setRainbowSpeed(int s){
        speed = s;
    }

    public int getRainbowSpeed(){
        return speed;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
            c = Color.getHSBColor(hue, 1f, 1f);
            rgb = Color.HSBtoRGB(hue, 1f, 1f);
            hue += speed / 2000f;
        if (mc.player != null)
            ModuleManager.onUpdate();
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) return;
        ModuleManager.onWorldRender(event);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
            ModuleManager.onRender();
        OsirisMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            ModuleManager.onBind(Keyboard.getEventKey());
            OsirisMod.getInstance().macroManager.getMacros().forEach(m -> {
                if(m.getKey() == Keyboard.getEventKey())
                    m.onMacro();
            });
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(ClientChatEvent event) {
        if (event.getMessage().startsWith(Command.getPrefix())) {
            event.setCanceled(true);
            try {
                mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                commandManager.callCommand(event.getMessage().substring(1));
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendClientMessage(ChatFormatting.DARK_RED + "Error: " + e.getMessage());
            }
            //event.setMessage("");
        }
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
        OsirisMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event){
        OsirisMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        OsirisMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event){
        OsirisMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event){
        OsirisMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event){ OsirisMod.EVENT_BUS.post(event); }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event){
        OsirisMod.EVENT_BUS.post(event);
    }
    @SubscribeEvent
    public void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        OsirisMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event){
        OsirisMod.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event){OsirisMod.EVENT_BUS.post(event);}

}
