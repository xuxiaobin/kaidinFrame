package com.kaidin.appframe.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProxyFactory {

	private transient static Log                log             = LogFactory.getLog(ProxyFactory.class);
	// 优化newProxyInstance
	private static final Class[]                CONSTRUCT_PARAM = { InvocationHandler.class };
	private static final HashMap<String, Class> CLAZZ_CACHE     = new HashMap<>();

	/*
	 * 普通的代理拦截器类
	 */
	public static Object getCommonProxyInstance(Class interfaceClassName, ProxyInvocationHandler handler) throws Exception {
		Object tmpClass = ProxyFactory.getProxyObject(interfaceClassName.getClassLoader(), new Class[] { interfaceClassName },
		        handler);
		return tmpClass;
	}

	/**
	 * 
	 * @param loader
	 *            ClassLoader
	 * @param interfaces
	 *            Class[]
	 * @param h
	 *            InvocationHandler
	 * @return Object
	 */
	public static final Object getProxyObject(ClassLoader loader, Class[] interfaces, InvocationHandler h) {
		String key = interfaces[0].getName();
		Class clazz = (Class) CLAZZ_CACHE.get(key);
		if (clazz == null) {
			synchronized (CLAZZ_CACHE) {
				if (!CLAZZ_CACHE.containsKey(key)) {
					Class c = Proxy.getProxyClass(loader, interfaces);
					CLAZZ_CACHE.put(key, c);
				}
				clazz = (Class) CLAZZ_CACHE.get(key);
			}
		}

		try {
			Constructor cons = clazz.getConstructor(CONSTRUCT_PARAM);
			return (Object) cons.newInstance(new Object[] { h });
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			throw new InternalError(e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public static Object ExecuteMethodByName(Object obj, String methodName, Class objClass) {
		try {
			Method m1 = objClass.getDeclaredMethod(methodName, (Class[]) null);
			return m1.invoke(obj, (Object[]) null);
		} catch (Exception ex) {
			log.error("Execute a method name:'" + methodName + "' error,details:" + ex.getMessage());
		}
		return null;

	}

	public static Object ExecuteMethodByName(Object obj, String methodName, Class objClass, Class[] parameterTypes, Object[] args) {
		try {
			Method m1 = objClass.getDeclaredMethod(methodName, parameterTypes);
			return m1.invoke(obj, args);
		} catch (Exception ex) {
			log.error("Execute a method name:'" + methodName + "' error,details:" + ex.getMessage());
		}
		return null;

	}
}