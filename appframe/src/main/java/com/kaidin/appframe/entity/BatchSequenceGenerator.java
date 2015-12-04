package com.kaidin.appframe.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;

import com.kaidin.common.util.PropertyUtil;

public class BatchSequenceGenerator extends SequenceGenerator {
	private int count = 0;
	private Long position_id;
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
					position_id = (Long) super.generate(session, object);
				}
				return new Long(position_id + (++count));
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
			HashMap<String, String> seqMap = PropertyUtil.readPropertyFile("/cfg/sequence.properties");
			for (String seqName: seqMap.keySet()) {
				if (seqName.equalsIgnoreCase(super.getSequenceName())) {
					needBatch = true;
					break;
				}
			}
		} catch (Exception e) {
			throw new MappingException("Can't load sequence.properties.");
		}
	}
	
//	public static void main(String[] args) throws Exception {
//		InputStream is = BatchSequenceGenerator.class.getResourceAsStream("/cfg/sequence.properties");
//		if (is == null) {
//			throw new Exception("Can not find sequence.properties.");
//		}
//		Properties p = new Properties();
//		p.load(is);
//		for (Object obj : p.keySet()) {
//			String sequence = String.valueOf(obj).toUpperCase();
//			System.out.println(sequence + ",");
//		}
//	}
}
