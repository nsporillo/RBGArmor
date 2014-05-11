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

			@Override
			public void run() {
				final double f = (7.0 / 64.0);
				for (int i = 0; i < 64; ++i) {
					double r = sin(f * i + 0.0D) * 127.0D + 128.0D;
					double g = sin(f * i + (2 * PI / 3)) * 127.0D + 128.0D;
					double b = sin(f * i + (4 * PI / 3)) * 127.0D + 128.0D;
					rb[i] = Color.fromRGB((int) r, (int) g, (int) b);
				}
				RainbowGear.this.getLogger().info(
						"[Post-Startup] Generated Color Array for Sine Bow");
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
		this.cancel(e.getPlayer());
	}

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (this.hasWorker(p.getUniqueId())) {
			return;
		}
		for (ItemStack is : p.getInventory().getArmorContents()) {
			if (is != null && is.getItemMeta() instanceof LeatherArmorMeta) {
				ItemMeta meta = is.getItemMeta();
				if (isWorthy(meta)) {
					this.initWorker(p);
					return;
				}
			}
		}
	}

	public static boolean isWorthy(ItemMeta meta) {
		if (meta.hasLore()) {
			for (String lore : meta.getLore()) {
				if (lore.equals("RAINBOW")) {
					return true;
				}
			}
		}
		return false;
	}

	private void initWorker(Player p) {
		Worker rw = new Worker(new Sinebow(), p.getUniqueId());
		BukkitTask id = Bukkit.getScheduler().runTaskTimer(this, rw, 5, 5);
		rw.setId(id.getTaskId());
		workerz.put(p.getUniqueId(), rw);
		p.sendMessage(GOLD + "Rainbow armor activated, logout to deactivate!");
	}

	public boolean hasWorker(UUID uuid) {
		return workerz.containsKey(uuid);
	}

	public void cancel(Player p) {
		if (hasWorker(p.getUniqueId())) {
			Worker w = workerz.get(p.getUniqueId());
			Bukkit.getScheduler().cancelTask(w.getId());
			workerz.remove(p.getUniqueId());
		}
	}
}
