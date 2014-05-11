package net.milkycraft;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Worker implements Runnable {

	private PlayerInventory inv;
	private Sinebow rain;
	private int id;

	public Worker(Sinebow r, String p) {
		this.rain = r;
		this.inv = Bukkit.getPlayerExact(p).getInventory();
	}

	@Override
	public void run() {
		// Retrieves the next color in the series
		Color c = rain.getNext();
		// Iterates all armor slots
		for (ItemStack is : inv.getArmorContents()) {
			if (is != null && is.getItemMeta() instanceof LeatherArmorMeta) {
				/*
				 * Redundant check to ensure user does not enable rainbow armor,
				 * and the switch it out with regular leather armor and still
				 * have it colorize
				 */
				if (!RainbowGear.isWorthy(is.getItemMeta())) {
					continue;
				}
				// Apply the color to the leather armor peice
				LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
				lam.setColor(c);
				is.setItemMeta(lam);
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
