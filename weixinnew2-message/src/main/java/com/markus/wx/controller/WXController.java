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

import com.markus.wx.service.WXService;

@Controller
public class WXController {

	@Autowired
	WXService wxSrv;

	/**
	 * 	开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上 GET请求，在微信公众平台接口配置信息处填写并提交
	 *  GET请求携带参数如下表所示： signature timestamp nonce echostr
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
	 * 	当普通微信用户向公众账号发消息时，
	 * 	微信服务器将POST消息的XML数据包到开发者填写的URL上
	 * 
	 * 
	 * @param req
	 * @param resp
	 */
	@PostMapping("/sig")
	public void wxPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//设置字符集
			req.setCharacterEncoding("utf8");
			resp.setCharacterEncoding("utf8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		//通过输入流读取从微信服务器发送过来的各种XML消息
//		try {
//			ServletInputStream is = req.getInputStream();
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
		 * 	用户给微信服务器发送消息，
		 * 	1、通过输入流，将服务器转发消息的xml转成map格式fromRequsetParseXMLToMap()  requestMap
		 * 	2、根据requestMap里的信息如MsgType，开发者处理对用户的回复replyMessageToUser()
		 */
		try {
			Map<String, String> requestMap = wxSrv.fromRequsetParseXMLToMap(req.getInputStream());
			//{Content=hi, CreateTime=1609483159, ToUserName=gh_0b42d20b5fc7, FromUserName=ot4eH6Iaf9lmMlDhQDy_BDFrgE98, MsgType=text, MsgId=23042254832302270}
			//MsgType=text , 根据MsgType来分别处理
			
			String respXml = wxSrv.replyMessageToUser(requestMap);
			System.out.println("准备返回给用户的消息内容是 = " + respXml);
			
			
			
			System.out.println(requestMap.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
