package com.kaidin.biz.common.constant;

/**
 * 登陆用户的状态信息，对应到数据库表字段
 * @author xiaobin
 * @date 2020-09-06 22:32
 */
public enum UserStatusEnum {
    INIT("INIT", "初始状态"),
    ENABLE("ENABLE", "正常状态"),
    LOCKED("LOCKED", "锁定状态");


    /** 状态 */
    private String code;
    /** 描述 */
    private String desc;

    private UserStatusEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static UserStatusEnum codeOf(String code) {
        for (UserStatusEnum tmp : UserStatusEnum.values()) {
            if (tmp.code.equals(code)) {
                return tmp;
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
