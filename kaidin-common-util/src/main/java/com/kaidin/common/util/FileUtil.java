/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.kaidin.common.constant.ConstType;

/**
 * 文件操作工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2016-5-18下午02:51:48
 */
public abstract class FileUtil {
	/** 拷贝文件使用的buffsize 2M */
	private final static long BUFF_SIZE = ConstType.fileSize.M_SIZE * 2;

	/**
	 * 拷贝文件
	 * @param srcFile
	 * @param targetFile
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private static boolean copyFile(File srcFile, File targetFile) throws IOException {
		if (targetFile.exists()) {
			throw new IOException("target file " + targetFile.getName() + " in " + targetFile.getAbsolutePath() + " is exists.");
		}
		new File(targetFile.getParent()).mkdirs();
		targetFile.createNewFile();
		try (FileChannel inChannel = new FileInputStream(srcFile).getChannel();
		        FileChannel outChannel = new FileOutputStream(targetFile).getChannel()) {
			long fileSize = inChannel.size();
			long buffSize = BUFF_SIZE;
			for (long position = 0; position < fileSize; position += buffSize) {
				long restSize = fileSize - position;
				if (BUFF_SIZE > restSize) {
					buffSize = restSize;
				}
				inChannel.transferTo(position, buffSize, outChannel);
			}
			return true;
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 拷贝文件或者递归拷贝目录
	 * @param srcFileName
	 * @param targetFileName
	 * @return
	 * @throws IOException
	 */
	public static boolean copyFiles(String srcFileName, String targetFileName) throws IOException {
		File srcFile = new File(srcFileName);
		if (!srcFile.isDirectory()) {
			return copyFile(srcFile, new File(targetFileName));
		}

		File[] subFileArry = srcFile.listFiles();
		if (CollectionUtil.isEmpty(subFileArry)) {
			new File(targetFileName).mkdirs();
		}
		boolean result = false;
		for (File subFile : subFileArry) {
			String subTargetFileName = targetFileName + File.separator + subFile.getName();
			result = copyFiles(subFile.getAbsolutePath(), subTargetFileName);
			if (!result) {
				break;
			}
		}

		return result;
	}

	/**
	 * 删除文件或者目录
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFiles(String fileName) {
		File srcFile = new File(fileName);
		if (!srcFile.exists()) {
			return true;
		}

		boolean result = false;
		if (srcFile.isDirectory()) {
			File[] subFileArry = srcFile.listFiles();
			if (CollectionUtil.isNotEmpty(subFileArry)) {
				for (File subFile : subFileArry) {
					result = deleteFiles(subFile.getAbsolutePath());
					if (!result) {
						break;
					}
				}
			}
		}
		result = srcFile.delete();

		return result;
	}

	/**
	 * 获取文件大小伙子目录大小
	 * @param targetFile
	 * @return
	 */
	private static long getFileSize(File targetFile) {
		if (targetFile.isFile()) {
			return targetFile.length();
		}

		File[] subFileArry = targetFile.listFiles();
		if (null == subFileArry) {
			return 0;
		}
		long result = 0;
		for (File subFile : subFileArry) {
			result += getFileSize(subFile);
		}

		return result;
	}

	/**
	 * 获取文件大小伙子目录大小
	 * @param fileName
	 * @return
	 */
	public static long getFileSize(String targetFileName) {
		return getFileSize(new File(targetFileName));
	}

	/**
	 * 将文件大小转成常见的单位大小，保留两位小数
	 * @param byteSize
	 * @return
	 */
	public static String asDisplaySize(long byteSize) {
		if (ConstType.fileSize.K_SIZE > byteSize) {
			return byteSize + "B";
		}

		int i = 0;
		for (; ConstType.fileSize.M_SIZE <= byteSize; i++) {
			byteSize = byteSize >> 10;
		}
		char[] unitArray = new char[] { 'K', 'M', 'G', 'T', 'E' };

		return NumberUtil.format2Decimal(byteSize / 1024F) + unitArray[i];
	}
}
