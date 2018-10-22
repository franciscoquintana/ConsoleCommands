package net.ddns.fquintana.ConsoleCommands.Console;

import biz.source_code.utils.RawConsoleInput;
import net.ddns.fquintana.ChatColor;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.ExceptionExtern;
import net.ddns.fquintana.ConsoleCommands.Console.Events.ConsoleInputEvent;
import net.ddns.fquintana.ConsoleCommands.Console.Events.ConsoleInvokeEvent;

import java.io.IOException;
import java.io.PrintStream;
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

    private Integer currentDespl;
    
    private PrintStream out = System.out;

    public PrintStream getOut() {
        return out;
    }

    private List<Consumer<ConsoleInputEvent>> eventsInput = new ArrayList<>();
    private List<Consumer<ConsoleInvokeEvent>> eventsInvoke = new ArrayList<>();

    public List<Consumer<ConsoleInputEvent>> getEventsInput() {
        return eventsInput;
    }

    public List<Consumer<ConsoleInvokeEvent>> getEventsInvoke() {
        return eventsInvoke;
    }

    public String readLine() {
        return readLine(null);
    }

    public String readLine(Character character) {
        try {
            int read;
            StringBuilder b = new StringBuilder();
            AnsiReader ansiReader = new AnsiReader();
            boolean terminate = false;
            currentDespl = 0;

            while(!terminate) {
                read = RawConsoleInput.read(true);
                if(read == -1)
                    read = ConsoleConstants.CHAR_CTRL_D;
                if(read == '\r')
                    read = '\n';

                ansiReader.add((char) read);

                if (!ansiReader.isReading()){
                    if (!ConsoleUtils.isPrintableChar((char) read)) {
                        if(read == ConsoleConstants.CHAR_BACKSPACE || read == 127) {
                            if (b.length() + currentDespl != 0 ) {
                                Integer posChar = b.length() - 1 + currentDespl;
                                if (posChar == b.length() -1)
                                    ConsoleUtils.clear(1);
                                else {
                                    getOut().print(ConsoleConstants.CHAR_BACKSPACE);
                                    Integer amount = Math.abs(currentDespl) + 1;
                                    for (int i = 0; i < amount; i++) {
                                        getOut().print(' ');
                                    }
                                    getOut().print(ConsoleUtils.left(amount));
                                    write(b.substring(posChar + 1, b.length()), character);
                                    getOut().print(ConsoleUtils.left(amount-1));
                                }

                                b.deleteCharAt(posChar);
                            }
                        } else if(read == '\n') {
                            getOut().print('\n');
                            terminate = true;

                        }
                    }
                    //SUPRIMIR
                    else if (read == 57427 || ansiReader.getResult().equals(ConsoleConstants.SUPR)) {
                        if (currentDespl < 0) {
                            Integer posChar = b.length() + currentDespl;
                            if (posChar == b.length() - 1) {
                                getOut().print(' ');
                                getOut().print(ConsoleConstants.CHAR_BACKSPACE);
                            } else {
                                Integer amount = Math.abs(currentDespl);
                                for (int i = 0; i < amount; i++) {
                                    getOut().print(' ');
                                }
                                getOut().print(ConsoleUtils.left(amount));
                                write(b.substring(posChar + 1, b.length()), character);
                                getOut().print(ConsoleUtils.left(amount - 1));
                            }
                            b.deleteCharAt(posChar);
                            currentDespl++;
                        }
                    }
                    //IZQUIERDA
                    else if (read == 57419 || ansiReader.getResult().equals(ConsoleConstants.IZQUIERDA)) {
                        if (currentDespl > -b.toString().length()) {
                            getOut().print(ConsoleUtils.left(1));
                            currentDespl += -1;
                        }
                    }
                    //DERECHA
                    else if (read == 57421 || ansiReader.getResult().equals(ConsoleConstants.DERECHA)) {
                        if (currentDespl < 0) {
                            getOut().print(ConsoleUtils.right(1));
                            currentDespl += 1;
                        }
                    }
                    else if (ansiReader.getResult().equals("")){
                        if (RawConsoleInput.isStdinIsConsole()) {
                            write(((Character) (char) read).toString(), character);
                            String str = b.substring(b.length() + currentDespl, b.length());
                            write(str, character);
                            for (int i = 0; i < str.length(); i++) {
                                getOut().print(ConsoleConstants.CHAR_BACKSPACE);
                            }

                            b.insert(b.length() + currentDespl, (char) read);
                        }
                        else
                            b.append((char) read);

                    }

                    ConsoleInputEvent event = new ConsoleInputEvent(b, (char) read, ansiReader);
                    for (Consumer<ConsoleInputEvent> consumerEvent : getEventsInput()){
                        consumerEvent.accept(event);
                    }
                    if(event.isShouldCancel())
                        terminate = true;
                }
            }

            ConsoleInvokeEvent event = new ConsoleInvokeEvent(b);
            for (Consumer<ConsoleInvokeEvent> consumerEvent : getEventsInvoke()){
                consumerEvent.accept(event);
            }
            return b.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public void sendMessage(String string)
    {
        getOut().println(string + ChatColor.RESET);
    }

    public void write(String string)
    {
        getOut().print(string);
    }

    public void write(String string, Character character)
    {
        if (character != null)
            write(string.replaceAll("(?s).", character.toString()));
        else
            write(string);
    }

    public void error(String string)
    {
        sendMessage(ChatColor.BOLD + ChatColor.RED + string);
    }

    public void error(ExceptionExtern ex) {
        sendMessage("");
        getOut().print(ChatColor.BOLD + ChatColor.DARK_PURPLE);

        ex.printStackTrace();

        getOut().print(ChatColor.RESET);
        sendMessage("");
    }

    public void sendMessageB(String string)
    {
        sendMessage(ChatColor.BOLD + string);
    }

}
