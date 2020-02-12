package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.GeometryMasks;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HoleESP extends Module {
    public HoleESP() {
        super("HoleESP", Category.RENDER);
    }

    Setting rangeS;
    Setting r;
    Setting g;
    Setting b;
    Setting a;
    Setting rainbow;
    Setting mode;
    Setting width;

    private final BlockPos[] surroundOffset = {
            new BlockPos(0, -1, 0), // down
            new BlockPos(0, 0, -1), // north
            new BlockPos(1, 0, 0), // east
            new BlockPos(0, 0, 1), // south
            new BlockPos(-1, 0, 0) // west
    };

    public void setup(){
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Full");
        modes.add("Bottom");
        modes.add("Outline");
        modes.add("OutlineBottom");
        rangeS = new Setting("heRange", this, 8, 0, 20, true);
        OsirisMod.getInstance().settingsManager.rSetting(rangeS);
        r = new Setting("heRed", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(r);
        g = new Setting("heGreen", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(g);
        b = new Setting("heBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(b);
        a = new Setting("heAlpha", this, 50, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(a);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("heRainbow", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(mode = new Setting("heMode", this, "Full", modes));
        OsirisMod.getInstance().settingsManager.rSetting(width = new Setting("heLineWidth", this, 3, 1, 10, true));
    }

    private ConcurrentHashMap<BlockPos, Boolean> safeHoles;

    @Override
    public void onUpdate() {

        if (safeHoles == null) {
            safeHoles = new ConcurrentHashMap<>();
        } else {
            safeHoles.clear();
        }

        int range = (int) Math.ceil(rangeS.getValDouble());

        List<BlockPos> blockPosList = getSphere(getPlayerPos(), range, range, false, true, 0);

        for (BlockPos pos : blockPosList) {

            // block gotta be air
            if (!mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            // block 1 above gotta be air
            if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            // block 2 above gotta be air
            if (!mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            boolean isSafe = true;
            boolean isBedrock = true;

            for (BlockPos offset : surroundOffset) {
                Block block = mc.world.getBlockState(pos.add(offset)).getBlock();
                if (block != Blocks.BEDROCK) {
                    isBedrock = false;
                }
                if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                    isSafe = false;
                    break;
                }
            }

            if (isSafe) {
                safeHoles.put(pos, isBedrock);
            }

        }

    }

    @Override
    public void onWorldRender(final RenderEvent event) {
        if (mc.player == null || safeHoles == null) {
            return;
        }

        if (safeHoles.isEmpty()) {
            return;
        }

        if(!mode.getValString().equalsIgnoreCase("Outline") && !mode.getValString().equalsIgnoreCase("OutlineBottom"))
            OsirisTessellator.prepare(GL11.GL_QUADS);

        safeHoles.forEach((blockPos, isBedrock) -> {
            drawBox(blockPos, (int)r.getValDouble(), (int)g.getValDouble(), (int)b.getValDouble());
        });

        if(!mode.getValString().equalsIgnoreCase("Outline") && !mode.getValString().equalsIgnoreCase("OutlineBottom"))
            OsirisTessellator.release();

    }

    private void drawBox(BlockPos blockPos, int r, int g, int b) {
        Color color;
        Color c = Rainbow.getColor();
        AxisAlignedBB bb = mc.world.getBlockState(blockPos).getSelectedBoundingBox(mc.world, blockPos);
        if(rainbow.getValBoolean())
            color = new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)a.getValDouble());
        else
            color = new Color(r, g, b, (int)a.getValDouble());
        if(mode.getValString().equalsIgnoreCase("Outline")){
            OsirisTessellator.drawBoundingBox(bb, width.getValInt(), color.getRGB());
        } else if (mode.getValString().equalsIgnoreCase("Bottom")) {
            OsirisTessellator.drawBox(blockPos, color.getRGB(), GeometryMasks.Quad.DOWN);
        } else if(mode.getValString().equalsIgnoreCase("OutlineBottom")){
            OsirisTessellator.drawBoundingBoxBottom(bb, width.getValInt(), color.getRGB());
        } else {
            OsirisTessellator.drawBox(blockPos, color.getRGB(), GeometryMasks.Quad.ALL);
        }
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

}
