package com.kaidin.appframe.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;

import com.kaidin.common.util.PropertyUtil;

/**
 * 批量获取oracle数据库序列作id使用
 * 
 * @version 1.1
 * @author xuxiaobin kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class BatchSequenceGenerator extends SequenceGenerator {
	private int     count     = 0;
	private Long    positionId;
	private boolean needBatch = false;

	public Object generatorKey() {
		return super.generatorKey();
	}

	public String[] sqlCreateStrings(Dialect dialect) throws HibernateException {
		return super.sqlCreateStrings(dialect);
	}

	public String[] sqlDropStrings(Dialect dialect) throws HibernateException {
		return super.sqlDropStrings(dialect);
	}

	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		if (needBatch) {
			synchronized (this) {
				if (count >= 999) {
					count = 0;
				}
				if (count == 0) {
					positionId = (Long) super.generate(session, object);
				}
				return new Long(positionId + (++count));
			}
		} else {
			return super.generate(session, object);
		}
	}

	/**
	 * 读取配置文件，做标记判断是否要自动生成id
	 */
	public void configure(Type type, Properties params, Dialect d) throws MappingException {
		super.configure(type, params, d);

		try {
			Map<String, String> seqMap = PropertyUtil.readPropertyFile("/cfg/sequence.properties");
			for (String seqName : seqMap.keySet()) {
				if (seqName.equalsIgnoreCase(super.getSequenceName())) {
					needBatch = true;
					break;
				}
			}
		} catch (Exception e) {
			throw new MappingException("Can't load sequence.properties.");
		}
	}
}
