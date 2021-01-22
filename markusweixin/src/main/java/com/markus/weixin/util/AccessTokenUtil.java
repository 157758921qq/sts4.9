package com.markus.weixin.util;

import com.markus.weixin.entity.token.AccessToken;

import net.sf.json.JSONObject;

public class AccessTokenUtil {
	
	
	//获取AccessToken的url
	public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?"
			+ "grant_type=client_credential&appid=" + ConfigUtil.APPID + "&secret=" + ConfigUtil.APPSECRET + "";

	
	public static AccessToken at;


	//获得AccessToken
	public static String getAccessToken() {
		if (at == null || at.isExpired()) {
			getToken();
		}
		return at.getAccessToken();
	}

	
	private static void getToken() {
		String tokenStr = HttpUtil.get(GET_TOKEN_URL);
		//System.out.println(tokenStr);
		// String封装成json对象，对象获取token和expiressIn，封装成对象
		JSONObject jsonObject = JSONObject.fromObject(tokenStr);
		String token = jsonObject.getString("access_token");
		String expiressIn = jsonObject.getString("expires_in");
		at = new AccessToken(token, expiressIn);
	}
	

}





















