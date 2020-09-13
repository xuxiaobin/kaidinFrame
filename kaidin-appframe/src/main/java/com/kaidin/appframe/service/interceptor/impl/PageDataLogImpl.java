package com.kaidin.appframe.service.interceptor.impl;

import com.kaidin.appframe.service.interceptor.PageDataLog;
import com.kaidin.common.util.DefaultUtil;
import com.kaidin.common.util.exception.BaseException;
import com.kaidin.common.util.log.LoggerUtil;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageDataBuilder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 抓取返回值为PageData的异常，能放到PageData中
 *
 * @author xiaobin
 * @date 2020-09-09 22:23
 */
public class PageDataLogImpl implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageDataLogImpl.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        PageDataLog pageDataLog = method.getAnnotation(PageDataLog.class);
        if (null == pageDataLog) {
            // 没有注解的方法直接放过
            return invocation.proceed();
        }
        boolean returnPageData = PageData.class.isAssignableFrom(method.getReturnType());

        String logTag = DefaultUtil.ifEmpty(pageDataLog.value(), method.getName());
        try {
            if (pageDataLog.fullLog()) {
                LoggerUtil.debug(LOGGER, "{0} orgReq={1}", logTag, invocation.getArguments());
            }
            // 正常情况返回
            return invocation.proceed();
        } catch (BaseException e) {
            LoggerUtil.warn(LOGGER, e, "{0} warn orgReq={1}", logTag, invocation.getArguments());
            if (returnPageData) {
                // 如果返回PageData就自动包装
                return PageDataBuilder.error(e);
            }
            throw e;
        } catch (Throwable e) {
            LoggerUtil.error(LOGGER, e, "{0} err orgReq={1}", logTag, invocation.getArguments());
            if (returnPageData) {
                // 如果返回PageData就自动包装
                return PageDataBuilder.error("SYSTEM_ERROR", "系统异常");
            }
            throw e;
        }
    }
}
