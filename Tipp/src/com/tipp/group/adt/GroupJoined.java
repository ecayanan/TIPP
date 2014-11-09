package com.tipp.group.adt;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.tipp.user.adt.User;

public class GroupJoined implements Group{
	
	private String name = "";
	private String groupId;
	private List<User>members;
	
	public GroupJoined(String name){ 
		setName(name);
		setMembers(null);
	}
	
	public void setName(String groupName) {
		this.name = groupName;
	}
	@Override
	public String getName() {
		return name;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}
}
