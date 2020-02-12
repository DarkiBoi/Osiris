package me.finz0.osiris.module.modules.combat;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.friends.Friends;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoTrap extends Module {
    public AutoTrap() {
        super("AutoTrap", Category.COMBAT);
    }

    Setting rotate;
    Setting ec;
    Setting range;
    Setting bpt;

    int blocksPlaced;

    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(rotate = new Setting("atRotate", this, true));
        OsirisMod.getInstance().settingsManager.rSetting(ec = new Setting("atUseEchests", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(range = new Setting("atRange", this, 5, 0, 10, false));
        OsirisMod.getInstance().settingsManager.rSetting(bpt = new Setting("atBlocksPerTick", this, 8, 1, 15, true));
    }

    public void onUpdate(){
        mc.world.loadedEntityList.stream()
                .filter(e -> e instanceof EntityPlayer)
                .filter(e -> mc.player.getDistance(e) <= range.getValDouble())
                .filter(e -> e != mc.player)
                .filter(e -> !Friends.isFriend(e.getName()))
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e)))
                .forEach(e -> {
                    Vec3d vec = Surround.getInterpolatedPos(e, mc.getRenderPartialTicks());
                    BlockPos playerPos = new BlockPos(vec);
                    BlockPos x = playerPos.add(1, 0, 0);
                    BlockPos xMinus = playerPos.add(-1, 0, 0);
                    BlockPos z = playerPos.add(0, 0, 1);
                    BlockPos zMinus = playerPos.add(0, 0, -1);
                    BlockPos up = playerPos.add(0, 2, 0);
                    BlockPos xUp = x.up();
                    BlockPos xMinusUp = xMinus.up();
                    BlockPos zUp = z.up();
                    BlockPos zMinusUp = zMinus.up();
                    BlockPos xUp2 = xUp.up();
                    BlockPos xMinusUp2 = xMinusUp.up();
                    BlockPos zUp2 = zUp.up();
                    BlockPos zMinusUp2 = zMinusUp.up();

                    // search blocks in hotbar
                    int newSlot = -1;
                    for(int i = 0; i < 9; i++)
                    {
                        // filter out non-block items
                        ItemStack stack =
                                mc.player.inventory.getStackInSlot(i);

                        if(stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                            continue;
                        }
                        // only use whitelisted blocks
                        Block block = ((ItemBlock) stack.getItem()).getBlock();
                        if(!ec.getValBoolean()){
                            if (!(block instanceof BlockObsidian)) {
                                continue;
                            }
                        } else {
                            if (!(block instanceof BlockObsidian) && !(block instanceof BlockEnderChest)) {
                                continue;
                            }
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

                    blocksPlaced = 0;
                    if(blocksPlaced > bpt.getValDouble()){
                        blocksPlaced = 0;
                        return;
                    }
//                    if(mc.player.getName().equalsIgnoreCase("LolmanPlox")) return;
                        // x
                        if (shouldPlace(x)) {
                            Surround.placeBlockScaffold(x, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // xMinus
                        if (shouldPlace(xMinus)) {
                            Surround.placeBlockScaffold(xMinus, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // z
                        if (shouldPlace(z)) {
                            Surround.placeBlockScaffold(z, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // zMinus
                        if (shouldPlace(zMinus)) {
                            Surround.placeBlockScaffold(zMinus, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // xUp
                        if (shouldPlace(xUp)) {
                            Surround.placeBlockScaffold(xUp, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // xMinusUp
                        if (shouldPlace(xMinusUp)) {
                            Surround.placeBlockScaffold(xMinusUp, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // zUp
                        if (shouldPlace(zUp)) {
                            Surround.placeBlockScaffold(zUp, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // zMinusUp
                        if (shouldPlace(zMinusUp)) {
                            Surround.placeBlockScaffold(zMinusUp, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // xUp2
                        if (shouldPlace(xUp2)) {
                            Surround.placeBlockScaffold(xUp2, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // xMinusUp2
                        if (shouldPlace(xMinusUp2)) {
                            Surround.placeBlockScaffold(xMinusUp2, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // zUp2
                        if (shouldPlace(zUp2)) {
                            Surround.placeBlockScaffold(zUp2, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // zMinusUp2
                        if (shouldPlace(zMinusUp2)) {
                            Surround.placeBlockScaffold(zMinusUp2, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                        // up
                        if (shouldPlace(up)) {
                            Surround.placeBlockScaffold(up, rotate.getValBoolean());
                            blocksPlaced++;
                        }
                    mc.player.inventory.currentItem = oldSlot;
            });
    }

    private boolean shouldPlace(BlockPos pos){
        List<Entity> entities =  mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream()
                .filter(e -> !(e instanceof EntityItem))
                .filter(e -> !(e instanceof EntityXPOrb))
                .collect(Collectors.toList());
        boolean a = entities.isEmpty();
        boolean b = mc.world.getBlockState(pos).getMaterial().isReplaceable();
        boolean c = blocksPlaced < bpt.getValDouble();
        return a && b && c;
    }
}
