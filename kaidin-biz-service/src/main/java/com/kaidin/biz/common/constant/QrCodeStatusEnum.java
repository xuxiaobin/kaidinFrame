package com.kaidin.biz.common.constant;

/**
 * 二维码状态，对应到数据库表字段
 * @author xiaobin
 * @date 2020-10-04 16:08
 */
public enum QrCodeStatusEnum {
    INIT("INIT", "初始转态中"),
    ENABLE("ENABLE", "生效状态中"),
    DISABLE("DISABLE", "失效状态中");

    /** 状态 */
    private String code;
    /** 描述 */
    private String desc;

    private QrCodeStatusEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static QrCodeStatusEnum codeOf(String code) {
        for (QrCodeStatusEnum statusEnum : QrCodeStatusEnum.values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
