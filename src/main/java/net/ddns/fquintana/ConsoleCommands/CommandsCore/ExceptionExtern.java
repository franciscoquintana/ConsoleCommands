package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ExceptionExtern extends Exception {
    
    Exception exceptionReal;

    public ExceptionExtern(Exception exceptionReal) {
        this.exceptionReal = exceptionReal;
    }

    @Override
    public String getMessage() {
        return exceptionReal.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return exceptionReal.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return exceptionReal.getCause();
    }

    @Override
    public void printStackTrace() {
        exceptionReal.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream printStream) {
        exceptionReal.printStackTrace(printStream);
    }

    @Override
    public void printStackTrace(PrintWriter printWriter) {
        exceptionReal.printStackTrace(printWriter);
    }
}
