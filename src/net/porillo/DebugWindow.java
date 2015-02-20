package net.porillo;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class DebugWindow {

    private Player player;
    private ScoreboardManager manager;
    private RBGArmor main;
    private int id;
    private final Scoreboard board;

    public DebugWindow(RBGArmor main, Player player) {
        this.main = main;
        this.player = player;
        this.manager = Bukkit.getScoreboardManager();
        this.board = manager.getNewScoreboard();
    }

    public void display() {
        final Objective obj = board.registerNewObjective("rgDebug", "debug");
        final int rr = main.getOurConfig().getRefreshRate();
        id = Bukkit.getScheduler().runTaskTimer(main, new Runnable() {

            @Override
            public void run() {
                show(board, obj);
            }

        }, rr, rr).getTaskId();

    }

    public void show(Scoreboard board, Objective obj) {
        if (!main.getWorkers().containsKey(player.getUniqueId())) {
            return;
        }
        ItemStack[] armor = player.getInventory().getArmorContents();
        ItemStack is = null;
        for (int i = 0; i < armor.length; i++) {
            if (armor[i] != null) {
                is = armor[i];
                break;
            }
        }
        Color color = null;
        if (is != null && is.hasItemMeta()) {
            ItemMeta im = is.getItemMeta();
            if (im instanceof LeatherArmorMeta) {
                LeatherArmorMeta lam = (LeatherArmorMeta) im;
                color = lam.getColor();
            }
        }
        if (color != null) {
            Score red = obj.getScore(Lang.RED.toString() + ": ");
            red.setScore(color.getRed());
            Score green = obj.getScore(Lang.GREEN.toString() + ": ");
            green.setScore(color.getGreen());
            Score blue = obj.getScore(Lang.BLUE.toString() + ": ");
            blue.setScore(color.getBlue());
        }
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(Lang.TITLE.toString() + " " + Lang.DEBUG.toString());
        player.setScoreboard(board);
    }

    public void close() {
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        Bukkit.getScheduler().cancelTask(id);
    }
}
