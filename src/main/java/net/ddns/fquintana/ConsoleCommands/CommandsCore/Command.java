package net.ddns.fquintana.ConsoleCommands.CommandsCore;

public interface Command {

    public String getName();

    public boolean onCommand(ColoredConsole console, String[] Args);

    public void showHelp(ColoredConsole console);
}
