package com.yz.wx;


import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.yz.wx.entity.AbsButton;
import com.yz.wx.entity.Button;
import com.yz.wx.entity.ClickButton;
import com.yz.wx.entity.PhotoOrAlbum;
import com.yz.wx.entity.SubButton;
import com.yz.wx.entity.ViewButton;
import com.yz.wx.service.WxService;
import com.yz.wx.uploadimg.UploadImg;
import com.yz.wx.uploadimg.UploadImg2;
import com.yz.wx.util.QrCode;
import com.yz.wx.util.Util;

import net.sf.json.JSONObject;

public class Test {

	@org.junit.jupiter.api.Test
	public void testToken() {
		System.out.println(WxService.getAccessToken());
		System.out.println(WxService.getAccessToken());
	}
	
	@org.junit.jupiter.api.Test
	public void createButton() {
		//菜单对象
		Button btn = new Button("Markus");
		//第一个一级菜单
		btn.getButton().add(new ClickButton("一级点击", "1"));
		//第二个一级菜单
		btn.getButton().add(new ViewButton("一级跳转", "http://baidu.com"));
		//创建第三个一级菜单
		SubButton sb = new SubButton("有子菜单");
		//第三个菜单的子菜单
		sb.getSub_button().add(new PhotoOrAlbum("传图", "31"));
		sb.getSub_button().add(new ClickButton("点击", "32"));
		sb.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));
		
		btn.getButton().add(sb);
		
		JSONObject jsonObj = JSONObject.fromObject(btn);
		System.out.println(jsonObj.toString());
		
	
	}
	
	@org.junit.jupiter.api.Test
	public void testTempUpload() {
		String file = "C:"+File.separator+"12.jpg";   //"c:" + File.separator + 
		System.out.println(file);
		String result = WxService.upLoad(file, "image");
		//"media_id":"5NiRw8-5P5GesXoCeBSWogYK4miCASsFBJzJR0s9LLoSIZW5fKV84vdPU612_hvS"
	}
	
	@org.junit.jupiter.api.Test
	public void testUpload() {
		String file = "C:"+File.separator+"12.jpg";   //"c:" + File.separator + 
		System.out.println(file);
		String result = UploadImg.upload(file, "image");
//http://mmbiz.qpic.cn/mmbiz_jpg/SiaRSn312IPawkhSj0h9XxeibIokB2Xribosw8sXWKWmIOyMoQ4dnrbhwrr83hPicbxHQP7Cnk7FQxCC7wAKc2m8Bw/0

	}
	
	//永久图片
	@org.junit.jupiter.api.Test
	public void testUpload2() {
		String file = "C:"+File.separator+"12.jpg";   //"c:" + File.separator + 
		System.out.println(file);
		String result = UploadImg2.upload(file, "image");
		//6bjdeUIo8prvpGCwgeqILobj4eQA0TJg-pVwfUjN9Dg
	}
	
	
	//https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN
	//获取永久素材
		@org.junit.jupiter.api.Test
		public void testGetload2() {
			
			String str = "{\r\n" + 
					"  \"media_id\":6bjdeUIo8prvpGCwgeqILqVHFGkMdqHFAx8GjTwpN-0\r\n" + 
					"}";
			JSONObject jsonObj = JSONObject.fromObject(str);
			System.out.println(jsonObj);
			
			String url= "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token="+WxService.getAccessToken()+"";
			String result = Util.post(url, jsonObj.toString());
			System.out.println("result = " + result);	
			
			//6bjdeUIo8prvpGCwgeqILqVHFGkMdqHFAx8GjTwpN-0  永久图文media_id
		}
		
		
		
		
	@org.junit.jupiter.api.Test
	private String generateWord1() {  
        String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",  
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",  
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
                "W", "X", "Y", "Z" };  
        List list = Arrays.asList(beforeShuffle);  
        Collections.shuffle(list);  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < list.size(); i++) {  
            sb.append(list.get(i));  
        }  
        String afterShuffle = sb.toString();  
        String result = afterShuffle.substring(5, 9);
        System.out.println(result);
        return result;  
    }
	
	@org.junit.jupiter.api.Test
	public void testGetTicket() {
		new QrCode().getQrCodeTicket();
	}
	
}
