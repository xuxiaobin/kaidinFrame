package com.kaidin.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateTimeUtilTest {

	@Test
	public void testGetHourOffset() {
	}

	@Test
	public void testGetLastHourBeginTime() {
	}

	@Test
	public void testGetThisHourBeginTime() {
	}

	@Test
	public void testGetNextHourBeginTime() {
	}

	@Test
	public void testGetLastDayBeginTime() {
	}

	@Test
	public void testGetThisDayBeginTime() {
	}

	@Test
	public void testGetNextDayBeginTime() {
	}

	@Test
	public void testGetLastWeekBeginTime() {
	}

	@Test
	public void testGetThisWeekBeginTime() {
	}

	@Test
	public void testGetNestWeekBeginTime() {
	}

	@Test
	public void testGetDayOfMonth() {
	}

	@Test
	public void testGetLastMonthBeginTime() {
	}

	@Test
	public void testGetThisMonthBeginTime() {
	}

	@Test
	public void testGetNextMonthBeginTime() {
	}
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		System.out.println(format.format(0));
		System.out.println(format.format(c.getTime()));
		
		
		System.out.println(DateTimeUtil.convertTimesToString(0L));
		System.out.println(DateTimeUtil.convertTimesToString(1L));
		System.out.println(DateTimeUtil.convertTimesToString(200L));
		System.out.println("----");
		System.out.println(DateTimeUtil.convertTimesToString(1000L));
		System.out.println(DateTimeUtil.convertTimesToString(1234L));
		System.out.println(DateTimeUtil.convertTimesToString(1000L * 60 + 234));
		System.out.println(DateTimeUtil.convertTimesToString(1000L * 60 * 59 + 57234));
		System.out.println("----");
		System.out.println(DateTimeUtil.convertTimesToString(1000L * 60 * 60));
		System.out.println(DateTimeUtil.convertTimesToString(1000L * 60 * 60 * 2));
		System.out.println(DateTimeUtil.convertTimesToString(1000L * 60 * 60 * 24));
	}
}
