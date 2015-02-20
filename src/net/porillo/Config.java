package net.porillo;

public class Config {

    private boolean debug;
    private int refreshRate;
    private int colors;

    public Config(RBGArmor rg) {
        rg.saveDefaultConfig();
        colors = rg.getConfig().getInt("Core.Colors", 64);
        refreshRate = rg.getConfig().getInt("Core.RefreshRate", 5);
        debug = rg.getConfig().getBoolean("Core.Debug", false);
    }

    public int getColors() {
        return colors;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public boolean shouldDebug() {
        return debug;
    }

}
