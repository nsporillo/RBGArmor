package net.porillo;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static net.porillo.util.Utility.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.porillo.commands.CommandHandler;
import net.porillo.util.Config;
import net.porillo.util.DebugWindow;
import net.porillo.util.Lang;
import net.porillo.workers.Worker;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
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

    private CommandHandler handler;
    private Map<UUID, DebugWindow> debuggers;
    private Map<UUID, Worker> workerz;
    private static Config config;
    public static Color[] rb;

    @Override
    public void onEnable() {
        this.loadLang();
        handler = new CommandHandler(this);
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
                debug("RGBArmor enabled, using " + colors + " colors");
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
        this.removeUUID(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        this.removeUUID(e.getEntity().getUniqueId());
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
        send(p, Lang.ACTIVATE.replace("%mode", rw.getType()));
        send(p, Lang.DISABLERMD.toString());
        if(config.shouldDebug()) {
            debug("New worker for " + p.getName() + ", type: " + rw.getMode().getName());
        }
    }

    private void removeUUID(UUID uuid) {
        if (workerz.containsKey(uuid)) {
            Worker w = workerz.get(uuid);
            Bukkit.getScheduler().cancelTask(w.getUniqueId());
            workerz.remove(uuid);
            if(config.shouldDebug()) {
                debug("Removed: " + uuid.toString());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void loadLang() {
        File lang = new File(getDataFolder(), "lang.yml");
        OutputStream out = null;
        InputStream defLangStream = this.getResource("lang.yml");
        if (!lang.exists()) {
            try {
                getDataFolder().mkdir();
                lang.createNewFile();
                if (defLangStream != null) {
                    out = new FileOutputStream(lang);
                    int read;
                    byte[] bytes = new byte[1024];
                    while ((read = defLangStream.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    YamlConfiguration defConfig = YamlConfiguration
                            .loadConfiguration(defLangStream);
                    Lang.setFile(defConfig);
                }
            } catch (IOException e) {
                e.printStackTrace();
                getLogger().severe("[RGBArmor] Couldn't create language file.");
                this.setEnabled(false);
            } finally {
                if (defLangStream != null) {
                    try {
                        defLangStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        for (Lang item : Lang.values()) {
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getDefault());
            }
        }
        Lang.setFile(conf);
        try {
            conf.save(lang);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
