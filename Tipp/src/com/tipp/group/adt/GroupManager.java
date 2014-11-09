package com.tipp.group.adt;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

public class GroupManager {

	private List<GroupJoined> gj;
	private List<GroupNotJoined> gnj;
	
	public GroupManager(JSONObject obj){
		gj = new ArrayList<GroupJoined>();
		gnj = new ArrayList<GroupNotJoined>();
		
		try {
			JSONArray groupNotJoinedJSON = obj.getJSONArray("groupsNotJoined");
	        JSONArray groupJoinedJSON = obj.getJSONArray("groupsJoined");
	       
	        //Log.d("ONPOSTEXECUTE", "starting post execute");
	        for(int i = 0; i < groupNotJoinedJSON.length(); i++)
	        {
	                JSONObject childGroupJSON = groupNotJoinedJSON.getJSONObject(i);
	                gnj.add(new GroupNotJoined (childGroupJSON.getString("name")));                       
	        }
	        //Log.d("GROUPS_LIST", tempArray);
	        for(int i = 0; i < groupJoinedJSON.length(); i++)
	        {
	                JSONObject childGroupJSON = groupJoinedJSON.getJSONObject(i);
	                gj.add(new GroupJoined(childGroupJSON.getString("name")));                         
	        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getGroupJoinedName(){
		ArrayList<String> groupNames = new ArrayList<String>();
		for(int i = 0; i < gj.size(); i++)
			groupNames.add(((GroupJoined) gj.toArray()[i]).getName());
		return groupNames;
	}
	
	public ArrayList<String> getGroupNotJoinedName(){
		ArrayList<String> groupNames = new ArrayList<String>();
		for(int i = 0; i < gnj.size(); i++)
			groupNames.add(((GroupNotJoined) gnj.toArray()[i]).getName());
		return groupNames;
	}
	
	public Bundle getGroupJoinedBundle(){
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("groupMemberStringArray", getGroupJoinedName());
		return bundle;
	}
	public Bundle getGroupNotJoinedBundle(){
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("groupStringArray", getGroupNotJoinedName());
		return bundle;
	}
}
