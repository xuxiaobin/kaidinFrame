package com.kaidin.common.util;

import java.util.List;

public class ListUtil {
	
	public <T> List<T> subList(List<T> dataList, int size) {
		if (0 >= size || CollectionUtil.isEmpty(dataList)) {
			return null;
		}
		return dataList.subList(0, Math.min(dataList.size(), size));
	}
	
	public <T> List<T> subList(List<T> dataList, int fromIndex, int toIndex) {
		if (CollectionUtil.isEmpty(dataList)) {
			return null;
		}
		int listSize = dataList.size();
		
		fromIndex = Math.min(fromIndex, listSize);
		toIndex = Math.min(toIndex, listSize);
		
		return dataList.subList(fromIndex, toIndex);
	}
}
