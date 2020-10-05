package com.kaidin.gui.controller.util;

import com.kaidin.common.util.StringUtil;
import com.kaidin.common.util.exception.BaseException;
import com.kaidin.common.util.exception.IExceptionCode;
import com.kaidin.gui.common.constant.GuiConstType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 抽象工具类
 *
 * @author xiaobin
 * @date 2020-09-28 22:27
 */
public abstract class WebUtil {
    private static final int SESSION_OUT_TIMES = 30 * 60;
    /** 错误页面 */
    private static final String ERR_PAGE = "common/error";
    private static final String ERR_CODE = "errCode";
    private static final String ERR_MSG = "errMsg";

    /**
     * 获取httpServletRequest
     * @return
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    /**
     * 获取session
     * @return
     */
    public static HttpSession getSession() {
        HttpSession result = getRequest().getSession(false);
        if (null != result) {
            result.setMaxInactiveInterval(SESSION_OUT_TIMES);
        }

        return result;
    }

    /**
     * 获取session
     * @return
     */
    public static HttpSession getSession(boolean isCreate) {
        HttpSession result = getRequest().getSession(isCreate);
        result.setMaxInactiveInterval(SESSION_OUT_TIMES);

        return result;
    }

    /**
     * 从session中获取指定的对象
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getSessionAttribute(String key) {
        HttpSession session = getSession();
        if (null == session) {
            return null;
        }

        return (T) session.getAttribute(key);
    }

    /**
     * 从session中删除指定的对象
     * @param key
     * @return
     */
    public static String removeSessionAttribute(String key) {
        HttpSession session = getSession();
        if (null == session) {
            return null;
        }
        String result = (String) session.getAttribute(key);
        session.removeAttribute(key);

        return result;
    }

    /**
     * 返回异常页面
     * @param e
     * @return
     */
    public static ModelAndView buildErrView(IExceptionCode e) {
        return buildErrView(e, null);
    }

    /**
     * 返回异常页面
     * @param e
     * @param errMsg
     * @return
     */
    public static ModelAndView buildErrView(IExceptionCode e, String errMsg) {
        // 返回到异常页面
        ModelAndView result = new ModelAndView(ERR_PAGE);
        result.addObject(ERR_CODE, e.getErrCode());
        if (StringUtil.isBlank(errMsg)) {
            errMsg = e.getErrMsg();
        }
        result.addObject(ERR_MSG, errMsg);

        return result;
    }
}
