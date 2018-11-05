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
    String unicode;

    KeyConstants(Character chWindows, String unicode) {
        this.chWindows = chWindows;
        this.unicode = unicode;
    }

    public static KeyConstants getByUnicode(String unicode) {
        for (KeyConstants keyConstant : values()) {
            if (keyConstant.unicode.equals(unicode))
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
