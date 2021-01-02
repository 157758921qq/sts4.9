package com.markus.wx.service;

import org.springframework.stereotype.Service;

import com.markus.wx.util.VerificationCode;


@Service
public class LoginService {
	public static String verification;
	
	public String createVerificationCode() {
		// TODO Auto-generated method stub
		verification = VerificationCode.generateWord();
		return verification;
	}

	public String verificationCodeDo(String verificationCode) {
		if(verificationCode.equalsIgnoreCase(verification))
			return "SUCCESS";
		else 
			return "ERROR";
	}

}
