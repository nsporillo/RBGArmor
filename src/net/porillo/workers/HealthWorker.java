package net.porillo.workers;

import java.util.UUID;

import net.porillo.Utility;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class HealthWorker extends Worker {

    private Player player;
    private Color[] colors;
    
    public HealthWorker(UUID uuid) {
        super(uuid);
        this.player = Bukkit.getPlayer(uuid);
        int max = (int) (player.getMaxHealth());
        colors = getColors((max*3)+1);
    }

    @Override
    public void run() {
        Color c = getNext(player.getHealth());
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

    public Color getNext(double health){
        return colors[(int)health*3];
    }
    
    private Color[] getColors(int n) {
        Color[] colors = new Color[n];
        double g = 0;
        for (int i = 0; i < (n/2); i++) {
            colors[i] = Color.fromRGB(255, (int) g, 0);
            g += 255 / n; // under half health should be red
        }
        double r = 255;
        for (int i = (n/2); i < n; i++) {
            colors[i] = Color.fromRGB((int) r, 255, 0);
            r -= 255 / (n / 2); // over half health is yellow->green
        }
        return colors;
    }
    
    @Override
    public String getType() {
        return "health";
    }
}
