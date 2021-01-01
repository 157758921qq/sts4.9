package com.yz.wx.uploadimg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.yz.wx.service.WxService;

public class UploadImg2 {

	

	//https 永久上传 图片 post 文件
	public static String upload(String path, String type) {
		File file = new File(path);
		String url ="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE ";

		url = url.replace("ACCESS_TOKEN", WxService.getAccessToken()).replace("TYPE", type);
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
