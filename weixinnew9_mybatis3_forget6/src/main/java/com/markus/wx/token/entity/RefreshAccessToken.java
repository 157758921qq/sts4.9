package com.markus.wx.token.entity;

public class RefreshAccessToken {

	private String refreshAccessToken;
	private long expireTime;

	
	public String getRefreshaccessToken() {
		return refreshAccessToken;
	}


	public void setRefreshaccessToken(String refreshaccessToken) {
		this.refreshAccessToken = refreshaccessToken;
	}


	public long getExpireTime() {
		return expireTime;
	}


	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}


	public RefreshAccessToken(String refreshaccessToken, String expireIn) {
		super();
		this.refreshAccessToken = refreshaccessToken;
		expireTime = System.currentTimeMillis()+Integer.parseInt(expireIn)*1000 ;
	}
	
	
	//是否超时
	public boolean isExpired() {
		return System.currentTimeMillis()>expireTime;
	}
	
}
