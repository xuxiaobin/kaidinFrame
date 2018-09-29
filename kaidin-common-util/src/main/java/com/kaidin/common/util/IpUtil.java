/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.kaidin.common.constant.ConstType;
import com.kaidin.common.util.regex.RegexUtil;

/**
 * ip地址和整数之间互转工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class IpUtil {
	private static final long MASK[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };

	/**
	 * 获取本机ip
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalHostIp() throws SocketException {
		String localIp = null; // 本地IP，如果没有配置外网IP则返回它
		String netIp = null; // 外网IP

		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress address = null;
		boolean finded = false; // 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface netInterface = netInterfaces.nextElement();
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				address = addresses.nextElement();
				if (!address.isSiteLocalAddress() && !address.isLoopbackAddress() && address.getHostAddress().indexOf(":") == -1) {
					// 外网IP
					netIp = address.getHostAddress();
					finded = true;
					break;
				} else if (address.isSiteLocalAddress() && !address.isLoopbackAddress()
				        && address.getHostAddress().indexOf(":") == -1) {
					// 内网IP
					localIp = address.getHostAddress();
				}
			}
		}

		if (null != netIp && 7 <= netIp.length()) {
			return netIp;
		}

		return localIp;
	}

	/**
	 * 判断是否满足ip规则
	 * @param ipStr
	 * @return
	 */
	public static boolean isIpAddr(String ipStr) {
		return RegexUtil.isIpAddr(ipStr);
	}

	/**
	 * 判断是否满足ip规则
	 * @param ipLong
	 * @return
	 */
	public static boolean isIpAddr(Long ipLong) {
		return (null != ipLong && ConstType.ip.MIN_VALUE <= ipLong && ConstType.ip.MAX_VALUE >= ipLong);
	}

	public static byte[] asByteIp(String ipStr) {
		if (!isIpAddr(ipStr)) {
			return null;
		}

		byte[] result = new byte[4];
		String[] tmpArry = ipStr.split("[.]");
		for (int i = 0; i < 4; i++) {
			short tmpInt = Short.valueOf(tmpArry[i]);
			result[i] = (byte) tmpInt;
		}

		return result;
	}

	public static int asIntIp(String ipStr) {
		if (!isIpAddr(ipStr)) {
			return 0;
		}
		int result = 0;
		String[] tmpArry = ipStr.split("[.]");
		for (int i = 0; i < 4; i++) {
			int tmpInt = Integer.valueOf(tmpArry[i]);
			result += tmpInt << 8 * (3 - i);
		}

		return result;
	}

	/**
	 * 字符串ip转换为long
	 * @param 字符串ip
	 * @return
	 */
	public static Long asLongIp(String ipStr) {
		if (null == ipStr) {
			return null;
		}
		Long result = null;
		String http = "http://";
		if (ipStr.startsWith(http)) {
			ipStr = ipStr.substring(http.length());
		}
		if (-1 < ipStr.indexOf(":")) {
			ipStr = ipStr.substring(0, ipStr.indexOf(":"));
		}
		if (isIpAddr(ipStr)) {
			String[] ips = ipStr.split("[.]");
			result = Long.parseLong(ips[0]) << 24;
			result += Long.parseLong(ips[1]) << 16;
			result += Long.parseLong(ips[2]) << 8;
			result += Long.parseLong(ips[3]);
		}
		if ("0".equals(ipStr)) {
			result = 0l;
		}

		return result;
	}

	public static String asStringIp(byte[] ipByte) {
		StringBuffer strBuffer = new StringBuffer(15);

		for (int i = 0; i < 4; i++) {
			short tmp = Short.valueOf(ipByte[i]);
			strBuffer.append(".").append(tmp & 0xff);
		}

		return strBuffer.substring(1);
	}

	public static String asStringIp(int ipInt) {
		StringBuffer strBuffer = new StringBuffer(15);

		strBuffer.append(ipInt >> 24 & 0xff); // 最高位，无符号右移
		strBuffer.append(".").append((ipInt >> 16) & 0xff); // 次高位
		strBuffer.append(".").append((ipInt >> 8) & 0xff); // 次低位
		strBuffer.append(".").append(ipInt & 0xff); // 最低位

		return strBuffer.toString();
	}

	/**
	 * 长整型ip转换为string
	 * @param long型ip
	 * @return
	 */
	public static String asStringIp(Long ipLong) {
		if (!isIpAddr(ipLong)) {
			return StringUtil.EMPTY_STR;
		}

		StringBuffer strBuffer = new StringBuffer(15);
		for (int i = 3; i >= 0; i--) {
			long num = (ipLong & MASK[i]) >> (i * 8);
			strBuffer.append(".").append(num);
		}

		return strBuffer.substring(1);
	}
}
