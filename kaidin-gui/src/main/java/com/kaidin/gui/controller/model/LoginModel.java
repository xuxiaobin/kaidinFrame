package com.kaidin.gui.controller.model;

/**
 * @author xiaobin
 * @date 2020-09-26 20:40
 */
public class LoginModel {
    /** 登陆用户名 */
    private String loginName;
    /** 登陆密码 */
    private String password;
    /** 验证码 */
    private String captchaCode;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }
}
