package com.markus.weixin.util;

import java.io.File;

import net.sf.json.JSONObject;


public class MaterialsUtil {

	//上传永久图片，返回永久media_id
	public static void upForeverImg() {
		String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE ";
		url = url.replace("ACCESS_TOKEN", AccessTokenUtil.getAccessToken()).replace("TYPE", "image");
		
		String file = "D:"+File.separator+"img"+File.separator+"hardstudy.jpg";   //图片所在目录
		String result = HttpUtil.upload(file, url);
		System.out.println("永久图片上传"+result);
		JSONObject jsonObj = JSONObject.fromObject(result);
		String mediaIdStr = jsonObj.getString("media_id");
		String urlStr = jsonObj.getString("url");
		System.out.println(mediaIdStr);
		System.out.println(urlStr);
	}
	
	
	//上传永久图文消息，返回永久media_id
	public static void upForeverNews() {
		String url = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", AccessTokenUtil.getAccessToken());
		
		String data = "{\r\n"
				+ "    \"articles\":\r\n"
				+ "     [\r\n"
				+ "     	{\r\n"
				+ "	     \"title\": \"1代码就是我的人生\",\r\n"
				+ "	    \"thumb_media_id\": \"6bjdeUIo8prvpGCwgeqILu3lXCsQPSNNTDSa_rlKbxY\",\r\n"
				+ "	    \"author\": \"Markus\",\r\n"
				+ "	    \"digest\": \"1爱好是最好的老师，我对代码的感情源自内心对代码的热爱，没有什么可以阻止\",\r\n"
				+ "	    \"show_cover_pic\": 1,\r\n"
				+ "	    \"content\": \"1图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS,涉及图片url必须来源 \"上传图文消息内的图片获取URL\"接口获取。外部图片url将被过滤。\",\r\n"
				+ "	    \"content_source_url\": \"https://mp.weixin.qq.com/s/tGWpdAoFXg-D9zHDoENZWA\",\r\n"
				+ "	    \"need_open_comment\":1,\r\n"
				+ "	    \"only_fans_can_comment\":1\r\n"
				+ "		},\r\n"
				+ "		\r\n"
				+ "		\"articles\":\r\n"
				+ "     [\r\n"
				+ "     	{\r\n"
				+ "	     \"title\": \"2代码就是我的人生\",\r\n"
				+ "	    \"thumb_media_id\": \"6bjdeUIo8prvpGCwgeqILu3lXCsQPSNNTDSa_rlKbxY\",\r\n"
				+ "	    \"author\": \"Markus\",\r\n"
				+ "	    \"digest\": \"2爱好是最好的老师，我对代码的感情源自内心对代码的热爱，没有什么可以阻止\",\r\n"
				+ "	    \"show_cover_pic\": 1,\r\n"
				+ "	    \"content\": \"2图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS,涉及图片url必须来源 \"上传图文消息内的图片获取URL\"接口获取。外部图片url将被过滤。\",\r\n"
				+ "	    \"content_source_url\": \"https://mp.weixin.qq.com/s/tGWpdAoFXg-D9zHDoENZWA\",\r\n"
				+ "	    \"need_open_comment\":1,\r\n"
				+ "	    \"only_fans_can_comment\":1\r\n"
				+ "		},\r\n"
				+ "\r\n"
				+ "	]\r\n"
				+ "}";
		String result = HttpUtil.post(url, data);
		System.out.println(result);
		
	}
	
	
	//获得图片永久素材
	public static void getForeverImg() {
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", AccessTokenUtil.getAccessToken());
		
		String data = "{\r\n"
				+ "  \"media_id\":\"6bjdeUIo8prvpGCwgeqILu3lXCsQPSNNTDSa_rlKbxY\"\r\n"
				+ "\r\n"
				+ "}";
		
		String result = HttpUtil.post(url, data);
		System.out.println(result);
	}

	
	//获得图文永久素材
	public static void getForeverNews() {
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", AccessTokenUtil.getAccessToken());
		
		String data = "{\r\n"
				+ "  \"media_id\":\"6bjdeUIo8prvpGCwgeqILvdAsEJk5fjHaW3TtJhasgE\"\r\n"
				+ "\r\n"
				+ "}";
		
		String result = HttpUtil.post(url, data);
		System.out.println(result);
		
	}
}
