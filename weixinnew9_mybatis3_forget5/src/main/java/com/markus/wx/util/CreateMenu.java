package com.markus.wx.util;



import com.markus.wx.config.WXConfig;
import com.markus.wx.menu.entity.Button;
import com.markus.wx.menu.entity.ClickButton;
import com.markus.wx.menu.entity.PhotoOrAlbum;
import com.markus.wx.menu.entity.SubButton;
import com.markus.wx.menu.entity.ViewButton;

import net.sf.json.JSONObject;

public class CreateMenu {

	public static void main(String[] args) {

		// 菜单对象
		Button btn = new Button("Markus");
		// 第一个一级菜单
		btn.getButton().add(new ClickButton("导航帮助", "1"));
		// 第二个一级菜单
		btn.getButton().add(new ViewButton("历史消息", "http://mp.weixin.qq.com/s?__biz=MzI5MzE3NDMwOA==&mid=100000165&idx=1&sn=1637a3fc58575e32ac17cae37e136363&chksm=6c776cee5b00e5f84804ae80e544329e7629fe40034bcf7e94b7cb00214903b1d97d0d573aff#rd"));
		// 创建第三个一级菜单
		SubButton sb = new SubButton("有子菜单");
		// 第三个菜单的子菜单
//		sb.getSub_button().add(new PhotoOrAlbum("传图", "31"));
//		sb.getSub_button().add(new ClickButton("点击", "32"));
		sb.getSub_button().add(new ViewButton("会员中心", "http://yzcoder.nat300.top/wx/login"));
//		sb.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));

		btn.getButton().add(sb);
		//对象转为json
		JSONObject jsonObj = JSONObject.fromObject(btn);
		System.out.println(jsonObj.toString());
		
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=41_FM_uqweIHL-x-oA5ImgstmAkUdnce6xACw3sXN9-c1hLWxRkS-SKARWI11SqHeFLF0dPcfs1MCURx2WKlIkcIgaUOWSbOCQ4vr60cGUo-tYbAX6L5LFcMGsQf_LvDyVzgPzaFJrsUdEMeyVGHHJbAHACCE\r\n"
				+ "";
		String result = Util.post(url, jsonObj.toString());
		System.out.println("result = " + result);
		

	}

}
