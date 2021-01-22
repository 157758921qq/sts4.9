package com.markus.weixin.service;

import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.markus.weixin.entity.ArticleItem;
import com.markus.weixin.entity.InMsgEntity;
import com.markus.weixin.entity.OutMsgEntity;
import com.markus.weixin.util.SecutityUtil;
import com.markus.weixin.util.ConfigUtil;

@Service
public class WXService {

	
	/**
	 * Service中对微信签名的验证
	 * @param timestamp
	 * @return
	 */
	public String validate(String timestamp, String nonce) {
		String[] arr = {ConfigUtil.TOKEN, timestamp, nonce};
		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for(String temp : arr) {
			sb.append(temp);
		}
		String mySig = SecutityUtil.sha1(sb.toString());
		return mySig;
	}

	
	/**
	 * 处理接收到的是text文本消息
	 * @param inMsg 
	 * @param outMsg 
	 * @return
	 */
	public OutMsgEntity dealTextMessage(InMsgEntity inMsg, OutMsgEntity outMsg) {
		String outContent = "";
		outMsg.setFromUserName(inMsg.getToUserName());
		outMsg.setToUserName(inMsg.getFromUserName());
		outMsg.setCreateTime(new Date().getTime()); 
		outMsg.setMsgType("text");
		//根据输入的文字处理各类消息
		String content = inMsg.getContent();
		if(content.equals("开班")) {
			outContent = "上海班\n" + "北京班\n";
		} else if(content.equals("地址")) {
			outContent = "地址在香港";
		} else {
			outContent = inMsg.getContent();
		}
		outMsg.setContent(outContent);	
		return outMsg;
	}


	/**
	 * 处理接收到的是image图片消息
	 * @param outMsg 
	 * @param inMsg 
	 * @return
	 */
	public OutMsgEntity dealImageMessage(InMsgEntity inMsg, OutMsgEntity outMsg) {
		outMsg.setFromUserName(inMsg.getToUserName());
		outMsg.setToUserName(inMsg.getFromUserName());
		outMsg.setCreateTime(new Date().getTime()); 
		outMsg.setMsgType("image");
		outMsg.setMediaId(new String[]{inMsg.getMediaId()});;
		return outMsg;
	}


	/**
	 * 处理接收到的是news图文消息
	 * @param outMsg 
	 * @param inMsg 
	 * @return
	 */
	public OutMsgEntity dealNewsMessage(InMsgEntity inMsg, OutMsgEntity outMsg) {
		outMsg.setFromUserName(inMsg.getToUserName());
		outMsg.setToUserName(inMsg.getFromUserName());
		outMsg.setCreateTime(new Date().getTime()); 
		String outContent = "";
		if(inMsg.getEvent().equals("subscribe")) {
//			outMsg.setMsgType("text");
//			outMsg.setContent("欢迎关注！！[微笑]");
			outMsg.setMsgType("news");
			outMsg.setArticleCount(3);
			ArticleItem item1 = new ArticleItem();
			item1.setTitle("特朗普");
			item1.setPicUrl("https://inews.gtimg.com/newsapp_bt/0/13046129559/1000");
			item1.setDescription("牛弹琴：果然很不一般，特朗普最后一天计划曝光！");
			item1.setUrl("https://new.qq.com/omn/20210116/20210116A023P600.html");
			
			ArticleItem item2 = new ArticleItem();
			item2.setTitle("习近平");
			item2.setPicUrl("https://inews.gtimg.com/newsapp_bt/0/13046129559/1000");
			item2.setDescription("牛弹琴：果然很不一般，特朗普最后一天计划曝光！");
			item2.setUrl("https://new.qq.com/omn/20210116/20210116A023P600.html");
			
			ArticleItem item3 = new ArticleItem();
			item3.setTitle("李克强");
			item3.setPicUrl("https://inews.gtimg.com/newsapp_bt/0/13046129559/1000");
			item3.setDescription("牛弹琴：果然很不一般，特朗普最后一天计划曝光！");
			item3.setUrl("https://new.qq.com/omn/20210116/20210116A023P600.html");
			
			outMsg.setItem(new ArticleItem[] {item1, item2, item3});
			
		} else if(inMsg.getEvent().equals("CLICK")) {
			String eventKey = inMsg.getEventKey();
			if("kaiban".equals(eventKey)) {
				outContent = "kaiban";
			} else if("add".equals(eventKey)) {
				outContent = "add";
			} else if("1".equals(eventKey)) {
				outContent = "历史消息展示";
			}
			outMsg.setContent(outContent);
			outMsg.setMsgType("text");
		}
		return outMsg;
	}

}
