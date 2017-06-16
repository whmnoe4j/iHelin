package me.ianhe.model.wx;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 多客服
 * PackageName:   com.seven.ianhe.model
 * ClassName:     CustomerMessage
 * Description:
 * Date           16/12/22
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
public class CustomerMessage {

    @JsonProperty("ToUserName")
    private String toUserName;
    @JsonProperty("FromUserName")
    private String fromUserName;
    @JsonProperty("CreateTime")
    private long createTime;
    @JsonProperty("MsgType")
    private String msgType;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
