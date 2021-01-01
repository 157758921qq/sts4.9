package com.yz.wx.util;

import com.yz.wx.service.WxService;

import net.sf.json.JSONObject;

public class QrCode {
	
	
	//获取带参数的二维码ticket
	public static String getQrCodeTicket() {
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
		url = url.replace("TOKEN", WxService.getAccessToken());
		//生成临时字符串二维码
		String data = "{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"宇宙最美\"}}}";
		String result = Util.post(url, data);
		System.out.println(result);
		String ticket = JSONObject.fromObject(result).getString("ticket");
		System.out.println(ticket);
		return ticket;
	}

	
}
