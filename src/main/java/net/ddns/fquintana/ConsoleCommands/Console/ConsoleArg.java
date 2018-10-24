package net.ddns.fquintana.ConsoleCommands.Console;

public class ConsoleArg {
    private String argStr;
    private boolean haveQuotes;

    public ConsoleArg(String argStr, boolean haveQuotes) {
        this.argStr = argStr;
        this.haveQuotes = haveQuotes;
    }

    public String getArgStr() {
        return argStr;
    }

    public boolean isHaveQuotes() {
        return haveQuotes;
    }
}
