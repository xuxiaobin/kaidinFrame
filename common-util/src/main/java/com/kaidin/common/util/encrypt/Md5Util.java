package com.kaidin.common.util.encrypt;

import java.io.File;
import java.io.IOException;
/**
 * 获取MD5散列值工具，仅仅是多一个入口方便调用
 * 实质是调用EncryptUtil类的md5方法
 * @author xuxiaobin	kaidin@foxmail.com
 */
public abstract class Md5Util {
	/**
	 * 计算指定字符串的md5值
	 * @param passwd
	 * @return
	 */
	public static String md5(String content) {
		return EncryptUtil.md5(content);
	}
	/**
	 * 计算指定字符串的md5值
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String md5(File file) throws IOException {
		return EncryptUtil.md5(file);
	}
}
