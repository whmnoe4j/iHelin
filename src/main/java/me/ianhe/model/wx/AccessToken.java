package me.ianhe.model.wx;

import java.util.Date;

public class AccessToken {
	private String token;

	private Date genTime;

	private Date expiresTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Date getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}

	// 距离过期毫秒数
	public long getLeftValidTimeMillis() {
		return this.expiresTime.getTime() - System.currentTimeMillis();
	}
}