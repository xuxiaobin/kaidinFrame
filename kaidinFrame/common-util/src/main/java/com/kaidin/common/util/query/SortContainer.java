package com.kaidin.common.util.query;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 页面查询传入的排序信息
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class SortContainer implements Serializable {
	private static final long serialVersionUID = -4902827484393351934L;
	private Map<String, String> container = new LinkedHashMap<String, String>(5);
	
	
	public void addSort(String column) {
		container.put(column, "asc");
	}
	public void addSortDesc(String column) {
		container.put(column, "desc");
	}
	
	public Map<String, String> getSort() {
		return container;
	}
	
	public String toSortSql() {
		String result = "";
		
		StringBuilder resultBuild = new StringBuilder();
		if (!container.isEmpty()) {
			for (String field: container.keySet()) {
				resultBuild.append(field).append(" ").append(container.get(field)).append(", ");
			}
			result = resultBuild.substring(1, resultBuild.length() - 2);
			if (0 < result.length()) {
				result = " order by " + result;
			}
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		String result = null;
		
		StringBuilder resultBuild = new StringBuilder();
		if (!container.isEmpty()) {
			for (String field: container.keySet()) {
				resultBuild.append(field).append(":").append(container.get(field)).append(", ");
			}
			result = resultBuild.substring(1, resultBuild.length() - 2);
		}
		
		return result;
	}
}
