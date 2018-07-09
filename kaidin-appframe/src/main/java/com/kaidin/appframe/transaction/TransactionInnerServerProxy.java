package com.kaidin.appframe.transaction;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TransactionInnerServerProxy extends BaseTransactionProxy {

	private static transient Log log = LogFactory
			.getLog(TransactionInnerServerProxy.class);

	private static Context context;

	private UserTransaction userTransaction;

	protected boolean isXATransaction() {
		return true;
	}

	public TransactionInnerServerProxy() {

	}

	public TransactionInnerServerProxy(UserTransaction aUserTransaction) {
		m_status.setBMT(true);
		userTransaction = aUserTransaction;

	}

	@Override
	public EntityManager getEntityManager() {
		// 见ServiceFactory.getEntityManager()
		return null;
	}

	@Override
	public ITransactionAdapter getTransactionAdapter() throws Exception {
		return new TransactionAdapter(userTransaction);
	}

	/**
	 * 交由容器管理
	 */
	public void begin() throws Exception {
		if (m_status.isBMT()) {
			if (m_status.m_transactionCount > 0) {
				log.info(
						"Transaction is joined in BMT model, last called address:"
								+ getCallPath(this.m_status.m_addr),
						new Exception("Transaction is nested， Be Careful!"));
			}
		}
		// just count it
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
			// 之前没有开启过事务, 即是只在BMT模式
			if (m_status.isBMT()) {
				log.info("Transaction status in BMT Model : "+m_status.m_currentTransaction.getStatus());
				if (m_status.m_currentTransaction.getStatus() == Status.STATUS_NO_TRANSACTION)
					log.info("Begin Transaction in BMT Model");
					m_status.m_currentTransaction.begin();
			}
		}
	}

	public void commit() throws Exception {
		if (m_status.isBMT()) {
			// BMT下， 判断之前是否有事务开启
			if (this.m_status.isStartTransaction() == false) {
				throw new RollbackException(
						"Can't commit, current transaction is not start.");
			}
		} else {
			// CMT下交由容器管理
			log.info("Commit in CMT, just ignore.");
		}
	
		
		if (this.m_status.m_onlyRollback == true) {
			// 设置了事务只能回滚，该事务只能进行回滚操作,由异常抛出后在catch中执行rollback处理
			throw new RollbackException(
					"Can't commit, current transaction is rollback only.");
		}

		//count it
		this.m_status.m_transactionCount--;
		
		if (log.isInfoEnabled()) {
			log.info("Commit transaction,Transaction  Level:"
					+ this.m_status.m_transactionCount);
		}
		// // 如果不是最后一层的事务提交，则不做什么处理
		if (this.m_status.m_transactionCount > 0) {
			return;
		}
		try {
			// 如果是BMT的事务，提交。
			if (m_status.isBMT()) {
				log.info("Commit Transaction in BMT Model");
				this.m_status.m_currentTransaction.commit();
			}
		} catch (Exception e) {
			log.fatal("Distributed, Commit error, Rollback at once.......");
			m_status.m_isCommitError = true;
			this.setRollbackOnly();
			log.error("Commit Excetion", m_status.m_addr);
			throw new RollbackException(e.getMessage());
		} finally {
			if (m_status.m_startTime > 0) {
				if (System.currentTimeMillis() - m_status.m_startTime > warnTimeLong) {
					if (log.isWarnEnabled()) {
						log.warn("It takes too long to deal transaction,need optimize.Total time:"
								+ (System.currentTimeMillis() - m_status.m_startTime));
					}
				}
			}
			this.m_status.clear();
		}
	}

	public void rollback() throws Exception {
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
			if (m_status.isBMT()) {
				// BMT模式下
				m_status.m_currentTransaction.rollback();
			} else {// 标记事务只能回滚
				this.setRollbackOnly();
			}
		} catch (Exception ex) {
			log.error("rollback Exception", ex);
			throw new SystemException(ex.getMessage());
		} finally {
			if (System.currentTimeMillis() - m_status.m_startTime > warnTimeLong) {
				if (log.isWarnEnabled()) {
					log.warn("It takes too long to deal transaction,need optimize.Total time:"
							+ (System.currentTimeMillis() - m_status.m_startTime));
				}
			}
			this.m_status.clear();
		}
	}

	public void setRollbackOnly() throws Exception {
		this.m_status.m_onlyRollback = true;
		this.m_status.m_currentTransaction.setRollbackOnly();
		// SessionContext sctx =
		// (SessionContext) ctx.lookup("java:comp/EJBContext");
		// sctx.setRollbackOnly();
	}
}
