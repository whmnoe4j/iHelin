package me.ianhe.model;

import java.io.Serializable;

public class MailModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String toAddress;
    private String toName;
    private String title;
    private String content;

    public MailModel(String toAddress, String toName, String title, String content) {
        this.toAddress = toAddress;
        this.toName = toName;
        this.title = title;
        this.content = content;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
