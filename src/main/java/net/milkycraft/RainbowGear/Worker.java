package net.milkycraft.RainbowGear;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Worker implements Runnable {

	private PlayerInventory inv;
	private int c = -1;
	private final int t;
	private int uid;

	public Worker(UUID uuid) {
		this.t = (RainbowGear.rb.length - 2);
		this.inv = Bukkit.getPlayer(uuid).getInventory();
	}

	@Override
	public void run() {
		Color c = Worker.this.getNext();
		for (ItemStack is : inv.getArmorContents()) {
			if (is != null && is.getItemMeta() instanceof LeatherArmorMeta) {
				if (!RainbowGear.isWorthy(is.getItemMeta())) {
					continue;
				}
				LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
				lam.setColor(c);
				lam.setDisplayName(c.toString().toLowerCase());
				is.setItemMeta(lam);
			}
		}
	}
	
	public Color getNext() {
		if (c > t)
			c = -1;
		c++;
		return RainbowGear.rb[c];
	}

	public int getUniqueId() {
		return uid;
	}

	public void setUniqueId(int id) {
		this.uid = id;
	}
}