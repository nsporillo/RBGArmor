package net.porillo.commands;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.porillo.DebugWindow;
import net.porillo.Lang;
import net.porillo.RBGArmor;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand extends BaseCommand {

    public DebugCommand(RBGArmor plugin) {
        super(plugin);
        super.setName("debug");
        super.addUsage(Lang.DEBUG_USAGE.toString());
        super.setPermission("rgbarmor.debug");
    }

    @Override
    public void runCommand(CommandSender s, List<String> args) {
        if (!this.checkPermission(s)) {
            this.noPermission(s);
            return;
        }
        if (s instanceof Player) {
            Player p = (Player) s;
            UUID uuid = p.getUniqueId();
            Map<UUID, DebugWindow> debuggers = plugin.getDebuggers();
            if (debuggers.containsKey(uuid)) {
                DebugWindow dw = debuggers.get(uuid);
                dw.close();
                debuggers.remove(uuid);
            } else {
                DebugWindow dw = new DebugWindow(plugin, p);
                debuggers.put(uuid, dw);
                dw.display();
            }
        }
    }
}
