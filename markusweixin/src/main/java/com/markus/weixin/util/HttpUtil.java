package com.markus.weixin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;


public class HttpUtil {
	
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
	
	//https 发送post 文件
		public static String upload(String path,String url) {
			File file = new File(path);
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
