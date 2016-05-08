package com.kaidin.common.util.query;

import java.io.Serializable;
import java.util.Map;
/**
 * 页面查询传入的分页信息
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class PageLoadConfig implements Serializable {
	private static final long serialVersionUID = 9104457759636937598L;
	private int offset = 1;	// 记录开始部分，默认1
	private int limit = 15;	// 记录条数限制，默认15条
	private SortContainer sortContainer;	// 排序使用
	
	
	public int getOffset() {
		return 1 < offset ? offset: 1;
	}
	public void setOffset(int offset) {
		// 记录数从1开始
		this.offset = 1 < offset ? offset: 1;
	}
	public int getLimit() {
		return 1 < limit ? limit: 1;
	}
	public void setLimit(int limit) {
		// 记录数量最少一条，否则没有意义
		this.limit = 1 < limit ? limit: 1;
	}
	public SortContainer getSortContainer() {
		return sortContainer;
	}
	public void setSortContainer(SortContainer sortContainer) {
		this.sortContainer = sortContainer;
	}
	
	public void addSort(String column) {
		if (null == sortContainer) {
			sortContainer = new SortContainer();
		}
		Map<String, String> container = sortContainer.getSort();
		container.put(column, "asc");
	}
	public void addSortDesc(String column) {
		if (null == sortContainer) {
			sortContainer = new SortContainer();
		}
		Map<String, String> container = sortContainer.getSort();
		container.put(column, "desc");
	}
	
	public Map<String, String> getSort() {
		Map<String, String> result = null;
		
		if (null != sortContainer) {
			result = sortContainer.getSort();
		}
		
		return result;
	}
	
	/**
	 * 获取排序的部分
	 * order by xxx xxx xxx
	 * @return
	 */
	public String toSortSql() {
		String result = "";
		
		if (null != sortContainer) {
			result = sortContainer.toSortSql();
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuild = new StringBuilder();
		
		resultBuild.append("offset:").append(offset);
		resultBuild.append(", limit:").append(limit);
		resultBuild.append(", sort[").append(sortContainer).append("]");
		
		return resultBuild.toString();
	}
}
