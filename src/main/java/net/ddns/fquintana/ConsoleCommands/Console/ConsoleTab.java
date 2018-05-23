package net.ddns.fquintana.ConsoleCommands.Console;

import java.util.Arrays;
import java.util.List;

public class ConsoleTab {
    private Integer lastIndex = 0;
    private List<String> opciones;
    private String string = null;

    public ConsoleTab(String ...opciones) {
        this.opciones = Arrays.asList(opciones);
    }

    public String get() {
        return null;
    }

    public void reset() {
        string = null;
        lastIndex = 0;
    }

    public void tab(StringBuilder b) {
        String str;
        if (string == null)
            string = b.toString();
        else {
            int amount = opciones.get(lastIndex).length() - string.length();
            //main.clear(amount);
            b.setLength(b.length() - amount);
        }
        str = string;

        String[] strs = str.split(" ");
        String strCompare = strs[strs.length - 1];
        for (int i = lastIndex; i < opciones.size(); i++) {
            String opcion = opciones.get(i);
            if (opcion.startsWith(strCompare))
            {
                String strAdd = opcion.substring(strCompare.length(), opcion.length());
                System.out.print(strAdd);
                b.append(strAdd);
                lastIndex = i;
                return;
            }
        }
    }
}
