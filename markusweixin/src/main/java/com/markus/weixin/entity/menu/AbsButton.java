package com.markus.weixin.entity.menu;

public abstract class AbsButton {
	//公共属性name
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
