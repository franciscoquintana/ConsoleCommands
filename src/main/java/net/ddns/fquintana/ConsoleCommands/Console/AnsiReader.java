package net.ddns.fquintana.ConsoleCommands.Console;

import java.util.Arrays;
import java.util.List;

public class AnsiReader {
    public enum ANSI {DISABLED, READINGBRACKET, READCOMMAND}
    private List<String> ansiCmds;
    public ANSI status;
    public StringBuilder builder;

    private String result;

    public AnsiReader() {
        this.status = ANSI.DISABLED;
        this.builder = new StringBuilder();
        ansiCmds = Arrays.asList(ConsoleConstants.IZQUIERDA, ConsoleConstants.DERECHA, ConsoleConstants.ABAJO, ConsoleConstants.ARRIBA, ConsoleConstants.SUPR, ConsoleConstants.INSERT);
    }

    public void reset() {
        status = ANSI.DISABLED;
        builder = new StringBuilder();
    }

    public boolean existInList(String str){
        for (String strCmd : ansiCmds) {
            if (strCmd.startsWith(str))
                return true;
        }
        return false;
    }

    public boolean equalsInList(String str){
        for (String strCmd : ansiCmds) {
            if (strCmd.equals(str))
                return true;
        }
        return false;
    }

    public void add(char ch) {
        result = null;
        if(ch == ConsoleConstants.CHAR_ESC)
            status = ANSI.READINGBRACKET;
        else
        if (status == ANSI.READINGBRACKET) {
            if (ch != ConsoleConstants.CHAR_LEFTBRACKET) {
                reset();
                return;
            } else {
                status = ANSI.READCOMMAND;
            }
        }
        else
        if (status == ANSI.READCOMMAND) {
            if (!existInList(builder.toString() + ch))
                reset();
        }
        else
        if (status == ANSI.DISABLED)
            return;

        builder.append(ch);

        if (equalsInList(builder.toString())) {
            result = builder.toString();
            reset();
        }
    }

    public String getResult() {
        String resultCopy = result;
        if (resultCopy == null)
            resultCopy = "";
        return resultCopy;
    }

    public boolean isReading() {
        return status != ANSI.DISABLED;
    }
}
