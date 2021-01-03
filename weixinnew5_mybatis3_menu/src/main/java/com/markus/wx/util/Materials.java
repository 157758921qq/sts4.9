package com.markus.wx.util;

import java.io.File;


public class Materials {

	
	
	public static void uploadForeverImg() {

		String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE ";
		url = url.replace("ACCESS_TOKEN", AccessTokenUtil.getAccessToken()).replace("TYPE", "image");
		
		String file = "C:"+File.separator+"snakebg.jpg";   //"c:" + File.separator + 
		String result = Util.upload(file, url);
		System.out.println("永久图片上传"+result);
		//永久图片上传{"media_id":"6bjdeUIo8prvpGCwgeqILi39zSWTn1PkJB5Ig6DcWJw","url":"http:\/\/mmbiz.qpic.cn\/mmbiz_jpg\/SiaRSn312IPbBxFWk9PmLd0KffiacFIKDWzM0um1l7RSoGia11aiaTNsJaFTGwcUwkJXDhAibJsCuUSjj5OZ7Y69YibA\/0?wx_fmt=jpeg","item":[]}

	}
}
