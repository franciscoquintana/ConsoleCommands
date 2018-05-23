package net.ddns.fquintana.ConsoleCommands.Console;

public class ConsoleInputEvent {
    private final StringBuilder currentBuffer;
    private final char addedChar;

    private boolean shouldCancel;

    public ConsoleInputEvent(StringBuilder currentBuffer, char addedChar) {
        this.currentBuffer = currentBuffer;
        this.addedChar = addedChar;
    }

    public StringBuilder getCurrentBuffer() {
        return currentBuffer;
    }

    public char getAddedChar() {
        return addedChar;
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