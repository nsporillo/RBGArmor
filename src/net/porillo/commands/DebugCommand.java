package net.porillo.commands;

import static net.porillo.Utility.send;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.porillo.DebugWindow;
import net.porillo.RainbowGear;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand extends BaseCommand {

    public DebugCommand(RainbowGear plugin) {
        super(plugin);
        super.setName("debug");
        super.addUsage("Toggles a armor debug scoreboard");
        super.setPermission("rainbowgear.debug");
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if(sender instanceof Player) {
           Player p = (Player)sender;
           UUID uuid = p.getUniqueId();
           Map<UUID, DebugWindow> debuggers = plugin.getDebuggers();
           if(debuggers.containsKey(uuid)) {
               DebugWindow dw = debuggers.get(uuid);
               dw.close();
               debuggers.remove(uuid);
               send(p, "&eSuccess! Removed debug window");
           } else {
               DebugWindow dw = new DebugWindow(plugin, p);
               debuggers.put(uuid, dw);                          
               dw.display();
               send(p, "&eSuccess! Enabled debug window");
           }
        }     
    }
}
