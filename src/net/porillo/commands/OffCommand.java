package net.porillo.commands;

import static net.porillo.Utility.send;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.porillo.RainbowGear;
import net.porillo.workers.Worker;

public class OffCommand extends BaseCommand {

    public OffCommand(RainbowGear plugin) {
        super(plugin);
        super.setName("off");
        super.addUsage("Disables your armor coloring");
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            final UUID uuid = p.getUniqueId();
            Map<UUID, Worker> workerz = plugin.getWorkers();
            if (workerz.containsKey(uuid)) {
                Worker w = workerz.get(uuid);
                Bukkit.getScheduler().cancelTask(w.getUniqueId());
                workerz.remove(uuid);
                send(p, "&eSuccess. Deactivated your armor.");
            } else {
                send(p, "&cError: Your armor is not updating, &ecant turn off.");
            }
        }
    }

}
