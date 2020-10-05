package com.kaidin.gui.controller.interceptor;

import com.kaidin.biz.common.constant.CommonErrEnum;
import com.kaidin.common.util.exception.BaseException;
import com.kaidin.common.util.log.LoggerUtil;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageDataBuilder;
import com.kaidin.gui.controller.util.WebUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * 抓取返回值为PageData的异常，能放到PageData中
 *
 * @author xiaobin
 * @date 2020-09-09 22:23
 */
@Component
public class ControllerExceptionAop implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionAop.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (null == requestMapping) {
            // 没有注解的方法直接放过
            return invocation.proceed();
        }
        boolean returnPageData = PageData.class.isAssignableFrom(method.getReturnType());
        String logTag = method.getName();
        try {
            // 正常情况返回
            return invocation.proceed();
        } catch (BaseException e) {
            LoggerUtil.warn(LOGGER, e, "{0} warn orgReq={1}", logTag, invocation.getArguments());
            if (returnPageData) {
                // 如果返回PageData就自动包装
                return PageDataBuilder.error(e);
            }
            return WebUtil.buildErrView(e);
        } catch (Throwable e) {
            LoggerUtil.error(LOGGER, e, "{0} err orgReq={1}", logTag, invocation.getArguments());
            if (returnPageData) {
                // 如果返回PageData就自动包装
                return PageDataBuilder.error(CommonErrEnum.SYSTEM_ERROR);
            }
            return WebUtil.buildErrView(CommonErrEnum.SYSTEM_ERROR);
        }
    }
}
