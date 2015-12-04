package com.kaidin.appframe.entity;

import java.util.Date;

public class SQLLogEntity {
	private String threadName;
	private String sql;
    private String params;
    private Date startTime;
    private long costTime;
    private int resultSize;
    
    public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
    
    public static SQLLogEntity getInstance() {
    	return new SQLLogEntity();
    }
    
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public long getCostTime() {
		return costTime;
	}
	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}
	public int getResultSize() {
		return resultSize;
	}
	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}
}
