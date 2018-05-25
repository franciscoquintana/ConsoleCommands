package net.ddns.fquintana.ConsoleCommands.Console;

import java.util.ArrayList;
import java.util.List;

public class ConsoleHistory {
    List<String> commands;
    Integer max;
    Integer actual;

    String strRestore;

    public ConsoleHistory(Integer max) {
        this.max = max;
        this.commands = new ArrayList<>(max);
    }

    public void add(String str) {
        if (commands.size() == max)
            commands.remove(0);
        commands.add(str);
        actual = commands.indexOf(str) + 1;
    }

    public boolean up() {
        if (actual != null && actual != 0) {
            actual--;
            return true;
        }
        return false;
    }

    public boolean down() {
        if (actual != null && actual + 1 < commands.size()) {
            actual++;
            return true;
        }
        return false;
    }

    public void reset() {
        actual = commands.size();
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
            return commands.get(actual);
        }
        return null;
    }
}
