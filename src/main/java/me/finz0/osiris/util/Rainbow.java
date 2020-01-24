package me.finz0.osiris.util;

import me.finz0.osiris.event.ForgeEventProcessor;

import java.awt.*;

public class Rainbow {
    public static int getInt(){
        return ForgeEventProcessor.INSTANCE.getRgb();
    }

    public static Color getColor(){
        return ForgeEventProcessor.INSTANCE.getC();
    }
}
