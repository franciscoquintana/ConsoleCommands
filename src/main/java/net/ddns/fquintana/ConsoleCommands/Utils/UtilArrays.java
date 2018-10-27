package net.ddns.fquintana.ConsoleCommands.Utils;

import net.ddns.fquintana.ConsoleCommands.Console.ConsoleArg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HounterX on 03/01/2018.
 */
public class UtilArrays {
    public static String[] removeArgs(String[] args, int amount)
    {
        String[] newArgs;

        try {
            newArgs = Arrays.copyOfRange(args,amount,args.length);
        }
        catch (Exception ex)
        {
            newArgs = new String[0];
        }

        return newArgs;
    }

    public static List<String> filterArray(List<String> array, String string)
    {
        List<String> newArray = (List<String>) ((ArrayList) array).clone();
        if (!string.isEmpty())
            for (String s : array) {
                if (!s.startsWith(string))
                    newArray.remove(s);
            }
        return newArray;
    }

}
