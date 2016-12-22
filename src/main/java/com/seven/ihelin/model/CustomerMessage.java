package com.seven.ihelin.model;

/**
 * 多客服
 * PackageName:   com.seven.ihelin.model
 * ClassName:     CustomerMessage
 * Description:
 * Date           16/12/22
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
public class CustomerMessage {

    private String ToUserName;
    private String FromUserName;
    private long CreateTime;
    private String MsgType;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
