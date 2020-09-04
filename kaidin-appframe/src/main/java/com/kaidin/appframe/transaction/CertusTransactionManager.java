//package com.kaidin.appframe.transaction;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Stack;
//
//import javax.ejb.EJB;
//import javax.ejb.TransactionManagementType;
//import javax.persistence.EntityManager;
//import javax.transaction.UserTransaction;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.kaidin.appframe.service.ServiceFactory;
//
//public class CertusTransactionManager implements ITransactionManager {
//	private static transient Log log = LogFactory
//			.getLog(CertusTransactionManager.class);
//
//	/**
//	 * 记录现有的事务信息
//	 */
//	protected static List<TransactionStatus> m_transactionCollection = Collections
//			.synchronizedList(new ArrayList<TransactionStatus>());
//
//
//	/**
//	 * TransactionStatus的堆栈
//	 */
//	protected Stack transactionStatusStack = new Stack();
//
//	public CertusTransactionManager(ServiceFactory aServiceFactory) {
//		ITransactionProxyLocal transactionProxy = null;
//		if(!aServiceFactory.isEjb())
//		{
//			transactionProxy  = new TransactionLocalProxy();
//		}
//		else
//		{
//			transactionProxy  = new TransactionInnerServerProxy();
//		}
//		transactionStatusStack.push(transactionProxy);
//	}
//
//	public CertusTransactionManager(ServiceFactory aServiceFactory, UserTransaction aUserTransaction) {
//		ITransactionProxyLocal transactionProxy = null;
//		if(!aServiceFactory.isEjb())
//		{
//			transactionProxy  = new TransactionLocalProxy();
//		}
//		else
//		{
//			transactionProxy  = new TransactionInnerServerProxy(aUserTransaction);
//			transactionProxy.setIsBMTTransaction(true);
//		}
//		transactionStatusStack.push(transactionProxy);
//	}
//
//
//	public CertusTransactionManager(ServiceFactory aServiceFactory, boolean isRptTransaction) {
//		ITransactionProxyLocal transactionProxy = null;
//		if(!aServiceFactory.isEjb())
//		{
//			transactionProxy  = new TransactionLocalProxy();
//		}
//		else
//		{
//			transactionProxy  = new TransactionRptServerProxy();
//		}
//		transactionStatusStack.push(transactionProxy);
//	}
//
//	protected ITransactionProxyLocal getCurrentTransactionProxy() {
//		return (ITransactionProxyLocal) transactionStatusStack.peek();
//	}
//
//	public void startTransaction() throws Exception {
//		getCurrentTransactionProxy().begin();
//		TransactionStatus transactionStatus = getCurrentTransactionProxy()
//				.getTransactionStatus();
//		if (transactionStatus.isStartTransaction()) {
//			m_transactionCollection.add(transactionStatus);
//		}
//	}
//
//	/**
//	 * 判断是否已经开始事务
//	 *
//	 * @return boolean
//	 */
//	public boolean isStartTransaction() {
//		return getCurrentTransactionProxy().getTransactionStatus()
//				.isStartTransaction();
//	}
//
//	/**
//	 * 提交事务
//	 *
//	 * @throws Exception
//	 */
//	public void commitTransaction() throws Exception {
//		getCurrentTransactionProxy().commit();
//		TransactionStatus status = getCurrentTransactionProxy()
//				.getTransactionStatus();
//		if (status.isStartTransaction() == false) {
//			m_transactionCollection.remove(status);
//		}
//	}
//
//	/**
//	 * 回滚事务
//	 *
//	 * @throws Exception
//	 */
//	public void rollbackTransaction() throws Exception {
//		TransactionStatus status = getCurrentTransactionProxy()
//				.getTransactionStatus();
//		try {
//			if(status.isStartTransaction())
//			{
//				getCurrentTransactionProxy().rollback();
//			}
//		} finally {
//			if (status.isStartTransaction() == false) {
//				m_transactionCollection.remove(status);
//			}
//		}
//	}
//
//	/*
//	 // start 删除
//	public void suspend() throws Exception {
//		// 在当前事务没有启动的情况下，也要挂起，避免在EJB容器管事务的情况下出错
//		TransactionStatus status = getCurrentTransactionProxy()
//				.getTransactionStatus();
//		if (log.isDebugEnabled())
//			log.debug("suspend:" + status);
//		getCurrentTransactionProxy().suspend();
//
//		BaseTransactionProxy newTransactionProxy = new TransactionInnerServerProxy();
//		transactionStatusStack.push(newTransactionProxy);
//	}
//
//	public void resume() throws Exception {
//		// 抛出顶层的事务状态
//		if (this.transactionStatusStack.size() == 1) {
//			throw new Exception(
//					"cannot resume, no transacption has been suspended before");
//		}
//		if (getCurrentTransactionProxy().getTransactionStatus()
//				.isStartTransaction()) {
//			throw new Exception(
//					"cannot resume, current transacption is not commit or rollback");
//		}
//		this.transactionStatusStack.pop();
//		TransactionStatus status = getCurrentTransactionProxy()
//				.getTransactionStatus();
//		if (log.isDebugEnabled())
//			log.debug("resume:" + status.m_transactionName);
//		getCurrentTransactionProxy().resume();
//
//	}
//	//end
//*/
//
//	public String debuger() {
//		StringBuffer sb = new StringBuffer();
//		TransactionStatus[] list = (TransactionStatus[]) m_transactionCollection
//				.toArray(new TransactionStatus[0]);
//		sb.append("Total not commit:" + list.length + "\n");
//		for (int i = 0; i < list.length; i++) {
//			try {
//				sb.append("Not commit:").append(list[i]).append(" Begin time:")
//						.append(new Date(list[i].m_startTime));
//
//				sb.append(BaseTransactionProxy.getCallPath(list[i].m_addr))
//						.append("\n");
//			} catch (Exception ex) {
//				log.error(ex.getMessage(), ex);
//			}
//		}
//		return sb.toString();
//	}
//
//	public void forceRollbackTransaction(String classHashCode) throws Exception {
//		TransactionStatus[] list = (TransactionStatus[]) m_transactionCollection
//				.toArray(new TransactionStatus[0]);
//		for (int i = 0; i < list.length; i++) {
//			if (list[i].toString().equalsIgnoreCase(classHashCode) == true) {
//				log.warn("Rollback through console:" + list[i].toString());
//				while (list[i].isStartTransaction()) {
//					list[i].m_currentTransaction.rollback();
//				}
//				m_transactionCollection.remove(list[i]);
//				break;
//			}
//		}
//	}
//
//	public static int getLeavTransaction() {
//		return m_transactionCollection.size();
//	}
//
//	public EntityManager getEntityManager() {
//		return getCurrentTransactionProxy().getEntityManager();
//	}
//
//	public String getCurrentTransactionName() {
//		TransactionStatus status = getCurrentTransactionProxy().getTransactionStatus();
//		return status.m_transactionName;
//	}
//
//}
