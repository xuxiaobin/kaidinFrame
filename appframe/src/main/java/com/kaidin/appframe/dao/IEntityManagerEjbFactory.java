package com.kaidin.appframe.dao;

import javax.ejb.Local;
import javax.persistence.EntityManager;

@Local
public interface IEntityManagerEjbFactory {

	public EntityManager getEntityManager();
	
}
