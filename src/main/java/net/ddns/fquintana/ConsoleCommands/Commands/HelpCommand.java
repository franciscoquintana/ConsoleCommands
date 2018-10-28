package net.ddns.fquintana.ConsoleCommands.Commands;

import net.ddns.fquintana.ConsoleCommands.Console.ChatColor;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.*;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;
import net.ddns.fquintana.ConsoleCommands.Utils.UtilArrays;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class HelpCommand extends CommandSingle {


    public HelpCommand() {
        super("help","[comando]","Muestra la ayuda",0);
    }

    @Override
    public List<String> getOptions(String[] args) {
        String commandName = args[0];
        ArrayList<String> options = new ArrayList<>();
        CommandManager manager = CommandManager.getManager();
        if (args.length == 1) {
            for (ICommand iCommand : manager.getCommands()) {
                if (iCommand.getName().startsWith(commandName)) {
                    options.add(iCommand.getName());
                }
            }
        }
        else {
            ICommand command = manager.getCommand(commandName);
            return command.getOptions(UtilArrays.removeArgs(args, 1));
        }
        return options;
    }



    @Override
    public boolean run(ColoredConsole console, String[] args) {
        if (args.length == 0)
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
            ICommand command = CommandManager.getManager().getCommand(args[0]);
            return showHelp(console, command, UtilArrays.removeArgs(args, 1));
        }
    }

    public boolean showHelp(ColoredConsole console, ICommand command, String[] args) {
        if (args.length != 0 && command instanceof CommandMultiple) {
            CommandMultiple multiple = (CommandMultiple) command;
            ICommand command1 = multiple.getCommands(args[0]);
            return showHelp(console, command1, UtilArrays.removeArgs(args, 1));
        }
        else {
            if (command == null) {
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
