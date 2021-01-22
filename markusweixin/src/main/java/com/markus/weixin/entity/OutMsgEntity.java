package com.markus.weixin.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 *   被动回复消息
 *   	1、文本
 *   	2、图片
 *   	3、语音
 *   	4、视频
 *   	5、音乐
 *   	6、图文
 * 
 * @author Administrator
 *
 */
public class OutMsgEntity {
	//消息共有的，应该抽象出类
	//如果这个变量名是小写，比如toUserName,而xml里却是大写ToUserName
	//可以在需要的字段上贴,注解 @XmlElement(name="ToUserName")
	private String ToUserName;
	private String FromUserName;
	private Long CreateTime;
	private String MsgType;     //text image voice
	
	
	//文本消息独有的
	private String Content;
	
	
	//图片消息独有
	//增加二级目录
	@XmlElementWrapper(name="Image")
	private String[] MediaId;
	
	
	//图文消息
	private Integer ArticleCount; //图文个数
	
	//图文列表明细
	//@XmlElementWrapper注解可以在原xml结点上再包装一层xml
	@XmlElementWrapper(name="Articles")
	private ArticleItem[] item;

}








