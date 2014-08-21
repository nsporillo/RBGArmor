package net.milkycraft.RainbowGear;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static org.bukkit.ChatColor.GOLD;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
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

	private Map<UUID, Worker> workerz = new HashMap<UUID, Worker>();
	public static Color[] rb = new Color[64];

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			public void run() {
				final double f = (7.0 / 64.0);
				for (int i = 0; i < 64; ++i) {
					double r = sin(f * i + 0.0D) * 127.0D + 128.0D;
					double g = sin(f * i + (2 * PI / 3)) * 127.0D + 128.0D;
					double b = sin(f * i + (4 * PI / 3)) * 127.0D + 128.0D;
					rb[i] = Color.fromRGB((int) r, (int) g, (int) b);
				}
				getLogger().info("[Post-Startup] Generated rainbow colors");
			}
		});
	}

	@Override
	public void onDisable() {
		workerz.clear();
		Bukkit.getScheduler().cancelTasks(this);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		this.cancel(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (workerz.containsKey(p.getUniqueId())) {
			return;
		}
		for (ItemStack is : p.getInventory().getArmorContents()) {
			if (is != null && is.getItemMeta() instanceof LeatherArmorMeta) {
				if (isWorthy(is.getItemMeta())) {
					this.initWorker(p);
					return;
				}
			}
		}
	}
	
	public static boolean isWorthy(ItemMeta meta) {
		if(meta.hasLore()) {
			return meta.getLore().contains("RAINBOW");
		} 
		return false;
	}

	private void initWorker(Player p) {
		Worker rw = new Worker(p.getUniqueId());
		BukkitTask id = Bukkit.getScheduler().runTaskTimer(this, rw, 5, 5);
		rw.setUniqueId(id.getTaskId());
		workerz.put(p.getUniqueId(), rw);
		p.sendMessage(GOLD + "Rainbow armor activated, logout to deactivate!");
	}

	public void cancel(UUID uuid) {
		if (workerz.containsKey(uuid)) {
			Worker w = workerz.get(uuid);
			Bukkit.getScheduler().cancelTask(w.getUniqueId());
			workerz.remove(uuid);
		}
	}
}
