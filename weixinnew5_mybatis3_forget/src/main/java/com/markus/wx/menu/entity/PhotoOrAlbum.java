package com.markus.wx.menu.entity;

import java.util.ArrayList;
import java.util.List;

public class PhotoOrAlbum extends AbsButton{

	private String type = "pic_photo_or_album";
	private String key;
	private List<AbsButton> sub_button = new ArrayList<AbsButton>();
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<AbsButton> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<AbsButton> sub_button) {
		this.sub_button = sub_button;
	}

	public PhotoOrAlbum(String name, String key) {
		super(name);
		this.key = key;
	}
	
	
}
