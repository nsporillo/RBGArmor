package net.porillo.commands;

import static net.porillo.util.Utility.send;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.porillo.RBGArmor;
import net.porillo.util.Lang;
import net.porillo.util.Permission;
import net.porillo.workers.Worker;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OffCommand extends BaseCommand {

    public OffCommand(RBGArmor plugin) {
        super(plugin);
        super.setName("off");
        super.addUsage(Lang.OFF_USAGE.toString());
        super.setPermission(Permission.OFF.node());
    }

    @Override
    public void runCommand(CommandSender s, List<String> args) {
        if (!this.checkPermission(s)) {
            this.noPermission(s);
            return;
        }
        if (s instanceof Player) {
            Player p = (Player) s;
            final UUID uuid = p.getUniqueId();
            Map<UUID, Worker> workerz = plugin.getWorkers();
            if (workerz.containsKey(uuid)) {
                Worker w = workerz.get(uuid);
                Bukkit.getScheduler().cancelTask(w.getUniqueId());
                workerz.remove(uuid);
                send(p, Lang.OFF_SUCCESS.toString());
            } else {
                send(p, Lang.OFF_FAILURE.toString());
            }
        }
    }

}
