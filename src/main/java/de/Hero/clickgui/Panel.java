package de.Hero.clickgui;

import java.awt.Color;
import java.util.ArrayList;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import net.minecraft.client.gui.GuiScreen;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class Panel {
	public String title;
	public double x;
	public double y;
	public double x2;
	public double y2;
	public double width;
	public double height;
	public boolean dragging;
	public boolean extended;
	public boolean visible;
	public ArrayList<ModuleButton> Elements = new ArrayList<>();
	public ClickGUI clickgui;

	protected static final Minecraft mc = Minecraft.getMinecraft();
	public boolean isHudComponent = false;
	public boolean isHudComponentPinned = false;

	/*
	 * Konstrukor
	 */
	public Panel(String ititle, double ix, double iy, double iwidth, double iheight, boolean iextended, ClickGUI parent) {
		this.title = ititle;
		this.x = ix;
		this.y = iy;
		this.width = iwidth;
		this.height = iheight;
		this.extended = iextended;
		this.dragging = false;
		this.visible = true;
		this.clickgui = parent;
		setup();
	}

	/*
	 * Wird in ClickGUI berschrieben, sodass auch ModuleButtons hinzugefgt werden knnen :3
	 */
	public void setup() {}

	public void drawHud(){}

	/*
	 * Rendern des Elements.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (!this.visible)
			return;

		if (this.dragging) {
			x = x2 + mouseX;
			y = y2 + mouseY;
		}
		
		Color temp = ColorUtil.getClickGUIColor().darker();
		int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();

		if(OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("New")){
			Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, 0xff121212);
			Gui.drawRect((int)x - 2, (int)y, (int)x, (int)y + (int)height, outlineColor);
			FontUtil.drawStringWithShadow(title, x + 2, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffefefef);
		}else if(OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("JellyLike")){
			Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, 0xff121212);
			Gui.drawRect((int)x + 4,			(int)y + 2, (int)x + (int)4.3, 		(int)y + (int)height - 2, 0xffaaaaaa);
			Gui.drawRect((int)x - 4 + (int)width, (int)y + 2, (int)x - (int)4.3 + (int)width, (int)y + (int)height - 2, 0xffaaaaaa);
			FontUtil.drawTotalCenteredStringWithShadow(title, x + width / 2, y + height / 2, 0xffefefef);
		}else if(OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("f0nzi")) {
			Gui.drawRect((int)x - 2, (int)y, (int)x + (int)width + 2, (int)y + (int)height, 0xff121212);
			//Gui.drawRect((int) x + 4, (int) y + 2, (int) x + (int) 4.3, (int) y + (int) height - 2, Color.GRAY.getRGB());
			//Gui.drawRect((int) x - 4 + (int) width, (int) y + 2, (int) x - (int) 4.3 + (int) width, (int) y + (int) height - 2, Color.GRAY.getRGB());
			Gui.drawRect((int) x - 2, (int)(y + height - 2), (int) (x + width + 2), (int)(y + height), ColorUtil.getClickGUIColor().darker().getRGB());
			FontUtil.drawTotalCenteredStringWithShadow(title, x + width / 2, y + height / 2, 0xffffffff);
		}else if(OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("Windows")){
			Gui.drawRect((int)x - 2, (int)y - 2, (int)x + (int)width + 2, (int)y + (int)height + 2, Color.GRAY.getRGB());
			Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, Color.BLUE.darker().darker().darker().getRGB());
			FontUtil.drawStringWithShadow(title, x + 2, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffffffff);
		}
		if (this.extended && !Elements.isEmpty()) {
			double startY = y + height;
			int epanelcolor = OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("New") ? 0xff232323 :
					OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("JellyLike") ? 0xbb151515 :
					OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("f0nzi") ? 0xbb303030 :
					OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("Windows") ? 0xffffffff : 0;
			for (ModuleButton et : Elements) {
				if(OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("New")){
					Gui.drawRect((int)x - 2, (int)startY, (int)x + (int)width, (int)startY + (int)et.height + 1, outlineColor);
				}
				if(OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("f0nzi")){
					Gui.drawRect((int)x - 2, (int)startY, (int)x, (int)startY + (int)height - 1, ColorUtil.getClickGUIColor().darker().getRGB());
					Gui.drawRect((int)x + (int)width, (int)startY, (int)x + (int)width + 2, (int)startY + (int)height - 1, ColorUtil.getClickGUIColor().darker().getRGB());
				}
				if(OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("Windows")){
					Gui.drawRect((int)x - 2, (int)startY, (int)x, (int)startY + (int)height - 1, Color.GRAY.getRGB());
					Gui.drawRect((int)x + (int)width, (int)startY, (int)x + (int)width + 2, (int)startY + (int)height - 1, Color.GRAY.getRGB());
				}
				Gui.drawRect((int)x, (int)startY, (int)x + (int)width, (int)startY + (int)et.height + 1, epanelcolor);
				et.x = x + 2;
				et.y = startY;
				et.width = width - 4;
				et.drawScreen(mouseX, mouseY, partialTicks);
				startY += et.height + 1;
			}
			if(OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("f0nzi"))
				Gui.drawRect((int)x, (int)startY, (int)x + (int)width, (int)startY + 2, ColorUtil.getClickGUIColor().darker().getRGB());
			if(OsirisMod.getInstance().settingsManager.getSettingByName("Design").getValString().equalsIgnoreCase("Windows"))
				Gui.drawRect((int)x, (int)startY, (int)x + (int)width, (int)startY + 2, Color.GRAY.getRGB());
			Gui.drawRect((int)x, (int)startY + 1, (int)x + (int)width, (int)startY + 1, epanelcolor);
		}
	}

	/*
	 * Zum Bewegen und Extenden des Panels
	 * usw.
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!this.visible) {
			return false;
		}
		if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
			x2 = this.x - mouseX;
			y2 = this.y - mouseY;
			dragging = true;
			return true;
		} else if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
			extended = !extended;
			return true;
		} else if (mouseButton == 2 && isHovered(mouseX, mouseY)) {
			if(isHudComponent)
				isHudComponentPinned = !isHudComponentPinned;
		} else if (extended) {
			for (ModuleButton et : Elements) {
				if (et.mouseClicked(mouseX, mouseY, mouseButton)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Damit das Panel auch losgelassen werden kann 
	 */
	public void mouseReleased(int mouseX, int mouseY, int state) {
		if (!this.visible) {
			return;
		}
		if (state == 0) {
			this.dragging = false;
		}
	}

	/*
	 * HoverCheck
	 */
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
