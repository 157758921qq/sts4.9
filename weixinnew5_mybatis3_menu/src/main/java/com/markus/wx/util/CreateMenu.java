package com.markus.wx.util;



import com.markus.wx.config.WXConfig;
import com.markus.wx.menu.entity.Button;
import com.markus.wx.menu.entity.ClickButton;
import com.markus.wx.menu.entity.PhotoOrAlbum;
import com.markus.wx.menu.entity.SubButton;
import com.markus.wx.menu.entity.ViewButton;
import com.markus.wx.service.WXService;

import net.sf.json.JSONObject;

public class CreateMenu {

	public static void main(String[] args) {

		// 菜单对象
		Button btn = new Button("Markus");
		// 第一个一级菜单
		btn.getButton().add(new ClickButton("一级点击", "1"));
		// 第二个一级菜单
		btn.getButton().add(new ViewButton("一级跳转", "http://yzcoder.nat300.top/wx/web"));
		// 创建第三个一级菜单
		SubButton sb = new SubButton("有子菜单");
		// 第三个菜单的子菜单
		sb.getSub_button().add(new PhotoOrAlbum("传图", "31"));
		sb.getSub_button().add(new ClickButton("点击", "32"));
		sb.getSub_button().add(new ViewButton("会员中心", "http://yzcoder.nat300.top/wx/login"));
		sb.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));

		btn.getButton().add(sb);
		// 转为json
		JSONObject jsonObj = JSONObject.fromObject(btn);
		System.out.println(jsonObj.toString());
		
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+AccessTokenUtil.getAccessToken()+"";
		String result = Util.post(url, jsonObj.toString());
		System.out.println("result = " + result);
		

	}

}
