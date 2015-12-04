package com.kaidin.gui.model;

import java.util.Date;
/**
 * 
 * @author xuxiaobin	kaidin@foxmail.com
 *
 */
public class VoDownloadFile {
	private String name;
	private String size;
	private String downloadPath;
	private Date lastModified;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
