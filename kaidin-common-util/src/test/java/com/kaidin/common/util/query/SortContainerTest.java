package com.kaidin.common.util.query;

import java.util.Iterator;

import org.junit.Test;

public class SortContainerTest {

	@Test
	public void testGetSort() {
		SortContainer instance = new SortContainer();
		instance.addSortDesc("dddd");
		instance.addSort("222222");
		instance.addSort("11111");
		instance.addSort("aaa");
		instance.addSortDesc("bbbb");
		instance.addSort("cccc");

		for (Iterator<String> iterator = instance.getSort().keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = instance.getSort().get(key);
			System.out.println(key + ":" + value);
		}
		System.out.println(instance);
	}
}