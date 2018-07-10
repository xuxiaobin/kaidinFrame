/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.query;

import java.io.Serializable;
import java.util.List;

import com.kaidin.common.util.CollectionUtil;

/**
 * 用于接口的返回值
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class PageData<T> implements Serializable {
	private static final long serialVersionUID = 8125027060909448117L;
	/** 是否调用成功 */
	private boolean           success;
	/** 错误代码（可以自己定义） */
	private String            errorCode;
	/** 出错信息 */
	private String            errorMsg;
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

	public PageData(PageLoadConfig pageLoadConfig) {
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

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getTotalCount() {
		// 记录总数至少为0
		return 0 < totalCount ? totalCount : 0;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = 0 < totalCount ? totalCount : 0;
	}

	public int getOffset() {
		// 记录数从1开始
		return 1 < offset ? offset : 1;
	}

	public void setOffset(int offset) {
		// 记录数从1开始
		this.offset = 1 < offset ? offset : 1;
	}

	public int getLimit() {
		// 记录数量最少一条，否则没有意义
		return 1 < limit ? limit : 1;
	}

	public void setLimit(int limit) {
		// 记录数量最少一条，否则没有意义
		this.limit = 1 < limit ? limit : 1;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		StringBuilder resultBuild = new StringBuilder();

		if (!success) {
			// 有错误才输出错误
			resultBuild.append("errorCode:").append(errorCode);
			resultBuild.append(", errorMsg:").append(errorMsg);
			return resultBuild.toString();
		}
		// 没有错误输出数据信息
		resultBuild.append("totalCount:").append(totalCount);
		resultBuild.append(", offset:").append(offset);
		resultBuild.append(", limit:").append(limit);
		if (CollectionUtil.isEmpty(dataList)) {
			resultBuild.append(", dataList:null");
			return resultBuild.toString();
		}
		resultBuild.append(", dataList:{");
		boolean isFirst = true;
		for (T element : dataList) {
			if (isFirst) {
				resultBuild.append(element);
				isFirst = false;
			} else {
				resultBuild.append(", ").append(element);
			}
		}
		resultBuild.append("}");

		return resultBuild.toString();
	}
}
