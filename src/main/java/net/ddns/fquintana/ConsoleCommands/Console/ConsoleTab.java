package net.ddns.fquintana.ConsoleCommands.Console;

import net.ddns.fquintana.ConsoleCommands.Console.Events.ConsoleInputEvent;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class ConsoleTab implements Consumer<ConsoleInputEvent> {
    private Integer lastIndex = 0;
    private String originalString = null;
    private ColoredConsole coloredConsole;

    public ConsoleTab(ColoredConsole coloredConsole) {
        this.coloredConsole = coloredConsole;
    }

    public abstract List<String> getOptions(String[] args);

    private void reset() {
        originalString = null;
        lastIndex = 0;
    }

    private void tab(StringBuilder b) {
        String str;
        boolean previous = false;

        if (originalString == null)
            originalString = b.toString();
        else {
            previous = true;
        }
        str = originalString;

        List<ConsoleArg> args = ConsoleUtils.strToArgs(str);

        if (str.length() != 0 && str.charAt(str.length()-1) == ' ')
            args.add(new ConsoleArg("", false));

        //TODO REMOVE THIS
        List<String> argsStr = args.stream().map(ConsoleArg::getArgStr).collect(Collectors.toList());

        List<String> opciones = getOptions(argsStr.toArray(new String[0]));

        ConsoleArg argCompare = args.get(args.size() - 1);

        //SI NO ES EL PRIMERO LIMPIAMOS LO ESCRITO
        if (previous) {
            int amount = opciones.get(lastIndex-1).length() - argCompare.getArgStr().length();
            if (argCompare.isHaveQuotes())
                amount++;
            ConsoleUtils.clear(amount);
            b.setLength(b.length() - amount);
            if (argCompare.isHaveQuotes())
                coloredConsole.addToCurrent("\"");
        }

        //RESET TAB INDEX IF LAST
        if (lastIndex == opciones.size())
            lastIndex = 0;

        for (int i = lastIndex; i < opciones.size(); i++) {
            String opcion = opciones.get(i).toLowerCase();
            if (opcion.startsWith(argCompare.getArgStr().toLowerCase()))
            {
                String strAdd = opcion.substring(argCompare.getArgStr().length());
                coloredConsole.consoleMovement.moveRight();
                if (argCompare.isHaveQuotes())
                    coloredConsole.consoleMovement.deleteOne();

                coloredConsole.write(strAdd);
                b.append(strAdd);

                if (argCompare.isHaveQuotes())
                    coloredConsole.addToCurrent("\"");

                lastIndex = i + 1;
                return;
            }
        }


        //SI SE USA MAL LA API FIX
        //Nos pasan opciones que no tienen que ver con la stringOriginal
        //Ya no es necesario
        /*if (previous) {
            String opcion = opciones.get(lastIndex - 1).toLowerCase();
            String strAdd = opcion.substring(argCompare.getArgStr().length(), opcion.length());
            System.out.print(strAdd);
            b.append(strAdd);
        }*/

        originalString = null;
    }

    @Override
    public void accept(ConsoleInputEvent consoleInputEvent) {
        if (consoleInputEvent.getAddedChar() == ConsoleConstants.CHAR_TAB)
            tab(consoleInputEvent.getCurrentBuffer());

        if (consoleInputEvent.getAddedChar() != ConsoleConstants.CHAR_TAB)
            reset();
    }
}
