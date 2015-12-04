package com.kaidin.appframe.transaction;

import javax.persistence.EntityManager;


public interface ITransactionAdapter {
	
	
	public boolean isUserTransaction() throws IllegalStateException;

	public boolean isEntityTransaction()throws IllegalStateException;

	public void begin() throws Exception;

	public void commit() throws Exception;

	public int getStatus() throws Exception;

	public void rollback() throws Exception;

	public void setRollbackOnly() throws Exception;

}
