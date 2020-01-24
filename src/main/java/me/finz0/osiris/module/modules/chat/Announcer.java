package me.finz0.osiris.module.modules.chat;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.DestroyBlockEvent;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.event.events.PlayerJumpEvent;
import me.finz0.osiris.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class Announcer extends Module {
    public Announcer() {
        super("Announcer", Category.CHAT);
    }
    public static int blockBrokeDelay = 0;
    static int blockPlacedDelay = 0;
    static int jumpDelay = 0;
    static int attackDelay = 0;
    static int eattingDelay = 0;

    static long lastPositionUpdate;
    static double lastPositionX;
    static double lastPositionY;
    static double lastPositionZ;
    private static double speed;
    String heldItem = "";

    int blocksPlaced = 0;
    int blocksBroken = 0;
    int eaten = 0;

    public Setting clientSide;
    Setting walk;
    Setting place;
    Setting jump;
    Setting breaking;
    Setting attack;
    Setting eat;
    public Setting clickGui;
    Setting delay;

    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(clientSide = new Setting("anClientSide", this, true));
        OsirisMod.getInstance().settingsManager.rSetting(walk = new Setting("anWalk", this, true));
        OsirisMod.getInstance().settingsManager.rSetting(place = new Setting("anBlockPlace", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(jump = new Setting("anJump", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(breaking = new Setting("anBlockBreak", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(attack = new Setting("anAttackEntity", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(eat = new Setting("anEat", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(clickGui = new Setting("anClickGui", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(delay = new Setting("anDelayMultiplier", this, 1, 1, 20, true));
    }


    public void onUpdate() {
        blockBrokeDelay++;
        blockPlacedDelay++;
        blockBrokeDelay++;
        jumpDelay++;
        attackDelay++;
        eattingDelay++;
        heldItem = mc.player.getHeldItemMainhand().getDisplayName();

        if (walk.getValBoolean()) {
            if (this.lastPositionUpdate + (5000L * delay.getValDouble()) < System.currentTimeMillis()) {

                double d0 = lastPositionX - mc.player.lastTickPosX;
                double d2 = lastPositionY - mc.player.lastTickPosY;
                double d3 = lastPositionZ - mc.player.lastTickPosZ;

                speed = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);

                if (speed <= 1 || speed > 5000) {
                } else {
                    if (clientSide.getValBoolean()) {
                        Command.sendClientMessage("I just walked " + new DecimalFormat("0").format(speed) + " blocks thanks to Osiris!");
                    } else {
                        mc.player.sendChatMessage("I just walked " + new DecimalFormat("0").format(speed) + " blocks thanks to Osiris!");
                    }
                    this.lastPositionUpdate = System.currentTimeMillis();
                    lastPositionX = mc.player.lastTickPosX;
                    lastPositionY = mc.player.lastTickPosY;
                    lastPositionZ = mc.player.lastTickPosZ;
                }
            }
        }

    }

    @EventHandler
    private Listener<LivingEntityUseItemEvent.Finish> eatListener = new Listener<>(event -> {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if(event.getEntity() == mc.player){
            if(event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold){
                eaten++;
                if(eattingDelay >= 300 * delay.getValDouble()) {
                    if (eat.getValBoolean() && eaten > randomNum) {
                        if(clientSide.getValBoolean()){
                            Command.sendClientMessage("I just ate " + eaten + " " + mc.player.getHeldItemMainhand().getDisplayName() + " thanks to Osiris!");
                        } else {
                            mc.player.sendChatMessage
                                    ("I just ate " + eaten + " " + mc.player.getHeldItemMainhand().getDisplayName() + " thanks to Osiris!");
                        }
                        eaten = 0;
                        this.eattingDelay = 0;
                    }
                }
            }
        }
    });

    @EventHandler
    private Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            blocksPlaced++;
            int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            if (this.blockPlacedDelay >= 150 * delay.getValDouble()) {
                if (place.getValBoolean() && blocksPlaced > randomNum){
                    String msg = "I just placed " + blocksPlaced + " "
                            + mc.player.getHeldItemMainhand().getDisplayName()
                            + " thanks to Osiris!";
                    if(clientSide.getValBoolean()){
                        Command.sendClientMessage(msg);
                    } else {
                        mc.player.sendChatMessage(msg);
                    }
                    blocksPlaced = 0;
                    this.blockPlacedDelay = 0;
                }
            }
        }
    });

    @EventHandler
    private Listener<DestroyBlockEvent> destroyListener = new Listener<>(event -> {
        blocksBroken++;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            if (blockBrokeDelay >= 300 * delay.getValDouble()) {
                if (breaking.getValBoolean() && blocksBroken > randomNum) {
                String msg = "I just broke " + blocksBroken + " "
                        + mc.world.getBlockState(event.getBlockPos()).getBlock().getLocalizedName()
                        + " thanks to Osiris!";
                if(clientSide.getValBoolean()){
                    Command.sendClientMessage(msg);
                } else {
                    mc.player.sendChatMessage(msg);
                }
                blocksBroken = 0;
                this.blockBrokeDelay = 0;
            }
        }
    });

    @EventHandler
    private Listener<AttackEntityEvent> attackListener = new Listener<>(event -> {
        if (attack.getValBoolean() && !(event.getTarget() instanceof EntityEnderCrystal)) {
            if (this.attackDelay >= 300 * delay.getValDouble()) {
                String msg = "I just attacked " + event.getTarget().getName()
                        + " with a " + mc.player.getHeldItemMainhand().getDisplayName()
                        + " thanks to Osiris!";
                if(clientSide.getValBoolean()){
                    Command.sendClientMessage(msg);
                } else {
                    mc.player.sendChatMessage(msg);
                }
                attackDelay = 0;
            }
        }
    });

    @EventHandler
    private Listener<PlayerJumpEvent> jumpListener = new Listener<>(event -> {
        if (jump.getValBoolean()) {
            if (jumpDelay >= 300 * delay.getValDouble()) {
                if(clientSide.getValBoolean()){
                    Command.sendClientMessage("I just jumped thanks to Osiris!");
                } else {
                    mc.player.sendChatMessage("I just jumped thanks to Osiris!");
                }
                jumpDelay = 0;
            }
        }
    });

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }

}
