package com.kaidin.common.util.query;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kaidin.common.util.CollectionUtil;
import com.kaidin.common.util.StringUtil;
/**
 * 页面查询传入的排序信息
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class SortContainer implements Serializable {
	private static final long serialVersionUID = -4902827484393351934L;
	private Map<String, String> container = new LinkedHashMap<>(2);
	
	
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
		if (CollectionUtil.isEmpty(container)) {
			return StringUtil.EMPTY_STR;
		}
		
		StringBuilder resultBuild = new StringBuilder(" order by ");
		for (String field: container.keySet()) {
			resultBuild.append(field).append(" ").append(container.get(field)).append(", ");
		}
		int strLength = resultBuild.length();
		
		return resultBuild.substring(0, strLength - 2);
	}
	
	@Override
	public String toString() {
		if (CollectionUtil.isEmpty(container)) {
			return StringUtil.EMPTY_STR;
		}
		StringBuilder resultBuild = new StringBuilder();
		for (String field: container.keySet()) {
			resultBuild.append(field).append(":").append(container.get(field)).append(", ");
		}
		int strLength = resultBuild.length();
		
		return resultBuild.substring(0, strLength - 2);
	}
}
