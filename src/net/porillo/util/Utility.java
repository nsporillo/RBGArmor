package net.porillo.util;

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
     * @return {@code String} of symbols with length num
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
     * @param player {@code ItemMeta} to set lore for armor
     * @param str Lore string
     */
    public static void setLore(Player player, String str) {
        List<String> lores = new ArrayList<String>();
        ItemStack[] armor = player.getInventory().getArmorContents();
        lores.add(str);
        ItemMeta meta;
        for (ItemStack is : armor) {
            if (is != null && (meta = is.getItemMeta()) instanceof LeatherArmorMeta) {
                if (meta.hasLore()) {
                    List<String> localAdd = new ArrayList<String>();
                    for (String l : meta.getLore()) {
                        if (!l.startsWith(Lang.TITLE_PREFIX.toString() + "|")) {
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
     * Checks if armor is worthy to color
     * @param meta armors {@code ItemMeta}
     * @return true if item has correct lore
     */
    public static boolean isWorthy(ItemMeta meta) {
        if (meta.hasLore()) {
            String str = Lang.TITLE_PREFIX.toString() + "|";
            for (String s : meta.getLore()) {
                if (s.startsWith(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the {@code Worker} based on lore
     * 
     * @param player {@code Player}
     * @param lores {@code List<String>} of item lore
     * @return a new {@code worker} instance based on the lore
     */
    public static Worker getWorker(Player player, List<String> lores) {
        String pre = Lang.TITLE_PREFIX.toString();
        for (String lore : lores) {
            if (lore.equals(pre + "|" + Lang.FADE_NAME.cap())) {
                return new FadeWorker(player.getUniqueId());
            } else if (lore.equals(pre + "|" + Lang.SYNC_NAME.cap())) {
                return new SyncWorker(player.getUniqueId());
            } else if (lore.equals(pre + "|" + Lang.HEALTH_NAME.cap())) {
                return new HealthWorker(player.getUniqueId());
            }
        }
        return null;
    }

    /**
     * Dispatches color formatted message to {@code Player}
     * @param sender receiver
     * @param str message
     */
    public static void send(Player player, String str) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
    }

    /**
     * Dispatches color formatted message to {@code CommandSender}
     * @param sender receiver
     * @param str message
     */
    public static void send(CommandSender sender, String str) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
    }

    /**
     * Debug to console 
     * @param message debug
     */
    public static void debug(String message) {
        logger.info("[RGBArmor][Debug] " + message);
    }
}
