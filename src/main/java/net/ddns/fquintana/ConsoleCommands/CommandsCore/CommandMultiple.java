package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;

import java.util.*;

public class CommandMultiple implements Command{

    private String name;
    private int minargs;

    private List cmds = new ArrayList();

    public CommandMultiple(String name, int minargs) {
        this.name = name;
        this.minargs = minargs;
    }



    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean onCommand(ColoredConsole console, String[] args) {
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
            String Args[] = Arrays.copyOfRange(args,1,args.length);
            this.getCommands(args[0]).processCmd(console, Args);
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

        console.sendMessage(ChatColor.GRAY + "---------------------- [" + ChatColor.DARK_PURPLE + name.toUpperCase() + ChatColor.GRAY + "] ---------------------");

        while(Commands.hasNext()) {
            SubCommand cmd = (SubCommand) Commands.next();
            console.sendMessage(ChatColor.GRAY + "- " + ChatColor.AQUA +  cmd.helper());
            console.sendMessage(cmd.descr());
        }

        console.sendMessage( ChatColor.GRAY + String.join("", Collections.nCopies(53, "-")));
    }

    private SubCommand getCommands(String s) {
        Iterator Commands = this.cmds.iterator();

        while(Commands.hasNext()) {
            SubCommand cmd = (SubCommand)Commands.next();
            if(cmd.cmdName.equalsIgnoreCase(s)) {
                return cmd;
            }
        }

        return null;
    }

    public boolean addSubCommand(SubCommand command)
    {
        //Check name
        cmds.add(command);
        return true;
    }



    //public abstract void execute(ColoredConsole console, String[] arg) throws NotEnoughArgumentsException;
}
