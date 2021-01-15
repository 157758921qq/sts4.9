package com.markus.wx.util;



import org.springframework.stereotype.Controller;


import com.markus.wx.token.entity.RefreshAccessToken;


import net.sf.json.JSONObject;
/**
 *  	刷新refresh_token
 * @author Administrator
 *
 */
@Controller
public class WebAuthAccessTokenUtil {

	/**
		"access_token":"41_o42gQczC-6xTX2QbedEaVnG_wNnHB0Poj0L4wv4IQwtkjvR4l36elyLsRX5uH-P3gw1lRGjzptNqsdfdQ6oCPjtD7Eh5QDpkpgkgTiNomJI",
		"expires_in":7200,
		"refresh_token":"41_mEWovxSGKgPMvyfaexnlbP9tv186cnvOuh8rKapPb3wk25D-K6kWKs60QfDrR0E7iOAAf0NsEHXJhezYTzk4KZqe4U0CkdSBAoyCM36DaWo",
		"openid":"ot4eH6Iaf9lmMlDhQDy_BDFrgE98",
		"scope":"snsapi_userinfo"}

	 */
	
	public static String refreshAccessTokenStr;
	
	//存储refre_token
	public static RefreshAccessToken refreshAccessToken;

	//获得AccessToken
	public static String getRefreshAccessToken() {
		if (refreshAccessToken == null || refreshAccessToken.isExpired())
			getRefreshToken();
		return refreshAccessToken.getRefreshaccessToken();
	}

	
	/**
	 *  	得到当前的refresh_token
	 */
	private static void getRefreshToken() {
		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=wx98b0a5b92b436370&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

		url = url.replace("REFRESH_TOKEN", refreshAccessTokenStr);
		String tokenStr = Util.get(url);
		System.out.println("refreshtokenstr = " +tokenStr); 
		
		// String封装成json对象
		JSONObject jsonObject = JSONObject.fromObject(tokenStr);
		String refresh_token = jsonObject.getString("refresh_token");
		String expiressIn = jsonObject.getString("expires_in");
		refreshAccessToken = new RefreshAccessToken(refresh_token, expiressIn);
	}
	
}
