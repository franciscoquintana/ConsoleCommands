package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;
import net.ddns.fquintana.ConsoleCommands.Console.ConsoleTab;
import net.ddns.fquintana.ConsoleCommands.Utils.UtilArrays;

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

        ColoredConsole coloredConsole = new ColoredConsole();
        ConsoleTab tab = new ConsoleTab() {
            @Override
            public List<String> getOptions(String[] args) {
                return CommandManager.this.getOptions(args);
            }
        };

        coloredConsole.getEvents().add(tab);

        while (!isClosing())
        {
            System.out.print(ChatColor.BOLD + ChatColor.GREEN);
            String Input = coloredConsole.readLine();
            if (Input.trim().isEmpty())
            {
                continue;
            }
            String InputSplitted[] = Input.split(" ");
            String NombreComando = InputSplitted[0];
            String Args[] = Arrays.copyOfRange(InputSplitted,1,InputSplitted.length);

            coloredConsole.sendMessage("");
            if (Commands.containsKey(NombreComando.toUpperCase()) )
            {
                if (isRestricted() && !CommandsValid.contains(NombreComando.toUpperCase()))
                {
                    coloredConsole.error("Ese comando no se puede usar en este momento");
                    continue;
                }
                Command command = getCommand(NombreComando);
                try {
                    command.onCommand(coloredConsole, Args);
                } catch (ExceptionExtern ex) {
                    coloredConsole.error("Ocurrio un error no controlado al ejecutar el comando");
                    coloredConsole.error("Ha ocurrido el siguiente error en la app " + ChatColor.GRAY + new java.io.File(command.getClass().getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .getPath())
                            .getName() + ":");
                    coloredConsole.error(ex);
                    coloredConsole.sendMessageB(ChatColor.GOLD + "Powered By: <fquintana-Commands>");

                } catch (Exception ex) {
                    coloredConsole.error("Ocurrio un error, por favor reporte este fallo");
                    ex.printStackTrace();
                }
            }
            else
            {
                coloredConsole.sendMessage( ChatColor.CYAN + "'" + NombreComando + "'" + ChatColor.RED + " comando desconocido, usa help para ver la lista de comandos");
            }

        }
    }

    public List<String> getOptions(String[] args) {
        if (args.length != 0 && this.getCommand(args[0]) != null)
            return this.getCommand(args[0]).getOptions(UtilArrays.removeArgs(args, 1));
        if (args.length > 0)
            return isRestricted() ? CommandsValid : Arrays.asList(Commands.keySet().toArray(new String[0]));
        /*if (args.length != 0 && this.getCommands(args[0]) != null) {
            return this.getCommands(args[0]).getOptions(UtilArrays.removeArgs(args, 1));
        } else {
            return UtilArrays.filterArray(getOptions(), args[0]);
        }*/
        return new ArrayList<>();
    }

    /*public List<String> getOptions(String[] args) {
        if (args.length == 0)
            return new ArrayList<>();
        if(args.length != 0 && this.getCommands(args[0]) != null) {
            return this.getCommands(args[0]).getOptions(UtilArrays.removeArgs(args,1));
        } else {
            return UtilArrays.filterArray(getOptions(),args[0]);
        }
    }*/
}
