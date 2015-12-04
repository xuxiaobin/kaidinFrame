package com.kaidin.common.util;

public interface BeanConvertFilter<T> {
	public boolean doFilter(T obj);
}
