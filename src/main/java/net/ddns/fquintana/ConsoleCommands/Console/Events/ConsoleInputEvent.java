package net.ddns.fquintana.ConsoleCommands.Console.Events;

import net.ddns.fquintana.ConsoleCommands.Console.AnsiReader;

public class ConsoleInputEvent {
    private final StringBuilder currentBuffer;
    private final Character addedChar;
    private final AnsiReader ansiReader;

    private boolean shouldCancel;

    public ConsoleInputEvent(StringBuilder currentBuffer, Character addedChar, AnsiReader ansiReader) {
        this.currentBuffer = currentBuffer;
        this.addedChar = addedChar;
        this.ansiReader = ansiReader;
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