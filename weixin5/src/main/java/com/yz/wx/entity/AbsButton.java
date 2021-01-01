package com.yz.wx.entity;

public abstract class AbsButton {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AbsButton(String name) {
		super();
		this.name = name;
	}
	
	
}
