package net.porillo.commands;

import static net.porillo.util.Utility.getSym;
import static net.porillo.util.Utility.send;

import java.util.List;

import org.bukkit.command.CommandSender;

import net.porillo.RBGArmor;
import net.porillo.util.Lang;
import net.porillo.util.Mode;

public class ListCommand extends BaseCommand {

    public ListCommand(RBGArmor plugin) {
        super(plugin);
        super.setName("list");
        super.addUsage(Lang.LIST_USAGE.toString());
        super.setPermission("rgbarmor.list");
    }

    @Override
    public void runCommand(CommandSender s, List<String> args) {
        if (!this.checkPermission(s)) {
            this.noPermission(s);
            return;
        }
        String dash = getSym("-", 8);
        send(s, Lang.LIST_HEADER.toString().replace("%dash", dash));
        for (Mode m : Mode.values()) {
            String send = Lang.LIST_OUTPUT.toString();
            send = send.replace("%mode", m.getName());
            send = send.replace("%description", m.getDescription());
            send(s, send);
        }
    }
}
