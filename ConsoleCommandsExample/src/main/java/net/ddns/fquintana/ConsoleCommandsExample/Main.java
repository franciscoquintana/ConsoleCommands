package net.ddns.fquintana.ConsoleCommandsExample;

import net.ddns.fquintana.ConsoleCommands.Commands.HelpCommand;
import net.ddns.fquintana.ConsoleCommands.Commands.StopCommand;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandManager;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;
import net.ddns.fquintana.ConsoleCommandsExample.Clases.ClassManager;
import net.ddns.fquintana.ConsoleCommandsExample.Commands.CreateCommand;
import net.ddns.fquintana.ConsoleCommandsExample.Commands.InfoCommand;
import net.ddns.fquintana.ConsoleCommandsExample.Commands.RestrictCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClassManager.getManager().load();

        CommandManager commandManager = CommandManager.getManager();
        commandManager.setRestricted(false);
        commandManager.addCommand(new StopCommand());
        commandManager.addCommand(new HelpCommand());
        commandManager.addCommand(new CreateCommand());
        commandManager.addCommand(new InfoCommand());
        commandManager.addCommand(new RestrictCommand());
        List<String> strings = Arrays.asList("help", "info", "restrict");
        commandManager.setValidCommands(strings);

        commandManager.start();


    }
}
