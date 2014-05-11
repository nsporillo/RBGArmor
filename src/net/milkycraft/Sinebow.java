package net.milkycraft;

import org.bukkit.Color;

public class Sinebow {
	
	private int c = -1;
	private final int t;
	
	public Sinebow() {
		this.t = (RainbowGear.rb.length - 2);
	}
	
	public Color getNext() {
		if (c > t)
			c = -1;
		c++;
		return RainbowGear.rb[c];
	}
}
