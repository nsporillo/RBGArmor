package net.porillo.workers;

import java.util.UUID;

import net.porillo.util.Mode;

import org.bukkit.Bukkit;
import org.bukkit.inventory.PlayerInventory;

public abstract class Worker implements Runnable {

    protected PlayerInventory inv;
    private int uid;

    /**
     * Worker constructor
     * @param uuid {@code Player}'s UUID
     */
    public Worker(UUID uuid) {
        this.inv = Bukkit.getPlayer(uuid).getInventory();
    }

    /**
     * Get the task id for this worker
     * @return workers task id
     */
    public int getUniqueId() {
        return uid;
    }

    /**
     * Set the task id for this worker
     * @param taskid {@code BukkitTask} task id 
     */
    public void setUniqueId(int taskid) {
        this.uid = taskid;
    }

    /**
     * Gets player inventory associated with this worker
     * @return {@code PlayerInventory} 
     */
    public PlayerInventory getInventory() {
        return inv;
    }

    public abstract String getType();
    
    public abstract Mode getMode();
}
