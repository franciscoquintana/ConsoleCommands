package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;

public abstract class SubCommand
{
    public String[] args;
    public String cmdName;
    public Command cmdPrincipal;
    public int argLength = 0;
    public String usage = "";
    public String desc = "";
    public ColoredConsole console;

    public SubCommand(String cmdName, Command cmdPrincipal, String usage, String desc, int argLength) {
        this.cmdName = cmdName;
        this.cmdPrincipal = cmdPrincipal;
        this.usage = usage;
        this.desc = desc;
        this.argLength = argLength;
    }

    public boolean processCmd(ColoredConsole console, String[] arg) {
        this.args = arg;
        this.console = console;

        if(this.argLength <= arg.length) {
            return this.run();
        }

        console.error ("Uso incorrecto: " + cmdPrincipal.getName() + " " + this.helper());


        return true;
    }

    public abstract boolean run();



    public String helper() {
        return ChatColor.GREEN + this.cmdName + " " + this.usage;
    }

    public String descr() {
        return "   " + ChatColor.GRAY + this.desc;
    }
}
