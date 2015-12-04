package com.kaidin.gui.model;

import java.util.List;

public class VoMenu {
	private String name;
	private String url;
	private List<VoMenu> submenuList;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<VoMenu> getSubmenuList() {
		return submenuList;
	}
	public void setSubmenuList(List<VoMenu> submenuList) {
		this.submenuList = submenuList;
	}
}
