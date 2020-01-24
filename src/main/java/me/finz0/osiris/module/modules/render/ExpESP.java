package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.modules.combat.Surround;
import me.finz0.osiris.util.GeometryMasks;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ExpESP extends Module {
    public ExpESP() {
        super("ExpESP", Category.RENDER);
    }

    Setting red;
    Setting blue;
    Setting green;
    Setting rainbow;
    Setting alpha;

    public void setup(){
        red = new Setting("esRed", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        green = new Setting("esGreen", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        blue = new Setting("esBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        alpha = new Setting("esAlpha", this, 150, 0, 255, false);
        OsirisMod.getInstance().settingsManager.rSetting(alpha);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("esRainbow", this, false));
    }

    public void onWorldRender(RenderEvent event) {
        mc.world.loadedEntityList.stream()
                .filter(e -> e != mc.player)
                .filter(e -> e instanceof EntityExpBottle)
                .filter(e -> mc.player.getDistance(e) > 1)
                .forEach(e -> {
                    Vec3d pos = e.getPositionVector();
                    Color c;
                    Color r = Rainbow.getColor();
                    if(rainbow.getValBoolean())
                        c = new Color(r.getRed(), r.getGreen(), r.getBlue());
                    else
                        c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
                    OsirisTessellator.prepare(GL11.GL_QUADS);
                    GlStateManager.pushMatrix();
                    GL11.glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, (float)alpha.getValDouble() / 255);
                    if(mc.player.getDistance(e) > 1)
                        OsirisTessellator.drawSmallBox(pos, c.getRed(), c.getGreen(), c.getBlue(), (int)alpha.getValDouble(), GeometryMasks.Quad.ALL);
                    GL11.glColor4f(1, 1, 1, 1);
                    OsirisTessellator.release();
                    GlStateManager.popMatrix();
                });
    }
}
