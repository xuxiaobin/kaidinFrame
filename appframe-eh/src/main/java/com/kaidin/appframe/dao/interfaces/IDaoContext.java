package com.kaidin.appframe.dao.interfaces;

import javax.persistence.EntityManager;

import com.kaidin.appframe.exception.AppframeException;
/**
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public interface IDaoContext {
	public EntityManager getEntityManager() throws AppframeException;
	public EntityManager getEntityManager(String jndiName) throws AppframeException;
}
