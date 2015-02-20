package net.porillo.commands;

import static net.porillo.Utility.send;
import static net.porillo.Utility.setLore;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.porillo.Lang;
import net.porillo.Mode;
import net.porillo.RBGArmor;

public class SetCommand extends BaseCommand {

    public SetCommand(RBGArmor plugin) {
        super(plugin);
        super.setName("set");
        super.setRequiredArgs(1);
        super.addUsage(Lang.SET_USAGE.toString(), "mode");
        super.setPermission("rgbarmor.set");
    }

    @Override
    public void runCommand(CommandSender s, List<String> args) {
        if (!this.checkPermission(s)) {
            this.noPermission(s);
            return;
        }
        if (s instanceof Player) {
            Player p = (Player) s;
            String toAdd = "";
            String two = args.get(0);
            if (plugin.getWorkers().containsKey(p.getUniqueId())) {
                send(p, Lang.SET_ERROR.toString());
                return;
            }
            for (Mode m : Mode.values()) {
                if (m.getName().equalsIgnoreCase(two)) {
                    if (p.hasPermission("rgbarmor.set." + m.getName().toLowerCase())) {
                        toAdd = Lang.TITLE_PREFIX.toString() + "|" + m.getName();
                    } else {
                        super.noPermission(s);
                    }
                }
            }
            if (toAdd == "") {
                send(p, Lang.SET_FAILURE.replace("%mode", two));
            } else {
                setLore(p, toAdd);
                send(p, Lang.SET_SUCCESS.replace("%mode", two));
            }
        }
    }
}
