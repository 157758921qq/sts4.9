package com.markus.weixin.entity.menu;

import java.util.ArrayList;
import java.util.List;

public class Button extends AbsButton{

	public Button(String name) {
		super(name);
	}
	//button
	private List<AbsButton> button = new ArrayList<>();

	public List<AbsButton> getButton() {
		return button;
	}

	public void setButton(List<AbsButton> button) {
		this.button = button;
	}

}
