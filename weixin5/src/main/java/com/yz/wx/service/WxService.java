package com.yz.wx.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
import com.yz.wx.config.WxConfig;
import com.yz.wx.entity.AccessToken;
import com.yz.wx.entity.Article;
import com.yz.wx.entity.BaseMessage;
import com.yz.wx.entity.ImageMessage;
import com.yz.wx.entity.MusicMessage;
import com.yz.wx.entity.NewsMessage;
import com.yz.wx.entity.TextMessage;
import com.yz.wx.entity.VideoMessage;
import com.yz.wx.entity.VoiceMessage;
import com.yz.wx.util.Util;

import net.sf.json.JSONObject;

@Service
public class WxService {

	public static final String APPID = "wx98b0a5b92b436370";
	public static final String APPSECRET = "5d1e238c98806e1f4bb0fde9b0a70bda";
	public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?"
			+ "grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET + "";

	public static AccessToken at;

	/**
	 * 1）将token、timestamp、nonce三个参数进行字典序排序 2）将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * 
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @return
	 */
	// 验证签名
	public boolean check(String timestamp, String nonce, String signature) {
		// 1 将token 、timestamp、 nonce 三个参数字典排序
		String[] strs = new String[] { WxConfig.TOKEN, timestamp, nonce };
		Arrays.sort(strs);
		// 2将三个参数字符串拼接成一个字符串，再加密
		String str = strs[0] + strs[1] + strs[2];
		String mysig = sha1(str);
		// System.out.println(mysig.toString());
		// System.out.println(signature.toString());
		return mysig.equals(signature);
	}

	// sha1加密
	private String sha1(String src) {
		try {
			// 获取加密对象
			MessageDigest md = MessageDigest.getInstance("sha1"); // md5
			// 加密
			byte[] digest = md.digest(src.getBytes());
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

	// 解析XML to Map
	// 从流里面读取XML的信息，并放在Map中，返回Map
	public Map<String, String> parseRequsetXMLToMap(InputStream is) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		try {
			// 读取输入流获取文档对象
			Document document = reader.read(is);
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

	// 处理所有的事件和消息的回复
	// 返回的是xml数据包
	public String getResponseFromMap(Map<String, String> requestMap) {
		BaseMessage msg = null;
		String msgType = requestMap.get("MsgType");
		// 根据不同的消息类型做相应的处理
		switch (msgType) {
		case "text":
			// 根据不同的消息类型，开始处理具体的消息
			msg = dealTextMessage(requestMap);
			break;
		case "image":
			msg = dealNewsMessage(requestMap);
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
		case "news":
			msg = dealNewsMessage(requestMap);
			break;

		case "event":
			msg = dealEventMesssage(requestMap);
System.out.println("event msg = " + msg.toString());
			break;

		}
		// System.out.println(msg);
		if (msg != null) {
			return beanToXml(msg);
		}
		return null;
	}

	// 事件推送处理
	//CLICK
	private BaseMessage dealEventMesssage(Map<String, String> requestMap) {
		BaseMessage msg = null;
		String event = requestMap.get("Event");
		switch (event) {
		case "CLICK":
			msg =  dealClickEvent(requestMap);
			break;
		case "VIEW":
			msg = dealViewEvent(requestMap);
			break;
		}
		return msg;
	}
	
	
	

	private BaseMessage dealViewEvent(Map<String, String> requestMap) {
		
		return new BaseMessage(requestMap);
	}

	// 根据eventKey的值来处理
	private BaseMessage dealClickEvent(Map<String, String> requestMap) {
		String key = requestMap.get("EventKey");
System.out.println("EventKey = " +key);

		switch (key) {
		case "1":
			return new TextMessage(requestMap, "你点个锤子，这里是一级CLICK菜单，我准备给你返回一个图文消息,或者说是微信的图文消息链接汇总地址");
			
		case "32":
			List<Article> articles = new ArrayList<Article>();
			Article article1 = new Article("我的家乡有高铁", "家乡 高大上",
					"http://mmbiz.qpic.cn/mmbiz_jpg/KibYFFIUMm70gjT4MDATDqX8icc27ib90dwHQjSx5ns2CPqV3Kbia9ZCsQj4PqADJXuvxUjK19psKufH9Q9YLs9Dfw/0",
					"www.baidu.com");
			Article article2 = new Article("家乡美", "UPUPUP！",
					"http://mmbiz.qpic.cn/mmbiz_jpg/KibYFFIUMm70gjT4MDATDqX8icc27ib90dwHQjSx5ns2CPqV3Kbia9ZCsQj4PqADJXuvxUjK19psKufH9Q9YLs9Dfw/0",
					"www.baidu.com");
			Article article3 = new Article("美女图片", "妹妹美",
					"http://mmbiz.qpic.cn/mmbiz_jpg/KibYFFIUMm73z8uOlriabcfCq1VDbS2vs683JkFzrwarqA7kiadO4VK5oMjZb20f1pRkNWMCFica0VhLknAeropNDQ/0",
					"www.baidu.com");
			articles.add(article1);
			articles.add(article2);
			articles.add(article3);
			return new NewsMessage(requestMap, articles);

		}
		
		return null;
	}

	private String beanToXml(BaseMessage msg) {
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

	// 处理Text格式的信息
	private BaseMessage dealTextMessage(Map<String, String> requestMap) {
		String msg = requestMap.get("Content");
		String resp = chat(msg);
		TextMessage tm = new TextMessage(requestMap, resp);
		return tm;
	}

	private String chat(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	private BaseMessage dealNewsMessage(Map<String, String> requestMap) {
		List<Article> articles = new ArrayList<Article>();
		Article article = new Article("我的家乡", "家乡美",
				"http://mmbiz.qpic.cn/mmbiz_jpg/KibYFFIUMm73z8uOlriabcfCq1VDbS2vs683JkFzrwarqA7kiadO4VK5oMjZb20f1pRkNWMCFica0VhLknAeropNDQ/0",
				"www.baidu.com");
		articles.add(article);
		NewsMessage news = new NewsMessage(requestMap, articles);
		return news;
	}

	private static void getToken() {
		String tokenStr = Util.get(GET_TOKEN_URL);
		System.out.println("Token = " + tokenStr);
		// String类封装成json对象
		JSONObject jsonObject = JSONObject.fromObject(tokenStr);
		String token = jsonObject.getString("access_token");
		String expiressIn = jsonObject.getString("expires_in");
		at = new AccessToken(token, expiressIn);
	}

	public static String getAccessToken() {
		if (at == null || at.isExpired())
			getToken();
		return at.accessToken;
	}
	
	
	
	//https 发送post 文件
	public static String upLoad(String path, String type) {
		File file = new File(path);
		String url ="https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+WxService.getAccessToken()+"&type="+type+"";
		try {
			URL urlObj = new URL(url);
			//强转为安全连接
			HttpsURLConnection con = (HttpsURLConnection) urlObj.openConnection();
			//设置连接信息
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			
			//设置请求头的信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "utf8");
			con.setRequestMethod("POST");
			//数据的边界
			String boundary = "------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			
			OutputStream out = con.getOutputStream();
			
			InputStream is = new FileInputStream(file);
			//写的数据
			//第一部分：头部信息
			//准备头部信息
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition:form-data;name=\"media\";filename=\""+file.getName()+"\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			System.out.println(sb.toString());
			out.write(sb.toString().getBytes());
			
			//第二部分：文件内容
			byte[] b = new byte[1024];
			int len;
			while((len = is.read(b)) != -1) {
				out.write(b, 0, len);
			}
			
			//第三部分：尾部信息
			String foot = "\r\n--"+boundary+"--\r\n";
			out.write(foot.getBytes());
			out.flush();
			out.close();
			//写出数据结束
			
			
			//读取信息
			InputStream is2 = con.getInputStream();
			StringBuilder resp = new StringBuilder();
			while((len = is2.read(b)) != -1) {
				resp.append(new String(b, 0, len));
			}
			System.out.println("resp = " + resp.toString());
			return resp.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	

}
