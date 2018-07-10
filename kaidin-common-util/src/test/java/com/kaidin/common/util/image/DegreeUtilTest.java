/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.image;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DegreeUtilTest {

	@Test
	public void testAngle2radian() {
		assertEquals(0F, DegreeUtil.angle2radian(0), 0.0);
		assertEquals(Math.PI * 315 / 180, DegreeUtil.angle2radian(315), 0.0001);
		assertEquals(Math.PI * 315 / 180, DegreeUtil.angle2radian(315 + 360), 0.0001);
		assertEquals(Math.PI / 2, DegreeUtil.angle2radian(90), 0.0001);
	}

	@Test
	public void testRadian2angle() {
		assertEquals(0F, DegreeUtil.radian2angle(0), 0.0);
		assertEquals(315F, DegreeUtil.radian2angle((float) (Math.PI / -4)), 0.0);
		assertEquals(315F, DegreeUtil.radian2angle((float) (Math.PI / -4 + 2 * Math.PI)), 0.0);
		assertEquals(90F, DegreeUtil.radian2angle((float) (Math.PI / 2 - 2 * Math.PI)), 0.0);
	}

}
