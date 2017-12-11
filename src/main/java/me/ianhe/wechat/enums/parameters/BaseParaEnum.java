package me.ianhe.wechat.enums.parameters;

/**
 * 基本请求参数
 * 1. webWxInit      初始化
 * 2. wxStatusNotify 微信状态通知
 *
 * @author iHelin
 * @since 2017/12/11 10:44
 */
public enum BaseParaEnum {

    Uin("Uin", "wxuin"),
    Sid("Sid", "wxsid"),
    Skey("Skey", "skey"),
    DeviceID("DeviceID", "pass_ticket");

    private String param;
    private String value;

    BaseParaEnum(String param, String value) {
        this.param = param;
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public Object getValue() {
        return value;
    }

}
