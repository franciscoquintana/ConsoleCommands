package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;
import net.ddns.fquintana.ConsoleCommands.Console.ConsoleArg;
import net.ddns.fquintana.ConsoleCommands.Utils.UtilArrays;

import java.util.*;

public class CommandMultiple implements ICommand {

    private String name;
    private int minargs = 1;
    private CommandMultiple parentCmd = null;

    private List<ICommand> cmds = new ArrayList();

    public CommandMultiple(String name) {
        this.name = name;
    }

    @Override
    public void setParent(CommandMultiple multiple) {
        parentCmd = multiple;
    }

    public boolean isSubCommand() {
        return parentCmd != null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean onCommand(ColoredConsole console, String[] args) throws ExceptionExtern {
        if (args.length < minargs){
            try {
                throw new NotEnoughArgumentsException();
            } catch (NotEnoughArgumentsException e) {
                console.error("Faltan argumentos");
                showHelp(console);
                return false;
            }
        }
        if(args.length != 0 && this.getCommands(args[0]) != null) {
            String argsWhitoutName[] = Arrays.copyOfRange(args,1, args.length);
            this.getCommands(args[0]).onCommand(console, argsWhitoutName);
        } else {

            if (cmds.size() == 0)
            {
                console.error("Error");
            }

            showHelp(console);

        }
        return true;

    }

    public void showHelp(ColoredConsole console)
    {
        Iterator Commands = this.cmds.iterator();

        if (!isSubCommand())
            console.sendMessage(ChatColor.GRAY + "---------------------- [" + ChatColor.DARK_PURPLE + name.toUpperCase() + ChatColor.GRAY + "] ---------------------");
        else
            console.sendMessage(ChatColor.YELLOW + name.toUpperCase());

        while(Commands.hasNext()) {
            ICommand cmd = (ICommand) Commands.next();
            cmd.showHelp(console);
        }

        if (isSubCommand())
            console.sendMessage( ChatColor.GRAY + String.join("", Collections.nCopies(24*2+name.length()-1, "-")));
    }

    public String helper() {
        String helper = isSubCommand() ? parentCmd.helper() : "";
        return helper + ChatColor.GREEN + this.name + " ";
    }

    @Override
    public List<String> getOptions(String[] args) {
        String commandName = args[0];
        if (args.length > 1 && this.getCommands(commandName) != null)
            return this.getCommands(commandName).getOptions(UtilArrays.removeArgs(args, 1));

        List<String> strings = new ArrayList<>();
        Iterator Commands = this.cmds.iterator();

        while(Commands.hasNext()) {
            ICommand cmd = (ICommand) Commands.next();
            String name = cmd.getName().toLowerCase();
            if(name.startsWith(commandName)) {
                strings.add(name.toLowerCase());
            }
        }

        return strings;
    }

    public ICommand getCommands(String s) {
        Iterator Commands = this.cmds.iterator();

        while(Commands.hasNext()) {
            ICommand cmd = (ICommand) Commands.next();
            if(cmd.getName().equalsIgnoreCase(s)) {
                return cmd;
            }
        }
        return null;
    }

    public boolean addSubCommand(ICommand command)
    {
        //TODO Check name
        cmds.add(command);
        command.setParent(this);
        return true;
    }
}
