package me.ianhe.wechat.enums.parameters;

/**
 * 状态通知
 *
 * @author iHelin
 * @since 2017/12/11 14:06
 */
public enum StatusNotifyParaEnum {

    CODE("Code", "3"),
    FROM_USERNAME("FromUserName", ""),
    TO_USERNAME("ToUserName", ""),
    /**
     * 时间戳
     */
    CLIENT_MSG_ID("ClientMsgId", "");

    private String para;
    private String value;

    StatusNotifyParaEnum(String para, String value) {
        this.para = para;
        this.value = value;
    }

    public String para() {
        return para;
    }

    public String value() {
        return value;
    }
}
