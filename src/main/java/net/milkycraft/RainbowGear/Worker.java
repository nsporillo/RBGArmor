package net.milkycraft.RainbowGear;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Worker implements Runnable {

	private PlayerInventory inv;
	private Sinebow rain;
	private int id;

	public Worker(Sinebow r, UUID uuid) {
		this.rain = r;
		this.inv = Bukkit.getPlayer(uuid).getInventory();
	}

	@Override
	public void run() {
		Color c = rain.getNext();
		for (ItemStack is : inv.getArmorContents()) {
			if (is != null && is.getItemMeta() instanceof LeatherArmorMeta) {
				if (!RainbowGear.isWorthy(is.getItemMeta())) {
					continue;
				}
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
