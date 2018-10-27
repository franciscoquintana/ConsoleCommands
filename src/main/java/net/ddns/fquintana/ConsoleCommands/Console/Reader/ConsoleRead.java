package net.ddns.fquintana.ConsoleCommands.Console.Reader;

import biz.source_code.utils.RawConsoleInput;

import java.io.IOException;

public class ConsoleRead {

    private Character character;
    private KeyConstants key;

    public ConsoleRead( KeyConstants key) {
        this.key = key;
    }

    public ConsoleRead(Character character) {
        this.character = character;
    }

    public boolean isKey() {
        return key != null;
    }

    public Character getCharacter() {
        return character;
    }

    public KeyConstants getKey() {
        return key;
    }

    public static ConsoleRead read() throws IOException {
        char read;
        for(;;) {
            read = (char) RawConsoleInput.read(true);
            if (read == '\r')
                read = '\n';

            AnsiReader ansiReader = new AnsiReader();
            ansiReader.add(read);

            if (!ansiReader.isReading()) {
                if (!ansiReader.isReading()) {
                    if (!ansiReader.getResult().equals("")) {
                        return new ConsoleRead(KeyConstants.getByAnsi(ansiReader.getResult()));
                    } else if (Character.getType(read) == Character.PRIVATE_USE) {
                        return new ConsoleRead(KeyConstants.getByWindows(read));
                    } else {
                        return new ConsoleRead(read);
                    }
                }
            }
        }
    }
}
