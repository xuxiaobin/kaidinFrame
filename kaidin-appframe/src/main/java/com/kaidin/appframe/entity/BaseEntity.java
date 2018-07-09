package com.kaidin.appframe.entity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
/**
 * 一般一个实体类对应一个表，该类所有实体类的基类
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = -98238666283832953L;

	public abstract long getId();

	public BaseEntity clone(final BaseEntity parent)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		PropertyUtils.copyProperties(this, parent);

		return this;
	}
}
