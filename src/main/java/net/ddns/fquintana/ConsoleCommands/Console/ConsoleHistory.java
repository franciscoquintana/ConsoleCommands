package net.ddns.fquintana.ConsoleCommands.Console;

import net.ddns.fquintana.ConsoleCommands.Console.Events.ConsoleInputEvent;
import net.ddns.fquintana.ConsoleCommands.Console.Events.ConsoleInvokeEvent;
import net.ddns.fquintana.ConsoleCommands.Console.Reader.ConsoleRead;
import net.ddns.fquintana.ConsoleCommands.Console.Reader.KeyConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConsoleHistory {
    private List<String> commands;
    private Integer max;
    private Integer actual;

    private Boolean moveUp = true;

    private String strRestore;

    /**
     * EVENTS
     */
    private Boolean previousHis = false;

    private HistoryInputEvent inputEvent = new HistoryInputEvent();

    private HistoryInvokeEvent invokeEvent = new HistoryInvokeEvent();

    public ConsoleHistory(Integer max) {
        this.max = max;
        this.commands = new ArrayList<>(max);
    }

    public HistoryInputEvent getInputEvent() {
        return inputEvent;
    }

    public HistoryInvokeEvent getInvokeEvent() {
        return invokeEvent;
    }

    private boolean containsCommand(String strCommand) {
        for (String str : commands) {
            if (strCommand.equals(str))
                return true;
        }

        return false;
    }

    public void add(String str) {
        if (commands.size() != 0 && str.equals(commands.get(commands.size()-1)))
            return;
        if (commands.size() == max)
            commands.remove(0);
        commands.add(str);
        reset();
        moveUp = false;
    }

    public boolean up() {
        if (actual != null && actual - 1 >= 0 && moveUp) {
            actual--;
            return true;
        }
        if (!moveUp)
            moveUp = true;
        return false;
    }

    public boolean down() {
        if (!moveUp)
            moveUp = true;

        if (actual != null && actual + 1 < commands.size()) {
            actual++;
            return true;
        }
        return false;
    }

    public void reset() {
        actual = commands.size() - 1;
    }

    //Evita el movimiento del historial a algo mas nuevo
    public void setActualNext() {
        if (actual != null) {
            moveUp = false;
        }
    }

    public String undo() {
        reset();
        return strRestore;
    }

    public void setStrRestore(String strRestore) {
        this.strRestore = strRestore;
    }

    public String get() {
        if (actual != null && actual != -2) {
            if (actual > commands.size() - 1)
                actual = commands.size() - 1;
            return commands.get(actual);
        }
        return null;
    }

    private class HistoryInputEvent implements Consumer<ConsoleInputEvent> {

        @Override
        public void accept(ConsoleInputEvent consoleInputEvent) {
            Boolean showHis = false;
            Boolean undoHis = false;
            Boolean resetHis = true;

            StringBuilder b = consoleInputEvent.getConsole().getCurrentStr();

            ConsoleRead read = consoleInputEvent.getLastRead();

            if (read.isKey()) {
                if (read.getKey() == KeyConstants.UP_ARROW) {
                    ConsoleHistory.this.up();
                    showHis = true;
                    resetHis = false;
                }
                else if (read.getKey() == KeyConstants.DOWN_ARROW) {
                    ConsoleHistory.this.down();
                    showHis = true;
                    resetHis = false;
                }
            }
            else if(read.getCharacter() == ConsoleConstants.CHAR_CTRL_Z)
                undoHis = true;


            if (previousHis && resetHis)
                ConsoleHistory.this.setActualNext();

            if (showHis || undoHis) {
                String str = undoHis ? ConsoleHistory.this.undo() : ConsoleHistory.this.get();
                if (str != null) {
                    if (!previousHis)
                        ConsoleHistory.this.setStrRestore(b.toString());
                    consoleInputEvent.getConsole().consoleMovement.moveRight();
                    consoleInputEvent.getConsole().consoleMovement.delete(b.length());
                    consoleInputEvent.getConsole().addToCurrent(str);
                }
            }
            previousHis = !resetHis;
        }
    }

    private class HistoryInvokeEvent implements Consumer<ConsoleInvokeEvent> {

        @Override
        public void accept(ConsoleInvokeEvent consoleInvokeEvent) {
            ConsoleHistory.this.add(consoleInvokeEvent.getStringBuilder().toString());
            previousHis = false;
        }
    }
}
