package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;
import net.ddns.fquintana.ConsoleCommands.Console.ConsoleArg;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandSingle implements ICommand {
    private String name;
    private String usage;
    private String desc;
    private Integer argLength;
    private CommandMultiple parentCmd = null;

    public CommandSingle(String name, String usage, String desc, Integer argLength) {
        this.name = name;
        this.usage = usage;
        this.desc = desc;
        this.argLength = argLength;
    }

    public CommandSingle(String name, String usage, String desc, Integer argLength, CommandMultiple multiple) {
        this(name, usage, desc, argLength);
        setParent(multiple);
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
    public void showHelp(ColoredConsole console) {
        console.sendMessage(helper());
        console.sendMessage(descr());
    }

    @Override
    public boolean onCommand(ColoredConsole console, String[] args) throws ExceptionExtern {
        if(this.argLength <= args.length) {
            try {
                return this.run(console, args);
            }
            catch (Exception ex) {
                throw new ExceptionExtern(ex);
            }
        }

        String helper = isSubCommand() ? parentCmd.helper() : "";

        console.error ("Uso incorrecto: " + helper +  this.helper());

        return true;
    }

    abstract public boolean run(ColoredConsole console, String[] args);

    public String helper() {
        String helper = isSubCommand() ? parentCmd.helper() : "";
        return helper + ChatColor.GREEN + this.name + " " + this.usage;
    }

    public String descr() {
        return "   " + ChatColor.GRAY + this.desc;
    }

    @Override
    public List<String> getOptions(String[] args) {
        return new ArrayList<>();
    }
}
