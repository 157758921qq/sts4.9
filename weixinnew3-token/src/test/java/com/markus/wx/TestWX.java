package com.markus.wx;

import org.junit.jupiter.api.Test;

import com.markus.wx.util.AccessTokenUtil;

public class TestWX {

	/**
	 * 对ACCESS_TOKEN的测试
	 */
	@Test
	public void TestGetAccessToken() {
		
		//{"access_token":"40_I4dCvTGjWiUvdmD9eHmdZMAQkE4PIDEnKacF_K0plr2-QN8hPW6_-AkSH5dRMgol33ZgbBoRX6jRtXKevjb9BScdmCRgRms6TuWiM22U2PAO_avP0QY-lhzOlUt7XVyEh-csCTyIXMma_ffoVJKhABAGUV","expires_in":7200}

		try {
			System.out.println(AccessTokenUtil.getAccessToken());
			Thread.sleep(5000);
			System.out.println(AccessTokenUtil.getAccessToken());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
			
	
	}
}
