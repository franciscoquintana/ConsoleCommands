package net.ddns.fquintana.ConsoleCommands.Console.Reader;

public enum KeyConstants {
    LEFT_ARROW('\uE04B', "\u001B[D"),
    RIGHT_ARROW('\uE04D', "\u001B[C"),
    DOWN_ARROW('\uE050', "\u001B[B"),
    UP_ARROW('\uE048', "\u001B[A"),
    SUPR('\uE053', "\u001B[3" + (char) 126),
    INSERT('\uE052', "\u001B[2~"),
    UNKNOWN('\0', "\0");



    Character chWindows;
    String ansi;

    KeyConstants(Character chWindows, String ansi) {
        this.chWindows = chWindows;
        this.ansi = ansi;
    }

    public static KeyConstants getByAnsi(String ansi) {
        for (KeyConstants keyConstant : values()) {
            if (keyConstant.ansi.equals(ansi))
                return keyConstant;
        }
        return UNKNOWN;
    }

    public static KeyConstants getByWindows(Character character) {
        for (KeyConstants keyConstant : values()) {
            if (keyConstant.chWindows.equals(character))
                return keyConstant;
        }
        return UNKNOWN;
    }
}
