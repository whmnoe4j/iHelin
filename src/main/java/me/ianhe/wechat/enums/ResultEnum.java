package me.ianhe.wechat.enums;

/**
 * 返回结构枚举类
 *
 * @author iHelin
 * @since 2017/12/11 14:29
 */
public enum ResultEnum {

    /**
     * 成功
     */
    SUCCESS("200"),
    /**
     * 请在手机上点击确认
     */
    WAIT_CONFIRM("201"),
    /**
     * 请扫描二维码
     */
    WAIT_SCAN("408");

    private String code;

    ResultEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
