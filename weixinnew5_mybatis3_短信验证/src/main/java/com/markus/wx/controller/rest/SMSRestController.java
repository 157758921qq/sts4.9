package com.markus.wx.controller.rest;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.markus.wx.service.SendMessage;

/**
 * restful风格URI的controller 只和用户交换JSON数据
 * 
 * @author Administrator
 *
 */

//从前端loginandregister.html提交过来后
@RestController
public class SMSRestController {

	@Autowired
	SendMessage sendMsg;

	@PostMapping("/sendMsg")
	public void sendMsg(String registerphone, HttpServletRequest request) {
		System.out.println("registerphone" + registerphone);
		// 获取现在的时间
		long regTime = System.currentTimeMillis();

		Random random = new Random();
		int num1 = random.nextInt(9);
		int num2 = random.nextInt(9);
		int num3 = random.nextInt(9);
		int num4 = random.nextInt(9);
		int num5 = random.nextInt(9);
		int num6 = random.nextInt(9);
		String code = "" + num1 + num2 + num3 + num4 + num5 + num6;
		// 电话号码和服务器生成的code都已经加入到session中。
			request.getSession().setAttribute("registerphone", registerphone);
			request.getSession().setAttribute("code", code);
			request.getSession().setAttribute("regTime", regTime);
			System.out.println("验证码是："+request.getSession().getAttribute("code"));
		sendMsg.sm(code);
//		return "验证码发送成功";

	}

}
