package net.ddns.fquintana.ConsoleCommands.Console.Events;

import net.ddns.fquintana.ConsoleCommands.Console.AnsiReader;
import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;

public class ConsoleInputEvent {
    private final StringBuilder currentBuffer;
    private final Character addedChar;
    private final AnsiReader ansiReader;
    private final ColoredConsole console;

    private boolean shouldCancel;

    public ConsoleInputEvent(StringBuilder currentBuffer, Character addedChar, AnsiReader ansiReader, ColoredConsole console) {
        this.currentBuffer = currentBuffer;
        this.addedChar = addedChar;
        this.ansiReader = ansiReader;
        this.console = console;
    }

    public StringBuilder getCurrentBuffer() {
        return currentBuffer;
    }

    public char getAddedChar() {
        return addedChar;
    }

    public AnsiReader getAnsiReader() {
        return ansiReader;
    }

    public ColoredConsole getConsole() {
        return console;
    }

    public void clearBuffer() {
        currentBuffer.setLength(0);
    }

    public void cancelLoop() {
        shouldCancel = true;
    }

    public boolean isShouldCancel() {
        return shouldCancel;
    }
}