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
     * @return
     * @author https://github.com/yaphone
     * @date 2017年4月20日 上午12:15:00
     */
    void textMsgHandle(BaseMsg msg);

    /**
     * 处理图片消息
     *
     * @param msg
     * @return
     * @author https://github.com/yaphone
     * @date 2017年4月21日 下午11:07:06
     */
    void picMsgHandle(BaseMsg msg);

    /**
     * 处理声音消息
     *
     * @param msg
     * @return
     * @author https://github.com/yaphone
     * @date 2017年4月22日 上午12:09:44
     */
    void voiceMsgHandle(BaseMsg msg);

    /**
     * 处理小视频消息
     *
     * @param msg
     * @return
     * @author https://github.com/yaphone
     * @date 2017年4月23日 下午12:19:50
     */
    void videoMsgHandle(BaseMsg msg);

    /**
     * 处理名片消息
     *
     * @param msg
     * @return
     * @author https://github.com/yaphone
     * @date 2017年5月1日 上午12:50:50
     */
    void nameCardMsgHandle(BaseMsg msg);

    /**
     * 处理系统消息
     *
     * @param msg
     * @return
     * @author Relyn
     * @date 2017年6月21日17:43:51
     */
    void sysMsgHandle(BaseMsg msg);

    /**
     * 处理确认添加好友消息
     *
     * @param msg
     * @return
     * @date 2017年6月28日 下午10:15:30
     */
    void verifyAddFriendMsgHandle(BaseMsg msg);

    /**
     * 处理收到的文件消息
     *
     * @param msg
     * @return
     * @date 2017年7月21日 下午11:59:14
     */
    void mediaMsgHandle(BaseMsg msg);

}
