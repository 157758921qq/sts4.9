package com.markus.weixin;

import org.junit.jupiter.api.Test;

import com.markus.weixin.util.AccessTokenUtil;
import com.markus.weixin.util.CreateMenuUtil;
import com.markus.weixin.util.MaterialsUtil;
import com.markus.weixin.util.SetIndustry;

public class TestWX {

	//获取AccessToken
	@Test
	public void testToken() {
		AccessTokenUtil accessToken = new AccessTokenUtil();
		try {
			System.out.println(accessToken.getAccessToken());
			Thread.sleep(5000);
			System.out.println(accessToken.getAccessToken());
			Thread.sleep(10000);
			System.out.println(accessToken.getAccessToken());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	
	
	//自定义菜单
	@Test
	public void TestMenu() {
		CreateMenuUtil.createMenu();
		
	}
	
	
	//设置所属行业
	@Test
	public void TestSetIndustry() {
		SetIndustry.setIndustry();
	}
	
	//获取所属行业
	@Test
	public void TestGetIndustry() {
		SetIndustry.getIndustry();
	}
	
	//上传永久图片
	@Test
	public void TestUpForeverImg() {
		MaterialsUtil.upForeverImg();
		//永久media_id:6bjdeUIo8prvpGCwgeqILgZqUvGS3iSu6NRdJi90_u0

	}
	
	
	//上传图文素材
	@Test
	public void TestUpForeverNews() {
		MaterialsUtil.upForeverNews();
		//{"media_id":"6bjdeUIo8prvpGCwgeqILgXAmp288h2-lRdfYUuX2t8","item":[]}
		//{"media_id":"6bjdeUIo8prvpGCwgeqILgIPxtJJaHT6C7XiVLX8Xf4","item":[]}
//{"media_id":"6bjdeUIo8prvpGCwgeqILr20My52ONy0LkBrJ_2Z8PE","item":[]}
//{"media_id":"6bjdeUIo8prvpGCwgeqILvdAsEJk5fjHaW3TtJhasgE","item":[]}




	}
	

	//获取永久图片
	@Test
	public void TestgetForeverImg() {
		MaterialsUtil.getForeverImg();
	}
	
	//获取永久图片
	@Test
	public void TestgetForeverNews() {
		MaterialsUtil.getForeverNews();
	}
	
	
}






















