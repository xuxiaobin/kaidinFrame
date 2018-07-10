/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取散列值工具
 * @version	2.0
 * @author	kaidin@foxmail.com
 * @date	2015-6-23下午01:51:48
 */
public abstract class EncryptUtil {
	/**
	 * 目前支持的散列值的类型
	 * @version	1.0
	 * @author	kaidin@foxmail.com
	 * @date	2015-6-23下午01:51:48
	 */
	public enum EncryptType {
		MD5, SHA1, SHA256, SHA384, SHA512;
	}

	/**
	 *  把字节数组转成16进位制数
	 * @param byteArray
	 * @return
	 */
	private static String bytesToHex(byte[] byteArray) {
		if (null == byteArray) {
			return null;
		}
		// 把数组每一字节换成16进制连成md5字符串
		StringBuffer strBuffer = new StringBuffer(2 * byteArray.length);
		for (int digital : byteArray) {
			if (digital < 0) {
				digital += 256;
			}
			if (digital < 16) {
				strBuffer.append("0");
			}
			strBuffer.append(Integer.toHexString(digital));
		}
		return strBuffer.toString();
	}

	private static MessageDigest getMessageDigest(EncryptType tncryptType) {
		try {
			return MessageDigest.getInstance(String.valueOf(tncryptType));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 计算content的散列值，散列值的类型为tncryptType
	 * @param content
	 * @param tncryptType
	 * @return
	 */
	public static String encrypt(byte[] content, EncryptType tncryptType) {
		if (null == content) {
			return null;
		}
		MessageDigest msgDigest = getMessageDigest(tncryptType);
		msgDigest.update(content);
		return bytesToHex(msgDigest.digest());
	}

	/**
	 * 计算content的散列值，散列值的类型为tncryptType
	 * @param content
	 * @param tncryptType
	 * @return
	 */
	public static String encrypt(String content, EncryptType tncryptType) {
		if (null == content) {
			return null;
		}

		return encrypt(content.getBytes(), tncryptType);
	}

	/**
	 * 计算文件file的散列值，散列值的类型为tncryptType
	 * @param content
	 * @param tncryptType
	 * @return
	 */
	public static String encrypt(File file, EncryptType tncryptType) throws IOException {
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			byte[] buffer = new byte[8192];
			int length;
			MessageDigest msgDigest = getMessageDigest(tncryptType);
			while (-1 != (length = fileInputStream.read(buffer))) {
				msgDigest.update(buffer, 0, length);
			}
			return bytesToHex(msgDigest.digest());
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 计算指定字符串的md5值
	 * @param passwd
	 * @return
	 */
	public static String md5(String content) {
		return encrypt(content, EncryptType.MD5);
	}

	/**
	 * 计算指定字符串的md5值
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String md5(File file) throws IOException {
		return encrypt(file, EncryptType.MD5);
	}

	/**
	 * 计算指定字符串的sha1值
	 * @param passwd
	 * @return
	 */
	public static String sha1(String content) {
		return encrypt(content, EncryptType.SHA1);
	}

	/**
	 * 计算指定字符串的sha1值
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String sha1(File file) throws IOException {
		return encrypt(file, EncryptType.SHA1);
	}
}
