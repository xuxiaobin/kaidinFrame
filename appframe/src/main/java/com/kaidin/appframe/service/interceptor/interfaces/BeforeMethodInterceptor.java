package com.kaidin.appframe.service.interceptor.interfaces;


/**
 * 在方法调用之前
 * @author shenli
 * @version 5.5
 */

public interface BeforeMethodInterceptor {

  /**
   * 拦截
   * @param obj Object
   * @param methodName String
   * @param objectArray Object[]
   * @throws Exception
   */
  public void interceptor(Object obj,String methodName,Object[] objectArray) throws Exception;

}
