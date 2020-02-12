package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.chat.Announcer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGuiModule extends Module {
    public ClickGuiModule INSTANCE;
    public ClickGuiModule() {
        super("ClickGUI", Category.GUI);
        setBind(Keyboard.KEY_P);
        INSTANCE = this;
    }

    public void setup(){
        ArrayList<String> options = new ArrayList<>();
        options.add("New");
        options.add("JellyLike");
        options.add("f0nzi");
        options.add("Windows");
        OsirisMod.getInstance().settingsManager.rSetting(new Setting("Design", this, "New", options));
        OsirisMod.getInstance().settingsManager.rSetting(new Setting("GuiRainbow", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(new Setting("GuiRed", this, 255, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(new Setting("GuiGreen", this, 26, 0, 255, true));
        OsirisMod.getInstance().settingsManager.rSetting(new Setting("GuiBlue", this, 42, 0, 255, true));
    }

    public void onEnable(){
        mc.displayGuiScreen(OsirisMod.getInstance().clickGui);
        if(((Announcer)ModuleManager.getModuleByName("Announcer")).clickGui.getValBoolean() && ModuleManager.isModuleEnabled("Announcer") && mc.player != null)
            if(((Announcer)ModuleManager.getModuleByName("Announcer")).clientSide.getValBoolean()){
                Command.sendClientMessage(Announcer.guiMessage);
            } else {
                mc.player.sendChatMessage(Announcer.guiMessage);
            }
        disable();
    }
}
