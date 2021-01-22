package com.markus.weixin.util;

public class SetIndustry {
	
	public static void setIndustry() {
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+AccessTokenUtil.getAccessToken()+"";
		String data = "{\r\n"
				+ "    \"industry_id1\":\"2\",\r\n"
				+ "    \"industry_id2\":\"16\"\r\n"
				+ "}";
		
		String result = HttpUtil.post(url, data);
		System.out.println(result);
	}
	
	
	public static void getIndustry() {
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", AccessTokenUtil.getAccessToken());
	
		String result = HttpUtil.get(url);
		System.out.println(result);
	}

}
