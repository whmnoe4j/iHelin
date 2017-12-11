package me.ianhe.wechat.beans;

import java.io.Serializable;

/**
 * AppInfo
 *
 * @author iHelin
 * @since 2017/12/11 14:27
 */
public class AppInfo implements Serializable {

    private static final long serialVersionUID = -2182240439236458011L;

    private int type;
    private String appId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

}
