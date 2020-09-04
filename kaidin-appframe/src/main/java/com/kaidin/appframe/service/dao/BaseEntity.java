package com.kaidin.appframe.service.dao;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.kaidin.common.util.ToString;

/**
 * 一般一个实体类对应一个表，该类所有实体类的基类
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class BaseEntity extends ToString {
	private static final long  serialVersionUID = -98238666283832953L;
	/** id */
	public static final String P_id             = "id";
	protected long id;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 克隆
	 * @param parent
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public BaseEntity clone(BaseEntity parent) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		PropertyUtils.copyProperties(this, parent);

		return this;
	}
}
