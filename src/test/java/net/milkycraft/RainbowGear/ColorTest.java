package net.milkycraft.RainbowGear;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ColorTest {

	Sinebow bow;
	
	@Before
	public void setUp() throws Exception {
		bow = new Sinebow();
	}

	@Test
	public void test() {
		for(int i = 0; i <= 256; i++) {
			bow.getNext();
			System.out.println("i: " + i + " s: " + bow.getValue()) ;
		}
		fail("Not yet implemented");
	}

}
