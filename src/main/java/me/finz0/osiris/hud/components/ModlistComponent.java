package me.finz0.osiris.hud.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.ModList;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.Comparator;

public class ModlistComponent extends Panel {
    public ModlistComponent(double ix, double iy, ClickGUI parent) {
        super("Mods", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;

    }



    ModList mod = ((ModList) ModuleManager.getModuleByName("ModList"));

    Color c;
    boolean font;
    Color text;
    Color color;

    int sort;
    int modCount;


    public void drawHud(){
        doStuff();
        if(mod.sortUp.getValBoolean()){ sort = -1;
        } else { sort = 1; }
        modCount = 0;
        ModuleManager.getModules()
                .stream()
                .filter(Module::isEnabled)
                .filter(Module::isDrawn)
                .sorted(Comparator.comparing(module -> mc.fontRenderer.getStringWidth(module.getName() + ChatFormatting.GRAY + " " + module.getHudInfo()) * (-1)))
                .forEach(m -> {
                    int stringWidth1;
                    if(font) stringWidth1 =  OsirisMod.fontRenderer.getStringWidth(m.getName() + ChatFormatting.GRAY + " ");
                    else stringWidth1 =  mc.fontRenderer.getStringWidth(m.getName() + ChatFormatting.GRAY + " ");
                    int stringWidth2;
                    if(font) stringWidth2 =  OsirisMod.fontRenderer.getStringWidth(m.getHudInfo());
                    else stringWidth2 =  mc.fontRenderer.getStringWidth(m.getHudInfo());
                    this.width = stringWidth1 + stringWidth2;
                    if(mod.sortUp.getValBoolean()) {
                        if (mod.right.getValBoolean()) {
                            drawText(m.getName() + " ", (int) x - (stringWidth1 + stringWidth2), (int) y + (modCount * 10), text.getRGB());
                            drawText(m.getHudInfo(), (int) ( x - (stringWidth1 + stringWidth2)) + stringWidth2, (int) y + (modCount * 10), Color.GRAY.getRGB());
                        } else {
                            drawText(m.getName() + " ", (int) x, (int) y + (modCount * 10), text.getRGB());
                            drawText(m.getHudInfo(), (int) x + stringWidth1, (int) y + (modCount * 10), Color.GRAY.getRGB());
                        }
                        modCount++;
                    } else {
                        if (mod.right.getValBoolean()) {
                            drawText(m.getName() + " ", (int) x - stringWidth1, (int) y + (modCount * -10), text.getRGB());
                            drawText(m.getHudInfo(), (int) (x - stringWidth1) + stringWidth2, (int) y + (modCount * -10), Color.GRAY.getRGB());
                        } else {
                            drawText(m.getName() + " ", (int) x, (int) (y + (modCount * 10)) + (modCount * -10), text.getRGB());
                            drawText(m.getHudInfo(), (int) x + stringWidth1, (int) y + (modCount * -10), Color.GRAY.getRGB());
                        }
                        modCount++;
                    }
                });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        doStuff();
        c = new Color(50, 50, 50, 100);
        if(isHudComponentPinned) c = new Color(ColorUtil.getClickGUIColor().darker().getRed(), ColorUtil.getClickGUIColor().darker().getGreen(), ColorUtil.getClickGUIColor().darker().getBlue(), 100);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        modCount = 0;

        if(extended) {
            double startY = y + height;
            Gui.drawRect((int) x, (int) startY, (int) x + (int) width, (int) startY + (int) height, c.getRGB());
            if(mod.sortUp.getValBoolean()){ sort = -1;
            } else { sort = 1; }
            ModuleManager.getModules()
                    .stream()
                    .filter(Module::isEnabled)
                    .filter(Module::isDrawn)
                    .sorted(Comparator.comparing(module -> mc.fontRenderer.getStringWidth(module.getName() + ChatFormatting.GRAY + " " + module.getHudInfo()) * (-1)))
                    .forEach(m -> {
                        int stringWidth =  mc.fontRenderer.getStringWidth(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo());
                        this.width = stringWidth;
                        if(mod.sortUp.getValBoolean()) {
                            if (mod.right.getValBoolean()) {
                                this.drawText(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), (int) x - stringWidth, (int) y + (modCount * 10), text.getRGB());
                                Gui.drawRect((int)x - stringWidth, (int)y + (modCount * 10), (int)x, (int)y, c.getRGB());
                            } else {
                                this.drawText(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), (int) x, (int) y + (modCount * 10), text.getRGB());
                                Gui.drawRect((int)x, (int)y + (modCount * 10), (int)x + stringWidth, (int)y, c.getRGB());
                            }
                            modCount++;
                        } else {
                            if (mod.right.getValBoolean()) {
                                this.drawText(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), (int) x - stringWidth, (int) y + (modCount * -10), text.getRGB());
                                Gui.drawRect((int)x - stringWidth, (int)y + (modCount * -10), (int)x, (int)y, c.getRGB());
                            } else {
                                this.drawText(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), (int) x, (int) y + (modCount * -10), text.getRGB());
                                Gui.drawRect((int)x, (int)y + (modCount * -10), (int)x + stringWidth, (int)y, c.getRGB());
                            }
                            modCount++;
                        }
                    });
        }
        this.height = (modCount * 10) + 2;
        if(modCount <=0 || !extended)
            FontUtil.drawStringWithShadow(title, x, y, 0xffffffff);
        if(width < 10) width = 10;
        if(height < 10) height = 10;
    }

    private void doStuff() {
        color = new Color(mod.red.getValInt(), mod.green.getValInt(), mod.blue.getValInt());
        text = mod.rainbow.getValBoolean() ? Rainbow.getColor() : color;
        font = mod.customFont.getValBoolean();
    }

    private void drawText(String text, int x, int y, int color){
        if(font) OsirisMod.fontRenderer.drawStringWithShadow(text, x, y, color);
        else mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }
}
