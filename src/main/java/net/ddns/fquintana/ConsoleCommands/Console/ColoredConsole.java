package net.ddns.fquintana.ConsoleCommands.Console;

import biz.source_code.utils.RawConsoleInput;
import net.ddns.fquintana.ChatColor;
import net.ddns.fquintana.ConsoleCommands.CommandsCore.ExceptionExtern;

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
        history = new ConsoleHistory(10);
    }

    private Integer currentDespl;
    private ConsoleHistory history;

    private List<Consumer<ConsoleInputEvent>> events = new ArrayList<>();

    public List<Consumer<ConsoleInputEvent>> getEvents() {
        return events;
    }

    public String readLine() {
        try {
            int read;
            StringBuilder b = new StringBuilder();
            AnsiReader ansiReader = new AnsiReader();
            boolean terminate = false;
            currentDespl = 0;
            Boolean previousHis = false;


            while(!terminate) {

                Boolean showHis = false;
                Boolean undoHis = false;
                Boolean resetHis = true;

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
                                    System.out.print(ConsoleConstants.CHAR_BACKSPACE);
                                    Integer amount = Math.abs(currentDespl) + 1;
                                    for (int i = 0; i < amount; i++) {
                                        System.out.print(' ');
                                    }
                                    System.out.print(ConsoleUtils.left(amount));
                                    System.out.print(b.substring(posChar + 1, b.length()));
                                    System.out.print(ConsoleUtils.left(amount-1));
                                }

                                b.deleteCharAt(posChar);
                            }
                        } else if(read == '\n') {
                            System.out.print('\n');
                            terminate = true;

                        } else if(read == ConsoleConstants.CHAR_CTRL_Z) {
                            undoHis = true;
                        }
                    }
                    //SUPRIMIR
                    else if (read == 57427 || ansiReader.getResult().equals(ConsoleConstants.SUPR)) {
                        if (currentDespl < 0) {
                            Integer posChar = b.length() + currentDespl;
                            if (posChar == b.length() - 1) {
                                System.out.print(' ');
                                System.out.print(ConsoleConstants.CHAR_BACKSPACE);
                            } else {
                                Integer amount = Math.abs(currentDespl);
                                for (int i = 0; i < amount; i++) {
                                    System.out.print(' ');
                                }
                                System.out.print(ConsoleUtils.left(amount));
                                System.out.print(b.substring(posChar + 1, b.length()));
                                System.out.print(ConsoleUtils.left(amount - 1));
                            }
                            b.deleteCharAt(posChar);
                            currentDespl++;
                        }
                    }
                    //IZQUIERDA
                    else if (read == 57419 || ansiReader.getResult().equals(ConsoleConstants.IZQUIERDA)) {
                        if (currentDespl > -b.toString().length()) {
                            System.out.print(ConsoleUtils.left(1));
                            currentDespl += -1;
                        }
                    }
                    //DERECHA
                    else if (read == 57421 || ansiReader.getResult().equals(ConsoleConstants.DERECHA)) {
                        if (currentDespl < 0) {
                            System.out.print(ConsoleUtils.right(1));
                            currentDespl += 1;
                        }
                    }
                    //ARRIBA
                    else if (read == 57416 || ansiReader.getResult().equals(ConsoleConstants.ARRIBA)) {
                        history.up();
                        showHis = true;
                        resetHis = false;
                    }
                    //ABAJO
                    else if (read == 57424|| ansiReader.getResult().equals(ConsoleConstants.ABAJO)) {
                        history.down();
                        showHis = true;
                        resetHis = false;
                    }
                    else {
                        if (RawConsoleInput.isStdinIsConsole()) {
                            System.out.print((char) read);
                            String str = b.substring(b.length() + currentDespl, b.length());
                            System.out.print(str);
                            for (int i = 0; i < str.length(); i++) {
                                System.out.print(ConsoleConstants.CHAR_BACKSPACE);
                            }

                            b.insert(b.length() + currentDespl, (char) read);
                        }
                        else
                            b.append((char) read);

                    }

                    if (previousHis && resetHis)
                        history.setActualNext();

                    if (showHis || undoHis) {
                        String str = undoHis ? history.undo() : history.get();
                        if (str != null) {
                            if (!previousHis)
                                history.setStrRestore(b.toString());
                            ConsoleUtils.clear(b.length());
                            System.out.print(str);

                            b = new StringBuilder(str);
                        }
                    }

                    previousHis = !resetHis;
                }



                ConsoleInputEvent event = new ConsoleInputEvent(b, (char) read);
                for (Consumer<ConsoleInputEvent> consumerEvent : getEvents()){
                    consumerEvent.accept(event);
                }
                if(event.isShouldCancel())
                    terminate = true;
            }

            history.add(b.toString());
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

    public void write(String string)
    {
        System.out.print(string);
    }

    public void error(String string)
    {
        sendMessage(ChatColor.BOLD + ChatColor.RED + string);
    }

    public void error(ExceptionExtern ex) {
        sendMessage("");
        System.out.print(ChatColor.BOLD + ChatColor.DARK_PURPLE);

        ex.printStackTrace();

        System.out.print(ChatColor.RESET);
        sendMessage("");
    }

    public void sendMessageB(String string)
    {
        sendMessage(ChatColor.BOLD + string);
    }




}
