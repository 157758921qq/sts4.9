package com.markus.wx.mapper;

import com.markus.wx.cooperate.entity.Coorperate;
import org.springframework.stereotype.Repository;

/**
 * CoorperateMapper继承基类
 */
@Repository
public interface CoorperateMapper extends MyBatisBaseDao<Coorperate, Integer, CoorperateExample> {
}