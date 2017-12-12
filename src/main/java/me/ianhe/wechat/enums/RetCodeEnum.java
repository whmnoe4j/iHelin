package me.ianhe.wechat.enums;

/**
 * RetCodeEnum
 *
 * @author iHelin
 * @since 2017/12/11 12:14
 */
public enum RetCodeEnum {

    NORMAL("0", "普通"),
    LOGIN_OUT("1102", "退出"),
    LOGIN_OTHERWHERE("1101", "其它地方登录"),
    MOBILE_LOGIN_OUT("1102", "移动端退出"),
    UNKOWN("9999", "未知返回");

    private String code;
    private String type;

    RetCodeEnum(String code, String type) {
        this.code = code;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

}
