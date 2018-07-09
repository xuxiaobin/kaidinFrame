package com.kaidin.appframe.service.id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;

import com.kaidin.common.util.CollectionUtil;
import com.kaidin.common.util.PropertyUtil;
/**
 * 批量获取数据序列作id使用
 * @version 1.1
 * @author xuxiaobin	kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class BatchSequenceGenerator extends SequenceGenerator {
	/** 序列的步长 */
	public static final int STEP = 1000;
	private boolean needBatch	= false;
	private long positionId;
	private int count	= 0;
	
	
	/**
	 * 读取配置文件，做标记判断是否要自动生成id
	 */
	@Override
	public void configure(Type type, Properties params, Dialect d) throws MappingException {
		super.configure(type, params, d);
		
		try {
			HashMap<String, String> generatorKeyMap = PropertyUtil.readPropertyFile("/cfg/sequence.properties");
			if (CollectionUtil.isNotEmpty(generatorKeyMap)) {
				String generatorKey = String.valueOf(generatorKey());
				for (String tmpKey: generatorKeyMap.keySet()) {
					if (tmpKey.equalsIgnoreCase(generatorKey)) {
						needBatch = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new MappingException("Can't load sequence.properties.");
		}
	}
	
	@Override
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		if (needBatch) {
			synchronized (this) {
				if (STEP <= count) {
					count = 0;
				}
				if (0 == count) {
					positionId = (Long) generateHolder(session).makeValue();
				}
				return positionId + (count++);
			}
		}

		return generateHolder(session).makeValue();
	}
}
