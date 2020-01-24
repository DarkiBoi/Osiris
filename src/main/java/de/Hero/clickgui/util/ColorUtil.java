package de.Hero.clickgui.util;

import java.awt.Color;

import de.Hero.settings.SettingsManager;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.util.Rainbow;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		if(OsirisMod.getInstance().settingsManager.getSettingByName("GuiRainbow").getValBoolean()) {
			return Rainbow.getColor();
		}else
			return new Color((int) OsirisMod.getInstance().settingsManager.getSettingByName("GuiRed").getValDouble(), (int) OsirisMod.getInstance().settingsManager.getSettingByName("GuiGreen").getValDouble(), (int)OsirisMod.getInstance().settingsManager.getSettingByName("GuiBlue").getValDouble());
	}
}
