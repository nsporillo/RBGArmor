package net.porillo.util;

import net.porillo.RBGArmor;

public class Config {

    private String storeUrl;
    private boolean debug;
    private boolean requirePerms;
    private int refreshRate;
    private int colors;

    /**
     * Config constructor
     * 
     * @param rgb main class
     */
    public Config(RBGArmor rgb) {
        rgb.saveDefaultConfig();
        colors = rgb.getConfig().getInt("core.colors", 64);
        refreshRate = rgb.getConfig().getInt("core.refresh-rate", 5);
        debug = rgb.getConfig().getBoolean("core.debug", false);
        requirePerms = rgb.getConfig().getBoolean("integration.force-perms-to-colorize", false);
        storeUrl = rgb.getConfig().getString("integration.store-url", "none");
    }

    /**
     * Total # of colors pre-loaded for rainbow array
     * 
     * @return how many colors we're using
     */
    public int getColors() {
        return colors;
    }

    /**
     * Get the armor update speed
     * 
     * @return armor refresh rate in ticks
     */
    public int getRefreshRate() {
        return refreshRate;
    }

    /**
     * Should we send debug messages?
     * 
     * @return true if debugging is enabled
     */
    public boolean shouldDebug() {
        return debug;
    }

    public boolean isIntegrating() {
        return !storeUrl.equalsIgnoreCase("none");
    }

    public String getStoreLink() {
        return storeUrl;
    }

    public boolean shouldForcePermsToColorize() {
        return requirePerms;
    }

    @Override
    public String toString() {
        return "Config [storeUrl=" + storeUrl + ", debug=" + debug + ", forcePerms=" + requirePerms + ", refreshRate=" + refreshRate + ", colors=" + colors + "]";
    }  
}
