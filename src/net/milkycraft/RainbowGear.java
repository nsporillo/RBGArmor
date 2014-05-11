package net.milkycraft;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static org.bukkit.ChatColor.GOLD;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class RainbowGear extends JavaPlugin implements Listener {

	private Map<String, Worker> workerz = new HashMap<String, Worker>();
	public static Color[] rb = new Color[64];

	@Override
	public void onEnable() {
		// Register listeners for enabling/disabling color shifts
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				final double f = (7.0 / 64);
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
		// Clean up hashmap and tasks
		workerz = null;
		Bukkit.getScheduler().cancelTasks(this);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		// Iterate map, check if r. armor is active and if so, disable
		this.cancel(e.getPlayer());
	}
	

	@EventHandler
	public void onSneak(org.bukkit.event.player.PlayerToggleSneakEvent e) {		
		Player p = e.getPlayer();
		//Simple check to save CPU, dont bother scanning armor slots if they have a worker
		if(this.hasWorker(p.getName())){
			return;
		}
		for (ItemStack is : p.getInventory().getArmorContents()) {
			if (is != null && is.getItemMeta() instanceof LeatherArmorMeta) {
				ItemMeta meta = is.getItemMeta();
				// Verify item has proper lore
				if (isWorthy(meta)) {
					this.initWorker(p);	
					return;
				}
			}
		}
	}

	public static boolean isWorthy(ItemMeta meta) {
		// Easy boolean check
		if (meta.hasLore()) {
			// List is usually one element, iteration is fast
			for (String lore : meta.getLore()) {
				// .equals() > .equalsIgnoreCase()
				if (lore.equals("RAINBOW")) {
					return true;
				}
			}
		}
		return false;
	}

	private void initWorker(Player p) {
		// Generate a worker object for the player
		Worker rw = new Worker(new Sinebow(), p.getName());
		// Create a TaskTimer, running Synchronously
		BukkitTask id = Bukkit.getScheduler().runTaskTimer(this, rw, 5, 5);
		// Assign Task ID to worker, used when we cancel the task.
		rw.setId(id.getTaskId());
		// Store worker in memory for iteration later
		workerz.put(p.getName(), rw);
		// Message player they have activated the armor
		p.sendMessage(GOLD + "Rainbow armor activated, logout to deactivate!");
	}

	public boolean hasWorker(String player) {
		// Checks if player has a worker active
		return workerz.containsKey(player);
	}

	public void cancel(Player p) {
		// Cant cancel without a available worker
		if (hasWorker(p.getName())) {
			// Retrieve the worker
			Worker w = workerz.get(p.getName());
			// Cancel the workers task id
			Bukkit.getScheduler().cancelTask(w.getId());
			// Remove the worker from memory
			workerz.remove(p.getName());
		}
	}
}
