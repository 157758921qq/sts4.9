package com.markus.wx.replymessage.entity;


import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "xml")
public class MusicMessage extends BaseMessage{
	@XStreamAlias("Music")
	Music music;
	
	
	public Music getMusic() {
		return music;
	}


	public void setMusic(Music music) {
		this.music = music;
	}


	public MusicMessage(Map<String, String> requstMap, Music music) {
		super(requstMap);
		this.setMsgType("music");
		this.music = music;
	}

}
