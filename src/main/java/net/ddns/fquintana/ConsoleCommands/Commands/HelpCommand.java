package net.ddns.fquintana.ConsoleCommands.Commands;

import net.ddns.fquintana.ConsoleCommands.CommandsCore.*;

import java.util.Collection;
import java.util.Iterator;

public class HelpCommand extends CommandSingle {


    public HelpCommand() {
        super("help","[Comando]","Muestra la ayuda");
    }

    @Override
    public boolean onCommand(ColoredConsole console, String[] Args) {
        if (Args.length == 0)
        {
            Collection<Command> Comandos = CommandManager.getManager().getCommands();

            Iterator Commands = Comandos.iterator();

            while (Commands.hasNext()) {
                Command cmd = (Command) Commands.next();
                cmd.showHelp(console);
            }
            return true;
        }
        else
        {
            Command command = CommandManager.getManager().getCommand(Args[0]);
            if (command == null)
            {
                console.error("Ese comando no existe");
                return false;
            }
            command.showHelp(console);
            return true;
        }


    }
}
