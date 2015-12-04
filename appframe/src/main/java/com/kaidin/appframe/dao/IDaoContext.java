package com.kaidin.appframe.dao;

import javax.persistence.EntityManager;
/**
 * 获取连接使用
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public interface IDaoContext {
	public EntityManager getEntityManager() throws Exception; 
	public EntityManager getEntityManager(String jndiName) throws Exception; 
}
