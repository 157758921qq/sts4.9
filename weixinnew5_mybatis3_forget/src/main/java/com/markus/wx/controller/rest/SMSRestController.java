package com.markus.wx.controller.rest;

import java.awt.Dialog.ModalExclusionType;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.markus.wx.service.SendMessage;

/**
 * restful风格URI的controller 只和用户交换JSON数据
 * 
 * @author Administrator
 *
 */

@RestController
public class SMSRestController {

	@Autowired
	SendMessage sendMsg;

	@RequestMapping(value = "/sendMsg",method = RequestMethod.POST)
	public String sendMsg(String registerphone, HttpServletRequest request, Model map) {
//
//		String remoteAddr = request.getRemoteAddr();// 返回发出骑牛的IP地址
//		String host = request.getRemoteHost();// 返回发出请求的客户机的主机名
//		int port = request.getRemotePort();// 返回发出请求的客户机的端口号。
//		System.out.println("remoteAddr" + remoteAddr);
//		System.out.println("host" + host);
//		System.out.println("port" + port);
//		
		String ipAddress = SMSRestController.getIpAddress(request);
System.out.println("ipAddress = "+ ipAddress);
		
		// 获取现在的时间
		//long regTime = System.currentTimeMillis();

		Random random = new Random();
		int num1 = random.nextInt(9);
		int num2 = random.nextInt(9);
		int num3 = random.nextInt(9);
		int num4 = random.nextInt(9);
		int num5 = random.nextInt(9);
		int num6 = random.nextInt(9);
		String code = "" + num1 + num2 + num3 + num4 + num5 + num6;
		// 电话号码和服务器生成的code都已经加入到session中。
		//request.getSession().setAttribute("code", code);
		//request.getSession().setAttribute("ip", ipAddress);
		System.out.println("验证码是：" +code);
		//map.addAttribute("code", code);
		sendMsg.sm(registerphone, code);
		
		return code;

	}

	/**
	  * 获取ip地址
	  */
	// @RequestMapping(value = "/getIpAddress",method = RequestMethod.POST)
	 public static String getIpAddress(HttpServletRequest request) {  
	        String ip = request.getHeader("x-forwarded-for");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	             ip = request.getHeader("HTTP_CLIENT_IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	             // 若以上方式均为获取到ip则证明获得客户端并没有采用反向代理直接使用getRemoteAddr()获取客户端的ip地址
	             ip = request.getRemoteAddr();  
	        }  
	        // 多个路由时，取第一个非unknown的ip
	        final String[] arr = ip.split(",");
	        for (final String str : arr) {
	            if (!"unknown".equalsIgnoreCase(str)) {
	                ip = str;
	                break;
	            }
	        }
	        return ip;  
	     }

}
