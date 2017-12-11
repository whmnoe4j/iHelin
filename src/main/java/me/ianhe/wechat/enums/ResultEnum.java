package me.ianhe.wechat.enums;

/**
 * 返回结构枚举类
 *
 * @author iHelin
 * @since 2017/12/11 14:29
 */
public enum ResultEnum {

    SUCCESS("200", "成功"),
    WAIT_CONFIRM("201", "请在手机上点击确认"),
    WAIT_SCAN("408", "请扫描二维码");

    private String code;
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

}
