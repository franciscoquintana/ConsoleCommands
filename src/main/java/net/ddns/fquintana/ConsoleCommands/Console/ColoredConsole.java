package net.ddns.fquintana.ConsoleCommands.Console;

import biz.source_code.utils.RawConsoleInput;
import net.ddns.fquintana.ChatColor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ColoredConsole {

    public ColoredConsole() {
        try {
            RawConsoleInput.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Consumer<ConsoleInputEvent>> events = new ArrayList<>();

    public List<Consumer<ConsoleInputEvent>> getEvents() {
        return events;
    }

    public String readLine() {
        try {
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
                            ConsoleUtils.clear(1);
                        }
                    } else if(read == '\n') {
                        System.out.print('\n');
                        terminate = true;
                    }
                }
                else if (read == 57419) {
                    System.out.print(CharConstants.IZQUIERDA);
                }
                else {
                    if (RawConsoleInput.isStdinIsConsole())
                        System.out.print((char) read);
                    b.append((char) read);
                }
                ConsoleInputEvent event = new ConsoleInputEvent(b, (char) read);
                for (Consumer<ConsoleInputEvent> consumerEvent : getEvents()){
                    consumerEvent.accept(event);
                }
                if(event.isShouldCancel())
                    terminate = true;
            }

            return b.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public void sendMessage(String string)
    {
        System.out.println(string + ChatColor.RESET);
    }

    public void error(String string)
    {
        sendMessage(ChatColor.BOLD + ChatColor.RED + string);
    }

    public void sendMessageB(String string)
    {
        sendMessage(ChatColor.BOLD + string);
    }




}
