package net.porillo.commands;

import static net.porillo.Utility.getSym;
import static net.porillo.Utility.send;

import java.util.List;

import org.bukkit.command.CommandSender;

import net.porillo.Lang;
import net.porillo.Mode;
import net.porillo.RBGArmor;

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
        send(s, Lang.LIST_HEADER.format(dash));
        for(Mode m : Mode.values()) {
            send(s, Lang.LIST_OUTPUT.format(m.toString(), m.getDescription()));
        }
    }
}
