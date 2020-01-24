package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.Rainbow;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Tps extends Module {
    public static Tps INSTANCE;
    public Tps() {
        super("TPS", Category.GUI);
        setDrawn(false);
        INSTANCE = this;
    }

    private float[] tickRates = new float[20];
    private int nextIndex = 0;
    private long timeLastTimeUpdate;

    Setting red;
    Setting green;
    Setting blue;
    Setting x;
    Setting y;
    Setting rainbow;
    DecimalFormat decimalFormat = new DecimalFormat("##.#");

    public void setup(){
        red = new Setting("TpsRed", this, 255, 0, 255, true);
        green = new Setting("TpsGreen", this, 255, 0, 255, true);
        blue = new Setting("TpsBlue", this, 255, 0, 255, true);
        OsirisMod.getInstance().settingsManager.rSetting(red);
        OsirisMod.getInstance().settingsManager.rSetting(green);
        OsirisMod.getInstance().settingsManager.rSetting(blue);
        x = new Setting("TpsX", this, 2, 0, 1000, true);
        y = new Setting("TpsY", this, 2, 0, 1000, true);
        OsirisMod.getInstance().settingsManager.rSetting(x);
        OsirisMod.getInstance().settingsManager.rSetting(y);
        OsirisMod.getInstance().settingsManager.rSetting(rainbow = new Setting("tpsRainbow", this, false));
    }

    public void onRender(){
        Color c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());
        if(!rainbow.getValBoolean())
            mc.fontRenderer.drawStringWithShadow(decimalFormat.format(getTickRate()) + " TPS", (int)x.getValDouble(), (int)y.getValDouble(), c.getRGB());
        else
            mc.fontRenderer.drawStringWithShadow(decimalFormat.format(getTickRate()) + " TPS", (int)x.getValDouble(), (int)y.getValDouble(), Rainbow.getInt());
    }

    public void onEnable(){
        OsirisMod.EVENT_BUS.subscribe(this);
        this.nextIndex = 0;
        this.timeLastTimeUpdate = -1L;
        Arrays.fill(tickRates, 0.0F);
    }

    public void onDisable(){
        OsirisMod.EVENT_BUS.unsubscribe(this);
    }

    //Credit 086

    @EventHandler
    Listener<PacketEvent.Receive> packetEventListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            onTimeUpdate();
        }
    });

    public float getTickRate() {
        float numTicks = 0.0F;
        float sumTickRates = 0.0F;
        for (float tickRate : tickRates) {
            if (tickRate > 0.0F) {
                sumTickRates += tickRate;
                numTicks += 1.0F;
            }
        }
        return MathHelper.clamp(sumTickRates / numTicks, 0.0F, 20.0F);
    }

    public void onTimeUpdate() {
        if (this.timeLastTimeUpdate != -1L) {
            float timeElapsed = (float) (System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0F;
            tickRates[(this.nextIndex % tickRates.length)] = MathHelper.clamp(20.0F / timeElapsed, 0.0F, 20.0F);
            this.nextIndex += 1;
        }
        this.timeLastTimeUpdate = System.currentTimeMillis();
    }
}
