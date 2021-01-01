package com.yz.wx.uploadimg;

import java.util.List;

import com.yz.wx.service.WxService;
import com.yz.wx.util.Util;

import net.sf.json.JSONObject;

public class Uploadnews {
	public static void main(String[] args) {
		EntityNews en = new EntityNews();
		List<Articles> articles = en.getArticles();
		
		Articles a = new Articles("2021我爱我家",
				"6bjdeUIo8prvpGCwgeqILobj4eQA0TJg-pVwfUjN9Dg", 
				"Markus", 
				"2020年是极不平凡的一年。面对突如其来的新冠肺炎疫情，我们以人民至上、生命至上诠释了人间大爱，用众志成城、坚忍不拔书写了抗疫史诗。",
				"1", "在共克时艰的日子里，http://mmbiz.qpic.cn/mmbiz_jpg/SiaRSn312IPawkhSj0h9XxeibIokB2Xribosw8sXWKWmIOyMoQ4dnrbhwrr83hPicbxHQP7Cnk7FQxCC7wAKc2m8Bw/0，www.baidu.com",
				"www.qq.com",
				"1", 
				"0");
		articles.add(a);
		// 转为json
		JSONObject jsonObj = JSONObject.fromObject(en);
		System.out.println(jsonObj.toString());
		
		String url = " https://api.weixin.qq.com/cgi-bin/material/add_news?access_token="+WxService.getAccessToken()+"";
		String result = Util.post(url, jsonObj.toString());
		System.out.println("result = " + result);	
		
	}
	
	
	
	

}
