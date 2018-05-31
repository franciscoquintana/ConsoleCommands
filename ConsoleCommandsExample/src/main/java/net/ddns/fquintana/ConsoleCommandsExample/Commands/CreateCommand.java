package net.ddns.fquintana.ConsoleCommandsExample.Commands;

import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandMultiple;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandSingle;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;
import net.ddns.fquintana.ConsoleCommandsExample.Clases.Alumno;
import net.ddns.fquintana.ConsoleCommandsExample.Clases.Clase;
import net.ddns.fquintana.ConsoleCommandsExample.Clases.ClassManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CreateCommand extends CommandMultiple {

    public CreateCommand() {
        super("crear");
        addSub();
    }

    private void addSub() {
        CommandSingle crearAlumno = new CommandSingle("alumno", "{nombre} {clase}", "crea un alumno en la clase indicada",2) {
            @Override
            public boolean run(ColoredConsole console, String[] args) {
                Clase clase = ClassManager.getManager().getClase(args[1]);
                if (clase != null)
                {
                    Alumno alumno = new Alumno(args[0]);
                    clase.addAlumno(alumno);
                    console.sendMessage("Alumno añadido con exito");
                    clase.save();
                    return true;
                }
                console.error("Esa clase no existe");
                return false;
            }

            @Override
            public List<String> getOptions(String[] args) {
                if (args.length > 1) {
                    List<String> clasesFiltradas = new ArrayList<>();
                    Set<String> clases = ClassManager.getManager().getClases().keySet();
                    for (String str : clases) {
                        if (str.startsWith(args[1]))
                            clasesFiltradas.add(str);
                    }

                    return clasesFiltradas;
                }
                return new ArrayList<>();
            }
        };

        addSubCommand(crearAlumno);

        CommandSingle crearClase = new CommandSingle("clase", "{nombre}", "crea una clase",1) {
            @Override
            public boolean run(ColoredConsole console, String[] args) {
                boolean exist = ClassManager.getManager().existClase(args[0]);

                if (!exist) {
                    Clase clase = new Clase(args[0]);
                    ClassManager.getManager().addClase(clase);
                    clase.save();
                    console.sendMessage("La clase " + args[0] + " ha sido añadida con exito");
                    return true;
                }
                console.error("Esa clase ya existe");
                return false;
            }
        };

        addSubCommand(crearClase);
    }
}
