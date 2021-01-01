package com.markus.wx.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.markus.wx.config.WXConfig;




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

}
