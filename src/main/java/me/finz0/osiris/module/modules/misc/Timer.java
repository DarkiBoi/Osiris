package me.finz0.osiris.module.modules.misc;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.TpsUtils;

import java.text.DecimalFormat;

public class Timer extends Module {
    public static Timer INSTANCE;
    public Timer() {
        super("Timer", Category.MISC);
        INSTANCE = this;
    }

    Setting tpsSync;
    Setting multiplier;

    public void setup(){
        tpsSync = new Setting("timerTpsSync", this, true);
        OsirisMod.getInstance().settingsManager.rSetting(tpsSync);
        multiplier = new Setting("timerMultiplier", this, 5.0, 0.1, 20.0, false);
        OsirisMod.getInstance().settingsManager.rSetting(multiplier);
    }

    public void onUpdate(){
        mc.timer.tickLength = 50f / getMultiplier();
    }

    public void onDisable(){
        mc.timer.tickLength = 50f;
    }

    public float getMultiplier() {
        if (this.isEnabled()) {
            if (tpsSync.getValBoolean()) {
                float f = TpsUtils.getTickRate() / 20 * (float)multiplier.getValDouble();
                if(f < 0.1f) f = 0.1f;
                return f;
            } else {
                return (float)multiplier.getValDouble();
            }
        } else {
            return 1.0f;
        }
    }

    public String getHudInfo(){
        return new DecimalFormat("#0.##").format(getMultiplier());
    }

}
