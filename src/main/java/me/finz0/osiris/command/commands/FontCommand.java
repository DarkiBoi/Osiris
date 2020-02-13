package me.finz0.osiris.command.commands;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.util.font.CFontRenderer;

import java.awt.*;

public class FontCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{
                "font", "setfont"
        };
    }

    @Override
    public String getSyntax() {
        return "font <Name> <Size>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        String font = args[0].replace("_", " ");
        int size = Integer.parseInt(args[1]);
        OsirisMod.fontRenderer = new CFontRenderer(new Font(font, Font.PLAIN, size), true, false);
        OsirisMod.fontRenderer.setFontName(font);
        OsirisMod.fontRenderer.setFontSize(size);
    }
}
