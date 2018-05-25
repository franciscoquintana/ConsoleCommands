package net.ddns.fquintana.ConsoleCommands.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class ConsoleTab implements Consumer<ConsoleInputEvent> {
    private Integer lastIndex = 0;
    private String string = null;

    public ConsoleTab(String ...opciones) {
        //this.opciones = Arrays.asList(opciones);
    }

    public String get() {
        return null;
    }

    public void setOpciones(List<String> opciones) {
        //this.opciones = opciones;
    }

    public abstract List<String> getOptions(String[] args);

    private void reset() {
        string = null;
        lastIndex = 0;
    }

    private void tab(StringBuilder b) {
        String str;
        boolean previous = false;
        if (string == null)
            string = b.toString();
        else {
            previous = true;
        }
        str = string;


        List<String> strs = new ArrayList<String>(Arrays.asList(str.split(" ")));
        if (str.length() != 0 && str.charAt(str.length()-1) == ' ')
            strs.add("");
        List<String> opciones = getOptions(strs.toArray(new String[0]));
        String strCompare = strs.get(strs.size() - 1).toLowerCase();

        /*if (str.length() != 0 && str.charAt(str.length()-1) == ' ')
            strCompare = "";*/

        if (previous) {
            int amount = opciones.get(lastIndex-1).length() - strCompare.length();
            ConsoleUtils.clear(amount);
            b.setLength(b.length() - amount);
        }

        if (lastIndex == opciones.size())
            lastIndex = 0;

        for (int i = lastIndex; i < opciones.size(); i++) {
            String opcion = opciones.get(i).toLowerCase();
            if (opcion.startsWith(strCompare))
            {
                String strAdd = opcion.substring(strCompare.length(), opcion.length());
                System.out.print(strAdd);
                b.append(strAdd);
                lastIndex = i + 1;
                return;
            }
        }

        //SI SE USA MAL LA API FIX
        if (previous) {
            String opcion = opciones.get(lastIndex - 1).toLowerCase();
            String strAdd = opcion.substring(strCompare.length(), opcion.length());
            System.out.print(strAdd);
            b.append(strAdd);
        }

        string = null;
    }

    @Override
    public void accept(ConsoleInputEvent consoleInputEvent) {
        if (consoleInputEvent.getAddedChar() == ConsoleConstants.CHAR_TAB)
            tab(consoleInputEvent.getCurrentBuffer());

        if (consoleInputEvent.getAddedChar() != ConsoleConstants.CHAR_TAB)
            reset();
    }
}
