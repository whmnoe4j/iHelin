package me.ianhe.wechat.core;

import me.ianhe.wechat.beans.BaseMsg;

/**
 * 消息处理接口
 *
 * @author iHelin
 * @since 2017/12/11 14:06
 */
public interface MessageHandler {

    /**
     * 文本消息处理
     *
     * @param msg
     */
    void handleTextMsg(BaseMsg msg);

    /**
     * 处理图片消息
     *
     * @param msg
     */
    void handlePicMsg(BaseMsg msg);

    /**
     * 处理声音消息
     *
     * @param msg
     */
    void handleVoiceMsg(BaseMsg msg);

    /**
     * 处理小视频消息
     *
     * @param msg
     */
    void handleVideoMsg(BaseMsg msg);

    /**
     * 处理名片消息
     *
     * @param msg
     */
    void handleNameCardMsg(BaseMsg msg);

    /**
     * 处理系统消息
     *
     * @param msg
     */
    void handleSysMsg(BaseMsg msg);

    /**
     * 处理确认添加好友消息
     *
     * @param msg
     */
    void handleVerifyAddFriendMsg(BaseMsg msg);

    /**
     * 处理收到的文件消息
     *
     * @param msg
     */
    void handleMediaMsg(BaseMsg msg);

}
