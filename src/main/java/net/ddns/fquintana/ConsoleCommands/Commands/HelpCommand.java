package net.ddns.fquintana.ConsoleCommands.Commands;

import net.ddns.fquintana.ChatColor;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.*;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;

import java.util.Collection;
import java.util.Iterator;

public class HelpCommand extends CommandSingle {


    public HelpCommand() {
        super("help","[Comando]","Muestra la ayuda",0);
    }

    @Override
    public boolean run(ColoredConsole console, String[] Args) {
        if (Args.length == 0)
        {
            Collection<ICommand> Comandos = CommandManager.getManager().getCommands();

            Iterator Commands = Comandos.iterator();

            while (Commands.hasNext()) {
                ICommand cmd = (ICommand) Commands.next();
                if (CommandManager.getManager().isAllowed(cmd))
                    cmd.showHelp(console);
            }
            console.sendMessageB(ChatColor.GOLD + "Powered By: <fquintana-Commands>");
            return true;
        }
        else
        {
            ICommand command = CommandManager.getManager().getCommand(Args[0]);
            if (command == null)
            {
                console.error("Ese comando no existe");
                return false;
            }
            if (CommandManager.getManager().isAllowed(command))
                command.showHelp(console);
            else
                console.error("Ese comando no esta permitido, por lo que no podemos mostrar su ayuda");
            return true;
        }


    }
}
