package com.markus.weixin.entity.menu;

import java.util.ArrayList;
import java.util.List;

public class SubButton extends AbsButton{

	private List<AbsButton> sub_button = new ArrayList<AbsButton>();

	public List<AbsButton> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<AbsButton> sub_button) {
		this.sub_button = sub_button;
	}

	public SubButton(String name) {
		super(name);
	}
	
	
	
}
