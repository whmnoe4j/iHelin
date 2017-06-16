package me.ianhe.model.wx;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.riversoft.weixin.common.message.XmlMessageHeader;

/**
 * @author iHelin
 * @since 2017/6/16 22:45
 */
public class LocationMessage extends XmlMessageHeader {

    @JsonProperty("Location_X")
    private String locationX;// 纬度
    @JsonProperty("Location_Y")
    private String locationY;// 经度
    @JsonProperty("scale")
    private Integer Scale;// 地图缩放大小
    @JsonProperty("label")
    private String Label;// 地理位置信息
    @JsonProperty("MsgId")
    private long msgId;

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public Integer getScale() {
        return Scale;
    }

    public void setScale(Integer scale) {
        Scale = scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }
}
