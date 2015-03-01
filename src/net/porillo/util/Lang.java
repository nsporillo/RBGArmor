/*
 * Copyright (C) 2013 drtshock
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.porillo.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * An enum for requesting strings from the language file.
 */
public enum Lang {

    TITLE_PREFIX("title-prefix", "RGB"),
    TITLE("title-name", "RGBArmor"),
    SYNC_NAME("sync-name", "sync"),
    FADE_NAME("fade-name", "fade"),
    HEALTH_NAME("health-name", "health"),
    SYNC_DESC("sync-desc", "All armor pieces are updated with same color"),
    FADE_DESC("fade-desc", "Each armor piece gets the next color update"),
    HEALTH_DESC("health-desc", "Armor color is based on health"),
    ACTIVATE("activate-msg", "&aYour armor is activated, using &b%mode &acoloring."),
    DISABLERMD("disable-reminder", "&dUse /rgb off or logout to stop armor coloring!"),
    NOPERMS("no-permission", "&cYou do not have permission to use that command!"),
    DEBUG_USAGE("debug-usage", "Toggles a armor debug scoreboard"),
    HELP_HEADER("help-header", "&2%dash&9 %title &2%dash"),
    LIST_USAGE("list-usage", "List available coloring modes"),
    LIST_HEADER("list-header", "&2%dash &9Available Modes&2 %dash"),
    LIST_OUTPUT("list-output", "- &b%mode: &3%description"),
    OFF_USAGE("off-usage", "Disables your armor coloring"),
    OFF_SUCCESS("off-success", "&eSuccess. Deactivated your armor."),
    OFF_FAILURE("off-failure", "&cError: Your armor is not updating, &ecant disable."),
    SET_USAGE("set-usage", "Sets your equiped armor's mode"),
    SET_ERROR("set-error", "&cError: Use '/rgb off' first to change mode"),
    SET_SUCCESS("set-success", "&eSuccess! &aColoring mode has been set to &b%mode&a."),
    SET_FAILURE("set-failure", "&cError: The mode '&f%mode&c' is not recognized."),
    KIT_USAGE("kit-usage", "Adds a full leather armor kit to your inventory"),
    KIT_SUCCESS("kit-sucess", "&eSuccess. You now have a leather armor kit."),
    KIT_INVFULL("kit-invfull", "&cError: Could not add entire kit, inventory is full."),
    CANT_USE_URL("cant-use-url", "&eUnlock that special armor on &6%store &e:)"),
    CANT_USE_NOURL("cant-use-nourl", "&cError: You dont have permission to use that armor.");

    private String path;
    private String def;
    private static YamlConfiguration LANG;

    /**
     * Lang enum constructor.
     *
     * @param path The string path.
     * @param start The default string.
     */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    /**
     * Set the {@code YamlConfiguration} to use.
     *
     * @param config The config to set.
     */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    @Override
    public String toString() {
        if (this == TITLE)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    public String replace(String string, String two) {
        return toString().replace(string, two);
    }

    public String cap() {
        return Character.toUpperCase(toString().charAt(0)) + toString().substring(1).toLowerCase();
    }

    /**
     * Get the default value of the path.
     *
     * @return The default value of the path.
     */
    public String getDefault() {
        return this.def;
    }

    /**
     * Get the path to the string.
     *
     * @return The path to the string.
     */
    public String getPath() {
        return this.path;
    }
}
