package net.porillo.util;

import org.bukkit.entity.Player;

public enum Permission {

    DEBUG("debug"),
    KIT("kit"),
    LIST("list"),
    OFF("off"),
    SET("set"),
    SET_MODE("set.%s"),
    USE_MODE("use.%s");

    private final String node;

    Permission(final String node) {
        this.node = "rgbarmor." + node;
    }

    public boolean has(Player player) {
        return player.hasPermission(node);
    }

    public String format(Object... args) {
        return String.format(node(), args);
    }

    public String node() {
        return this.node;
    }
}
