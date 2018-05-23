package net.ddns.fquintana;

import biz.source_code.utils.RawConsoleInput;
import net.ddns.fquintana.ConsoleCommands.Console.CharConstants;
import net.ddns.fquintana.ConsoleCommands.Console.ConsoleInputEvent;
import net.ddns.fquintana.ConsoleCommands.Console.ConsoleTab;
import net.ddns.fquintana.ConsoleCommands.Console.ConsoleUtils;

import java.io.IOException;
import java.util.function.Consumer;

import static biz.source_code.utils.RawConsoleInput.resetConsoleMode;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Consumer<ConsoleInputEvent> consoleHandler = new Consumer<ConsoleInputEvent>() {
            @Override
            public void accept(ConsoleInputEvent consoleInputEvent) {
            }
        };

        ConsoleTab tab = new ConsoleTab("prueba", "pra");

        Boolean exit = false;
        while (!exit) {
            int read;
            final StringBuilder b = new StringBuilder();
            boolean terminate = false;
            while(!terminate) {
                read = RawConsoleInput.read(true);
                if(read == -1)
                    read = CharConstants.CHAR_CTRL_D;
                if(read == '\r')
                    read = '\n';
                if (!ConsoleUtils.isPrintableChar((char) read)) {
                    if(read == CharConstants.CHAR_BACKSPACE) {
                        if (b.length() != 0) {
                            b.setLength(b.length() - 1);
                            clear(1);
                        }
                    } else if(read == '\n') {
                        terminate = true;
                    } else  if (read == CharConstants.CHAR_TAB) {
                        tab.tab(b);
                    }
                }
                else
                    b.append((char) read);

                if (read != CharConstants.CHAR_TAB)
                    tab.reset();

                ConsoleInputEvent event = new ConsoleInputEvent(b, (char) read);
                consoleHandler.accept(event);
                if(event.isShouldCancel())
                    terminate = true;

            }
            if (b.toString().equalsIgnoreCase("exit"))
                exit = true;
            System.out.println("Resultado: " + b.toString());

        }
        resetConsoleMode();
    }

    public static void clear(int amount) {
        for (int i=0; i<amount; i++) {
            System.out.print(CharConstants.CHAR_BACKSPACE);
            System.out.print(" ");
            System.out.print(CharConstants.CHAR_BACKSPACE);
        }
    }
}
