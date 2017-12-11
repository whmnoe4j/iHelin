package me.ianhe.wechat.enums.parameters;

/**
 * UUID
 *
 * @author iHelin
 * @since 2017/12/11 14:06
 */
public enum UUIDParaEnum {

    APP_ID("appid", "wx782c26e4c19acffb"),
    FUN("fun", "new"),
    LANG("lang", "zh_CN"),
    TIME_STAMP("TIME_STAMP", "时间戳");

    private String para;
    private String value;

    UUIDParaEnum(String param, String value) {
        this.para = param;
        this.value = value;
    }

    public String para() {
        return para;
    }

    public String value() {
        return value;
    }
}
