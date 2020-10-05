package com.kaidin.biz.service;

/**
 * 校验码相关服务
 * @author xiaobin
 * @date 2020-09-04 13:58
 */
public interface ICaptchaService {
    /* 验证码在session中的key */
    String CAPTCHA = "captcha";

    /**
     * 判断上传的验证码是否正常，会从req的session中获取暂存的code值类比较
     *
     * @param code
     * @return
     */
    boolean matchCaptcha(String code);

    /**
     * 判断上传的验证码是否正常，不是用sesson存储code场景使用
     * 服务端下发验证码、时间戳和散列值给服务端
     * 服务端可以先做一次前置校验，通过之后再上传到服务端，由服务端来兜底验证是否正常
     * @param code
     * @param times
     * @param hashCode
     * @return
     */
    boolean matchCaptcha(String code, long times, String hashCode);
}
