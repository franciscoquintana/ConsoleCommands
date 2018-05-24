package net.ddns.fquintana.ConsoleCommandsExample;

import net.ddns.fquintana.ConsoleCommands.Commands.HelpCommand;
import net.ddns.fquintana.ConsoleCommands.Commands.StopCommand;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandManager;
import net.ddns.fquintana.ConsoleCommandsExample.Clases.ClassManager;
import net.ddns.fquintana.ConsoleCommandsExample.Commands.CreateCommand;
import net.ddns.fquintana.ConsoleCommandsExample.Commands.InfoCommand;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ClassManager.getManager().load();

        CommandManager commandManager = CommandManager.getManager();
        commandManager.setRestricted(false);
        commandManager.addCommand(new StopCommand());
        commandManager.addCommand(new HelpCommand());
        commandManager.addCommand(new CreateCommand());
        commandManager.addCommand(new InfoCommand());
        /*commandManager.addValidCommand("help");
        commandManager.addValidCommand("stop");*/
        ArrayList<String> strings = new ArrayList<>();
        strings.add("help");
        strings.add("stop");
        commandManager.setValidCommands(strings);

        commandManager.start();
    }
}
