package com.kaidin.appframe.service.id;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.TableGenerator;
import org.hibernate.type.Type;
/**
 * 批量获取数据表字段作id使用
 * @version 1.1
 * @author xuxiaobin	kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class BatchTableGenerator extends TableGenerator {
	/**
	 * 读取配置文件，做标记判断是否要自动生成id
	 */
	public void configure(Type type, Properties params, Dialect d) throws MappingException {
		super.configure(type, params, d);
	}
	
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		return generateHolder(session).makeValue();
	}
}
