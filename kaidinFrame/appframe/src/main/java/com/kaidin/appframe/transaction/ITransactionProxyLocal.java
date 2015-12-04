package com.kaidin.appframe.transaction;

import javax.ejb.Local;
import javax.persistence.EntityManager;

 
public interface ITransactionProxyLocal {

	public abstract TransactionStatus getTransactionStatus();

	public abstract EntityManager getEntityManager();

	public abstract ITransactionAdapter getTransactionAdapter()
			throws Exception;

	public abstract void begin() throws Exception;

	public abstract void commit() throws Exception;

	public abstract void rollback() throws Exception;

	public abstract void setRollbackOnly() throws Exception;

	public abstract int getStatus() throws Exception;
	
	public void setIsBMTTransaction(boolean isBMTTransaction);

}