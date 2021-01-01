package com.yz.wx.template;

import com.yz.wx.service.WxService;
import com.yz.wx.util.Util;

public class Templates {

	public static void main(String[] args) {
		new Templates().sendTemplateMessage();;
	}
	
	
	public void set() {    //设置行业
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+WxService.getAccessToken()+"";
		String data = "{\r\n" + 
				"    \"industry_id1\":\"2\",\r\n" + 
				"    \"industry_id2\":\"16\"\r\n" + 
				"}";
		String result = Util.post(url, data);
		System.out.println(result);
	}
	
	//https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN
	public void get() {   //

	}
	
	public void sendTemplateMessage() {
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+WxService.getAccessToken()+"";
		String data = "{\r\n" + 
				"           \"touser\":\"ot4eH6K7aDuCCMz7tDB2uD46o5wY\",\r\n" + 
				"           \"template_id\":\"MzAQMFPgR4QrBXIRos6jmYhWIlE2yNZrPUbnoKGdjbQ\",\r\n" + 
				"           \"data\":{\r\n" + 
				"                   \"first\": {\r\n" + 
				"                       \"value\":\"您有新的反馈信息了！\",\r\n" + 
				"                       \"color\":\"#173177\"\r\n" + 
				"                   },\r\n" + 
				"                   \"company\":{\r\n" + 
				"                       \"value\":\"阿里巴巴\",\r\n" + 
				"                       \"color\":\"#173177\"\r\n" + 
				"                   },\r\n" + 
				"                   \"time\": {\r\n" + 
				"                       \"value\":\"2020年11月11日\",\r\n" + 
				"                       \"color\":\"#173177\"\r\n" + 
				"                   },\r\n" + 
				"                   \"result\": {\r\n" + 
				"                       \"value\":\"面试通过\",\r\n" + 
				"                       \"color\":\"#173177\"\r\n" + 
				"                   },\r\n" + 
				"                   \"remark\":{\r\n" + 
				"                       \"value\":\"请和本公司人事联系！\",\r\n" + 
				"                       \"color\":\"#173177\"\r\n" + 
				"                   }\r\n" + 
				"           }\r\n" + 
				"       } ";
		String result = Util.post(url, data);
		System.out.println(result);
	}
	
}
