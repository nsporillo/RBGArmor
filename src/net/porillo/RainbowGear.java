package net.porillo;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static net.porillo.Utility.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

public class RainbowGear extends JavaPlugin implements Listener {

    private Map<UUID, Worker> workerz;
    private Map<UUID, DebugWindow> debuggers;
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String toAdd = "";
            if (args.length == 0) {
                String dash = getSym("-", 8);
                send(p, "&2" + dash + "&9" + " RainbowGear " + "&2" + dash);
                send(p, "&2Help: &a/rg help");
                send(p, "&2Author: &3milkywayz");
            } else if (args.length == 1) {
                String one = args[0];
                if (one.equalsIgnoreCase("set")) {
                    send(p, "&cError: &aUse /rg set &emode");
                } else if (one.equalsIgnoreCase("help")) {
                    String dash = getSym("-", 8);
                    send(p, "&2" + dash + " &9Command Help&2 " + dash);
                    send(p, "- &b/rg set &amode &f- &3Sets coloring mode");
                    send(p, "- &b/rg modes &f- &3Lists available modes");
                    send(p, "- &b/rg off &f- &3Turns off armor coloring");
                } else if (one.equalsIgnoreCase("modes")) {
                    // static for now
                    String dash = getSym("-", 8);
                    send(p, "&2" + dash + " &9Available Modes&2 " + dash);
                    send(p, "- &bSync: &3All armor pieces are updated with same color");
                    send(p, "- &bFade: &3Every armor update, next color is used");
                    send(p, "- &bHelath: &3Armor color is based on health");
                } else if (one.equalsIgnoreCase("debug")) {
                    if(sender.hasPermission("rainbowgear.debug")) {
                        UUID uuid = p.getUniqueId();
                        if(debuggers.containsKey(uuid)) {
                            DebugWindow dw = debuggers.get(uuid);
                            dw.close();
                            debuggers.remove(uuid);
                            send(p, "&eSuccess! Removed debug window");
                        } else {
                            DebugWindow dw = new DebugWindow(this, p);
                            debuggers.put(uuid, dw);                          
                            dw.display();
                            send(p, "&eSuccess! Enabled debug window");
                        }
                    }                 
                } else if (one.equalsIgnoreCase("off")) {
                    final UUID uuid = p.getUniqueId();
                    if (workerz.containsKey(uuid)) {
                        Worker w = workerz.get(uuid);
                        Bukkit.getScheduler().cancelTask(w.getUniqueId());
                        workerz.remove(uuid);
                        send(p, "&eSuccess. Deactivated your armor.");
                    } else {
                        send(p, "&cError: Your armor is not updating, &ecant turn off.");
                    }
                }
            } else if (args.length == 2) {
                String one = args[0];
                String two = args[1];
                if (one.equalsIgnoreCase("set")) {
                    if (workerz.containsKey(p.getUniqueId())) {
                        send(p, "&cError: Use '/rg off' first to change mode");
                        return true;
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
                        return true;
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
}
