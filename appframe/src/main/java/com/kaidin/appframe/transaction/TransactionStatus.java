package com.kaidin.appframe.transaction;

import java.sql.Timestamp;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TransactionStatus {
	
	private static transient Log log = LogFactory
			.getLog(TransactionStatus.class);

	protected String m_transactionName;
	/**
	 * 记录事务开始的次数，每次StartTransaction 加 1，commit，rollback减1。
	 * 只有当m_transactionCount=0的时候才能执行真正的提交回滚。 如果中间执行了rollback，则需要设置
	 * m_onlyRollback = true。 在进行提交的时候如果m_onlyRollback=true,则抛出异常
	 */
	protected int m_transactionCount = 0;

	/**
	 * 标记事务是否只能回滚
	 */
	protected boolean m_onlyRollback = false;

	/**
	 * 开始事务的地址
	 */
	protected Exception m_addr;

	/**
	 * 开始事务时间
	 */
	protected long m_startTime;

	protected ITransactionAdapter m_currentTransaction = null;

	protected EntityManager m_entityManager = null;
	
	/**
	 * 是否BMT下自己启动的事务，
	 */
	private boolean isBMT = false;

	/**
	 * 标记是否提交失败
	 */
	protected boolean m_isCommitError = false;

	/**
	 * 用于存储事务的挂起与恢复
	 */
	protected javax.transaction.Transaction m_transactionSuspend = null;

	protected Long m_doneCode;
	protected Timestamp m_doneDate;
	
	protected boolean m_isSelfStartTransaction = false;

	public boolean isStartTransaction() {
		return this.m_transactionCount > 0;
	}

	public void clear() {

		m_transactionName = null;
		m_currentTransaction = null;
		m_transactionCount = 0;
		m_onlyRollback = false;
		m_addr = null;
		// m_startTime = 0;
		m_isCommitError = false;

		m_doneCode = null;
		m_doneDate = null;
		m_transactionName = null;
//		if (m_entityManager != null) {
//			m_entityManager.close();
//		}
		m_entityManager = null;
	}

	protected void setBMT(boolean isBMT) {
		log.info("set BMT model:"+isBMT);
		this.isBMT = isBMT;
	}
	
	protected boolean isBMT() {
		return this.isBMT;
	}
}
