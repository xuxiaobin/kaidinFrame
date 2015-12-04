package com.kaidin.common.util;

import static org.junit.Assert.assertEquals;

import java.net.SocketException;

import org.junit.Test;

import com.kaidin.common.constant.ConstType;

public class IpUtilTest {
	public static void main(String[] args) {
		for (long i = ConstType.IP.MIN_VALUE; i <= ConstType.IP.MAX_VALUE; i++) {
			if (!IpUtil.isIpAddr(IpUtil.getLongIp2String(i))) {
				System.out.println(i);
			}
		}
	}
	@Test
	public void testGetLocalHostIp() throws SocketException {
		System.out.println(IpUtil.getLocalHostIp());
		assertEquals("172.16.18.182", IpUtil.getLocalHostIp());
	}
	
	@Test
	public void testIsIpAddr() {
		assertEquals(true, IpUtil.isIpAddr("0.0.0.0"));
		assertEquals(true, IpUtil.isIpAddr("255.255.255.255"));
		assertEquals(false, IpUtil.isIpAddr("256.255.255.255"));
		assertEquals(false, IpUtil.isIpAddr("255.255.255."));
	}
	
	@Test
	public void testGetStringIp2Long() {
		assertEquals(ConstType.IP.MIN_VALUE, IpUtil.getStringIp2Long("0.0.0.0"));
		assertEquals(ConstType.IP.MAX_VALUE, IpUtil.getStringIp2Long("255.255.255.255"));
	}

	@Test
	public void testGetLongIp2String() {
		assertEquals("0.0.0.0", IpUtil.getLongIp2String(ConstType.IP.MIN_VALUE));
		assertEquals("255.255.255.255", IpUtil.getLongIp2String(ConstType.IP.MAX_VALUE));
		assertEquals("127.0.0.1", IpUtil.getLongIp2String(ConstType.IP.LOCALHOST_VALUE));
	}
}
