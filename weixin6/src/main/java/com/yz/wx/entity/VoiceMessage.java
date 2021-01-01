package com.yz.wx.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 *  
 *  ToUserName	开发者微信号
	FromUserName	发送方帐号（一个OpenID）
	CreateTime	消息创建时间 （整型）
	MsgType	语音为voice
	MediaId	语音消息媒体id，可以调用获取临时素材接口拉取数据。
	Format	语音格式，如amr，speex等
	MsgId	消息id，64位整型
 * @author Administrator
 *
 */
@XStreamAlias(value = "xml")
public class VoiceMessage extends BaseMessage {
	@XStreamAlias("Voice")
	Voice voice;

	public Voice getVoice() {
		return voice;
	}


	public void setVoice(Voice voice) {
		this.voice = voice;
	}


	public VoiceMessage(Map<String, String> requstMap, Voice voice) {
		super(requstMap);
		this.setMsgType("voice");
		this.voice = voice;
	}


	
}
