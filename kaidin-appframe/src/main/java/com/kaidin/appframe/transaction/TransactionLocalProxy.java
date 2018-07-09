package com.kaidin.appframe.transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TransactionLocalProxy extends BaseTransactionProxy {

	private static transient Log log = LogFactory
			.getLog(TransactionLocalProxy.class);

	private static EntityManagerFactory emf = null;

//	private static ThreadLocal<EntityManager> local_entity_manager = new ThreadLocal<EntityManager>();

	private EntityManager local_entity_manager = null;
	
	@Override
	public ITransactionAdapter getTransactionAdapter() throws Exception {
		return new TransactionAdapter(getEntityManager().getTransaction());
	}

	
	public void setTransactionTimeout(int int0) throws SystemException {
		log.info("Not implement this function:setTransactionTimeout");
	}

	protected boolean isXATransaction() {
		return false;
	}

	public EntityManager getEntityManager() {
//		EntityManager em = local_entity_manager.get();
		if (this.local_entity_manager == null) {
			synchronized (lockObject) {
				if (emf == null) {
					log
							.info("Creating EntityManagerFactory with \"isa20-local\"");
					emf = Persistence.createEntityManagerFactory("isa20-local");
					log.info("EntityManagerFactory Created");
				}
			}
			local_entity_manager = emf.createEntityManager();
			local_entity_manager.setFlushMode(FlushModeType.AUTO);
//			local_entity_manager.set(em);
		}
		return local_entity_manager;
	}
	
	public void begin() throws Exception {
		if (this.m_status.isStartTransaction()) {
			log.warn("Transaction is joined,last called address:"
					+ getCallPath(this.m_status.m_addr), new Exception(
					"Transaction is nested"));
		}
		this.m_status.m_transactionCount = this.m_status.m_transactionCount + 1;

		if (log.isInfoEnabled()) {
			log.info("Start transaction,Transaction  Level:"
					+ this.m_status.m_transactionCount);
		}
		if (this.m_status.m_transactionCount == 1) {
			m_status.m_transactionName = createTransactionName();
			this.m_status.m_addr = new Exception();
			this.m_status.m_startTime = System.currentTimeMillis();
			m_status.m_entityManager = getEntityManager();
			m_status.m_currentTransaction = getTransactionAdapter();
			// 判断是使用外部事务还是内部事务
			if (m_status.m_currentTransaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
				m_status.m_isSelfStartTransaction = true;
				m_status.m_currentTransaction.begin();
			}
		}
	}

	public void commit() throws Exception {
		if (this.m_status.isStartTransaction() == false) {
			throw new RollbackException(
					"Can't commit, current transaction is not start.");
		}
		if (this.m_status.m_onlyRollback == true) {
			// 设置了事务只能回滚，该事务只能进行回滚操作
			throw new RollbackException(
					"Can't commit, current transaction is rollback only.");
		}

		this.m_status.m_transactionCount = this.m_status.m_transactionCount - 1;
		// 如果不是最后一层的事务提交，则不做什么处理
		if (this.m_status.m_transactionCount > 0) {
			return;
		}
		try {
			// 如果是自己的事务，提交
			if (m_status.m_isSelfStartTransaction) {
				this.m_status.m_currentTransaction.commit();
				m_status.m_isSelfStartTransaction = false;
			}
		} catch (Exception e) {
			log.error("Distributed, Commit error, Rollback at once.......", e);
			m_status.m_isCommitError = true;
			m_status.m_currentTransaction.rollback();
			new RollbackException(e.getMessage());
		} finally {
			if (m_status.m_startTime > 0) {
				if (System.currentTimeMillis() - m_status.m_startTime > warnTimeLong) {
					if (log.isWarnEnabled()) {
						log
								.warn("It takes too long to deal transaction,need optimize.Total time:"
										+ (System.currentTimeMillis() - m_status.m_startTime));
					}
				}
			}
			this.m_status.clear();
		}
	}

	public void rollback() throws Exception {
		if (this.m_status.isStartTransaction() == false) {
			if (m_status.m_isCommitError == true) {
				// 提交失败后抛出异常导致的重复回滚
				log.warn(" can not rollback, overlaped rollback ");
				if (this.m_status != null) {
					this.m_status.clear();
				}
				this.m_status = null;
				return;
			}
			if (this.m_status != null) {
				this.m_status.clear();
				this.m_status = null;
			}
			throw new IllegalStateException(
					"can't rollback, current transaction is not start");
		}
		if (log.isDebugEnabled())
			log.debug("rollback,transaction level:"
					+ m_status.m_transactionCount);
		this.m_status.m_transactionCount = this.m_status.m_transactionCount - 1;

		// 如果不是最后一层的事务提交，设置事务只能进行回滚操作,不做其他处理
		if (this.m_status.m_transactionCount > 0) {
			this.m_status.m_onlyRollback = true;
			return;
		}

		try {
			if (m_status.m_isSelfStartTransaction) {
				m_status.m_currentTransaction.rollback();
				m_status.m_isSelfStartTransaction = false;
			} else {// 标记事务只能回滚
				m_status.m_currentTransaction.setRollbackOnly();
			}
		} catch (Exception ex) {
			throw new SystemException(ex.getMessage());
		} finally {
			if (System.currentTimeMillis() - m_status.m_startTime > warnTimeLong) {
				if (log.isWarnEnabled()) {
					log
							.warn("It takes too long to deal transaction,need optimize.Total time:"
									+ (System.currentTimeMillis() - m_status.m_startTime));
				}
			}
			this.m_status.clear();
		}
	}

	public void setRollbackOnly() throws Exception {
		this.m_status.m_onlyRollback = true;
		this.m_status.m_currentTransaction.setRollbackOnly();
	}
}
