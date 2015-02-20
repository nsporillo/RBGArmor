package net.porillo.workers;

import java.util.UUID;

import net.porillo.RBGArmor;
import net.porillo.Utility;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FadeWorker extends Worker {

    private int c = -1;
    private final int t;

    public FadeWorker(UUID uuid) {
        super(uuid);
        this.t = (RBGArmor.rb.length - 1);
    }

    @Override
    public void run() {
        for (ItemStack is : inv.getArmorContents()) {
            Color c = getNext();
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
        return RBGArmor.rb[++c];
    }

    @Override
    public String getType() {
        return "health";
    }
}
