package com.markus.wx;

import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;

import com.markus.wx.config.WXConfig;
import com.markus.wx.controller.WebController;
import com.markus.wx.token.entity.RefreshAccessToken;
import com.markus.wx.util.AccessTokenUtil;
import com.markus.wx.util.Materials;
import com.markus.wx.util.Util;
import com.markus.wx.util.WebAuthAccessTokenUtil;

public class TestWX {

	/**
	 * http:\/\/mmbiz.qpic.cn\/mmbiz_jpg\/SiaRSn312IPbBxFWk9PmLd0KffiacFIKDWzM0um1l7RSoGia11aiaTNsJaFTGwcUwkJXDhAibJsCuUSjj5OZ7Y69YibA\/0?wx_fmt=jpeg
	 * 对ACCESS_TOKEN的测试
	 * 
	 */
	
	@Test
	public void testGetRefreshAccessToken() {
//		
//		//{"access_token":"40_I4dCvTGjWiUvdmD9eHmdZMAQkE4PIDEnKacF_K0plr2-QN8hPW6_-AkSH5dRMgol33ZgbBoRX6jRtXKevjb9BScdmCRgRms6TuWiM22U2PAO_avP0QY-lhzOlUt7XVyEh-csCTyIXMma_ffoVJKhABAGUV","expires_in":7200}
//		System.out.println(testAccess.refreshAccessTokenStr);
		String url = "https:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/vi_32\\/DYAIOgq83epuQgaaLrxnezCtqudGiayMoziacpfiaIj9s8pja2VBlMjSGs03SCleJ9vbTDqZTpg6Iql96QH6L5gDg\\/132";
		url = url.replace("\\", "");
		
		String url2 = "http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/SiaRSn312IPbBxFWk9PmLd0KffiacFIKDWzM0um1l7RSoGia11aiaTNsJaFTGwcUwkJXDhAibJsCuUSjj5OZ7Y69YibA\\/0?wx_fmt=jpeg";
		url2 = url2.replace("\\", "");
		System.out.println(url2);
		
		//System.out.println(url);
	}
	
	/**
	 * 对refresh_ACCESS_TOKEN的测试
	 */
	@Test
	public void testGetAccessToken1() {
		
		//{"access_token":"40_I4dCvTGjWiUvdmD9eHmdZMAQkE4PIDEnKacF_K0plr2-QN8hPW6_-AkSH5dRMgol33ZgbBoRX6jRtXKevjb9BScdmCRgRms6TuWiM22U2PAO_avP0QY-lhzOlUt7XVyEh-csCTyIXMma_ffoVJKhABAGUV","expires_in":7200}

		try {
			System.out.println(WebAuthAccessTokenUtil.refreshAccessTokenStr);
			System.out.println(WebAuthAccessTokenUtil.getRefreshAccessToken());
			Thread.sleep(5000);
			System.out.println(WebAuthAccessTokenUtil.getRefreshAccessToken());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void testUploadForeverImg() {
		Materials.uploadForeverImg();
	}
	
	@Test
	public void testGetMaterials() {
		String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", AccessTokenUtil.getAccessToken() );
		System.out.println(url);
		
		String data = "{\r\n" + 
				"    \"type\":image,\r\n" + 
				"    \"offset\":0,\r\n" + 
				"    \"count\":10\r\n" + 
				"}";
		String result = Util.post(url, data);
		//String result = Util.get(url);
		System.out.println(result);
	}
		
}
