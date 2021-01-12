package com.markus.wx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markus.wx.service.WXService;
import com.markus.wx.util.Util;

import net.sf.json.JSONObject;

@Controller
public class WXController {
	Map<String, String> requestMap;

	@Autowired
	WXService wxSrv;

	/**
	 * 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上 GET请求，在微信公众平台接口配置信息处填写并提交 GET请求携带参数如下表所示：
	 * signature timestamp nonce echostr
	 * 
	 * @param req
	 * @param resp
	 */
	@GetMapping("/sig")
	public void wxGet(HttpServletRequest req, HttpServletResponse resp) {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		System.out.println(signature);
		
		
		// 验证，原样返回echostr
		if (wxSrv.check(timestamp, nonce, signature)) {
			try {
				PrintWriter out = resp.getWriter();
				out.print(echostr);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("接入失败");
		}

	}

	/**
	 * 当普通微信用户向公众账号发消息时， 微信服务器将POST消息的XML数据包到开发者填写的URL上
	 * 
	 * 
	 * @param req
	 * @param resp
	 */
	@PostMapping("/sig")
	public void wxPost(HttpServletRequest req, HttpServletResponse resp) {
		ServletInputStream is = null;
		try {
			is = req.getInputStream();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		try {
			// 设置字符集
			req.setCharacterEncoding("utf8");
			resp.setCharacterEncoding("utf8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// 通过输入流读取从微信服务器发送过来的各种XML消息
//		try {
//		    //用字符数组接收
//			byte[] b = new byte[1024];
//			int len = 0;
//			StringBuilder sb = new StringBuilder();
//			while((len = is.read(b)) != -1) {          
//				sb.append(new String(b,0, len));
//			}
//			System.out.println(sb.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		/*
		 * 用户给微信服务器发送消息， 1、通过输入流，将服务器转发消息的xml转成map格式，fromRequsetParseXMLToMap()
		 * 
		 * 2、根据requestMap里的信息如MsgType，开发者处理对用户的回复replyMessageToUser()
		 */
		requestMap = wxSrv.fromRequsetParseXMLToMap(is);
		String msgType = requestMap.get("MsgType");
		if (msgType.equals("event")) {
			String key = requestMap.get("Event");
			if (key.equals("subscribe")) {
				System.out.println("保存数据库 ：" + requestMap);
			}
			if (key.equals("unsubscribe")) {
				System.out.println("保存数据库 ：" + requestMap);
			}

		} else {
			try {
				String respXml = wxSrv.replyMessageToUser(requestMap);
System.out.println(respXml);
				if (respXml != null) {
					PrintWriter out = resp.getWriter();
					out.print(respXml);
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	
	
	
//
	@RequestMapping("/auth")
	public String getUserInfo(HttpServletRequest request) {
		String code = request.getParameter("code");
		System.out.println("获取code = " + code);
		
		//换取access_token
		String url ="https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid=APPID&secret=SECRET&code=CODE"
				+ "&grant_type=authorization_code";
		
		url = url.replace("APPID", "wx98b0a5b92b436370").replace("SECRET", "5d1e238c98806e1f4bb0fde9b0a70bda").replace("CODE", code);
		
		String result = Util.get(url);
		System.out.println("result = " + result);
		//获取refresh_token
		//拿到
		String at = JSONObject.fromObject(result).getString("access_token");
		String openid = JSONObject.fromObject(result).getString("openid");
		url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url = url.replace("ACCESS_TOKEN", at).replace("CODE", code).replace("OPENID", openid);
		result = Util.get(url);
		System.out.println("获取用户信息="+result);
		
		/*
		 获取用户信息={
		 "openid":"ot4eH6Iaf9lmMlDhQDy_BDFrgE98",
		 "nickname":"Markus",
		 "sex":1,
		 "language":"zh_CN",
		 "city":"",
		 "province":"",
		 "country":"阿尔及利亚",
		 "headimgurl":"https:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/DYAIOgq83epuQgaaLrxnezCtqudGiayMoziacpfiaIj9s8pja2VBlMjSGs03SCleJ9vbTDqZTpg6Iql96QH6L5gDg\/132",
		 "privilege":[]}

		 * 
		 */
		//取到这些值放到session或 数据库里面
		
		return "redirect:http://yzcoder.nat300.top/wx/login";
	}
	
	
	
}

























