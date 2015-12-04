package com.kaidin.appframe.transaction;

import javax.persistence.EntityManager;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class BaseTransactionProxy implements ITransactionProxyLocal {
	private static transient Log log = LogFactory
			.getLog(BaseTransactionProxy.class);

	protected TransactionStatus m_status;

	//1 min
	protected long warnTimeLong = 1000 * 60 ;
	
	protected static byte[] lockObject = new byte[] {};

	public BaseTransactionProxy() {
		m_status = new TransactionStatus();
	}
	
	public BaseTransactionProxy(boolean isBMTTransaction) {
		m_status = new TransactionStatus();
		m_status.setBMT(isBMTTransaction);
	}

	/* (non-Javadoc)
	 * @see com.certus.appframe.transaction.ITransactionProxyLocal#getTransactionStatus()
	 */
	public TransactionStatus getTransactionStatus() {
		return this.m_status;
	}
	
	protected String createTransactionName() {
		return "Transaction_" + Thread.currentThread().getId() + "_"
				+ System.currentTimeMillis();
	}

	protected static String getCallPath(Throwable e) {
		if (e == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		StackTraceElement stack[] = e.getStackTrace();
		for (int i = 0; i < stack.length; i++)
			sb.append(stack[i].getClassName() + "." + stack[i].getMethodName()
					+ "() Row:" + stack[i].getLineNumber() + "\n");

		return sb.toString();
	}

	protected String trasnferStatus(int status) {
		if (status == 0)
			return "STATUS_ACTIVE";
		if (status == 1)
			return "STATUS_MARKED_ROLLBACK";
		if (status == 2)
			return "STATUS_PREPARED";
		if (status == 3)
			return "STATUS_COMMITTED";
		if (status == 4)
			return "STATUS_ROLLEDBACK";
		if (status == 5)
			return "STATUS_UNKNOWN";
		if (status == 6)
			return "STATUS_NO_TRANSACTION";
		if (status == 7)
			return "STATUS_PREPARING";
		if (status == 8)
			return "STATUS_COMMITTING";
		if (status == 9)
			return "STATUS_ROLLING_BACK";
		return "STATUS DUBIOUS"; // 不确定的事务状态

	}

	protected abstract boolean isXATransaction();

	/* (non-Javadoc)
	 * @see com.certus.appframe.transaction.ITransactionProxyLocal#getEntityManager()
	 */
	public abstract EntityManager getEntityManager();

	/* (non-Javadoc)
	 * @see com.certus.appframe.transaction.ITransactionProxyLocal#getTransactionAdapter()
	 */
	public abstract ITransactionAdapter getTransactionAdapter() throws Exception;

	/* (non-Javadoc)
	 * @see com.certus.appframe.transaction.ITransactionProxyLocal#begin()
	 */
	public abstract void begin() throws Exception ;
	
	/* (non-Javadoc)
	 * @see com.certus.appframe.transaction.ITransactionProxyLocal#commit()
	 */
	public abstract void commit() throws Exception ;
	
	/* (non-Javadoc)
	 * @see com.certus.appframe.transaction.ITransactionProxyLocal#rollback()
	 */
	public abstract void rollback() throws Exception ;
	
	/* (non-Javadoc)
	 * @see com.certus.appframe.transaction.ITransactionProxyLocal#setRollbackOnly()
	 */
	public abstract void setRollbackOnly() throws Exception ;

	/* (non-Javadoc)
	 * @see com.certus.appframe.transaction.ITransactionProxyLocal#getStatus()
	 */
	public int getStatus() throws Exception {
		if (this.m_status.m_currentTransaction == null || this.m_status == null) {
			return Status.STATUS_NO_TRANSACTION;
		}
		if (this.m_status.m_onlyRollback == true) {
			return Status.STATUS_MARKED_ROLLBACK;
		}
		return this.m_status.m_currentTransaction.getStatus();
	}

	public void setIsBMTTransaction(boolean isBMTTransaction)
	{
		m_status.setBMT(isBMTTransaction);
	}

//	public void suspend() throws Exception {
//		log.info("Transaction supended");
//	}
//
//	public void resume() throws Exception {
//		log.info("Transaction resume");
//	}
}
