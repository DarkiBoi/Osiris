package me.finz0.osiris.util;

import me.finz0.osiris.event.EventProcessor;

import java.awt.*;

public class Rainbow {
    public static int getInt(){
        return EventProcessor.INSTANCE.getRgb();
    }

    public static Color getColor(){
        return EventProcessor.INSTANCE.getC();
    }
}
