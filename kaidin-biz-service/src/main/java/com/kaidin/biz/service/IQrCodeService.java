package com.kaidin.biz.service;

/**
 * 二维码相关的服务
 * @author xiaobin
 * @date 2020-10-04 15:55
 */
public interface IQrCodeService {

    /**
     * 初始化二维码
     * uri可以为空
     * @param uri
     * @return 保存的二维码
     */
    String initActiveCode(String uri);

    /**
     * 重定向二维码
     * qrCode不能为空，uri不能为空
     * @param qrCode
     * @param uri
     * @return
     */
    boolean redirectCode(String qrCode, String uri);

    /**
     * 启用二维码
     * qrCode不能为空，uri为空表示不修改uri
     * @param qrCode
     * @param uri
     * @return
     */
    boolean enableCode(String qrCode, String uri);

    /**
     * 失效二维码
     * qrCode不能为空
     * @param qrCode
     * @return
     */
    boolean disableCode(String qrCode);

    /**
     * 解析二维码，把短码解析为目标对象
     *
     * @param qrCode
     * @return
     */
    String decode(String qrCode);
}
