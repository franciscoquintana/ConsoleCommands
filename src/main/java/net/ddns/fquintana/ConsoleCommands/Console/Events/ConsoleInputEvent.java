package net.ddns.fquintana.ConsoleCommands.Console.Events;

import net.ddns.fquintana.ConsoleCommands.Console.ColoredConsole;
import net.ddns.fquintana.ConsoleCommands.Console.Reader.ConsoleRead;

public class ConsoleInputEvent {
    private final ConsoleRead lastRead;
    private final ColoredConsole console;

    private boolean cancelled;

    public ConsoleInputEvent( ColoredConsole console, ConsoleRead lastRead) {
        this.lastRead = lastRead;
        this.console = console;
    }


    public ConsoleRead getLastRead() {
        return lastRead;
    }


    public ColoredConsole getConsole() {
        return console;
    }

    public void setCancelled(boolean state) {
         cancelled = state;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}