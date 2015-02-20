package net.porillo.util;

import net.porillo.RBGArmor;

public class Config {

    private boolean debug;
    private int refreshRate;
    private int colors;

    /**
     * Config constructor
     * @param rgb main class 
     */
    public Config(RBGArmor rgb) {
        rgb.saveDefaultConfig();
        colors = rgb.getConfig().getInt("Core.Colors", 64);
        refreshRate = rgb.getConfig().getInt("Core.RefreshRate", 5);
        debug = rgb.getConfig().getBoolean("Core.Debug", false);
    }

    /**
     * Total # of colors pre-loaded for rainbow array
     * @return how many colors we're using
     */
    public int getColors() {
        return colors;
    }

    /**
     * Get the armor update speed
     * @return armor refresh rate in ticks
     */
    public int getRefreshRate() {
        return refreshRate;
    }

    /**
     * Should we send debug messages?
     * @return true if debugging is enabled
     */
    public boolean shouldDebug() {
        return debug;
    }

}
