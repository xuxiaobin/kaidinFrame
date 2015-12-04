package com.kaidin.appframe.entity;

import java.util.Date;
import java.util.List;

public class SQLLogMapEntity {
    private List<SQLLogEntity> SQLLogEntityList;
    private Date logDate;
	public List<SQLLogEntity> getSQLLogEntityList() {
		return SQLLogEntityList;
	}
	public void setSQLLogEntityList(List<SQLLogEntity> sQLLogEntityList) {
		SQLLogEntityList = sQLLogEntityList;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
}
