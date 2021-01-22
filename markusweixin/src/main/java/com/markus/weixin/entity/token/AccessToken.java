package com.markus.weixin.entity.token;

public class AccessToken {

	private String accessToken;  
	private long expireTime;
	
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	//构造方法
	//当前时间 + 加上2小时的过期时间
	public AccessToken(String accessToken, String expireIn) {
		super();
		this.accessToken = accessToken;
		expireTime = System.currentTimeMillis()+Integer.parseInt(expireIn)*1000 ;
	}
	
	
	//判断AccessToken是否超时
	public boolean isExpired() {
		return System.currentTimeMillis() > expireTime;
	}
	
}
