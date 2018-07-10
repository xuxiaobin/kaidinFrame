/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class CollectionUtillTest {

	@Test
	public void testisNotEmptyByCollection() {
		ArrayList<Integer> tmpList = null;
		Assert.assertFalse(CollectionUtil.isNotEmpty(tmpList));

		tmpList = new ArrayList<>();
		Assert.assertFalse(CollectionUtil.isNotEmpty(tmpList));

		tmpList.add(123);
		Assert.assertTrue(CollectionUtil.isNotEmpty(tmpList));
	}

	@Test
	public void testisNotEmptyByArray() {
		Integer[] tmpArray = null;
		Assert.assertFalse(CollectionUtil.isNotEmpty(tmpArray));

		tmpArray = new Integer[2];
		Assert.assertFalse(CollectionUtil.isNotEmpty(tmpArray));

		tmpArray[0] = 123;
		Assert.assertTrue(CollectionUtil.isNotEmpty(tmpArray));
	}

	@Test
	public void testDecode() {
		Assert.assertEquals("is null", CollectionUtil.decode(null, "defaultValue", null, "is null"));
		Assert.assertEquals("defaultValue", CollectionUtil.decode("123", "defaultValue", null, "is null"));

		Object[] objArray = new String[] { "hello", "world", "123", "abc", "abc", "123", StringUtil.EMPTY_STR, "is blank", "tt" };
		Assert.assertEquals("abc", CollectionUtil.decode("123", "defaultValue", null, "is null", objArray));
		Assert.assertEquals("123", CollectionUtil.decode("abc", "defaultValue", null, "is null", objArray));
	}
}
