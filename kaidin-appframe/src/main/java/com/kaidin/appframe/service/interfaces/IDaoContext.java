package com.kaidin.appframe.service.interfaces;

import javax.persistence.EntityManager;

import com.kaidin.appframe.exception.AppframeException;
/**
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public interface IDaoContext {
	EntityManager getEntityManager() throws AppframeException;
	EntityManager getEntityManager(String jndiName) throws AppframeException;
}
