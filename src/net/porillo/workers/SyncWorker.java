package net.porillo.workers;

import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.porillo.RainbowGear;
import net.porillo.Utility;

public class SyncWorker extends Worker {

    private int c = -1;
    private final int t;

    public SyncWorker(UUID uuid) {
        super(uuid);
        this.t = (RainbowGear.rb.length - 1);
    }

    @Override
    public void run() {
        Color c = getNext();
        for (ItemStack is : inv.getArmorContents()) {
            if (is != null && is.getItemMeta() instanceof LeatherArmorMeta) {
                if (!Utility.isWorthy(is.getItemMeta())) {
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

    @Override
    public String getType() {
        return "sync";
    }  
}
