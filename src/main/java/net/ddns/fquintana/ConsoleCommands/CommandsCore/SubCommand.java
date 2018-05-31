package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand implements ICommand
{
    public String[] args;
    public String cmdName;
    public ICommand cmdPrincipal;
    public int argLength = 0;
    public String usage = "";
    public String desc = "";
    public ColoredConsole console;

    public SubCommand(String cmdName, ICommand cmdPrincipal, String usage, String desc, int argLength) {
        this.cmdName = cmdName;
        this.cmdPrincipal = cmdPrincipal;
        this.usage = usage;
        this.desc = desc;
        this.argLength = argLength;
    }

    public boolean onCommand(ColoredConsole console, String[] arg) throws ExceptionExtern {
        this.args = arg;
        this.console = console;

        if(this.argLength <= arg.length) {
            try {
                return this.run();
            }
            catch (Exception ex) {
                throw new ExceptionExtern(ex);
            }
        }

        console.error ("Uso incorrecto: " + cmdPrincipal.getName() + " " + this.helper());
        
        return true;
    }

    public abstract boolean run();


    @Override
    public String getName() {
        return cmdName;
    }

    @Override
    public void showHelp(ColoredConsole console) {
        //console.sendMessage(ChatColor.GRAY + "- " + ChatColor.AQUA +  helper());
        console.sendMessage(ChatColor.AQUA +  helper());
        console.sendMessage(descr());
    }

    @Override
    public List<String> getOptions(String[] args) {
        return new ArrayList<>();
    }

    public String helper() {
        return ChatColor.GREEN + this.cmdName + " " + this.usage;
    }

    public String descr() {
        return "   " + ChatColor.GRAY + this.desc;
    }
}
