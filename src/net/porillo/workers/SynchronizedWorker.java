package net.porillo.workers;

import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.porillo.RainbowGear;

public class SynchronizedWorker extends Worker {

	private int c = -1;
	private final int t;
	
	public SynchronizedWorker(UUID uuid) {
		super(uuid);
		this.t = (RainbowGear.rb.length - 1);
	}

	@Override
	public void run() {
		Color c = getNext();
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

	
	private Color getNext() {
		if (c >= t)
			c = -1;
		return RainbowGear.rb[++c];
	}
}
