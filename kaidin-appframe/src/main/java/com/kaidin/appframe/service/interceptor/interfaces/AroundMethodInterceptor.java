package com.kaidin.appframe.service.interceptor.interfaces;


 
/**
 * 环绕拦截
 * @author shenli
 * @version 5.5
 */

public interface AroundMethodInterceptor {

  /**
   * 拦截    ！！加ServiceModuleDefine.ServicItemDefine的目的是在transaction拦截器中获取事务类型。
   * @param obj Object
   * @param methodName String
   * @param objectArray Object[]
   * @throws Exception
   */
  public void beforeInterceptor(Object obj,String methodName,Object[] objectArray) throws Exception;

  /**
   * 拦截
   * @param obj Object
   * @param methodName String
   * @param objectArray Object[]
   * @throws Exception
   */
  public void afterInterceptor(Object obj,String methodName,Object[] objectArray) throws Exception;

  /**
   * 异常拦截
   * @param obj Object
   * @param methodName String
   * @param objectArray Object[]
   * @throws Exception
   */
  public void exceptionInterceptor(Object obj,String methodName,Object[] objectArray, Throwable ex) throws Exception;
}
