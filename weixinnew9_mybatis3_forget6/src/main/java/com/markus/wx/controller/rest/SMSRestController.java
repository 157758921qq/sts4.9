package com.markus.wx.controller.rest;

import java.awt.Dialog.ModalExclusionType;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.markus.wx.account.entity.Account;
import com.markus.wx.service.LoginService;
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
	
	@Autowired
	LoginService  logSrv;

	@RequestMapping(value = "/sendMsg",method = RequestMethod.POST)
	public String sendMsg(String registerphone,String receiveRegisterCode,  HttpServletRequest request, Model map) {
//
//		String remoteAddr = request.getRemoteAddr();// 返回发出骑牛的IP地址
//		String host = request.getRemoteHost();// 返回发出请求的客户机的主机名
//		int port = request.getRemotePort();// 返回发出请求的客户机的端口号。
//		System.out.println("remoteAddr" + remoteAddr);
		System.out.println("registerphone = " + registerphone);
		System.out.println("receiveRegisterCode = " + receiveRegisterCode);
		
		//System.out.println("registerverificationcode = " + registerimgverificationcode);
		
//		
//		if(!LoginService.registerVerification.equalsIgnoreCase(registerimgverificationcode)) {
//			System.out.println("图形验证码输入错误");
//			return "图形验证码输入错误";
//		}
//		

		//收到：发送短信的请求，此时，已经验证好图片验证码
		//需要改变图片验证码
		
		//拿到服务器端生成的图片注册码
		String oldRegisterCode = (String) request.getSession().getAttribute("registerImgVerification");
		System.out.println("sessin oldRegisterCode = " +oldRegisterCode);
		System.out.println("input receiveRegisterCode = " +receiveRegisterCode);
		//如果和前端传递过来的图片验证码不一致，返回
		if(!oldRegisterCode.equalsIgnoreCase(receiveRegisterCode)) {
			return null;
		} 
		//如果和前端传递过来的图片验证码一致，刷新这个验证码
		//也就是第一发送短信验证码，获取到的图片验证码和或服务器生成的一定是一致的
		//当验证一致后，改变这个图片验证码
		//如果再刷新，填写旧图片验证码时，在点获取短信验证码时，这里，新和旧一定不同，return过不去。
		oldRegisterCode = logSrv.createRegisterVerificationCode(request.getSession());
		

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
		String result = sendMsg.sm(registerphone, code);
		return oldRegisterCode;
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
