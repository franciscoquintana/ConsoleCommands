package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;

import java.util.List;

public interface ICommand {

    public String getName();

    void setParent(CommandMultiple multiple);

    public boolean onCommand(ColoredConsole console, String[] Args) throws ExceptionExtern;

    public void showHelp(ColoredConsole console);

    List<String> getOptions(String[] args);
}
