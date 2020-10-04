package com.kaidin.biz.common.constant;

import com.kaidin.common.util.exception.IExceptionCode;

/**
 * 通用异常状态码
 * @author xiaobin
 * @date 2020-09-06 22:03
 */
public enum CommonErrEnum implements IExceptionCode {

    PARAMETER_ILLEGAL("PARAMETER_ILLEGAL", "参数异常"),
    BIZ_ERROR("BIZ_ERROR", "业务异常"),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常");

    /** 异常编码 */
    private String errCode;
    /** 异常描述 */
    private String errMsg;

    private CommonErrEnum(String errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public static CommonErrEnum codeOf(String errCode) {
        for (CommonErrEnum tmp : CommonErrEnum.values()) {
            if (tmp.errCode.equals(errCode)) {
                return tmp;
            }
        }
        return null;
    }

    @Override
    public String getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }
}
