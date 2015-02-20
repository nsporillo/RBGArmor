package net.porillo;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static net.porillo.Utility.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.porillo.commands.CommandHandler;
import net.porillo.workers.Worker;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class RBGArmor extends JavaPlugin implements Listener {

    private CommandHandler handler = new CommandHandler(this);
    private Map<UUID, DebugWindow> debuggers;
    private Map<UUID, Worker> workerz; 
    private static Config config;
    public static Color[] rb;

    @Override
    public void onEnable() {
        workerz = new HashMap<UUID, Worker>();
        debuggers = new HashMap<UUID, DebugWindow>();
        config = new Config(this);
        rb = new Color[config.getColors()];
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

            @Override
            public void run() {
                final int colors = config.getColors();
                final double f = (6.48 / (double) colors);
                for (int i = 0; i < colors; ++i) {
                    double r = sin(f * i + 0.0D) * 127.0D + 128.0D;
                    double g = sin(f * i + (2 * PI / 3)) * 127.0D + 128.0D;
                    double b = sin(f * i + (4 * PI / 3)) * 127.0D + 128.0D;
                    rb[i] = Color.fromRGB((int) r, (int) g, (int) b);
                }
                if (config.shouldDebug()) {
                    getLogger().info("------ RainbowGear Debug ------");
                    getLogger().info("- Using " + config.getColors() + " colors");
                    int ups = 20 / config.getRefreshRate();
                    getLogger().info("- Armor updates " + ups + " times per second");

                }
            }
        });
    }
    
    @Override
    public void onDisable() {
        workerz.clear();
        Bukkit.getScheduler().cancelTasks(this);
    }

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        handler.runCommand(s, l, a);
        return true;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        final UUID uuid = e.getPlayer().getUniqueId();
        if (workerz.containsKey(uuid)) {
            Worker w = workerz.get(uuid);
            Bukkit.getScheduler().cancelTask(w.getUniqueId());
            workerz.remove(uuid);
        }
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        final UUID uuid = e.getEntity().getUniqueId();
        if (workerz.containsKey(uuid)) {
            Worker w = workerz.get(uuid);
            Bukkit.getScheduler().cancelTask(w.getUniqueId());
            workerz.remove(uuid);
            send(e.getEntity(), "death");
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        if (!workerz.containsKey(p.getUniqueId())) {
            for (ItemStack is : p.getInventory().getArmorContents()) {
                ItemMeta meta;
                if (is != null && (meta = is.getItemMeta()) instanceof LeatherArmorMeta) {
                    if (meta.hasLore()) {
                        Worker worker = getWorker(p, meta.getLore());
                        if (worker != null) {
                            this.initWorker(p, worker);
                            return;
                        }
                    }
                }
            }
        }
    }

    private void initWorker(Player p, Worker rw) {
        int rr = config.getRefreshRate();
        BukkitTask id = Bukkit.getScheduler().runTaskTimer(this, rw, rr, rr);
        rw.setUniqueId(id.getTaskId());
        workerz.put(p.getUniqueId(), rw);
        send(p, "&aYour armor is activated, using &b" + rw.getType() + " &acoloring.");
        send(p, "&dUse /rg off or logout to stop armor coloring!");
    }
    
    public Config getOurConfig() {
        return config;
    }
    
    public Map<UUID, Worker> getWorkers() {
        return this.workerz;
    }
    
    public Map<UUID, DebugWindow> getDebuggers() {
        return this.debuggers;
    }
}
