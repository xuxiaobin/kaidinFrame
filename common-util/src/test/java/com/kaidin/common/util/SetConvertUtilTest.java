package com.kaidin.common.util;

import org.junit.Assert;
import org.junit.Test;

public class SetConvertUtilTest {

	@Test
	public void testDecode() {
		Assert.assertEquals("is null", SetConvertUtil.decode(null, "defaultValue", null, "is null"));
		Assert.assertEquals("defaultValue", SetConvertUtil.decode("123", "defaultValue", null, "is null"));
		
		Object[] objArray = new String[]{"hello", "world", "123", "abc", "abc", "123", "", "is blank", "tt"};
		Assert.assertEquals("abc", SetConvertUtil.decode("123", "defaultValue", null, "is null", objArray));
		Assert.assertEquals("123", SetConvertUtil.decode("abc", "defaultValue", null, "is null", objArray));
	}
}
