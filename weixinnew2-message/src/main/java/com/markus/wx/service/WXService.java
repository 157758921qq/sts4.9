package com.markus.wx.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.markus.wx.config.WXConfig;
import com.markus.wx.replymessage.entity.BaseMessage;
import com.markus.wx.replymessage.entity.ImageMessage;
import com.markus.wx.replymessage.entity.MusicMessage;
import com.markus.wx.replymessage.entity.NewsMessage;
import com.markus.wx.replymessage.entity.TextMessage;
import com.markus.wx.replymessage.entity.VideoMessage;
import com.markus.wx.replymessage.entity.VoiceMessage;
import com.thoughtworks.xstream.XStream;






@Service
public class WXService {

	/**
	 * 根据微信服务器发送过来的值，进行验证
	 * 
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @return
	 */
	public boolean check(String timestamp, String nonce, String signature) {
		// 1、将token 、timestamp、 nonce 三个参数字典排序
		String[] strs = new String[] { WXConfig.TOKEN, timestamp, nonce };
		Arrays.sort(strs);
		// 2将三个参数字符串拼接成一个字符串，再加密
		String str = strs[0] + strs[1] + strs[2];
		String mysig = sha1(str);
		// System.out.println(mysig.toString());
		// System.out.println(signature.toString());
		return mysig.equals(signature);
	}

	// sha加密
	private String sha1(String str) {
		try {
			// 获取加密对象
			MessageDigest md = MessageDigest.getInstance("sha1"); // md5
			// 加密
			byte[] digest = md.digest(str.getBytes());
			char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			StringBuilder sb = new StringBuilder();
			// 处理加密结果
			for (byte b : digest) {
				sb.append(chars[(b >> 4) & 15]);
				sb.append(chars[b & 15]);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	/**
	 * 	从输入流中 将xml 转成 map 格式
	 * 	Dom4j类
	 * @param inputStream
	 * @return
	 */
	public Map<String, String> fromRequsetParseXMLToMap(ServletInputStream inputStream) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		try {
			// 读取输入流获取文档对象
			Document document = reader.read(inputStream);
			// 根据文档对象获取根节点
			Element root = document.getRootElement();
			List<Element> elements = root.elements();
			for (Element e : elements) {
				map.put(e.getName(), e.getStringValue());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	
	/**
	 * 	从requestMap中获取MsgType
	 * 	根据接收的消息MsgType类型，进行处理或回复
	 * 	接收到的消息的MsgType类型有：
	 * 	接收到的普通消息：文本消息text 、图片消息image、语音消息voice、
	 * 		       视频消息video、 小视频消息shortvideo、地理位置location
	 * 		        链接消息link
	 * 
	 * 	接收到的事件推送：  MsgType	消息类型，event
	 * 			关注或取消              	Event 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	 * 
	 * 			扫码带参数二维码    	Event	事件类型，subscribe
	 * 			上报地理位置           	Event	事件类型，LOCATION
	 * 	  	
	 * 			自定义菜单			Event	事件类型，CLICK
	 * 						EventKey	事件KEY值，与自定义菜单接口中KEY值对应
	 * 
	 * 			点击菜单拉取消息时             Event	事件类型，CLICK
	 * 			点击菜单跳转 		Event	事件类型，VIEW
	 * 
	 * 
	 * 		回复处理
	 * 		1、文本
	 * 		2、图片
	 * 		3、语音
	 * 		4、视频
	 * 		5、音乐
	 * 		6、图文
	 * @param requestMap
	 * @return
	 */
	public String replyMessageToUser(Map<String, String> requestMap) {
		BaseMessage msg = null;
		String msgType = requestMap.get("MsgType");
		// 根据不同的消息类型做相应的处理
		switch (msgType) {
		case "text":
			// 根据不同的消息类型，开始处理具体的消息
			msg = dealTextMessage(requestMap);
			break;
		case "image":
			
			break;
		case "voice":
			break;
		case "video":
			break;
		case "shortvideo":
			break;
		case "location":
			break;
		case "link":
		
			break;

		case "event":
			
			break;

		}
		// System.out.println(msg);
		if (msg != null) {
			//将对象转成XML
			return objectToXml(msg);
		}
		return null;
	}

	

	//回复一条Text文本类型的消息
	private BaseMessage dealTextMessage(Map<String, String> requestMap) {
		TextMessage tm = new TextMessage(requestMap, "欢迎您来到Markus的公众号，目前公众号还在建设中，敬请期待！");
		return tm;
	}

	private String objectToXml(BaseMessage msg) {
		XStream stream = new XStream();
		// 设置需要处理的XStreamAlias("xml")注释的类
		stream.processAnnotations(TextMessage.class);
		stream.processAnnotations(ImageMessage.class);
		stream.processAnnotations(MusicMessage.class);
		stream.processAnnotations(NewsMessage.class);
		stream.processAnnotations(VideoMessage.class);
		stream.processAnnotations(VoiceMessage.class);
		String xml = stream.toXML(msg);
		return xml;
	}
}
