package com.yz.wx.entity;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "xml")
public class TextMessage extends BaseMessage{
	@XStreamAlias("Content")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public TextMessage(Map<String, String> requestMap, String content) {
		super(requestMap);
		this.setMsgType("text");
		this.content = content;
	}
	
	
	
	
}