package com.kaidin.appframe.service.interfaces;

import javax.persistence.EntityManager;

/**
 * <p>
 * Title: 事务管理器接口
 * </p>
 * 
 * @version 1.0
 */

public interface ITransactionManager {

	public String getCurrentTransactionName();

	/**
	 * 判断是否已经开始事务
	 * 
	 * @return boolean
	 */
	public boolean isStartTransaction();

	/**
	 * 开始事务
	 * 
	 * @throws Exception
	 */
	public void startTransaction() throws Exception;

	/**
	 * 提交事务
	 * 
	 * @throws AIException
	 */
	public void commitTransaction() throws Exception;

	/**
	 * 回滚事务
	 * 
	 * @throws AIException
	 */
	public void rollbackTransaction() throws Exception;

	public EntityManager getEntityManager();

	/**
	 * 获取一个只读数据源连接
	 * 
	 * @throws SQLException
	 * @return Connection 数据库连接
	 */
	// public Connection getReadonlyConnection() throws java.sql.SQLException;

	/**
	 * 挂起事务
	 * 
	 * @throws Exception
	 */
//	public void suspend() throws Exception;

	/**
	 * 恢复事务
	 * 
	 * @throws Exception
	 */
//	public void resume() throws Exception;

	/**
	 * 强制回滚事务
	 * 
	 * @param classHashCode
	 *            String
	 * @throws Exception
	 */
	public void forceRollbackTransaction(String classHashCode) throws Exception;

}
