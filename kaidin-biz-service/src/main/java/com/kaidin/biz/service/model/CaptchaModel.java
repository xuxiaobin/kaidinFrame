package com.kaidin.biz.service.model;

import com.kaidin.common.util.ToString;

/**
 * 图片验证码验证模型
 * @author xiaobin
 * @date 2020-09-09 22:00
 */
public class CaptchaModel extends ToString {
    /** 验证码的值 */
    private String code;
    /** 时间戳 */
    private long times;
    /** 验证的散列值 */
    private String hashValue;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }
}
