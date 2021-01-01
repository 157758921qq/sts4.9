package com.markus.wx.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;



public class Util {
	
	/**
	 * 	向指定的url地址，发送get请求，
	 * 	返回通过输入流读取返回值
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		try {
			URL urlObj = new URL(url);
			//开连接
			URLConnection connection = urlObj.openConnection();
			InputStream is = connection.getInputStream();
			byte[] b = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while((len = is.read(b)) != -1) {
				sb.append(new String(b, 0, len));
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//向指定地址发送post请求，并带数据
	public static String post(String url, String data) {
		try {
			URL urlObj = new URL(url);
			//开连接
			URLConnection connection = urlObj.openConnection();
			//要发送数据出去，必须要设置为可发送数据状态
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			os.write(data.getBytes());
			os.close();
			
			InputStream is = connection.getInputStream();
			byte[] b = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while((len = is.read(b)) != -1) {
				sb.append(new String(b, 0, len));
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
