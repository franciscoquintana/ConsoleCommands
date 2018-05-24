package net.ddns.fquintana.ConsoleCommands.Console;

import java.awt.event.KeyEvent;

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
            System.out.print(CharConstants.CHAR_BACKSPACE);
            System.out.print(" ");
            System.out.print(CharConstants.CHAR_BACKSPACE);
        }
    }

    public static String right(int amount) {
       return  "\u001B[" + amount + "C";
    }

    public static String left(int amount) {
        return  "\u001B[" + amount + "D";
    }

}
