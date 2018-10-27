package net.ddns.fquintana.ConsoleCommands.Console.Reader;

import net.ddns.fquintana.ConsoleCommands.Console.ConsoleConstants;

import java.util.Arrays;
import java.util.List;

public class AnsiReader {
    public enum ANSI {DISABLED, READINGBRACKET, READCOMMAND}
    public ANSI status;
    public StringBuilder builder;

    private String result;

    public AnsiReader() {
        this.status = ANSI.DISABLED;
        this.builder = new StringBuilder();
    }

    public void reset() {
        status = ANSI.DISABLED;
        builder = new StringBuilder();
    }

    boolean checkFinish() {
        String string = builder.toString();
        if (Character.isDigit(string.charAt(2)))
            return string.length() == 4;
        return string.length() == 3;
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
            if (checkFinish()) {
                result = builder.toString();
                reset();
            }
        }
        else
        if (status == ANSI.DISABLED)
            return;

        builder.append(ch);
    }

    public String getResult() {
        String resultCopy = result;
        if (resultCopy == null)
            return "";
        return resultCopy;
    }

    public boolean isReading() {
        return status != ANSI.DISABLED;
    }
}
