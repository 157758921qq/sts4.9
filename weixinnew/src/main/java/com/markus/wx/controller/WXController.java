package com.markus.wx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

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
	 * 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上 GET请求，在微信公众平台接口配置信息处填写并提交
	 * 
	 * GET请求携带参数如下表所示： signature timestamp nonce echostr
	 * 
	 * @param req
	 * @param resp
	 */
	@GetMapping("/sig")
	public void wx_get(HttpServletRequest req, HttpServletResponse resp) {
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

}
