package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;

public abstract class CommandSingle implements Command {
    private String name;
    private String usage;
    private String desc;

    public CommandSingle(String name, String usage, String desc) {
        this.name = name;
        this.usage = usage;
        this.desc = desc;
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

    public String helper() {
        return ChatColor.GREEN + this.name + " " + this.usage;
    }

    public String descr() {
        return "   " + this.desc;
    }
}
