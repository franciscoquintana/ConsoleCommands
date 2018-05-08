package net.ddns.fquintana.ConsoleCommandsExample.Commands;

import net.ddns.fquintana.ChatColor;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.CommandMultiple;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.SubCommand;
import net.ddns.fquintana.ConsoleCommandsExample.Clases.Clase;
import net.ddns.fquintana.ConsoleCommandsExample.Clases.ClassManager;

import java.util.Iterator;

public class InfoCommand extends CommandMultiple {
    public InfoCommand() {
        super("info", 1);
        addClase();
    }

    private void addClase()
    {

        SubCommand ClaseInfo = new SubCommand("Clase", this, "{Clase}", "Sirve para consultar información sobre una clase",1) {
            @Override
            public boolean run() {
                Clase clase = ClassManager.getManager().getClase(args[0]);
                if (clase != null)
                {
                    Clase.imprimeClase(clase);
                    return true;
                }
                console.error("Esa clase no existe");
                return false;
            }
        };

        addSubCommand(ClaseInfo);

        SubCommand ClaseList = new SubCommand("Clases", this, "", "Sirve para mostrar un listado con todas las clases",0) {
            @Override
            public boolean run() {

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

        addSubCommand(ClaseList);
    }


}
