package me.finz0.osiris.command.commands;

import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;

public class SetCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"set"};
    }

    @Override
    public String getSyntax() {
        return "set <Module> <Setting> <Value>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        for(Module m : ModuleManager.getModules()) {
            if(m.getName().equalsIgnoreCase(args[0])) {
                OsirisMod.getInstance().settingsManager.getSettingsByMod(m).stream().filter(s -> s.getName().equalsIgnoreCase(args[1])).forEach(s->{
                    if(s.isSlider()) {
                        if(Double.parseDouble(args[2]) > s.getMax()) s.setValDouble(s.getMax());
                        if(Double.parseDouble(args[2]) < s.getMin()) s.setValDouble(s.getMin());
                        if(!(Double.parseDouble(args[2]) > s.getMax()) && !(Double.parseDouble(args[2]) < s.getMin())) s.setValDouble(Double.parseDouble(args[2]));
                        Command.sendClientMessage(s.getName() + " set to " + s.getValDouble());
                    }
                    if(s.isCheck()){
                        s.setValBoolean(Boolean.parseBoolean(args[2]));
                        Command.sendClientMessage(s.getName() + " set to " + s.getValBoolean());
                    }
                    if(s.isCombo()){
                        if(!s.getOptions().contains(args[2])) return;
                        s.setValString(args[2]);
                        Command.sendClientMessage(s.getName() + " set to" + s.getValString());
                    }
                });
            }
        }
    }
}
