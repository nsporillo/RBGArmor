package net.porillo;

import static net.porillo.Lang.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.porillo.workers.FadeWorker;
import net.porillo.workers.HealthWorker;
import net.porillo.workers.SyncWorker;
import net.porillo.workers.Worker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Utility {

    private static Logger logger = Bukkit.getLogger();
    /**
     * Gets a repeated {@code String}
     * 
     * @param s Symbol
     * @param num of symbols
     * @return String of symbols with length num
     */
    public static String getSym(String s, int num) {
        StringBuilder sb = new StringBuilder(num);
        for (int i = 0; i < num; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * Sets lore to a players armor
     * 
     * @param p Player to set lore for armor
     * @param str Lore string
     */
    public static void setLore(Player p, String str) {
        List<String> lores = new ArrayList<String>();
        lores.add(str);
        ItemMeta meta;
        for (ItemStack is : p.getInventory().getArmorContents()) {
            if (is != null && (meta = is.getItemMeta()) instanceof LeatherArmorMeta) {
                if (meta.hasLore()) {
                    List<String> localAdd = new ArrayList<String>();
                    for (String l : meta.getLore()) {
                        if (!l.startsWith("RG|")) {
                            localAdd.add(l);
                        }
                    }
                    lores.addAll(localAdd);
                }
                meta.setLore(lores);
                is.setItemMeta(meta);
            }
        }
    }

    /**
     * 
     * @param meta
     * @return true if item has correct lore
     */
    public static boolean isWorthy(ItemMeta meta) {
        if (meta.hasLore()) {
            for (String s : meta.getLore()) {
                if (s.startsWith(TITLE_PREFIX.toString() + "|")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the worker based on lore
     * 
     * @param p Player
     * @param lores list of lore
     * @return a new worker instance based on the lore
     */
    public static Worker getWorker(Player p, List<String> lores) {
        for (String lore : lores) {
            if (lore.equals(TITLE_PREFIX.toString() + "|Fade")) {
                return new FadeWorker(p.getUniqueId());
            } else if (lore.equals(TITLE_PREFIX.toString() + "|Sync")) {
                return new SyncWorker(p.getUniqueId());
            } else if (lore.equals(TITLE_PREFIX.toString() + "|Health")) {
                return new HealthWorker(p.getUniqueId());
            }
        }
        return null;
    }

    public static void send(Player player, String str) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
    }

    public static void send(CommandSender sender, String str) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
    }
    
    public static void debug(String message) {
        logger.info("["+ Lang.TITLE + "][Debug] " + message);
    }
}
