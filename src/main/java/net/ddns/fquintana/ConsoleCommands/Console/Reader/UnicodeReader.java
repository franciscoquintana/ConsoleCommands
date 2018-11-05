package net.ddns.fquintana.ConsoleCommands.Console.Reader;

import net.ddns.fquintana.ConsoleCommands.Console.ConsoleConstants;

import java.util.Arrays;
import java.util.List;

public class UnicodeReader {
    public enum UNICODE {DISABLED, READINGBRACKET, READCOMMAND}
    public UNICODE status;
    public StringBuilder builder;

    private String result;

    public UnicodeReader() {
        this.status = UNICODE.DISABLED;
        this.builder = new StringBuilder();
    }

    public void reset() {
        status = UNICODE.DISABLED;
        builder = new StringBuilder();
    }

    boolean checkFinish() {
        String string = builder.toString();
        if (string.length() < 3)
            return false;
        if (Character.isDigit(string.charAt(2)))
            return string.length() == 4;
        return string.length() == 3;
    }


    public void add(char ch) {
        result = null;
        builder.append(ch);
        if(ch == ConsoleConstants.CHAR_ESC)
            status = UNICODE.READINGBRACKET;
        else if (status == UNICODE.READINGBRACKET) {
            if (ch != ConsoleConstants.CHAR_LEFTBRACKET) {
                reset();
                return;
            } else {
                status = UNICODE.READCOMMAND;
            }
        }
        else
        if (status == UNICODE.READCOMMAND) {
            if (checkFinish()) {
                result = builder.toString();
                reset();
            }
        }
        else
        if (status == UNICODE.DISABLED) {
            reset();
        }
    }

    public String getResult() {
        String resultCopy = result;
        if (resultCopy == null)
            return "";
        return resultCopy;
    }

    public boolean isReading() {
        return status != UNICODE.DISABLED;
    }
}
