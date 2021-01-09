package com.markus.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markus.wx.account.entity.Account;
import com.markus.wx.mapper.AccountMapper;
import com.markus.wx.util.RespStat;

@Service
public class RegisterService {

	@Autowired
	AccountMapper accMapper;

	public int insert(Account acc) {
	try {
			int row = accMapper.insertSelective(acc);
			System.out.println("result = " + row);
			if (row == 1)
				return 1;
			else
				return -1;
		} catch (Exception e) {
			return -1;
		}
	}
}
