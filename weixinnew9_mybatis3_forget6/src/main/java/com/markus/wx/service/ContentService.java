package com.markus.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markus.wx.cooperate.entity.Coorperate;
import com.markus.wx.mapper.CoorperateMapper;

@Service
public class ContentService {

	@Autowired
	CoorperateMapper cooMapper;
	
	public int save(String textareaDiv) {
		Coorperate coorperate = new Coorperate();
		coorperate.setContent(textareaDiv);
		int result = cooMapper.insertSelective(coorperate);
		return result;
	}
	

}
