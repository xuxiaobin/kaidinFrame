package com.kaidin.common.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomUtilTest {

	@Test
	public void testNextInt() {
		for (int i = -10; i <= 10; i++) {
			assertEquals(i, RandomUtil.nextInt(i, i));
		}
		for (int i = -10; i <= 10; i++) {
			int randValue = RandomUtil.nextInt(-10, 10);
			assertTrue(-10 <= randValue && 10 >= randValue);
		}
		
		for (int i = -10; i <= 10; i++) {
			int randValue = RandomUtil.nextInt(10, -10);
			assertTrue(-10 <= randValue && 10 >= randValue);
		}
	}

}
