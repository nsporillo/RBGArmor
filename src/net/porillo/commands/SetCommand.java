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
        super.setRequiredArgs(1);
        super.addUsage("Sets your equiped armor's mode", "mode");
        super.setPermission("rgbarmor.set");
    }

    @Override
    public void runCommand(CommandSender s, List<String> args) {
        if (!this.checkPermission(s)) {
            this.noPermission(s);
            return;
        }
        if(s instanceof Player) {
            Player p = (Player)s;
            String toAdd = "";
            String two = args.get(1);
            if (plugin.getWorkers().containsKey(p.getUniqueId())) {
                send(p, "&cError: Use '/rg off' first to change mode");
                return;
            }
            if (two.equalsIgnoreCase("fade")) {
                if (p.hasPermission("rgbarmor.set.fade")) {
                    toAdd += "RG|Fade";
                }
            } else if (two.equalsIgnoreCase("sync")) {
                if (p.hasPermission("rgbarmor.set.sync")) {
                    toAdd += "RG|Sync";
                }
            } else if (two.equalsIgnoreCase("health")) {
                if (p.hasPermission("rgbarmor.set.health")) {
                    toAdd += "RG|Health";
                }
            } else {
                send(p, "&cThe mode &4'" + two + "'&c is not recognized.");
                return;
            }
            if (toAdd == "") {
                super.noPermission(s);
            } else {
                setLore(p, toAdd);
                send(p, "&eSuccess! &aSet coloring mode to &b" + two + "&a.");                       
            }
        }
    }  
}
