package me.finz0.osiris.hud;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import me.finz0.osiris.hud.components.*;

import java.util.ArrayList;
import java.util.List;

public class HudComponentManager {
    public HudComponentManager(double ix, double iy, ClickGUI parent){
        hudComponents = new ArrayList<>();
        addComponent(new FpsComponent(ix, iy, parent));
        addComponent(new TpsComponent(ix, iy + 10, parent));
        addComponent(new ModlistComponent(ix, iy + 20, parent));
    }

    public static List<Panel> hudComponents;

    private void addComponent(Panel p){
        hudComponents.add(p);
    }

    public static Panel getHudComponentByName(String name){
            Panel pa = null;
            for(Panel p : hudComponents){
                if(p.title.equalsIgnoreCase(name)) pa = p;
            }
            return pa;
    }
}
