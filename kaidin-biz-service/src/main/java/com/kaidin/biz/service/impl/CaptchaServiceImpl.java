package com.kaidin.biz.service.impl;

import com.kaidin.biz.service.ICaptchaService;
import com.kaidin.common.util.log.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 校验码相关服务
 * @author xiaobin
 * @date 2020-09-09 21:36
 */
@Service
public class CaptchaServiceImpl implements ICaptchaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaServiceImpl.class);
    /** 万能验证码 */
    private static String OMNIPOTENT_CODE = "kaidinMama";


    @Resource
    private HttpServletRequest request;

    @Override
    public boolean matchCaptcha(String code) {
        LoggerUtil.debug(LOGGER, "matchCaptcha orgReq={0}", code);
        if (null == code) {
            return false;
        }
        if (OMNIPOTENT_CODE.equals(code)) {
            // 万能验证码
            return true;
        }
        String sessionCaptcha = getCaptchaCode();
        LoggerUtil.debug(LOGGER, "sessionCaptcha={0}", sessionCaptcha);
        if (!code.equalsIgnoreCase(sessionCaptcha)) {
            return false;
        }
        // 匹配成功之后删除session中保存的验证码
        delCaptchaCode();

        return true;
    }



    @Override
    public boolean matchCaptcha(String code, long times, String hashCode) {

        return false;
    }

    /**
     * 从session中获取存储的验证码的值
     * @return
     */
    private String getCaptchaCode() {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        return (String) session.getAttribute(CAPTCHA);
    }

    /**
     * 从session中删除验证码的值
     * @return
     */
    private String delCaptchaCode() {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        String result = (String) session.getAttribute(CAPTCHA);
        session.removeAttribute(CAPTCHA);

        return result;
    }
}
