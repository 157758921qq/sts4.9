package com.yz.wx.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.EnableLoadTimeWeaving;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias(value = "xml")
public class VideoMessage extends BaseMessage {
	@XStreamAlias("Video")
	Video video;
	
	
	public Video getVideo() {
		return video;
	}


	public void setVideo(Video video) {
		this.video = video;
	}


	public VideoMessage(Map<String, String> requstMap, Video video) {
		super(requstMap);
		this.setMsgType("video");
		this.video = video;
	}
	
}
