package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;
import net.ddns.fquintana.ConsoleCommands.Console.*;
import net.ddns.fquintana.ConsoleCommands.Utils.UtilArrays;

import java.util.*;
import java.util.stream.Collectors;

public class CommandManager extends Thread {

    private HashMap<String,ICommand> commands = new LinkedHashMap<>();
    private List<String> commandsValid = new ArrayList<>();

    private static CommandManager cm;

    private boolean closing = false;
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
        this.closing = closing;
    }

    public boolean isClosing() {
        return closing;
    }

    public boolean addCommand(ICommand command) {

        if (!existCommand(command.getName().toUpperCase()))
        {
            commands.put(command.getName().toUpperCase(),command);
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
            commandsValid = commandsUpper;
        return !error;
    }

    public boolean addValidCommand(String command) {

        if (existCommand(command.toUpperCase()))
        {
            commandsValid.add(command.toUpperCase());
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isAllowed(ICommand command) {
        return isAllowed(command.getName());
    }

    public boolean isAllowed(String command) {
        if (isRestricted() && !commandsValid.contains(command.toUpperCase()))
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

    public Collection<ICommand> getCommands()
    {
        return commands.values();
    }

    public ICommand getCommand(String name)
    {
        return commands.get(name.toUpperCase());
    }

    @Override
    synchronized public void run() {

        ColoredConsole coloredConsole = new ColoredConsole();
        ConsoleTab tab = new ConsoleTab(coloredConsole) {
            @Override
            public List<String> getOptions(ConsoleArg[] args) {
                return CommandManager.this.getOptions(args);
            }
        };

        ConsoleHistory history = new ConsoleHistory(10);

        coloredConsole.getEventsInput().add(tab);
        coloredConsole.getEventsInput().add(history.getInputEvent());
        coloredConsole.getEventsInvoke().add(history.getInvokeEvent());

        while (!isClosing())
        {
            System.out.print(ChatColor.BOLD + ChatColor.GREEN);
            String Input = null;
            try {
                Input = coloredConsole.readLine();
            } catch (InterruptedException e) {
                continue;
            }
            if (Input.trim().isEmpty() || isClosing())
            {
                continue;
            }
            ConsoleArg inputSplitted[] = ConsoleUtils.strToArgs(Input).toArray(new ConsoleArg[0]);
            String nombreComando = inputSplitted[0].getArgStr();
            ConsoleArg args[] = Arrays.copyOfRange(inputSplitted,1,inputSplitted.length);

            coloredConsole.sendMessage("");
            if (commands.containsKey(nombreComando.toUpperCase()) )
            {
                if (isRestricted() && !commandsValid.contains(nombreComando.toUpperCase()))
                {
                    coloredConsole.error("Ese comando no se puede usar en este momento");
                    continue;
                }
                ICommand command = getCommand(nombreComando);
                try {
                    command.onCommand(coloredConsole, args);
                } catch (ExceptionExtern ex) {
                    coloredConsole.error("Ocurrio un error no controlado al ejecutar el comando");
                    coloredConsole.error("Ha ocurrido el siguiente error en la app " + ChatColor.GRAY + new java.io.File(command.getClass().getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .getPath())
                            .getName() + ":");
                    coloredConsole.error(ex);
                    coloredConsole.sendMessageB(ChatColor.GOLD + "Powered By: <fquintana-commands>");

                } catch (Exception ex) {
                    coloredConsole.error("Ocurrio un error, por favor reporte este fallo");
                    ex.printStackTrace();
                }
            }
            else
            {
                coloredConsole.sendMessage( ChatColor.CYAN + "'" + nombreComando + "'" + ChatColor.RED + " comando desconocido, usa help para ver la lista de comandos");
            }
            coloredConsole.sendMessage("");

        }
    }

    public List<String> getOptions(ConsoleArg[] args) {
        if (args.length > 1 && this.getCommand(args[0].getArgStr()) != null)
            if (!isRestricted() || isAllowed(args[0].getArgStr()))
                    return this.getCommand(args[0].getArgStr()).getOptions(UtilArrays.removeArgs(args, 1));
        if (args.length == 1) {
            List<String> commandList = isRestricted() ? commandsValid : Arrays.asList(commands.keySet().toArray(new String[0]));
            //FILTRO
            return commandList.stream().filter(str -> str.toLowerCase().startsWith(args[0].getArgStr())).collect(Collectors.toList());
        }


        return new ArrayList<>();
    }
}
