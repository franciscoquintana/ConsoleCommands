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
    private StringBuilder currentStr;
    public ConsoleMovement consoleMovement = new ConsoleMovement();
    
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

    public String readLine(Character character) {
        try {
            int read;
            //TODO One access MUTEX
            currentStr = new StringBuilder();
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
                        //BORRAR
                        if(read == ConsoleConstants.CHAR_BACKSPACE || read == 127) {
                            consoleMovement.deleteOne(character);
                        } else if(read == '\n') {
                            getOut().print('\n');
                            terminate = true;

                        }
                    }
                    //SUPRIMIR
                    else if (read == 57427 || ansiReader.getResult().equals(ConsoleConstants.SUPR)) {
                        if (currentDespl < 0) {
                            Integer posChar = currentStr.length() + currentDespl;
                            if (posChar == currentStr.length() - 1) {
                                getOut().print(' ');
                                getOut().print(ConsoleConstants.CHAR_BACKSPACE);
                            } else {
                                Integer amount = Math.abs(currentDespl);
                                for (int i = 0; i < amount; i++) {
                                    getOut().print(' ');
                                }
                                getOut().print(ConsoleUtils.left(amount));
                                write(currentStr.substring(posChar + 1, currentStr.length()), character);
                                getOut().print(ConsoleUtils.left(amount - 1));
                            }
                            currentStr.deleteCharAt(posChar);
                            currentDespl++;
                        }
                    }
                    //IZQUIERDA
                    else if (read == 57419 || ansiReader.getResult().equals(ConsoleConstants.IZQUIERDA)) {
                        consoleMovement.moveOneLeft();
                    }
                    //DERECHA
                    else if (read == 57421 || ansiReader.getResult().equals(ConsoleConstants.DERECHA)) {
                        consoleMovement.moveOneRight();
                    }
                    else if (ansiReader.getResult().equals("")){
                        if (RawConsoleInput.isStdinIsConsole()) {
                            write(((Character) (char) read).toString(), character);
                            String str = currentStr.substring(currentStr.length() + currentDespl, currentStr.length());
                            write(str, character);
                            for (int i = 0; i < str.length(); i++) {
                                getOut().print(ConsoleConstants.CHAR_BACKSPACE);
                            }

                            currentStr.insert(currentStr.length() + currentDespl, (char) read);
                        }
                        else
                            currentStr.append((char) read);

                    }

                    ConsoleInputEvent event = new ConsoleInputEvent(currentStr, (char) read, ansiReader);
                    for (Consumer<ConsoleInputEvent> consumerEvent : getEventsInput()){
                        consumerEvent.accept(event);
                    }
                    if(event.isShouldCancel())
                        terminate = true;
                }
            }

            ConsoleInvokeEvent event = new ConsoleInvokeEvent(currentStr);
            for (Consumer<ConsoleInvokeEvent> consumerEvent : getEventsInvoke()){
                consumerEvent.accept(event);
            }
            return currentStr.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public String readLine() {
        return readLine(null);
    }

    public void addToCurrent(String str) {
        currentStr.append(str);
        write(str);
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

    public void sendMessage(String string)
    {
        getOut().println(string + ChatColor.RESET);
    }

    public void sendMessageB(String string)
    {
        sendMessage(ChatColor.BOLD + string);
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

    public class ConsoleMovement {

        private ConsoleMovement() {
        }

        public void moveOneLeft() {
            if (currentDespl > -currentStr.toString().length()) {
                getOut().print(ConsoleUtils.left(1));
                currentDespl += -1;
            }
        }

        public void moveOneRight() {
            if (currentDespl < 0) {
                getOut().print(ConsoleUtils.right(1));
                currentDespl += 1;
            }
        }

        public void moveLeft() {
            while (currentDespl > currentStr.toString().length()) {
                moveOneLeft();
            }
        }

        public void moveRight() {
            while (currentDespl < 0) {
                moveOneRight();
            }
        }

        public void deleteOne() {
            deleteOne(null);
        }

        private void deleteOne(Character character) {
            if (currentStr.length() + currentDespl != 0 ) {
                Integer posChar = currentStr.length() - 1 + currentDespl;
                if (posChar == currentStr.length() -1)
                    ConsoleUtils.clear(1);
                else {
                    getOut().print(ConsoleConstants.CHAR_BACKSPACE);
                    Integer amount = Math.abs(currentDespl) + 1;
                    for (int i = 0; i < amount; i++) {
                        getOut().print(' ');
                    }
                    getOut().print(ConsoleUtils.left(amount));
                    write(currentStr.substring(posChar + 1, currentStr.length()), character);
                    getOut().print(ConsoleUtils.left(amount-1));
                }

                currentStr.deleteCharAt(posChar);
            }
        }


    }
}
