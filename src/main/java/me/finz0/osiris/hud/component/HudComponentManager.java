package me.finz0.osiris.hud.component;

import me.finz0.osiris.hud.component.components.FpsComponent;

import java.util.ArrayList;
import java.util.List;

public class HudComponentManager {
    List<HudComponentText> textComponents;

    public HudComponentManager(){
        textComponents = new ArrayList<>();
        textComponents.add(new FpsComponent());
    }

    //public void draw(){textComponents.forEach(c -> c.drawScreen());}
}
