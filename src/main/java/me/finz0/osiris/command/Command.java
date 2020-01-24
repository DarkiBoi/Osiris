package me.finz0.osiris.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public abstract class Command {
    static Minecraft mc = Minecraft.getMinecraft();
    public static String prefix = "-";
    public abstract String getAlias();
    public abstract String getSyntax();
    public abstract void onCommand(String command, String[] args) throws Exception;

    public static void sendClientMessage(String message){
        mc.player.sendMessage(new TextComponentString("\u1d0f\ua731\u026a\u0280\u026a\ua731 \u300b"+"\2477" + message));
    }

    public static void sendRawMessage(String message){
        mc.player.sendMessage(new TextComponentString(message));
    }

    public static String getPrefix(){
        return prefix;
    }

    public static void setPrefix(String p){
        prefix = p;
    }

}
