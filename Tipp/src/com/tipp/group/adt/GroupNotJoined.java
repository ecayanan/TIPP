package com.tipp.group.adt;

import java.util.List;

public class GroupNotJoined implements Group{
	
	private String name = "";
	private int groupId;
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

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}
