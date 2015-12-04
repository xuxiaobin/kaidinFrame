package com.kaidin.appframe.transaction;

import javax.persistence.EntityTransaction;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

public class TransactionAdapter implements ITransactionAdapter{

	private UserTransaction userTransaction = null;
	
	private EntityTransaction entityTransaction = null;
	
	private int status = Status.STATUS_UNKNOWN;
	
	private TransactionAdapter()
	{
		
	}
	
	public TransactionAdapter(UserTransaction aUserTransaction)
	{
		this.userTransaction = aUserTransaction;
	}
	
	public  TransactionAdapter(EntityTransaction aEntityTransaction)
	{
		this.entityTransaction = aEntityTransaction;
	}

	public void begin() throws Exception {
		if(isEntityTransaction())
		{
			entityTransaction.begin();
		}
		if(isUserTransaction())
		{
			this.userTransaction.begin();
		}
	}

	public void commit() throws Exception {
		if(isEntityTransaction())
		{
			entityTransaction.commit();
			status = Status.STATUS_COMMITTED;
		}
		if(isUserTransaction())
		{
			this.userTransaction.commit();
		}
	}

	public int getStatus() throws Exception {
		
		if(isEntityTransaction())
		{
			if(status == Status.STATUS_COMMITTED || status == Status.STATUS_ROLLEDBACK)
				return status;
			if(entityTransaction.isActive())
			{
				status = Status.STATUS_ACTIVE;
			}
			else
			{
				status = Status.STATUS_NO_TRANSACTION;
			}
			return status;
		}
		else
		{ 
			status = this.userTransaction.getStatus();
		}
		return status;
	}

	public void rollback() throws Exception {
		if(isEntityTransaction())
		{
			entityTransaction.rollback(); 
			status = Status.STATUS_ROLLEDBACK;
		}
		if(isUserTransaction())
		{
			this.userTransaction.rollback();
		}	
	}

	public void setRollbackOnly() throws Exception {
		if(isEntityTransaction())
		{
			entityTransaction.setRollbackOnly(); 
		}
		if(isUserTransaction())
		{
			this.userTransaction.setRollbackOnly();
		}
	}
	public boolean isEntityTransaction() throws IllegalStateException {
		return entityTransaction == null ? false:true;
	}

	public boolean isUserTransaction() throws IllegalStateException {
		return userTransaction == null ? false:true;
	}	 

}
