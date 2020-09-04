//package com.kaidin.appframe.service;
//
//import java.lang.reflect.Constructor;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.persistence.EntityManager;
//import javax.sql.DataSource;
//import javax.transaction.Status;
//import javax.transaction.SystemException;
//import javax.transaction.UserTransaction;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.kaidin.appframe.dao.AppDao;
//import com.kaidin.appframe.dao.IDaoContext;
//import com.kaidin.appframe.dao.IEntityManagerEjbFactory;
//import com.kaidin.appframe.exception.AppframeException;
//import com.kaidin.appframe.service.interceptor.impl.TransactionInterceptorImpl;
//import com.kaidin.appframe.transaction.CertusTransactionManager;
//import com.kaidin.appframe.transaction.ITransactionManager;
//import com.kaidin.appframe.util.AppframeConfigUtil;
//
///**
// * 获取数据库链接服务
// * @version 1.0
// * @author kaidin@foxmail.com
// * @date 2015-6-23下午01:51:48
// */
//public class ServiceFactory {
//	private static final transient Logger logger = LoggerFactory.getLogger(ServiceFactory.class);
//	// public static final String DAO_FACTORY_JNDI = "isa2-qms/com.certus.appframe.dao.IsaEntityManagerEjbFactory/local";
//	public static final String DAO_FACTORY_JNDI = "isa-qms/IsaEntityManagerEjbFactory/local";
//	private static final boolean IS_EJB3 = AppframeConfigUtil.isEjb3();
//	private static Map<String, Class> clazzes_map = new HashMap<String, Class>(300);
//	private static ServiceFactory service_factory_instance = new ServiceFactory();
//	private static Context context;
//	private static InitialContext ctx = null;
//	/**
//	 * 保证一个线程里只有一个TransactionManager
//	 */
//	private static ThreadLocal<ITransactionManager> transaction_manager = new ThreadLocal<ITransactionManager>();
//
//
//	private ServiceFactory() {
//	}
//
//	private static void initContext() {
//		// Properties prop = new Properties();
//		// ResourceBundle resource = ResourceBundle.getBundle("jndi");
//		// prop.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
//		// prop.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interface");
//		// prop.put("java.naming.provider.url", "127.0.0.1:1099");
//
//		try {
//			// ctx = new InitialContext(prop);
//			ctx = new InitialContext();
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//	}
//
//
//	public static Object getService(String serviceName) throws Exception {
//		Object result = null;
//		if (IS_EJB3) {
//			if (ctx == null) {
//				initContext();
//			}
//			String name = serviceName + "/local";
//			result = ctx.lookup(name);
//		} else {
//			throw new Exception("Only can get Service in a J2ee container");
//		}
//		return result;
//	}
//
//	public static Object getLocalService(Class aInterfaceClass) throws Exception {
//		Object result = null;
//		Class clazz = getImplClazzByInterface(aInterfaceClass);
//		Object obj = clazz.newInstance();
//		Class[] interceptorImpls = { TransactionInterceptorImpl.class };// 拦截器数组，注意拦截器实现的顺序
//
//		ProxyInvocationHandler handler = new ProxyInvocationHandler(obj,
//				interceptorImpls, ServiceInvokeTypeEnum.INVOKE_LOCAL);
//		result = ProxyFactory.getCommonProxyInstance(aInterfaceClass, handler);
//		return result;
//	}
//
//	public static Object getReportService(Class aInterfaceClass) throws Exception {
//		Object result = null;
//
//		Object obj = null;
//		Class clazz = getImplClazzByInterface(aInterfaceClass);
//
//		if (IS_EJB3) {
//			if (null == context) {
//				context = new InitialContext();
//			}
//			obj = context.lookup(clazz.getSimpleName() + "/local");
//		} else {
//			obj = clazz.newInstance();
//		}
//		Class[] interceptorImpls = { };
//
//		ProxyInvocationHandler handler = new ProxyInvocationHandler(obj,
//				interceptorImpls, ServiceInvokeTypeEnum.INVOKE_LOCAL);
//		result = ProxyFactory.getCommonProxyInstance(aInterfaceClass, handler);
//
//		return result;
//	}
//
//	/**
//	 * 非模块化使用
//	 *
//	 * @param aInterfaceClass
//	 * @return
//	 * @throws Exception
//	 */
//	public static Object getDAO(Class aInterfaceClass) throws Exception {
//		Object result = null;
//
//		if (IS_EJB3) {
//			if (null == context) {
//				context = new InitialContext();
//			}
//			result = context.lookup(aInterfaceClass.getName() + "/local");
//		} else {
//			Class clazz = getImplClazzByInterface(aInterfaceClass);
//			Constructor ct = clazz.getConstructor(new Class[] { IDaoContext.class });
//			result = ct.newInstance(new Object[] { service_factory_instance.new DaoContext() });
//		}
//
//		return result;
//	}
//	/**
//	 * 模块化使用
//	 *
//	 * @param jndiName
//	 * @param aInterfaceClass
//	 * @return
//	 * @throws Exception
//	 */
//	public static Object getDAO(String jndiName, Class aInterfaceClass) throws Exception {
//		Object result = null;
//
//		if (IS_EJB3) {
//			if (null == context) {
//				context = new InitialContext();
//			}
//			result = context.lookup(aInterfaceClass.getName() + "/local");
//		} else {
//			Class clazz = getImplClazzByInterface(aInterfaceClass);
//			Constructor ct = clazz.getConstructor(new Class[] { String.class, IDaoContext.class });
//			result = ct.newInstance(new Object[] { jndiName, service_factory_instance.new DaoContext() });
//		}
//
//		return result;
//	}
//
//	/**
//	 * 模块化使用此方法
//	 *
//	 * @param unitName
//	 * @param aEntityImplClass
//	 * @return
//	 * @throws Exception
//	 */
//	public static AppDao getAppDAO(String jndiName, Class aEntityImplClass) throws Exception {
//		return new AppDao(jndiName, aEntityImplClass, service_factory_instance.new DaoContext());
//	}
//
//	/**
//	 * 非模块化使用
//	 *
//	 * @param aEntityImplClass
//	 * @return
//	 * @throws Exception
//	 */
//	public static AppDao getAppDAO(Class aEntityImplClass) throws Exception {
//		return new AppDao(aEntityImplClass, service_factory_instance.new DaoContext());
//	}
//
//	private static Class getImplClazzByInterface(Class aInterfaceClass) throws ClassNotFoundException {
//		Class result = null;
//
//		String interfaceName = aInterfaceClass.getName();
//		String clazzName = ServiceFactoryHelper.convertClassName(interfaceName);
//		if (clazzes_map.containsKey(clazzName)) {
//			result = clazzes_map.get(clazzName);
//		}
//		if (null == result) {
//			synchronized (clazzes_map) {
//				if (!clazzes_map.containsKey(clazzName)) {
//					result = Class.forName(clazzName);
//					clazzes_map.put(clazzName, result);
//				} else {
//					result = clazzes_map.get(clazzName);
//				}
//			}
//		}
//
//		return result;
//	}
//
//	private EntityManager getEntityManager() throws Exception {
//		if (IS_EJB3) {
//			// throw new RuntimeException("Can not use getEntityManager() in a EJB Container");
//			if (null == context) {
//				context = new InitialContext();
//			}
//			return ((IEntityManagerEjbFactory) context.lookup(ServiceFactory.DAO_FACTORY_JNDI))
//					.getEntityManager();
//		}
//		return getTransactionManager().getEntityManager();
//
//	}
//
//	private EntityManager getEntityManager(String jndiName) throws Exception {
//		if (IS_EJB3) {
//			// throw new RuntimeException(
//			// "Can not use getEntityManager() in a EJB Container");
//			if (null == context) {
//				context = new InitialContext();
//			}
//			return ((IEntityManagerEjbFactory) context.lookup(jndiName))
//					.getEntityManager();
//		}
//		return getTransactionManager().getEntityManager();
//
//	}
//
//	/**
//	 * 创建"TransactionManagementType.BEAN"下的TransactionManager
//	 * 由于一个线程里只有一个TransactionManager, 如果是由BMT先创建的，CMT内的 事务同样也可以得到继承。
//	 * 如果transaction_manager是由CMT的EJB创建的，
//	 * 需要替换掉原有的TrasnactionManager为BMTTransactionManager。
//	 */
//	public static void createBMTTransactionManager(UserTransaction aUserTransaction) {
//		if (aUserTransaction == null && ServiceFactory.IS_EJB3) {
//			throw new AppframeException("Can not create BMTTransactionManager, UserTransaction get from Ejb is null !!!");
//		}
//		ITransactionManager manager = transaction_manager.get();
//		if (manager != null) {
//			try {
//				// 通过aUserTransaction.getStatus()判断之前是否已经开启了事务，没开启的话就可以用新的BMT模式的CertusTransactionManager
//				if (aUserTransaction.getStatus() != Status.STATUS_NO_TRANSACTION) {
//					logger.warn("Can not create BMTTransactionManager, TransactionManager has started in this thread !!!");
//				}
//			} catch (SystemException e) {
//				throw new AppframeException(e);
//			}
//		} else {
//			transaction_manager.remove();
//			manager = new CertusTransactionManager(service_factory_instance,
//					aUserTransaction);
////			log.info("createBMTTransactionManager", new Exception(
////					"Here is the trace"));
//			transaction_manager.set(manager);
//		}
//	}
//
//	public static ITransactionManager getTransactionManager(UserTransaction aUserTransaction) {
//		createBMTTransactionManager(aUserTransaction);
//
//		return transaction_manager.get();
//	}
//
//	public static ITransactionManager getTransactionManager() {
//		ITransactionManager manager = transaction_manager.get();
//		if (manager == null) {
//			synchronized (transaction_manager) {
//				manager = transaction_manager.get();
//				if (manager == null) {
//					manager = new CertusTransactionManager(
//							service_factory_instance);
//					transaction_manager.set(manager);
//					logger.info("TransactionManager Created", new Exception(
//							"Here is the trace"));
//				}
//			}
//		}
//		return manager;
//	}
//
//	public static ITransactionManager getRptTransactionManager() {
//		boolean isRptTransaction = true;
//		ITransactionManager manager = transaction_manager.get();
//		if (manager == null) {
//			synchronized (transaction_manager) {
//				manager = transaction_manager.get();
//				if (manager == null) {
//					manager = new CertusTransactionManager(
//							service_factory_instance, isRptTransaction);
//					transaction_manager.set(manager);
//				}
//			}
//		}
//		return manager;
//
//	}
//
//	public static boolean isEjb() {
//		return ServiceFactory.IS_EJB3;
//	}
//
//	protected class DaoContext implements IDaoContext {
//		public EntityManager getEntityManager() throws Exception {
//			return ServiceFactory.this.getEntityManager();
//		}
//
//		public EntityManager getEntityManager(String jndiName) throws Exception {
//			return ServiceFactory.this.getEntityManager(jndiName);
//		}
//	}
//
//
//	public static Connection getConnection() throws Exception {
//		if (ctx == null) {
//			initContext();
//		}
//		DataSource ds = (DataSource) ctx.lookup(AppframeConfigUtil.getDataSource());
//		return ds.getConnection();
//
//		//其他获取链接的方式
////		EntityStbDaoImpl stbDaoImpl2 = (EntityStbDaoImpl) stbdao;
////		EntityManager entityManager2 = stbDaoImpl2.getEntityManager();
////		SessionImplementor session2 = entityManager2.unwrap(SessionImplementor.class);
////		conn = session2.connection();
////
////		EntityStbDaoImpl stbDaoImpl3 = (EntityStbDaoImpl) stbdao;
////		EntityManager entityManager3 = stbDaoImpl3.getEntityManager();
////		org.hibernate.Session session = (org.hibernate.Session) entityManager3.getDelegate();
////		SessionFactoryImplementor sf = (SessionFactoryImplementor) session.getSessionFactory();
////		conn = sf.getConnectionProvider().getConnection();
//	}
//
//	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
//		if (null != rs) {
//			try {
//				rs.close();
//				rs = null;
//			} catch (SQLException e) {
//				logger.error(e.getMessage(), e);
//			}
//		}
//		if (null != ps) {
//			try {
//				ps.close();
//				ps = null;
//			} catch (SQLException e) {
//				logger.error(e.getMessage(), e);
//			}
//		}
//		if (null != conn) {
//			try {
//				conn.close();
//				conn = null;
//			} catch (SQLException e) {
//				logger.error(e.getMessage(), e);
//			}
//		}
//	}
//}
