package me.finz0.osiris.module.modules.combat;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SmartOffhand extends Module {
    public SmartOffhand() {
        super("SmartOffhand", Category.COMBAT);
    }

    public int totems;
    int crystals;
    boolean moving = false;
    boolean returnI = false;
    Item item;

    Setting health;
    Setting crystalCheck;
    Setting itemSetting;

    public void setup(){
        ArrayList<String> items = new ArrayList<>();
        items.add("Crystal");
        items.add("Gapple");
        OsirisMod.getInstance().settingsManager.rSetting(health = new Setting("soHealth", this, 10, 1, 40, true));
        OsirisMod.getInstance().settingsManager.rSetting(crystalCheck = new Setting("soCrystalCheck", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(itemSetting = new Setting("soItem", this, "Crystal", items));
    }

    public void onUpdate() {
        if(itemSetting.getValString().equalsIgnoreCase("Gapple")) item = Items.GOLDEN_APPLE;
        else item = Items.END_CRYSTAL;

        if(mc.currentScreen instanceof GuiContainer) return;
        if (returnI) {
            int t = -1;
            for (int i = 0; i < 45; i++)
                if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
                    t = i;
                    break;
                }
            if (t == -1) return;
            mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
            returnI = false;
        }
        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        crystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == item).mapToInt(ItemStack::getCount).sum();
        if (shouldTotem() && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) totems++;
        else if (!shouldTotem() && mc.player.getHeldItemOffhand().getItem() == item) crystals += mc.player.getHeldItemOffhand().getCount();
        else {
            if (moving) {
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                moving = false;
                returnI = true;
                return;
            }
            if (mc.player.inventory.getItemStack().isEmpty()) {
                if(!shouldTotem() && mc.player.getHeldItemOffhand().getItem() == item) return;
                if(shouldTotem() && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) return;
                if(!shouldTotem()) {
                    if(crystals == 0) return;
                    int t = -1;
                    for (int i = 0; i < 45; i++)
                        if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
                            t = i;
                            break;
                        }
                    if (t == -1) return; // Should never happen!
                    mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
                    moving = true;
                } else {
                    if(totems == 0) return;
                    int t = -1;
                    for (int i = 0; i < 45; i++)
                        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                            t = i;
                            break;
                        }
                    if (t == -1) return; // Should never happen!
                    mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
                    moving = true;
                }
            } else {
                int t = -1;
                for (int i = 0; i < 45; i++)
                    if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
                        t = i;
                        break;
                    }
                if (t == -1) return;
                mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
            }
        }
    }

    private boolean shouldTotem(){
        boolean hp = (mc.player.getHealth() + mc.player.getAbsorptionAmount()) <= health.getValInt();
        boolean endcrystal = !isCrystalsAABBEmpty();
        //boolean totemCount = (totems > 0);
        if(crystalCheck.getValBoolean())
            return (hp || endcrystal);
        else
            return  hp;
    }

    private boolean isEmpty(BlockPos pos){
        List<Entity> crystalsInAABB =  mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream()
                .filter(e -> e instanceof EntityEnderCrystal)
                .collect(Collectors.toList());
        return crystalsInAABB.isEmpty();
    }

    private boolean isCrystalsAABBEmpty(){
        return  isEmpty(mc.player.getPosition().add(1, 0, 0)) &&
                isEmpty(mc.player.getPosition().add(-1, 0, 0)) &&
                isEmpty(mc.player.getPosition().add(0, 0, 1)) &&
                isEmpty(mc.player.getPosition().add(0, 0, -1)) &&
                isEmpty(mc.player.getPosition());
    }

}
