package com.kaidin.gui.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaidin.common.util.NumberUtil;
import com.kaidin.common.util.StringUtil;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageLoadConfig;
import com.kaidin.gui.model.BoDownloadFile;
/**
 * 
 * @author xuxiaobin	kaidin@foxmail.com
 * 
 */
@Service
public class FileService {
	private static final transient Logger logger = LoggerFactory.getLogger(FileService.class);
	private static List<String> EXCLUDE_EXTNAME_LIST = new ArrayList<>();
	
	
	public static PageData<BoDownloadFile> getDirFiles(File dirFile, PageLoadConfig pageLoadCfg) {
		PageData<BoDownloadFile> result = new PageData<>();
		
		List<BoDownloadFile> dataList = listDir(dirFile);
		if (null != pageLoadCfg) {
			dataList = dataList.subList(pageLoadCfg.getOffset(), pageLoadCfg.getLimit());
		}
		for (BoDownloadFile file: dataList) {
			logger.info("{}\t{}", file.getName(), file.getDownloadPath());
		}
		result.setDataList(dataList);
		
		return result;
	}
	
	
	// 显示目录的方法
	private static ArrayList<BoDownloadFile> listDir(File dirFile) {
		ArrayList<BoDownloadFile> result = new ArrayList<>();
		
		// 判断传入对象是否为一个文件夹对象
		if (dirFile.isDirectory()) {
			for(File tmp: dirFile.listFiles()) {
				// 判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
				if (tmp.isDirectory()) {
					ArrayList<BoDownloadFile> tmpDirList = listDir(tmp);
					if (!tmpDirList.isEmpty()) {
						result.addAll(tmpDirList);
					}
				} else {
					if (EXCLUDE_EXTNAME_LIST.contains(getExtname(tmp))) {
						// 根据扩展名过滤
						continue;
					}
					BoDownloadFile downloadFile = new BoDownloadFile();
					downloadFile.setName(tmp.getName());
					downloadFile.setDownloadPath(tmp.getAbsolutePath());
					downloadFile.setSize(getSize(tmp));
					downloadFile.setLastModified(getLastModified(tmp));
					result.add(downloadFile);
				}
			}
		} else {
			logger.info("你输入的不是一个文件夹，请检查路径是否有误！");
		}
		
		return result;
	}
	
	/**
	 * 获取文件扩展名，大写
	 * @param file
	 * @return
	 */
	private static String getExtname(File file) {
		String result = null;
		
		if (null != file && !file.isDirectory()) {
			String fileName = file.getName();
			if (StringUtil.isNotEmpty(fileName)) {
				int index = fileName.lastIndexOf('.');
				result = fileName.substring(index + 1);
				if (null != result) {
					result = result.toUpperCase();
				}
			}
		}
		
		return result;
	}
	
	private static String getSize(File file) {
		String result = null;
		
		if (null != file && !file.isDirectory()) {
			long size = file.length();
			
			if (1024 * 1024 <= size) {
				if (1024 * 1024 * 1024 <= size) {
					result = NumberUtil.format2Decimal(size / 1024 / 1024 / 1024.0) + "G";
				} else {
					result = NumberUtil.format2Decimal(size / 1024 / 1024.0) + "M";
				}
			} else {
				if (1024 <= size) {
					result = NumberUtil.format2Decimal(size / 1024.0) + "K";
				} else {
					result = size + "B";
				}
			}
		}
		
		return result;
	}
	
	private static Date getLastModified(File file) {
		Date result = null;
		
		if (null != file && !file.isDirectory()) {
			result = new Date(file.lastModified());
		}
		
		return result;
	}
}
