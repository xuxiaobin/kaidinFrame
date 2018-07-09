package com.kaidin.appframe.service.interceptor.impl;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kaidin.appframe.service.interceptor.interfaces.AroundMethodInterceptor;

/**
 * <p>
 * Title:服务层的事务环绕拦截实现
 * </p>
 * <p>
 * Description: 所有的服务的方法的事务代码不需要写，全部由拦截进行设置 自己管理的事务
 * </p>
 */

public class TransactionInterceptorImpl implements AroundMethodInterceptor {
	private transient static Log log = LogFactory
			.getLog(TransactionInterceptorImpl.class);

	// 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。
	private static final int REQUIRED = 1;

	// 新建事务，如果当前存在事务，把当前事务挂起
	private static final int REQUIRES_NEW = 2;

	// 支持当前事务，如果当前没有事务，就以非事务方式执行。
	private static final int SUPPORTS = 3;

	// 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
	private static final int NOT_SUPPORTED = 4;

	// 以非事务方式执行，如果当前存在事务，则抛出异常
	private static final int NEVER = 5;

	// 使用当前的事务，如果当前没有事务，就抛出异常
	private static final int MANDATORY = 6;

	private static final String JOIN = "join";
	private static final String INDEPENDENCE = "independence";

	private boolean isCreate = false;

	private boolean isSuspend = false;

	private static int DEFAULT_TRANSACTION_ATTRIBUTE = REQUIRED;

	private static HashMap METHOD_TX_MAP = new HashMap();

	public TransactionInterceptorImpl() {
	}

	/**
	 * 方法调用前拦截
	 * 
	 * @param obj
	 *            Object
	 * @param methodName
	 *            String
	 * @param objectArray
	 *            Object[]
	 * @throws Exception
	 */
	public void beforeInterceptor(Object obj, String methodName,
			Object[] objectArray) throws Exception {
//		if (log.isInfoEnabled()) {
//			log.info("Transaction property:" + int2tx(REQUIRED));
//		}
//
//		// 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中
//		if (ServiceFactory.getTransactionManager().isStartTransaction()) {
//			if (log.isDebugEnabled()) {
//				log.debug("Class name:" + obj.getClass().getName()
//						+ ",method name:" + methodName
//						+ " joins the transaction.");
//			}
//		} else {
//			if (log.isDebugEnabled()) {
//				log.debug("Class name:" + obj.getClass().getName()
//						+ ",method name:" + methodName
//						+ " start the transaction.");
//			}
//			ServiceFactory.getTransactionManager().startTransaction();
//			isCreate = true;
//		}
//		if (!isCreate && log.isDebugEnabled()) {
//			log
//					.debug("Not the beginning transaction and print out the current transaction information:"
//							+  ServiceFactory.getTransactionManager().getCurrentTransactionName());// 由于不是开始事务,打印当前事务的信息
//		}
		}

	/**
	 * 方法调用后拦截
	 * 
	 * @param obj
	 *            Object
	 * @param methodName
	 *            String
	 * @param objectArray
	 *            Object[]
	 * @throws Exception
	 */
	public void afterInterceptor(Object obj, String methodName,
			Object[] objectArray) throws Exception {
//		if (isCreate) {
//			ServiceFactory.getTransactionManager().commitTransaction();
//			if (log.isDebugEnabled()) {
//				log.debug("Class name:" + obj.getClass().getName()
//						+ ",method name:" + methodName
//						+ " commit the transaction.");// 提交事务
//			}
//		}
//		if (isSuspend) {
//			ServiceFactory.getTransactionManager().resume();
//			if (log.isDebugEnabled()) {
//				log.debug("Class name:" + obj.getClass().getName()
//						+ ",method name:" + methodName
//						+ " resume the transaction.");// 恢复事务
//			}
//
//		}
			}

	/**
	 * 方法异常拦截
	 * 
	 * @param obj
	 *            Object
	 * @param methodName
	 *            String
	 * @param objectArray
	 *            Object[]
	 * @throws Exception
	 */
	public void exceptionInterceptor(Object obj, String methodName,
			Object[] objectArray, Throwable ex) throws Exception {
		throw new Exception(ex);
//		if (isCreate) {
//			ServiceFactory.getTransactionManager().rollbackTransaction();
//			if (log.isDebugEnabled()) {
//				log.debug("Class name:" + obj.getClass().getName()
//						+ ",method name:" + methodName
//						+ " rollback the transaction.");// 回滚事务
//			}
//		}
//		if (isSuspend) {
//			ServiceFactory.getTransactionManager().resume();
//			if (log.isDebugEnabled()) {
//				log.debug("Class name:" + obj.getClass().getName()
//						+ ",method name:" + methodName
//						+ " resume the transaction.");// 恢复事务
//			}
//		}
			}

	/**
	 * 
	 * @param i
	 *            int
	 * @return String
	 */
	private static String int2tx(int i) {
		String rtn = null;
		if (i == REQUIRED) {
			rtn = "Required or Jion";
		} else if (i == REQUIRES_NEW) {
			rtn = "RequiresNew or Independence";
		} else if (i == SUPPORTS) {
			rtn = "Supports";
		} else if (i == NOT_SUPPORTED) {
			rtn = "NotSupported";
		} else if (i == NEVER) {
			rtn = "Never";
		} else if (i == MANDATORY) {
			rtn = "Mandatory";
		} else {
			// 无法认识的事务属性
			throw new RuntimeException("无法认识的事务属性");
		}
		return rtn;
	}

	/**
	 * 
	 * @param tx
	 *            String
	 * @return int
	 */
	private static int tx2int(String tx) {
		int rtn = -1;
		if (tx.equalsIgnoreCase("Required") || tx.equalsIgnoreCase(JOIN)) {// 兼容支持join
			rtn = REQUIRED;
		} else if (tx.equalsIgnoreCase("RequiresNew")
				|| tx.equalsIgnoreCase(INDEPENDENCE)) {// 兼容支持independence
			rtn = REQUIRES_NEW;
		} else if (tx.equalsIgnoreCase("Supports")) {
			rtn = SUPPORTS;
		} else if (tx.equalsIgnoreCase("NotSupported")) {
			rtn = NOT_SUPPORTED;
		} else if (tx.equalsIgnoreCase("Never")) {
			rtn = NEVER;
		} else if (tx.equalsIgnoreCase("Mandatory")) {
			rtn = MANDATORY;
		} else {
			// 无法认识的事务属性
			String msg = "unknown_tx_prop";
			throw new RuntimeException(msg + ":" + tx);
		}
		return rtn;
	}
}
