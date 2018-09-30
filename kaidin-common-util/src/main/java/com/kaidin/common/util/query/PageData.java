/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.query;

import java.util.List;

import com.kaidin.common.util.ToString;

/**
 * 用于接口的返回值
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class PageData<T> extends ToString {
	private static final long serialVersionUID = 8125027060909448117L;
	/** 是否调用成功 */
	private boolean           success;
	/** 错误代码（可以自己定义） */
	private String            errCode;
	/** 出错信息 */
	private String            errMsg;
	/** 记录总数，主要用于分页展示，默认为-1 */
	private int               totalCount       = -1;
	/** 记录开始部分，默认为1 */
	private int               offset           = 1;
	/** 记录条数限制，默认为15 */
	private int               limit            = 15;
	/**  */
	private List<T>           dataList;

	public PageData() {
	}

	public PageData(PageRequest pageLoadConfig) {
		if (null == pageLoadConfig) {
			return;
		}
		offset = pageLoadConfig.getOffset();
		limit = pageLoadConfig.getLimit();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public int getTotalCount() {
		// 记录总数至少为0
		return Math.max(totalCount, 0);
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getOffset() {
		// 记录数从1开始
		return Math.max(offset, 1);
	}

	public void setOffset(int offset) {
		// 记录数从1开始
		this.offset = offset;
	}

	public int getLimit() {
		// 记录数量最少一条，否则没有意义
		return Math.max(limit, 1);
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
