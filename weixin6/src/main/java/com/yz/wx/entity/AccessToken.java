package com.yz.wx.entity;

public class AccessToken {

	public String accessToken;
	long expireTime;
	
	public AccessToken(String accessToken, String expireIn) {
		super();
		this.accessToken = accessToken;
		expireTime = System.currentTimeMillis()+Integer.parseInt(expireIn)*1000 ;
	}
	
	public boolean isExpired() {
		return System.currentTimeMillis()>expireTime;
	}
	
}
