package net.porillo;

public class Config {

	private String lore;
	private String init;
	private String gear;
	private boolean printInfo;
	private int refreshRate;
	private int colors;
	

	public Config(RainbowGear rg) {
		rg.saveDefaultConfig();
		lore = rg.getConfig().getString("Lore", "RAINBOW");
		init = rg.getConfig().getString("Lang.Init");
		gear = rg.getConfig().getString("Lang.Gear");
		colors = rg.getConfig().getInt("Engine.Colors", 64);
		refreshRate = rg.getConfig().getInt("Engine.RefreshRate", 5);
		printInfo = rg.getConfig().getBoolean("Engine.PrintInfo", true);
	}

	public String getLore() {
		return lore;
	}

	public String getInit() {
		return init;
	}

	public String getGear() {
		return gear;
	}

	public int getColors() {
		return colors;
	}

	public int getRefreshRate() {
		return refreshRate;
	}

	public boolean doPrintInfo() {
		return printInfo;
	}
	
}
