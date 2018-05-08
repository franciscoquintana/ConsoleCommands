package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;

import java.util.*;

public class CommandManager extends Thread {

    private HashMap<String,Command> Commands = new HashMap<>();
    private List<String> CommandsValid = new ArrayList<>();

    private static CommandManager cm;

    private boolean Closing = false;
    private boolean restricted = false;

    private CommandManager()
    {

    }

    public static CommandManager getManager() {
        if (cm == null){
            cm = new CommandManager();
        }
        return cm;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
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
            return false;
        }
    }

    public boolean setValidCommands(List<String> commands) {
        boolean error = false;
        ArrayList<String> commandsUpper = new ArrayList<>();
        for (String str : commands) {
            if (!existCommand(str.toUpperCase()))
                error = true;
            commandsUpper.add(str.toUpperCase());
        }
        if (!error)
            CommandsValid = commandsUpper;
        return !error;
    }

    public boolean addValidCommand(String command) {

        if (existCommand(command.toUpperCase()))
        {
            CommandsValid.add(command.toUpperCase());
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isAllowed(Command command) {
        return isAllowed(command.getName());
    }

    public boolean isAllowed(String command) {
        if (isRestricted() && !CommandsValid.contains(command.toUpperCase()))
            return false;
        return true;
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

            if (Commands.containsKey(NombreComando.toUpperCase()) )
            {
                if (isRestricted() && !CommandsValid.contains(NombreComando.toUpperCase()))
                {
                    ColoredConsole.error("Ese comando no se puede usar en este momento");
                    continue;
                }
                Command command = getCommand(NombreComando);
                try {
                    command.onCommand(ColoredConsole, Args);
                } catch (Exception ex) {
                    ColoredConsole.error("Ocurrio un error al ejecutar el comando");
                }
            }
            else
            {
                ColoredConsole.sendMessage( ChatColor.CYAN + NombreComando + " comando desconocido, usa help para ver la lista de comandos");
            }

        }

        scanner.close();


    }
}
