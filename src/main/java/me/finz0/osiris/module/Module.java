package me.finz0.osiris.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.RenderEvent;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Module {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    String name;
    Category category;
    int bind;
    boolean enabled;
    boolean drawn;
    String hudInfo;

    public Module(String n, Category c) {
        name = n;
        category = c;
        bind = Keyboard.KEY_NONE;
        enabled = false;
        drawn = true;
        setup();
    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category c){
        category = c;
    }

    public int getBind(){
        return bind;
    }

    public void setBind(int b){
        bind = b;
    }

    protected void onEnable(){
    }
    protected void onDisable(){
    }
    public void onUpdate(){}
    public void onRender(){}
    public void onWorldRender(RenderEvent event) {}

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean e){
        enabled = e;
    }

    public void enable(){
        setEnabled(true);
        if(ModuleManager.isModuleEnabled("ToggleMsgs") && !getName().equalsIgnoreCase("ClickGUI")) Command.sendClientMessage(getName() + ChatFormatting.GREEN + " enabled!");
        onEnable();
    }

    public void disable(){
        setEnabled(false);
        if(ModuleManager.isModuleEnabled("ToggleMsgs") && !getName().equalsIgnoreCase("ClickGUI")) Command.sendClientMessage(getName() + ChatFormatting.RED + " disabled!");
        onDisable();
    }

    public void toggle(){
        if(isEnabled()) {
            disable();
        } else if(!isEnabled()){
            enable();
        }
    }

    public String getHudInfo(){
        return "";
    }

    public void setup(){}

    public boolean isDrawn(){
        return drawn;
    }

    public void setDrawn(boolean d){
        drawn = d;
    }

    static List<Category> categoryList = new ArrayList<Category>();

    public static List<Category> getCategories(){
        return categoryList;
    }

    public enum Category{
        COMBAT,
        PLAYER,
        MOVEMENT,
        MISC,
        CHAT,
        RENDER,
        GUI;
    }
}
