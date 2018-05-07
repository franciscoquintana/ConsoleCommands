package net.ddns.fquintana.ConsoleCommands.CommandsCore;

import net.ddns.fquintana.ChatColor;

public class ColoredConsole {


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
