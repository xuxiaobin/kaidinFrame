/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.kaidin.common.constant.ConstType;

public class FileUtilTest {

	@Test
	public void testCopyFile() throws IOException {
		//		String srcFile = "D:/download/thunder/bak";
		//		String targetFile = "D:/download/thunder/bak2";
		//		long costTimes = System.currentTimeMillis();
		//		FileUtil.copyFiles(srcFile, targetFile);
		//		costTimes = System.currentTimeMillis() - costTimes;
		//		System.out.println("costTimes:" + costTimes);
	}

	@Test
	public void testDeleteFiles() {
		//		String srcFile = "D:/download/thunder/bak2";
		//		FileUtil.deleteFiles(srcFile);
	}

	@Test
	public void testGetFileSizeDisplay() {
		assertEquals("456B", FileUtil.asDisplaySize(456));
		assertEquals("1.00K", FileUtil.asDisplaySize(ConstType.fileSize.K_SIZE));
		assertEquals("1.23K", FileUtil.asDisplaySize(DataTypeUtil.asLong(ConstType.fileSize.K_SIZE * 1.231)));
		assertEquals("1.00M", FileUtil.asDisplaySize(ConstType.fileSize.M_SIZE));
		assertEquals("3.22M", FileUtil.asDisplaySize(DataTypeUtil.asLong(3.216 * ConstType.fileSize.M_SIZE)));
		assertEquals("7.89G", FileUtil.asDisplaySize(DataTypeUtil.asLong(7.894 * ConstType.fileSize.G_SIZE)));
		assertEquals("7.89T", FileUtil.asDisplaySize(DataTypeUtil.asLong(7.895 * ConstType.fileSize.T_SIZE)));
		assertEquals("7.65E", FileUtil.asDisplaySize(DataTypeUtil.asLong(7.652 * ConstType.fileSize.E_SIZE)));
	}
}
