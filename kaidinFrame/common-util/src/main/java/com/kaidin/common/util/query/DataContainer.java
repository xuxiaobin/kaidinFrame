package com.kaidin.common.util.query;

import java.io.Serializable;
import java.util.List;
/**
 * 用于接口的返回值
 * @version	1.0
 * @author	kaidin@foxmail.com
 * @date	2015-6-23下午01:51:48
 */
public class DataContainer<T> implements Serializable {
	private static final long serialVersionUID = 8125027060909448117L;
	private int errorCode = 0;	// 错误代码（可以自己定义）
	private String errorMsg;	// 出错信息
	private int totalCount = -1;	// 记录总数，主要用于分页展示，默认为-1
	private int offset = 1;	// 记录开始部分，默认为1
	private int limit = 15;	// 记录条数限制，默认为15
	private List<T> dataList;
	
	
	public DataContainer() {
	}
	public DataContainer(PageLoadConfig pageLoadConfig) {
		if (null != pageLoadConfig) {
			offset = pageLoadConfig.getOffset();
			limit = pageLoadConfig.getLimit();
		}
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
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
		return 0 < totalCount ? totalCount: 0;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = 0 < totalCount ? totalCount: 0;
	}
	public int getOffset() {
		// 记录数从1开始
		return 1 < offset ? offset: 1;
	}
	public void setOffset(int offset) {
		// 记录数从1开始
		this.offset = 1 < offset ? offset: 1;
	}
	public int getLimit() {
		// 记录数量最少一条，否则没有意义
		return 1 < limit ? limit: 1;
	}
	public void setLimit(int limit) {
		// 记录数量最少一条，否则没有意义
		this.limit = 1 < limit ? limit: 1;
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
		
		if (0 == errorCode) {
			// 没有错误输出数据信息
			resultBuild.append("totalCount:").append(totalCount);
			resultBuild.append(", offset:").append(offset);
			resultBuild.append(", limit:").append(limit);
			if (null != dataList) {
				resultBuild.append(", dataList:{");
				boolean isFirst = true;
				for (T element: dataList) {
					if (isFirst) {
						resultBuild.append(element);
						isFirst = false;
					} else {
						resultBuild.append(", ").append(element);
					}
				}
				resultBuild.append("}");
			} else {
				resultBuild.append(", dataList:null");
			}
		} else {
			// 有错误才输出错误
			resultBuild.append("errorCode:").append(errorCode);
			resultBuild.append(", errorMsg:").append(errorMsg);
		}
		
		return resultBuild.toString();
	}
}
