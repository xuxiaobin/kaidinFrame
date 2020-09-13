package com.kaidin.appframe.service.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来处理异常
 *
 * @author xiaobin
 * @date 2020-09-09 22:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageDataLog {

    String value() default "";

    /**
     * 是否打印全量日志
     * @return
     */
    boolean fullLog() default false;
}
