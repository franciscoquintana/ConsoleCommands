package net.ddns.fquintana.ConsoleCommands.Console;

public final class ConsoleConstants {
    public static final char CHAR_CTRL_C = (char) 3;
    public static final char CHAR_CTRL_D = (char) 4;
    public static final char CHAR_CTRL_Z = (char) 26;
    public static final char CHAR_ESC = (char) 27;
    public static final char CHAR_LEFTBRACKET = '[';

    public static final char CHAR_BACKSPACE = '\b';

    public static final String IZQUIERDA = "\u001B[D";
    public static final String DERECHA = "\u001B[C";
    public static final String ARRIBA = "\u001B[A";
    public static final String ABAJO = "\u001B[B";
    public static final String SUPR = "\u001B[3" + (char) 126;

    public static final String GOTO_LEFT = "\u001B[1G";
    public static final char CHAR_TAB = '\t';

    private ConsoleConstants() {}
}
