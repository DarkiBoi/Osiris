package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;

public class FovModule extends Module {
    public FovModule() {
        super("FOV", Category.RENDER);
        OsirisMod.getInstance().settingsManager.rSetting(fov = new Setting("FovAmount", this, 90, 0, 180, true));
        setDrawn(false);
    }

    Setting fov;

    public void onUpdate(){
        mc.gameSettings.fovSetting = (float)fov.getValInt();
    }
}
