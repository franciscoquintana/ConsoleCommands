package net.ddns.fquintana.ConsoleCommands.Console;

import biz.source_code.utils.RawConsoleInput;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.ExceptionExtern;
import net.ddns.fquintana.ConsoleCommands.Console.Events.ConsoleInputEvent;
import net.ddns.fquintana.ConsoleCommands.Console.Events.ConsoleInvokeEvent;
import net.ddns.fquintana.ConsoleCommands.Console.Reader.ConsoleRead;
import net.ddns.fquintana.ConsoleCommands.Console.Reader.KeyConstants;

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

    private boolean reading = false;
    private String strRead;

    private Character character;

    //TODO WAIT UNTIL READ FINISH
    public void setCharacter(Character character) throws InterruptedException {
        synchronized (this) {
            while (reading)
                wait();
        }
        this.character = character;
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

    public String readLine() throws InterruptedException {
        synchronized (this) {
            if (reading)
                wait();
            if (!reading)
                internalReadLine();
            else
                wait();
        }
        return strRead;
    }

    private void internalReadLine() {
        String text = "";
        reading = true;
        try {
            ConsoleRead read;
            currentStr = new StringBuilder();
            boolean terminate = false;
            currentDespl = 0;

            while(!terminate) {
                read = ConsoleRead.read();

                ConsoleInputEvent event = new ConsoleInputEvent( this, read);

                for (Consumer<ConsoleInputEvent> consumerEvent : getEventsInput()){
                    consumerEvent.accept(event);
                }

                if(!event.isCancelled())
                    if (!read.isKey()) {
                        Character ch = read.getCharacter();
                        if(ch == ConsoleConstants.CHAR_BACKSPACE || ch == ConsoleConstants.CHAR_BACKSPACE_UNIX) {
                            consoleMovement.deleteOne();
                        } else if(ch == '\n') {
                            getOut().print('\n');
                            terminate = true;

                        }
                        else if (RawConsoleInput.isStdinIsConsole()) {
                            write(ch.toString(), character);
                            String str = currentStr.substring(currentStr.length() + currentDespl, currentStr.length());
                            write(str, character);
                            for (int i = 0; i < str.length(); i++) {
                                getOut().print(ConsoleConstants.CHAR_BACKSPACE);
                            }

                            currentStr.insert(currentStr.length() + currentDespl, ch);
                        }
                        else
                            currentStr.append(read.getCharacter());
                    }
                    else
                    {
                        KeyConstants key = read.getKey();
                        if (key == KeyConstants.SUPR) {
                            if (currentDespl < 0) {
                                int posChar = currentStr.length() + currentDespl;
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
                        else if (key == KeyConstants.LEFT_ARROW) {
                            consoleMovement.moveOneLeft();
                        }
                        else if (key == KeyConstants.RIGHT_ARROW) {
                            consoleMovement.moveOneRight();
                        }
                    }

                }

            ConsoleInvokeEvent event = new ConsoleInvokeEvent(currentStr);

            for (Consumer<ConsoleInvokeEvent> consumerEvent : getEventsInvoke()){
                consumerEvent.accept(event);
            }

            text = currentStr.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        reading = false;
        strRead = text;
        synchronized (this) {
            notifyAll();
        }
    }

    public StringBuilder getCurrentStr() {
        return currentStr;
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
            delete(1);
        }

        public void delete(int amount) {
            if (currentStr.length() + currentDespl - amount >= 0 ) {
                Integer posChar = currentStr.length() - amount + currentDespl;
                if (posChar == currentStr.length() - amount)
                    ConsoleUtils.clear(amount);
                else {
                    getOut().print(ConsoleConstants.CHAR_BACKSPACE);
                    Integer amountWrite = Math.abs(currentDespl) + amount;
                    for (int i = 0; i < amountWrite; i++) {
                        getOut().print(' ');
                    }
                    getOut().print(ConsoleUtils.left(amountWrite));
                    write(currentStr.substring(posChar + amount, currentStr.length()), null);
                    getOut().print(ConsoleUtils.left(amountWrite - amount));
                }

                currentStr.delete(posChar, posChar + amount);
            }
        }
    }
}
