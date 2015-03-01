package net.porillo.workers;

import java.util.UUID;

import net.porillo.RBGArmor;
import net.porillo.util.Lang;
import net.porillo.util.Mode;
import net.porillo.util.Utility;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SyncWorker extends Worker {

    private int c = -1;
    private final int t;

    public SyncWorker(UUID uuid) {
        super(uuid);
        this.t = (RBGArmor.rb.length - 1);
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
        return RBGArmor.rb[++c];
    }

    @Override
    public String getType() {
        return Lang.SYNC_NAME.toString();
    }

    @Override
    public Mode getMode() {
        return Mode.SYNC;
    }
}
