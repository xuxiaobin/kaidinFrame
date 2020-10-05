package com.kaidin.gui.controller.model;

/**
 * @author xiaobin
 * @date 2020-09-29 22:41
 */
public class CaptchaModel {
    /** 验证码图片的base64信息 */
    private String imageBase64;
    /** 验证码的散列值，默认md5 */
    private String hashValue;

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }
}
