package net.ddns.fquintana.ConsoleCommands.Commands;

import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandManager;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandSingle;

public class StopCommand extends CommandSingle {

    public StopCommand() {
        super("stop","","Cierra el programa",0);
    }

    @Override
    public boolean run(ColoredConsole console, String[] args) {
        console.sendMessage("Pulse una tecla para salir.");
        CommandManager.getManager().setClosing(true);
        return true;
    }
}
