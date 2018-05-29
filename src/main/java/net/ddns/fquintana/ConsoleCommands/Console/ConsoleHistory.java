package net.ddns.fquintana.ConsoleCommands.Console;

import java.util.ArrayList;
import java.util.List;

public class ConsoleHistory {
    List<String> commands;
    Integer max;
    Integer actual;

    Boolean moveUp = true;

    String strRestore;

    public ConsoleHistory(Integer max) {
        this.max = max;
        this.commands = new ArrayList<>(max);
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
}
