package me.ianhe.model.wx;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    @JsonProperty("media_id")
    private String mediaId;
    private Content content;
    @JsonProperty("update_time")
    private int updateTime;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }
}
