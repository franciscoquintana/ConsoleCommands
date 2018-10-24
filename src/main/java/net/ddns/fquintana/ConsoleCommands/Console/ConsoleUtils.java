package net.ddns.fquintana.ConsoleCommands.Console;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUtils {

    /*
        Code from OscarRyz on SO: https://stackoverflow.com/questions/220547/printable-char-in-java
     */
    public static boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (!Character.isISOControl(c)) &&
                c != KeyEvent.CHAR_UNDEFINED &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }

    public static void clear(int amount) {
        for (int i=0; i<amount; i++) {
            System.out.print(ConsoleConstants.CHAR_BACKSPACE);
            System.out.print(" ");
            System.out.print(ConsoleConstants.CHAR_BACKSPACE);
        }
    }

    public static String right(int amount) {
       return  "\u001B[" + amount + "C";
    }

    public static String left(int amount) {
        return  "\u001B[" + amount + "D";
    }

    /*public static String[] strToArgs(String str) {
        ArrayList<String> arrayList = new ArrayList<>();

        Pattern pattern = Pattern.compile("((\")([^\"]*)(\"))|[^ ]+");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            //El grupo 2 excluye las comillas
            arrayList.add(matcher.group(3) != null ? matcher.group(3) : matcher.group());
        }
        if (arrayList.isEmpty())
            arrayList.add("");
        return arrayList.toArray(new String[0]);
    }*/

    public static List<ConsoleArg> strToArgs(String str) {
        ArrayList<ConsoleArg> arrayList = new ArrayList<>();

        Pattern pattern = Pattern.compile("((\")([^\"]*)(\"))|[^ ]+");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            //El grupo 2 excluye las comillas
            boolean haveQuotes = matcher.group(3) != null;

            arrayList.add(new ConsoleArg(haveQuotes ? matcher.group(3) : matcher.group(), haveQuotes));
        }
        if (arrayList.isEmpty())
            arrayList.add(new ConsoleArg("", false));

        return arrayList;
    }

}
