package me.finz0.osiris.module.modules.combat;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoWeb extends Module {
    public AutoWeb() {
        super("AutoWeb", Category.COMBAT);
        OsirisMod.getInstance().settingsManager.rSetting(range = new Setting("awRange", this, 5, 0, 10, false));
        OsirisMod.getInstance().settingsManager.rSetting(tickDelay = new Setting("awTickDelay", this, 5, 0, 10, false));
        OsirisMod.getInstance().settingsManager.rSetting(rotate = new Setting("awRotate", this, true));
        OsirisMod.getInstance().settingsManager.rSetting(rotate = new Setting("awPlaceAtHead", this, false));
    }

    Setting range;
    Setting rotate;
    Setting head;
    Setting tickDelay;
    private int waitCounter = -1;

    public void onUpdate(){
        List<Entity> entities = mc.world.loadedEntityList.stream()
                .filter(e -> e != mc.player)
                .filter(e -> mc.player.getDistance(e) <= range.getValDouble())
                .filter(e -> e instanceof EntityPlayer)
                .filter(e -> !Friends.isFriend(e.getName()))
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e)))
                .collect(Collectors.toList());

        for(Entity e : entities){

        // search blocks in hotbar
        int newSlot = -1;
        for(int i = 0; i < 9; i++)
        {
            // filter out non-block items
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if(stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }
            // only use whitelisted blocks
            Block block = ((ItemBlock) stack.getItem()).getBlock();
                if (!(block instanceof BlockWeb)) {
                    continue;
                }

            newSlot = i;
            break;
        }

        // check if any blocks were found
        if(newSlot == -1)
            return;

        // set slot
        int oldSlot = mc.player.inventory.currentItem;
        mc.player.inventory.currentItem = newSlot;

            if (tickDelay.getValDouble() > 0) {
                if (waitCounter < tickDelay.getValDouble()) {
                    waitCounter++;
                    BlockUtils.placeBlockScaffold(e.getPosition(), rotate.getValBoolean());
                    mc.player.inventory.currentItem = oldSlot;
                    return;
                } else {
                    waitCounter = 0;
                }
            }

            if(head.getValBoolean()) {
                if (tickDelay.getValDouble() > 0) {
                    if (waitCounter < tickDelay.getValDouble()) {
                        waitCounter++;
                        BlockUtils.placeBlockScaffold(e.getPosition().up(), rotate.getValBoolean());
                        mc.player.inventory.currentItem = oldSlot;
                        return;
                    } else {
                        waitCounter = 0;
                    }
                }
            }

        }
    }

    public void onEnable(){
        waitCounter = -1;
    }

    public void onDisable(){
        waitCounter = -1;
    }
}
