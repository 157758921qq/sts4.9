package com.markus.weixin.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * 
 * 	接收到的消息分：
 * 		1、普通消息：
 * 				文本text、图片image、语音voice、
 * 				视频video(小视频shortvideo)、地理位置location、链接link消息
 * 		2、事件消息
 * 				 关注/取消关注事件、扫描带参数二维码事件、上报地理位置事件、自定义菜单事件、
 * 				 点击菜单拉取消息时的事件推送、点击菜单跳转链接时的事件推送
 * 
 * 
 * @author Administrator
 *
 */
public class InMsgEntity {
	//普通消息和事件消息共有的，应该抽象出类
	//如果这个变量名是小写，比如toUserName,而xml里却是大写ToUserName
	//可以在需要的字段上贴,注解 @XmlElement(name="ToUserName")
	private String ToUserName;
	private String FromUserName;
	private Long CreateTime;
	private String MsgType;     //text image voice
	
	//普通消息独有
	private Long MsgId;
	
	
	//文本消息独有的
	private String Content;
	
	
	//图片、语言、视频共有MediaId
	private String MediaId;
	
	//image独有
	private String PicUrl;
	
	//语音独有
	private String Format;

	//视频独有
	private String ThumbMediaId;  //视频消息缩略图的媒体id，可以调用获取临时素材接口拉取数据。
	
	
	//地理位置
	private String Location_X;
	private String Location_Y;
	private String Scale;
	private String Label;
	
	//链接消息
	private String Title;
	private String Description;
	private String Url;
		
	
	
	//事件独有的
	//关注和取消
	private String Event;   //subscribe(订阅)、unsubscribe(取消订阅)
	
	//扫描带参数二维码事件
	private String EventKey;
	private String Ticket;
	
	//上报地理位置事件
	private String Latitude;
	private String Longitude;
	private String Precision;
	
	//自定义菜单事件,已有
	
	
	
	
	
	
	
	
	
	
	
	

}
