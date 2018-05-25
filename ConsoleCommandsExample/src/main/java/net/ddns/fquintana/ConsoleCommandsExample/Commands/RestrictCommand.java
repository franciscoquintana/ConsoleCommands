package net.ddns.fquintana.ConsoleCommandsExample.Commands;

import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandManager;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandSingle;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;

public class RestrictCommand extends CommandSingle {
    public RestrictCommand() {
        super("restrict", "", "Activa/Desactiva las restricciones", 0);
    }

    @Override
    public boolean run(ColoredConsole console, String[] Args) {
        Boolean restrict = !CommandManager.getManager().isRestricted();
        CommandManager.getManager().setRestricted(!CommandManager.getManager().isRestricted());
        if (restrict)
            console.sendMessage("Has restringido el uso de comandos.");
        else
            console.sendMessage("Ya no esta restringido el uso de comandos.");
        return false;
    }
}
