package net.porillo.workers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.PlayerInventory;

public abstract class Worker implements Runnable {

	protected PlayerInventory inv;
	private int uid;
	
	public Worker(UUID uuid) {
		this.inv = Bukkit.getPlayer(uuid).getInventory();
	}
	
	public int getUniqueId() {
		return uid;
	}

	public void setUniqueId(int id) {
		this.uid = id;
	}
	
	public PlayerInventory getInventory() {
		return inv;
	}
}
