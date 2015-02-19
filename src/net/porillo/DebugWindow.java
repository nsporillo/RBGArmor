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
    private RainbowGear main;
    private int id;
    private final Scoreboard board;

    public DebugWindow(RainbowGear main, Player player) {
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
        ItemStack[] armor = player.getInventory().getArmorContents();
        ItemStack is = null;
        for(int i = 0; i < armor.length; i++) {
            if(armor[i] != null) {
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
        if(color != null) {
            Score red = obj.getScore("Red: ");
            red.setScore(color.getRed());
            Score green = obj.getScore("Green: ");
            green.setScore(color.getGreen());
            Score blue = obj.getScore("Blue: ");
            blue.setScore(color.getBlue());
        } else {
            Score err = obj.getScore("No armor -> ");
            err.setScore(0);
        }
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("RG Debug");
        player.setScoreboard(board);
    }

    public void close() {
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        Bukkit.getScheduler().cancelTask(id);
    }
}
