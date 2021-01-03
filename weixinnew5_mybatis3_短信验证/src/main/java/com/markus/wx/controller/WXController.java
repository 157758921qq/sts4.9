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

	@RequestMapping("/getuserinfo")
	@ResponseBody
	public void getUserInfo() {
	
	}
}
