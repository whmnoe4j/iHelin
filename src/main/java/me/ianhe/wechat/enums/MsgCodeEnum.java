package me.ianhe.wechat.enums;

/**
 * 微信发过来的消息类型
 *
 * @author iHelin
 * @since 2017/12/11 14:28
 */
public enum MsgCodeEnum {

    /**
     * 文本消息类型
     */
    MSGTYPE_TEXT(1),
    /**
     * 图片消息
     */
    MSGTYPE_IMAGE(3),
    /**
     * 语音消息
     */
    MSGTYPE_VOICE(34),
    /**
     * 好友请求
     */
    MSGTYPE_VERIFYMSG(37),
    MSGTYPE_POSSIBLEFRIEND_MSG(40),
    /**
     * 共享名片
     */
    MSGTYPE_SHARECARD(42),
    /**
     * 小视频消息
     */
    MSGTYPE_VIDEO(43),
    /**
     * 表情消息
     */
    MSGTYPE_EMOTICON(47),
    MSGTYPE_LOCATION(48),
    /**
     * 多媒体消息
     */
    MSGTYPE_MEDIA(49),
    MSGTYPE_VOIPMSG(50),
    /**
     * 状态通知消息
     */
    MSGTYPE_STATUSNOTIFY(51),
    MSGTYPE_VOIPNOTIFY(52),
    MSGTYPE_VOIPINVITE(53),
    /**
     * 短视频消息
     */
    MSGTYPE_MICROVIDEO(62),
    /**
     * 系统通知消息
     */
    MSGTYPE_SYSNOTICE(9999),
    /**
     * 系统消息
     */
    MSGTYPE_SYS(10000),
    MSGTYPE_RECALLED(10002);

    private Integer code;

    MsgCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
