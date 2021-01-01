package com.yz.wx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yz.wx.service.WxService;

import javassist.expr.NewArray;

@Controller
public class MainController {
	
	@Autowired
	WxService wxSrv;
	
	
	//微信验证
	@GetMapping("/wx")
	public void wx_get(HttpServletRequest req, HttpServletResponse resp) {
		/**
		 * 	微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数如下表所示：
		 * 
		 *      参数	  		描述
			signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
			timestamp	时间戳
			nonce	         随机数
			echostr	         随机字符串
		 *  
		 */
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		System.out.println(signature);
		System.out.println(timestamp);
		System.out.println(nonce);
		System.out.println(echostr);
		//拿到微信返回的4个参数，接入微信公众号
		//效验请求
		if(wxSrv.check(timestamp,nonce,signature)) {
			//验证成功，原样返回echostr
			try {
				System.out.println("接入成功");
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

	
	
	//接收从微信服务器推送过来的Post消息
	@PostMapping("/wx")
	public void wx_post(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//设置字符集
			req.setCharacterEncoding("utf8");
			resp.setCharacterEncoding("utf8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
//		//通过流读取从微信服务器发送过来的各种XML消息
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
		
		
		//处理消息和事件推送
		try {
			//将XML消息转成Map<String， String>存储
			Map<String, String> requestMap = wxSrv.parseRequsetXMLToMap(req.getInputStream());
			System.out.println("XML消息转成Map= "+requestMap);
			
			//准备回复的数据包
//			String respXml = "<xml>\r\n" + 
//					"  <ToUserName><![CDATA["+requestMap.get("FromUserName") +"]]></ToUserName>\r\n" + 
//					"  <FromUserName><![CDATA["+requestMap.get("ToUserName") +"]]></FromUserName>\r\n" + 
//					"  <CreateTime>"+System.currentTimeMillis()/1000 +"</CreateTime>\r\n" + 
//					"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
//					"  <Content><![CDATA[欢迎您来到我的测试公众号]]></Content>\r\n" + 
//					"</xml>";
			//Map转String
			String respXml = wxSrv.getResponseFromMap(requestMap);
			//将处理好的Message通过out流输出到服务器界面
			System.out.println("respXml = " + respXml);
			
			PrintWriter out = resp.getWriter();
			out.print(respXml);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
