package com.markus.wx.replymessage.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "xml")
public class ImageMessage extends BaseMessage {
	@XStreamAlias("Image")
	Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public ImageMessage(Map<String, String> requestMap, Image image) {
		super(requestMap);
		this.setMsgType("image");
		this.image = image;
		
	}

	

	
	

}
