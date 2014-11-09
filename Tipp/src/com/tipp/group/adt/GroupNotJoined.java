package com.tipp.group.adt;

import java.util.List;

public class GroupNotJoined implements Group{
	
	private String name = "";
	private List<String>members;
	
	public GroupNotJoined(String name){
		this.name = name;
		//members = new ArrayList();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
