package com.markus.wx.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markus.wx.account.entity.Account;
import com.markus.wx.service.LoginService;
import com.markus.wx.service.RegisterService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.utils.CaptchaUtil;




@Controller
public class WebController {
	
	@Autowired
	LoginService  logSrv;
	
	@Autowired
	RegisterService regSrv;
	
	
	
	@RequestMapping("/web")
	public String webString () {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	
	//login页面， 后端生成验证码，返还给前端
	@RequestMapping("/verification")
	@ResponseBody
	public String verification() {
		String verCode = logSrv.createVerificationCode();
		return verCode;
	}
	
	//注册页面， 后端生成图片验证码，返还给前端
		@RequestMapping("/registerverification")
		@ResponseBody
		public String registerverification() {
			String verCode = logSrv.createRegisterVerificationCode();
			return verCode;
		}
		
		
	
	
	//"/weixin/verificationDo";
	//前端提交信息在后端的验证
	// 注册验证码
	@RequestMapping("/verificationDo")			 
	@ResponseBody
	public String verificationDo(HttpServletRequest req, Model map) {

		
		String verificationCode = req.getParameter("verificationCode");
		String loginNameOrPhone = req.getParameter("loginNameOrPhone");
		String password = req.getParameter("password");
//		System.out.println(verificationCode);
//		System.out.println(loginNameOrPhone);
//		System.out.println(password);
		String result = logSrv.verificationCodeDo(loginNameOrPhone, password, verificationCode);
		return result;
	}
	
	//用户注册
	//数据录入到数据库
	@RequestMapping("/register")
	@ResponseBody
	public String register(
			@RequestParam("registerName") String registerName,
			@RequestParam("registerPhone") String registerPhone,
			@RequestParam("registerImgVerificationCode") String registerImgVerificationCode,
			@RequestParam("registerPhoneVerificationCode") String registerPhoneVerificationCode,
			@RequestParam("registerPassword") String registerPassword
			) {
		System.out.println("写入数据开始");
		
		System.out.println(registerName);
		System.out.println(registerPhone);
		System.out.println(registerImgVerificationCode);
		System.out.println(registerPhoneVerificationCode);
		System.out.println(registerPassword);

		
		Account acc = new Account();
		acc.setAccountName(registerName);
		acc.setPhoneNumber(registerPhone);
		acc.setLoginPassword(registerPassword);
		//acc.setValidateCode(registerVerificationCode);
		//acc.setRegisterTime(System.currentTimeMillis());
		Date date = new Date();//获得系统时间.
		acc.setRegisterTime(date);
		
		//将时间格式转换成符合Timestamp要求的格式.
//		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//		acc.setRegisterTime(nowTime);
//		
		//前后端的验证码需要验证
		int result = regSrv.insert(acc);
		if(result == 1) {
			System.out.println("数据插入成功");
			return "SUCCESS";
		}else {
			System.out.println("数据插入失败");
			return "ERROR";
		}
	}

}
