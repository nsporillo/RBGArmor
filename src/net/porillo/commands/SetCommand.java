package net.porillo.commands;

import static net.porillo.Utility.send;
import static net.porillo.Utility.setLore;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.porillo.RBGArmor;

public class SetCommand extends BaseCommand {

    public SetCommand(RBGArmor plugin) {
        super(plugin);
        super.setName("set");
        super.setRequiredArgs(2);
        super.addUsage("Sets your equiped armor's mode", "mode");
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            String toAdd = "";
            String two = args.get(1);
            if (plugin.getWorkers().containsKey(p.getUniqueId())) {
                send(p, "&cError: Use '/rg off' first to change mode");
                return;
            }
            if (two.equalsIgnoreCase("fade")) {
                if (sender.hasPermission("rainbowgear.set.fade")) {
                    toAdd += "RG|Fade";
                }
            } else if (two.equalsIgnoreCase("sync")) {
                if (sender.hasPermission("rainbowgear.set.sync")) {
                    toAdd += "RG|Sync";
                }
            } else if (two.equalsIgnoreCase("health")) {
                if (sender.hasPermission("rainbowgear.set.health")) {
                    toAdd += "RG|Health";
                }
            } else {
                send(p, "&cThe mode &4'" + two + "'&c is not recognized.");
                return;
            }
            if (toAdd == "") {
                send(p, "&cYou dont have permission to use that command.");
            } else {
                setLore(p, toAdd);
                send(p, "&eSuccess! &aSet coloring mode to &b" + two + "&a.");                       
            }
        }
    }  
}
