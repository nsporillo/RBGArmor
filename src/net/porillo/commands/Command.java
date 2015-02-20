package net.porillo.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface Command {

    public boolean checkPermission(CommandSender sender);

    public int getRequiredArgs();

    public void runCommand(CommandSender sender, List<String> args);

    public void showHelp(CommandSender sender, String label);
}