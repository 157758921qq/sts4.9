package com.markus.wx.mapper;

import com.markus.wx.account.entity.Account;
import org.springframework.stereotype.Repository;

/**
 * AccountMapper继承基类
 */
@Repository
public interface AccountMapper extends MyBatisBaseDao<Account, Integer, AccountExample> {
}