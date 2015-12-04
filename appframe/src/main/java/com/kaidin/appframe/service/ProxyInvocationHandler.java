package com.kaidin.appframe.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.kaidin.appframe.service.interceptor.interfaces.AfterMethodInterceptor;
import com.kaidin.appframe.service.interceptor.interfaces.AroundMethodInterceptor;
import com.kaidin.appframe.service.interceptor.interfaces.BeforeMethodInterceptor;

/**
 * <p>
 * Title: 代理拦截处理实现类
 * </p>
 * <p>
 * Description: 执行拦截器方法，包括方法前、后异常处理；反射真实方法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: AI(NanJing)
 * </p>
 */
public final class ProxyInvocationHandler implements InvocationHandler {
	private transient static Log log = LogFactory
			.getLog(ProxyInvocationHandler.class);

	private Object _obj = null;

	private Class[] _interceptors_class = null;

	private ServiceInvokeTypeEnum invokeType = null;

	public ProxyInvocationHandler(Object _obj, Class[] _interceptors_class,
			ServiceInvokeTypeEnum invokeType) {
		this._obj = _obj;
		this._interceptors_class = _interceptors_class;
		this.invokeType = invokeType;
	}

	/**
	 * invoke 代理反射方法
	 * 
	 * @param object
	 *            Object
	 * @param method
	 *            Method
	 * @param objectArray
	 *            Object[]
	 * @throws Throwable
	 * @return Object
	 */
	public Object invoke(Object object, Method method, Object[] objectArray)
			throws Throwable {

		long startTime = System.currentTimeMillis();

		// 实例化拦截器
		Object[] _interceptors = null;
		if (_interceptors_class != null) {
			_interceptors = new Object[_interceptors_class.length];
			for (int i = 0; i < _interceptors_class.length; i++) {
				_interceptors[i] = _interceptors_class[i].newInstance();
			}
			// 方法之前的拦截器
			// 环绕拦截器
			boolean[] isBeforeSuccess = new boolean[_interceptors.length];
			try {
				for (int i = 0; i < _interceptors.length; i++) {
					if (_interceptors[i] instanceof BeforeMethodInterceptor) {
						((BeforeMethodInterceptor) _interceptors[i])
								.interceptor(this._obj, method.getName(),
										objectArray);
						isBeforeSuccess[i] = true;
					} else if (_interceptors[i] instanceof AroundMethodInterceptor) {
						((AroundMethodInterceptor) _interceptors[i])
								.beforeInterceptor(this._obj, method.getName(),
										objectArray);
						isBeforeSuccess[i] = true;
					}
				}
			} catch (Throwable ex) {
				log
						.fatal(
								"The interceptor triggered before method invocation got an exception.",
								ex);// 在方法调用前面的拦截器工作失败
				// 将已经工作过的拦截器进行反向处理
				for (int i = _interceptors.length - 1; i >= 0; i--) {
					if (isBeforeSuccess[i] == true) {
						// 仅仅只有环绕拦截器需要反向处理
						if (_interceptors[i] instanceof AroundMethodInterceptor) {
							((AroundMethodInterceptor) _interceptors[i])
									.exceptionInterceptor(this._obj, method
											.getName(), objectArray, ex);
						}
					}
				}
				//Shixs修改于2011-07-04，异常统一抛至exceptionInterceptor（），由其决定是否抛出
				//throw ex;
			}
		}

		// 真实方法调用
		Object rtn = null;
		try {
			if (invokeType == ServiceInvokeTypeEnum.INVOKE_REMOTE) {// 针对server,stub端EJB做增强
//				rtn = getEJBObject(method, serviceType, objectArray);
			} else {
				rtn = method.invoke(this._obj, objectArray);
			}
		} catch (Throwable ex) {
			try {
				if (_interceptors != null)
					for (int i = _interceptors.length - 1; i >= 0; i--) {
						if (_interceptors[i] instanceof AroundMethodInterceptor) {
							((AroundMethodInterceptor) _interceptors[i])
									.exceptionInterceptor(this._obj, method
											.getName(), objectArray, ex);
						}
					}
			} catch (Throwable ex2) {
				log
						.fatal(
								"The exception interceptor occured an exception and failed to work well.",
								ex2);// 拦截器异常拦截方法工作失败异常
				// 对异常进行拆除成标准的异常
				Throwable root = null;
				try {
					root = ExceptionUtils.getRootCause(ex2);
				} catch (Throwable ex3) {
					log
							.error(
									"ExceptionUtils.getRootCause occured an exception,and it will be thrown out.",
									ex3);// ExceptionUtils.getRootCause出现异常,继续扔出原始异常
					throw ex3;
				}
				if (root != null) {
					log.error("Method exception:", root);// 方法异常
					throw root;
				} else {
					log.error("Method exception:", ex2);
					throw ex2;
				}
			}
		}

		// 方法之后的拦截器
		try {
			if (_interceptors != null) {
				for (int i = _interceptors.length - 1; i >= 0; i--) {
					if (_interceptors[i] instanceof AfterMethodInterceptor) {
						((AfterMethodInterceptor) _interceptors[i])
								.interceptor(this._obj, method.getName(),
										objectArray);
					} else if (_interceptors[i] instanceof AroundMethodInterceptor) {
						((AroundMethodInterceptor) _interceptors[i])
								.afterInterceptor(this._obj, method.getName(),
										objectArray);
					}
				}
			}
		} catch (Throwable ex) {
			log
					.fatal(
							"The interceptor triggered after method invocation got an exception ",
							ex);// 在方法调用后面的拦截器工作失败
		}

		return rtn;
	}

	public void setProxyObject(Object obj) {
		this._obj = obj;
	}

	private Object getEJBObject(Method method, String serviceType,
			Object[] objectArray) {
		Object rtn = null;
//		try {
//			rtn = ProxyFactory.ExecuteMethodByName(this._obj, method.getName(),
//					getSRVByInterface(AIClassLoader.getInstance().loadClass(
//							_service_module_define.getInterfaceName())),
//					parameterTypes, objectArrayNew);
//		} catch (Exception e) {
//			log.error("Execute EJB method '" + method.getName() + "' error:"
//					+ e.getMessage());
//		}
		return rtn;
	}
}
