package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandSingle implements Command {
    private String name;
    private String usage;
    private String desc;
    private Integer argLength;

    public CommandSingle(String name, String usage, String desc, Integer argLength) {
        this.name = name;
        this.usage = usage;
        this.desc = desc;
        this.argLength = argLength;
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
    public boolean onCommand(ColoredConsole console, String[] Args) throws ExceptionExtern {
        if(this.argLength <= Args.length) {
            try {
                return this.run(console, Args);
            }
            catch (Exception ex) {
                throw new ExceptionExtern(ex);
            }
        }

        console.error ("Uso incorrecto: " +  this.helper());


        return true;
    }

    abstract public boolean run(ColoredConsole console, String[] Args);

    public String helper() {
        return ChatColor.GREEN + this.name + " " + this.usage;
    }

    public String descr() {
        return "   " + this.desc;
    }

    @Override
    public List<String> getOptions(String[] args) {
        return new ArrayList<>();
    }
}
