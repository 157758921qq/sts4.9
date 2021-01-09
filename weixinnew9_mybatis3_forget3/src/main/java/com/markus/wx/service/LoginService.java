package com.markus.wx.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markus.wx.account.entity.Account;
import com.markus.wx.mapper.AccountMapper;
import com.markus.wx.util.VerificationCode;


@Service
public class LoginService {
	
	@Autowired
	AccountMapper accMapper;
	
	public static String verification;
	public static String registerVerification;
	
	
	
	public String createVerificationCode() {
		// TODO Auto-generated method stub
		verification = VerificationCode.generateWord();
		return verification;
	}
	

	/**
	 * 	注册图形验证码的获取,这个随机码是要不停改变的
	 * 
	 * @return
	 */
	public String createRegisterVerificationCode() {
		registerVerification = VerificationCode.generateWord();
		System.out.println("registerVerification = " + registerVerification);
		return registerVerification;
	}

	

	public String verificationCodeDo(String loginNameOrPhone, String password, String verificationCode) {
		//如果前端传递的验证码和后端生成的验证码相同，登录开始
		//否则出错
		if(verificationCode.equalsIgnoreCase(verification)) {
			List<Account> accounts = accMapper.findByNameOrPhoneAndPassword(loginNameOrPhone, password);
			System.out.println(accounts.size());
			//加入到session中
			return "登录成功";
		}
		return "前后端图形验证码不一致";
	}

}
