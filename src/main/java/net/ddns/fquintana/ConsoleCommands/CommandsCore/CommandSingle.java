package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;

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
    public boolean onCommand(ColoredConsole console, String[] Args) {
        if(this.argLength <= Args.length) {
            return this.run(console, Args);
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
}
