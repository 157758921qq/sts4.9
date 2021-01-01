package com.yz.wx.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.startup.WebappServiceLoader;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yz.wx.service.LoginService;

@Controller
public class WebController {
	
	@Autowired
	LoginService  logSrv;
	
	@RequestMapping("/web")
	public String webString () {
		
		return "index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	//生成验证码返还给前端
	@RequestMapping("/verification")
	@ResponseBody
	public String verification(Model map) {
		String verCode = logSrv.createVerificationCode();
		return verCode;
	}
	
	
	//"/weixin/verificationDo";
	//前端提交信息在后端的验证
	@RequestMapping("/verificationDo")			 
	@ResponseBody
	public String verificationDo(HttpServletRequest req, Model map) {
		String verificationCode = req.getParameter("verificationCode");
		System.out.println("verificationCode = "+ verificationCode);
		
		String result = logSrv.verificationCodeDo(verificationCode);
		return result;
	}
	
	

}
