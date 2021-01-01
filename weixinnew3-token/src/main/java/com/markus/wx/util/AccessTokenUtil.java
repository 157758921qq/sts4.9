package com.markus.wx.util;

import com.markus.wx.config.WXConfig;
import com.markus.wx.token.entity.AccessToken;

import net.sf.json.JSONObject;

public class AccessTokenUtil {

	public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?"
			+ "grant_type=client_credential&appid=" + WXConfig.APPID + "&secret=" + WXConfig.APPSECRET + "";

	public static AccessToken at;


	//获得AccessToken
	public static String getAccessToken() {
		if (at == null || at.isExpired())
			getToken();
		return at.getAccessToken();
	}

	
	private static void getToken() {
		String tokenStr = Util.get(GET_TOKEN_URL);
		// String封装成json对象
		JSONObject jsonObject = JSONObject.fromObject(tokenStr);
		String token = jsonObject.getString("access_token");
		String expiressIn = jsonObject.getString("expires_in");
		at = new AccessToken(token, expiressIn);
	}
	
}
