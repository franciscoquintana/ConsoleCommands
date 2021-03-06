package net.ddns.fquintana.ConsoleCommands.Console;

import net.ddns.fquintana.ConsoleCommands.Console.Events.ConsoleInputEvent;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collector;
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

    //Las opciones deben estar filtradas
    private void tab() {
        StringBuilder b = coloredConsole.getCurrentStr();
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

        List<String> opciones = getOptions(args.stream().map(ConsoleArg::getArgStr).toArray(size -> new String[size]));

        ConsoleArg argCompare = args.get(args.size() - 1);

        //SI NO ES EL PRIMERO LIMPIAMOS LO ESCRITO
        if (previous) {
            int amount = opciones.get(lastIndex-1).length() - argCompare.getArgStr().length();
            if (argCompare.isHaveQuotes())
                amount++;
            coloredConsole.consoleMovement.delete(amount);
            if (argCompare.isHaveQuotes())
                coloredConsole.addToCurrent("\"");
        }

        //RESET TAB INDEX IF LAST
        //Si no esta filtrado no funcionara
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

                coloredConsole.addToCurrent(strAdd);

                if (argCompare.isHaveQuotes())
                    coloredConsole.addToCurrent("\"");

                lastIndex = i + 1;
                return;
            }
        }
        originalString = null;
    }

    @Override
    public void accept(ConsoleInputEvent consoleInputEvent) {
        if (!consoleInputEvent.getLastRead().isKey()) {
            if (consoleInputEvent.getLastRead().getCharacter() == ConsoleConstants.CHAR_TAB) {
                consoleInputEvent.setCancelled(true);
                tab();
            }
            else if (consoleInputEvent.getLastRead().getCharacter() != ConsoleConstants.CHAR_TAB)
                reset();
        }
    }
}
