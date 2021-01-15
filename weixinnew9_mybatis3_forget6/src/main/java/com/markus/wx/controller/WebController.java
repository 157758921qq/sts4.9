package com.markus.wx.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markus.wx.account.entity.Account;
import com.markus.wx.config.WXConfig;
import com.markus.wx.service.ContentService;
import com.markus.wx.service.LoginService;
import com.markus.wx.service.RegisterService;
import com.markus.wx.util.Util;
import com.markus.wx.util.WebAuthAccessTokenUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.utils.CaptchaUtil;

import net.sf.json.JSONObject;




@Controller
public class WebController {
	
	
	@Autowired
	LoginService  logSrv;
	
	@Autowired
	RegisterService regSrv;
	
	@Autowired
	ContentService conSrv;


//	@RequestMapping("/web")
//	public String webString () {
//		return "index";
//	}
	

	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	

	@RequestMapping("/content")
	@ResponseBody
	public String content(String textAreaDiv) {
		System.out.println(textAreaDiv);
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(textAreaDiv);
		textAreaDiv = m.replaceAll("");
		System.out.println(textAreaDiv);
		if(textAreaDiv == null || textAreaDiv.equals(""))
			return "重新填写";
		else {
			int save = conSrv.save(textAreaDiv);
			if(save == 1)
				return "成功";
			else 
				return "失败";
		
		}
	}
	
	
	
	//login页面， 后端生成验证码，返还给前端
	@RequestMapping("/verification")
	@ResponseBody
	public String verification(HttpSession session) {
		String verCode = logSrv.createVerificationCode();
		return verCode;
	}
	
	//注册页面， 后端生成图片验证码，返还给前端
		@RequestMapping("/registerverification")
		@ResponseBody
		public String registerverification(HttpSession session) {
			String verCode = logSrv.createRegisterVerificationCode(session);
			return verCode;
		}
		

	//"/weixin/verificationDo";
	//前端提交信息在后端的验证
	// 注册验证码
	@RequestMapping("/verificationDo")			 
	@ResponseBody
	public String verificationDo(HttpServletRequest req, Model map, HttpSession session) {

		
		String verificationCode = req.getParameter("verificationCode");
		String loginNameOrPhone = req.getParameter("loginNameOrPhone");
		String password = req.getParameter("password");
//		System.out.println(verificationCode);
//		System.out.println(loginNameOrPhone);
//		System.out.println(password);
		String result = logSrv.verificationCodeDo(loginNameOrPhone, password, verificationCode, session);
		return result;
	}
	
	//用户注册
	//数据录入到数据库
	@RequestMapping("/register")
	@ResponseBody
	public String register(
			@RequestParam("registerName") String registerName,
			@RequestParam("registerPhone") String registerPhone,
			@RequestParam("registerPhoneVerificationCode") String registerPhoneVerificationCode,
			@RequestParam("registerPassword") String registerPassword
			) {
		System.out.println("写入数据开始");
		
		System.out.println(registerName);
		System.out.println(registerPhone);
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
	
	
	

	
	//什么时候初始化它？？
		//在页面授权的时候  	@RequestMapping("/auth")
	


	/**
	 * 	网页授权,并且保存refresh_token
	 * @param request
	 * @return
	 */
	@RequestMapping("/auth")
	public String getUserInfo(HttpServletRequest request) {
		String code = request.getParameter("code");

		//换取web_access_token
		String url ="https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid=APPID&secret=SECRET&code=CODE"
				+ "&grant_type=authorization_code";
		
		url = url.replace("APPID", "wx98b0a5b92b436370").replace("SECRET", "5d1e238c98806e1f4bb0fde9b0a70bda").replace("CODE", code);
		String result = Util.get(url);
		System.out.println(result);
		/**
		 * {"access_token":"41_j7gcOHRKYTWpTiVBSxmI7ik61zX_rxeBQ3M5o8yFz63ZCn_ZXG7bK7ZGYepHEKBMxkpdNPRE69VpC81z85wTAkjY5wMVJ8qTtH2FRO5mbtc",
		 * "expires_in":7200,
		 * "refresh_token":"41_6aeF23j49sxlZVpU444s45ALRTGWqEm5N6BqHBGBtsNQues2rJQ3r-pMGx9Z4m68WeQ7vvky-aqEg8IpoVlTGwcMzed9FSzln-Ci7k1_-Ik",
		 * "openid":"ot4eH6Iaf9lmMlDhQDy_BDFrgE98",
		 * "scope":"snsapi_userinfo"}

		 */
		//获取到网页授权，保存起来，这个access_token是网页的授权
		String at = JSONObject.fromObject(result).getString("access_token");
		
		//通过refresh_token来刷新
		String refresh_token = JSONObject.fromObject(result).getString("refresh_token");
		System.out.println("refresh_token = "+ refresh_token);
//		WebAuthAccessTokenUtil.refreshAccessTokenStr = refresh_token;
//	
//		String refreshAccessToken = WebAuthAccessTokenUtil.getRefreshAccessToken();
//		
//		try {
//			System.out.println("1 = "+refreshAccessToken);
//			Thread.sleep(50000);
//			refreshAccessToken = WebAuthAccessTokenUtil.getRefreshAccessToken();
//			System.out.println("2 = "+refreshAccessToken);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		String openid = JSONObject.fromObject(result).getString("openid");
		url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url = url.replace("ACCESS_TOKEN", at).replace("CODE", code).replace("OPENID", openid);
		result = Util.get(url);
		System.out.println("获取到用户信息="+result);
		
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
