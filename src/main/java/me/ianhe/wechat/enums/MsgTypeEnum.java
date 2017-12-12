package me.ianhe.wechat.enums;


/**
 * 经过转换后的消息类型
 *
 * @author iHelin
 * @since 2017/12/11 14:28
 */
public enum MsgTypeEnum {

    /**
     * 文本消息
     */
    TEXT("Text"),
    /**
     * 图片消息
     */
    PIC("Pic"),
    /**
     * 语音消息
     */
    VOICE("Voice"),
    /**
     * 小视频消息
     */
    VIEDO("Viedo"),
    /**
     * 名片消息
     */
    NAMECARD("NameCard"),
    /**
     * 系统消息
     */
    SYS("Sys"),
    /**
     * 添加好友
     */
    VERIFYMSG("VerifyMsg"),
    /**
     * 文件消息
     */
    MEDIA("app");

    private String type;

    MsgTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
