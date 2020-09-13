package com.kaidin.biz.common.constant;

/**
 * 登陆用户的状态信息，对应到数据库表字段
 * @author xiaobin
 * @date 2020-09-06 22:32
 */
public enum UserStatusEnum {
    INIT("INIT", "初始化"),
    LOCKED("LOCKED", "用户被锁定"),
    NORMAL("NORMAL", "用户状态正常");

    /** 状态 */
    private String code;
    /** 描述 */
    private String desc;

    private UserStatusEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static UserStatusEnum codeOf(String code) {
        for (UserStatusEnum statusEnum : UserStatusEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
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
