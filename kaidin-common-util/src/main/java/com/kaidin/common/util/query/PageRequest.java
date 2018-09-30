/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.query;

import java.util.Map;

import com.kaidin.common.util.StringUtil;
import com.kaidin.common.util.ToString;

/**
 * 页面查询传入的分页信息
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class PageRequest extends ToString {
	private static final long serialVersionUID = 9104457759636937598L;
	private int               offset           = 1;                   // 记录开始部分，默认1
	private int               limit            = 15;                  // 记录条数限制，默认15条
	private SortRequest     sortContainer;                          // 排序使用

	public int getOffset() {
		return Math.max(offset, 1);
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return Math.max(limit, 1);
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public SortRequest getSortContainer() {
		return sortContainer;
	}

	public void setSortContainer(SortRequest sortContainer) {
		this.sortContainer = sortContainer;
	}

	public void addSort(String column) {
		if (null == sortContainer) {
			sortContainer = new SortRequest();
		}
		sortContainer.getSort().put(column, "asc");
	}

	public void addSortDesc(String column) {
		if (null == sortContainer) {
			sortContainer = new SortRequest();
		}
		sortContainer.getSort().put(column, "desc");
	}

	public Map<String, String> getSort() {
		if (null == sortContainer) {
			return null;
		}

		return sortContainer.getSort();
	}

	/**
	 * 获取排序的部分
	 * order by xxx xxx xxx
	 * @return
	 */
	public String toSortSql() {
		if (null == sortContainer) {
			return StringUtil.EMPTY_STR;
		}

		return sortContainer.toSortSql();
	}
}
