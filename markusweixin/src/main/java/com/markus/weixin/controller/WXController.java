package com.markus.weixin.controller;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markus.weixin.entity.ArticleItem;
import com.markus.weixin.entity.InMsgEntity;
import com.markus.weixin.entity.OutMsgEntity;
import com.markus.weixin.service.WXService;
import com.markus.weixin.util.XMLUtil;


@Controller
public class WXController {
	
	@Autowired
	WXService wxSrv;
	/**
	 * URL 接入效验
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@RequestMapping(value="sig", method=RequestMethod.GET)
	@ResponseBody
	public String validate(String signature, String timestamp, String nonce, String echostr) {
		String mySig = wxSrv.validate(timestamp, nonce);
		if(mySig.equals(signature)) {
			System.out.println("接入成功");
			return echostr;
		} else {
			System.out.println("接入失败");
			return null;
		}	
	}
	
	
	/**
	 *  消息处理
	 *  处理接收从微信服务返回给xml消息
	 *  配合JAXB的注解来解析xml
	 *  
	 *  处理消息，是根据收到的消息来处理的
	 *  
	 * @return
	 */
	@RequestMapping(value="sig", method=RequestMethod.POST)
	@ResponseBody
	public Object handleMessage(@RequestBody InMsgEntity inMsg) {
		
		//System.out.println(ToStringBuilder.reflectionToString(inMsg, ToStringStyle.MULTI_LINE_STYLE));
		/*
		 * 根据收到的消息内容进行判断处理
		 */
		OutMsgEntity outMsg = new OutMsgEntity();
		String msgType = inMsg.getMsgType();
		//对文本消息的处理
		if(msgType.equals("text")) {
			outMsg = wxSrv.dealTextMessage(inMsg, outMsg);
			//对图片的处理
		} else if(msgType.equals("image")) {
			outMsg = wxSrv.dealImageMessage(inMsg, outMsg);
			//对事件的处理
		} else if(msgType.equals("event")) {
			outMsg = wxSrv.dealNewsMessage(inMsg, outMsg);
		} else {
			outMsg = wxSrv.dealTextMessage(inMsg, outMsg);
		}
		
		//@RequestBody InMsgEntity inMsg 的说明
		//@RequestBody 注解用于读取request请求的body部分数据，根据Content-Type来
		//判断把数据当做什么类型来解析，然后把相应的数据绑定到参数上
		//需要根据MsgId做消息排重

		String convertToXml = XMLUtil.convertToXml(outMsg);
		System.out.println(convertToXml);
		return convertToXml;
	}
	
	
	

}




























