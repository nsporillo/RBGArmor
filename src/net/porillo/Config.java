package net.porillo;

public class Config {

    private boolean debug;
    private int refreshRate;
    private int colors;

    public Config(RBGArmor rg) {
        rg.saveDefaultConfig();
        colors = rg.getConfig().getInt("Engine.Colors", 64);
        refreshRate = rg.getConfig().getInt("Engine.RefreshRate", 5);
        debug = rg.getConfig().getBoolean("Engine.PrintInfo", true);
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
