package me.ianhe.wechat.enums.parameters;

/**
 * 登录
 *
 * @author iHelin
 * @since 2017/12/11 13:58
 */
public enum LoginParaEnum {

    LOGIN_ICON("loginicon", "true"),
    UUID("uuid", ""),
    TIP("tip", "0"),
    R("r", ""),
    TIME_STAMP("TIME_STAMP", "");

    private String param;
    private String value;

    LoginParaEnum(String param, String value) {
        this.param = param;
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public String value() {
        return value;
    }
}
