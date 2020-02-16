package me.finz0.osiris;

import de.Hero.clickgui.ClickGUI;
import me.finz0.osiris.hud.HudComponentManager;
import de.Hero.settings.SettingsManager;
import me.finz0.osiris.command.CommandManager;
import me.finz0.osiris.event.EventProcessor;
import me.finz0.osiris.macro.MacroManager;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.util.CapeUtils;
import me.finz0.osiris.util.ConfigUtils;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.util.TpsUtils;
import me.finz0.osiris.util.font.CFontRenderer;
import me.finz0.osiris.waypoint.WaypointManager;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;

@Mod(modid = OsirisMod.MODID, name = OsirisMod.MODNAME, version = OsirisMod.MODVER, clientSideOnly = true)
public class OsirisMod {
    public static final String MODID = "osiris";
    public static final String MODNAME = "Osiris";
    public static final String MODVER = "1.0";

    public static final Logger log = LogManager.getLogger(MODNAME);
    public ClickGUI clickGui;
    public SettingsManager settingsManager;
    public Friends friends;
    public ModuleManager moduleManager;
    public ConfigUtils configUtils;
    public CapeUtils capeUtils;
    public MacroManager macroManager;
    EventProcessor eventProcessor;
    public WaypointManager waypointManager;
    public static CFontRenderer fontRenderer;

    public static final EventBus EVENT_BUS = new EventManager();

    @Mod.Instance
    private static OsirisMod INSTANCE;

    public OsirisMod(){
        INSTANCE = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        //log.info("PreInitialization complete!\n");

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        eventProcessor = new EventProcessor();
        eventProcessor.init();
        fontRenderer = new CFontRenderer(new Font("Arial", Font.PLAIN, 20), true, false);
        TpsUtils tpsUtils = new TpsUtils();

        settingsManager = new SettingsManager();
        log.info("Settings initialized!");

        friends = new Friends();
        log.info("Friends initialized!");

        moduleManager = new ModuleManager();
        log.info("Modules initialized!");

        clickGui = new ClickGUI();
        HudComponentManager hudComponentManager = new HudComponentManager(0, 0, clickGui);
        log.info("ClickGUI initialized!");

        macroManager = new MacroManager();
        log.info("Macros initialised!");

        configUtils = new ConfigUtils();
        Runtime.getRuntime().addShutdownHook(new ShutDownHookerino());
        log.info("Config loaded!");

        CommandManager.initCommands();
        log.info("Commands initialised!");

        waypointManager = new WaypointManager();
        log.info("Waypoints initialised!");

        log.info("Initialization complete!\n");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        Display.setTitle(MODNAME + " " + MODVER);

        capeUtils = new CapeUtils();
        log.info("Capes initialised!");

        //WelcomeWindow ww = new WelcomeWindow();
        //ww.setVisible(false);
        log.info("PostInitialization complete!\n");
    }

    public static OsirisMod getInstance(){
        return INSTANCE;
    }

}
