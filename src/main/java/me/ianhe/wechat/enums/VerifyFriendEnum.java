package me.ianhe.wechat.enums;

/**
 * 确认添加好友Enum
 *
 * @author iHelin
 * @since 2017/12/11 14:29
 */
public enum VerifyFriendEnum {

    ADD(2, "添加"),
    ACCEPT(3, "接受");

    private int code;
    private String desc;

    VerifyFriendEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

}
