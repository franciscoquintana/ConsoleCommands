package net.ddns.fquintana.ConsoleCommands.Console;

public class ConsoleInvokeEvent {
    private StringBuilder stringBuilder;

    public ConsoleInvokeEvent(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }
}
