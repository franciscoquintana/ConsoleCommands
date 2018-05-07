package net.ddns.fquintana.ConsoleCommands.Commands;

import net.ddns.fquintana.ConsoleCommands.CommandsCore.ColoredConsole;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandManager;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandSingle;

public class StopCommand extends CommandSingle {


    public StopCommand() {
        super("stop","","Cierra el programa");
    }

    @Override
    public boolean onCommand(ColoredConsole console, String[] Args) {
        console.sendMessage("Cerrando.");
        CommandManager.getManager().setClosing(true);
        return true;
    }
}
