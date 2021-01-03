package com.markus.wx;

import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.markus.wx.util.AccessTokenUtil;
import com.markus.wx.util.Materials;
import com.markus.wx.util.Util;

public class TestWX {

	/**
	 * 对ACCESS_TOKEN的测试
	 */
	@Test
	public void testGetAccessToken() {
		
		//{"access_token":"40_I4dCvTGjWiUvdmD9eHmdZMAQkE4PIDEnKacF_K0plr2-QN8hPW6_-AkSH5dRMgol33ZgbBoRX6jRtXKevjb9BScdmCRgRms6TuWiM22U2PAO_avP0QY-lhzOlUt7XVyEh-csCTyIXMma_ffoVJKhABAGUV","expires_in":7200}

		try {
			System.out.println(AccessTokenUtil.getAccessToken());
			Thread.sleep(5000);
			System.out.println(AccessTokenUtil.getAccessToken());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUploadForeverImg() {
		Materials.uploadForeverImg();
	}
		
}
