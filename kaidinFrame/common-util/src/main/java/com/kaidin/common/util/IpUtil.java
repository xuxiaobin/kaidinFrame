package com.kaidin.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.kaidin.common.constant.ConstType;
/**
 * ip地址和整数之间互转工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class IpUtil {
	/**
	 * 获取本机ip
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalHostIp() throws SocketException {
		String result = null;

		String localIp = null;	// 本地IP，如果没有配置外网IP则返回它
		String netIp = null;	// 外网IP

		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress address = null;
		boolean finded = false;	// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface netInterface = netInterfaces.nextElement();
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				address = addresses.nextElement();
				if (!address.isSiteLocalAddress() && !address.isLoopbackAddress()
						&& address.getHostAddress().indexOf(":") == -1) {
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
			result = netIp;
		} else {
			result = localIp;
		}

		return result;
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
		boolean isMatch = false;
		
		if (null != ipLong && ConstType.IP.MIN_VALUE <= ipLong && ConstType.IP.MAX_VALUE >= ipLong) {
			isMatch = true;
		}
		
		return isMatch;
	}
	
	/**
	 * 字符串ip转换为long
	 * @param 字符串ip
	 * @return
	 */
	public static Long getStringIp2Long(String ipStr) {
		Long result = null;
		
		if (null != ipStr) {
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
		}
		
		return result;
	}

	/**
	 * 长整型ip转换为string
	 * @param long型ip
	 * @return
	 */
	public static String getLongIp2String(Long ipLong) {
		String result = null;
		
		if (isIpAddr(ipLong)) {
			long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
			StringBuffer strBuffer = new StringBuffer();
			for (int i = 3; i >= 0; i--) {
				long num = (ipLong & mask[i]) >> (i * 8);
				if (3 == i) {
					strBuffer.append(num);
				} else {
					strBuffer.append(".").append(num);
				}
			}
			result = strBuffer.toString();
		}
		
		return result;
	}
}
