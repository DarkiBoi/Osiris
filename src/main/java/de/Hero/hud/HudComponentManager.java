package de.Hero.hud;

import de.Hero.clickgui.ClickGUI;
import de.Hero.hud.components.FpsComponent;

public class HudComponentManager {
    public HudComponentManager(double ix, double iy, ClickGUI parent){
        ClickGUI.panels.add(new FpsComponent(ix, iy, parent));
    }
}
