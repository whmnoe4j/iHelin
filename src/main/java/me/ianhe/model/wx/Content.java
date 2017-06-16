package me.ianhe.model.wx;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Content {
    @JsonProperty("news_item")
    private List<NewsItem> newsItem;
    @JsonProperty("create_time")
    private int createTime;
    @JsonProperty("update_time")
    private int updateTime;

    public List<NewsItem> getNewsItem() {
        return newsItem;
    }

    public void setNewsItem(List<NewsItem> newsItem) {
        this.newsItem = newsItem;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }
}
