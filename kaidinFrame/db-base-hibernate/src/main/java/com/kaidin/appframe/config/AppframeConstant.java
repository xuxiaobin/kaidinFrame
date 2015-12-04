package com.kaidin.appframe.config;

public class AppframeConstant {
	public static final int INCREMENT = 1000;	// 如果使用程序批量获取id，需要将表id设置的步长为1000
	public static final int MAX_QUERY_LIMIT = 10000;	// 最大的查询数据条数限制，默认10000
	
	public static class PropertiesKey {
		public static final String MAX_RESULTS	= "maxResults";
		public static final String DATA_SOURCE	= "dataSource";
		public static final String RPT_DATA_SOURCE	= "rptDataSource";
	}
}
