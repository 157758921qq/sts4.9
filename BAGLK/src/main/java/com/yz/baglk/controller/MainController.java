package com.yz.baglk.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yz.baglk.controller.service.RegisterService;
import com.yz.baglk.entity.Register;
import com.yz.baglk.wxpay.util.DataJoinUtils;



@Controller
public class MainController {
	
	

	@Autowired
	RegisterService regSrv;
	
	//解决前台传DATE参数为空找不到方法
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		dateFormat.setLenient(false);//是否严格解析时间 false则严格解析 true宽松解析
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//	}

	
	
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	


	@RequestMapping("/model")
	public String model() {
		return "testmodel";
	}
	

	
//	@RequestMapping("/list/{time}")
//	public String list(@PathVariable String time) {
//		List<Register> regs = regSrv.findByRegisterTime(time);
//		System.out.println(ToStringBuilder.reflectionToString(regs, ToStringStyle.MULTI_LINE_STYLE));
//		return "list";
//	}
	
	@RequestMapping("/listAll")
	public String listAll(Model model) {
		List<Register> regs = regSrv.findAll();
		System.out.println(ToStringBuilder.reflectionToString(regs, ToStringStyle.MULTI_LINE_STYLE));
		model.addAttribute("page", regs);
		return "listoneday";
	}
	
	//根据住院号查询
//	@RequestMapping("/listOneDay")
//	public String listOneDay(Model model) {
//		List<Register> regs = regSrv.findByPatientId("1");
//		
//		System.out.println(ToStringBuilder.reflectionToString(regs, ToStringStyle.MULTI_LINE_STYLE));
//		model.addAttribute("page", regs);
//		return "listall";
//	}
	
	@RequestMapping("/listToday")
	public String listToday(Model model) {
		Date date = new Date();//获得系统时间.
		//将时间格式转换成符合Timestamp要求的格式.
		String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		List<Register> regs = regSrv.findByRegisterTimeLike("%"+today+"%");
		
		System.out.println(ToStringBuilder.reflectionToString(regs, ToStringStyle.MULTI_LINE_STYLE));
		model.addAttribute("page", regs);
		return "listtoday";
	}
	
	
	
	
	@RequestMapping("/listMonth")
	public String listOneDay(Model model) {
		Date date = new Date();//获得系统时间.
		//将时间格式转换成符合Timestamp要求的格式.
		String month = new SimpleDateFormat("yyyy-MM").format(date);
		
		List<Register> regs = regSrv.findByRegisterTimeLike("%"+month+"%");
		
		System.out.println(ToStringBuilder.reflectionToString(regs, ToStringStyle.MULTI_LINE_STYLE));
		model.addAttribute("page", regs);
		return "listmonth";
	}
	
	@RequestMapping("/listScope")
	public String listScope(Model model) {
		return "listscope";
	}
	

	@RequestMapping("/listDoScope")
	public String doScope(
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startTime ,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endTime ,
			Model model) {

		//处理右边界
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endTime);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date dt1=calendar.getTime();
	
		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String startTimeStr = df.format(startTime);
		String endTimeStr = df.format(dt1);
		
		System.out.println("startTime="+startTimeStr+", endTiem= "+ endTimeStr);
		
		//List<Register> regs = regSrv.findByRegisterTimeLike("%"+today+"%");
		List<Register> regs = regSrv.findByRegisterTimeBetween(startTimeStr, endTimeStr);
		System.out.println(ToStringBuilder.reflectionToString(regs, ToStringStyle.MULTI_LINE_STYLE));
		model.addAttribute("page", regs);
		return "listall";
	}
	
	

	//收集表单元素
	@PostMapping("/collect")
	@ResponseBody
	public String collect(HttpServletRequest req, Model map) {
	
		//System.out.println(ToStringBuilder.reflectionToString(register, ToStringStyle.MULTI_LINE_STYLE));
		
//		Date date = new Date();
//
//		Timestamp timeStamp = new Timestamp(date.getTime());
//		register.setRegisterTime(timeStamp);
		String patientId = req.getParameter("patientId");
		String patientName = req.getParameter("patientName");
		String reason = req.getParameter("reason");
		String copies = req.getParameter("copies");
		String sheets = req.getParameter("sheets");
		String price = req.getParameter("price");
		String totalPrice = req.getParameter("totalPrice");

		
		
		Register register = new Register();
		register.setPatientId(patientId);
		register.setPatientName(patientName);
		register.setReason(reason);
		register.setCopies(copies);
		register.setSheets(sheets);
		register.setPrice(price);
		register.setTotalPrice(totalPrice);
		
		Date date = new Date();//获得系统时间.
		//将时间格式转换成符合Timestamp要求的格式.
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		register.setRegisterTime(nowTime);

		System.out.println(ToStringBuilder.reflectionToString(register, ToStringStyle.MULTI_LINE_STYLE));
		Register reg = regSrv.save(register);
		if(reg != null) {
			return "SUCCESS";
		} else {
			return "ERROR";
		}
}
	

	/**
	 * 	点击 前端html页面上的提交按钮
	 * 	接收到前端传递过来的参数
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/orderSubmit")
	public String orderSubmit(HttpServletRequest req, HttpServletResponse resp, Model map) {
		try {
			// 订单标号
			String orderNo = req.getParameter("orderNo");
			// 订单描述
			String body = req.getParameter("remark");
			// 订单价格
			String price = req.getParameter("price");

			// 数据保存到数据库
			
			
			DataJoinUtils wxPayUtils = new DataJoinUtils();
			// 回调地址
			String url = "http://www.jd.com";
			Map<String, String> result = null;
			result = wxPayUtils.wxPay(url, orderNo, price, "127.0.0.1", body);
			System.out.println("微信统一下单，获取的微信返回结果 = " + result);
	
			//req.setAttribute("code_url", result.get("code_url"));
			//req.getRequestDispatcher("/orderPay.html").forward(req, resp);
			map.addAttribute("code_url", result.get("code_url"));
			map.addAttribute("return_msg", result.get("return_msg"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "testpay2";
	}
	
	
	@ResponseBody
	@RequestMapping("/queryPayStatus")
	public String queryStatus(String orderNo) {
		String tip = "";
		
		try {
			int num = 0;
			DataJoinUtils dataJoinUtils = new DataJoinUtils();
			while(true) {
//				Map<String, String> map = dataJoinUtils.wxOrderQuery(orderNo);
//				if(map == null) {
//					tip = "支付发送错误";
//					break;
//				}
//				
//				if(map.get("trade_state").equals("SUCCESS")) {
//					tip = "OK";
//					
//					//修改数据库中的订单状态
//					break;
//				}
				
				Thread.sleep(3000);
				num++;
				if(num >= 10) {
					tip = "支付超时";
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tip;
	}
	
	
	
	
	@RequestMapping("/orderQuery")
	public String orderquery(HttpServletRequest req, HttpServletResponse resp, Model map) {
		String orderNo = req.getParameter("orderNo");
		System.out.println("orderNo= " + orderNo);
		DataJoinUtils wxPayUtils = new DataJoinUtils();
		Map<String, String> resultSet = null;
		try {
			resultSet = wxPayUtils.wxOrderQuery(orderNo);
			System.out.println("===================微信订单详情=====================");
			System.out.println("resultSet:"+resultSet.toString());
			map.addAttribute("resultSet", resultSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "orderquery";
	}
	
	
	@RequestMapping("/orderClose")
	public String orderClose(HttpServletRequest req, HttpServletResponse resp, Model map) {
		String orderNo = req.getParameter("orderNo");
		System.out.println("orderNo= " + orderNo);
		DataJoinUtils wxPayUtils = new DataJoinUtils();
		Map<String, String> resultSet = null;
		try {
			resultSet = wxPayUtils.wxOrderClose(orderNo);
			System.out.println("===================微信订单详情=====================");
			System.out.println("resultSet:"+resultSet.toString());
			map.addAttribute("resultSet", resultSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "orderclose";
	}
	
}
























