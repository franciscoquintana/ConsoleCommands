package net.ddns.fquintana.ConsoleCommandsExample.Commands;

import net.ddns.fquintana.ConsoleCommands.Console.ChatColor;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandMultiple;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandSingle;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;
import net.ddns.fquintana.ConsoleCommandsExample.Clases.Alumno;
import net.ddns.fquintana.ConsoleCommandsExample.Clases.Clase;
import net.ddns.fquintana.ConsoleCommandsExample.Clases.ClassManager;

import java.util.*;

public class InfoCommand extends CommandMultiple {
    public InfoCommand() {
        super("info");
        addClase();
        addAlumno();
    }

    private void addClase()
    {
        CommandMultiple commandMultiple = new CommandMultiple("clase");

        CommandSingle ClaseInfo = new CommandSingle("clase",  "{Clase}", "Sirve para consultar información sobre una clase",1) {

            @Override
            public boolean run(ColoredConsole console, String[] args) {
                Clase clase = ClassManager.getManager().getClase(args[0]);
                //if (clase != null)
                {
                    Clase.imprimeClase(clase);
                    return true;
                }
                /*console.error("Esa clase no existe");
                return false;*/
            }

            @Override
            public List<String> getOptions(String[] args) {
                List<String> clasesFiltradas = new ArrayList<>();
                Set<String> clases = ClassManager.getManager().getClases().keySet();
                for (String str : clases) {
                    if (str.startsWith(args[0]))
                        clasesFiltradas.add(str);
                }

                return clasesFiltradas;
            }
        };

        commandMultiple.addSubCommand(ClaseInfo);

        CommandSingle ClaseList = new CommandSingle("clases",  "", "Sirve para mostrar un listado con todas las clases",0) {
            @Override
            public boolean run(ColoredConsole console, String[] args) {

                Iterator<Clase> claseIterator = ClassManager.getManager().getClases().values().iterator();
                if (!claseIterator.hasNext())
                {
                    console.error("No hay clases");
                    return false;
                }
                console.sendMessage("Clases:");
                while (claseIterator.hasNext())
                {
                    Clase clase = claseIterator.next();
                    console.sendMessage( ChatColor.GRAY + clase.getNombre());

                }

                return true;
            }
        };
        commandMultiple.addSubCommand(ClaseList);
        addSubCommand(commandMultiple);
    }

    private void addAlumno()
    {
        CommandMultiple commandMultiple = new CommandMultiple("alumno");

       /* CommandSingle alumno = new CommandSingle("clase",  "{Clase}", "Sirve para consultar información sobre una clase",1) {

            @Override
            public boolean run(ColoredConsole console, String[] args) {

            }

            @Override
            public List<String> getOptions(String[] args) {
                List<String> clasesFiltradas = new ArrayList<>();
                Set<String> clases = ClassManager.getManager().getClases().keySet();
                for (String str : clases) {
                    if (str.startsWith(args[0]))
                        clasesFiltradas.add(str);
                }

                return clasesFiltradas;
            }
        };

        commandMultiple.addSubCommand(ClaseInfo);*/

        CommandSingle ClaseList = new CommandSingle("alumnos",  "", "Sirve para mostrar un listado con todos los alumnos",0) {
            @Override
            public boolean run(ColoredConsole console, String[] args) {

                Iterator<Clase> claseIterator = ClassManager.getManager().getClases().values().iterator();
                if (!claseIterator.hasNext())
                {
                    console.error("No hay alumnos");
                    return false;
                }
                console.sendMessage("Alumnos:");
                while (claseIterator.hasNext())
                {
                    Clase clase = claseIterator.next();
                    for (Alumno alumno : clase.getAlumnos()) {
                        console.sendMessage( ChatColor.GRAY + alumno.getNombre());
                    }

                }

                return true;
            }
        };
        commandMultiple.addSubCommand(ClaseList);
        addSubCommand(commandMultiple);
    }


}
