package net.porillo.commands;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BLUE;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.YELLOW;

import java.util.ArrayList;
import java.util.List;

import net.porillo.Lang;
import net.porillo.RBGArmor;
import net.porillo.Utility;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand implements Command {

    protected RBGArmor plugin;
    protected String name;
    protected String permission;
    protected int required = 0;
    protected List<String> usages = new ArrayList<String>();

    public BaseCommand(final RBGArmor plugin) {
        this.plugin = plugin;
    }

    public final void addUsage(String desc, String... uses) {
        final StringBuilder usage = new StringBuilder().append(BLUE).append(
                String.format("%1$-" + 8 + "s", this.name));
        boolean color = true;
        for (String use : uses) {
            if (color)
                usage.append(YELLOW);
            else
                usage.append(AQUA);
            color = !color;
            usage.append(String.format("%1$-" + 8 + "s", use));
        }
        usage.append(GREEN);
        usage.append(desc);
        this.usages.add(usage.toString());
    }

    @Override
    public boolean checkPermission(final CommandSender sender) {
        return sender.hasPermission(this.permission);
    }

    @Override
    public int getRequiredArgs() {
        return this.required;
    }

    protected void noPermission(final CommandSender sender) {
        Utility.send(sender, Lang.NOPERMS.toString());
    }

    protected final void setName(final String name) {
        this.name = name;
    }

    protected final void setPermission(final String perm) {
        this.permission = perm;
    }

    protected final void setRequiredArgs(final int req) {
        this.required = req;
    }

    @Override
    public void showHelp(final CommandSender sender, final String label) {
        for (final String usage : this.usages) {
            sender.sendMessage(GRAY + String.format("%1$-" + 10 + "s", label)
                    + ChatColor.translateAlternateColorCodes('&', usage));
        }
    }
}
