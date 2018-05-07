package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class CommandManager extends Thread {

    private HashMap<String,Command> Commands = new HashMap<>();

    private static CommandManager cm;

    private boolean Closing = false;

    private CommandManager()
    {

    }

    public static CommandManager getManager() {
        if (cm == null){
            cm = new CommandManager();
        }
        return cm;
    }

    public void setClosing(boolean closing) {
        Closing = closing;
    }

    public boolean isClosing() {
        return Closing;
    }

    public boolean addCommand(Command command) {

        if (!existCommand(command.getName().toUpperCase()))
        {
            Commands.put(command.getName().toUpperCase(),command);
            return true;
        }
        else
        {
            System.out.println("Command Already Exist");
            return false;
        }
    }

    public boolean existCommand(String name)
    {
        if (getCommand(name) != null)
        {
            return true;
        }
        return false;
    }

    public Collection<Command> getCommands()
    {
        return Commands.values();
    }

    public Command getCommand(String name)
    {
        return Commands.get(name.toUpperCase());
    }

    @Override
    synchronized public void run() {

        Scanner scanner = null;
        ColoredConsole ColoredConsole = new ColoredConsole();

        while (!isClosing())
        {
            System.out.println(ChatColor.BOLD + ChatColor.GREEN);
            scanner = new Scanner(System.in);
            String Input = scanner.nextLine();
            if (Input.trim().isEmpty())
            {
                continue;
            }
            System.out.println(ChatColor.RESET);
            String InputSplitted[] = Input.split(" ");
            String NombreComando = InputSplitted[0];
            String Args[] = Arrays.copyOfRange(InputSplitted,1,InputSplitted.length);

            if (Commands.containsKey(NombreComando.toUpperCase()))
            {
                Command command = getCommand(NombreComando);
                command.onCommand(ColoredConsole, Args);
            }
            else
            {
                ColoredConsole.sendMessage( ChatColor.CYAN + NombreComando + " comando desconocido, usa help para ver la lista de comandos");
            }

        }

        scanner.close();


    }
}
