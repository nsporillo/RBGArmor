package net.porillo;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class RainbowGear extends JavaPlugin implements Listener {

	private Map<UUID, Worker> workerz;
	private static Config config;
	protected static Color[] rb;

	@Override
	public void onEnable() {
		workerz = new HashMap<UUID, Worker>();
		config = new Config(this);
		rb = new Color[config.getColors()];
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				final int colors = config.getColors();
				final double f = (6.48 / (double)colors);
				for (int i = 0; i < colors; ++i) {
					double r = sin(f * i + 0.0D) * 127.0D + 128.0D;
					double g = sin(f * i + (2 * PI / 3)) * 127.0D + 128.0D;
					double b = sin(f * i + (4 * PI / 3)) * 127.0D + 128.0D;
					rb[i] = Color.fromRGB((int) r, (int) g, (int) b);
				}
				if(config.doPrintInfo()) {
					getLogger().info("------ RainbowGear Engine ------");
					getLogger().info("- Using " + config.getColors() + " colors");
					getLogger().info("- Armor updates " + 20/config.getRefreshRate() + " times per second");
				}
				
			}
		});
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.isOp()) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				List<String> lores = new ArrayList<String>();
				lores.add(config.getLore());
				for (ItemStack item : p.getInventory().getArmorContents()) {
					if(item.hasItemMeta()) {
						ItemMeta im = item.getItemMeta();
						im.setLore(lores);
						item.setItemMeta(im);
						String gear = config.getGear().replace("%armorpiece", item.getType().name().toLowerCase());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', gear));
					}	
				}
			}
		}
		return true;
	}

	@Override
	public void onDisable() {
		workerz.clear();
		Bukkit.getScheduler().cancelTasks(this);
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
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (!workerz.containsKey(p.getUniqueId())) {
			for (ItemStack is : p.getInventory().getArmorContents()) {
				if (is != null && is.getItemMeta() instanceof LeatherArmorMeta) {
					if (isWorthy(is.getItemMeta())) {
						this.initWorker(p);
						return;
					}
				}
			}
		}
	}

	public static boolean isWorthy(ItemMeta meta) {
		return meta.hasLore() ? meta.getLore().contains(config.getLore()) : false;
	}

	private void initWorker(Player p) {
		Worker rw = new Worker(p.getUniqueId());
		int rr = config.getRefreshRate();
		BukkitTask id = Bukkit.getScheduler().runTaskTimer(this, rw, rr, rr);
		rw.setUniqueId(id.getTaskId());
		workerz.put(p.getUniqueId(), rw);
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getInit()));
	}
}
