package com.tipp.group;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tipp.R;



public class GroupFragment extends ListFragment{
	ArrayAdapter<String> adapter;
    ArrayList <String> groupNames;
    ArrayList <Integer> groupIds;
	private int grp;
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
 
      //get bundle and get all groups array
	  groupIds = getArguments().getIntegerArrayList("groupIds");
      groupNames = getArguments().getStringArrayList("groupMemberStringArray");
      super.onActivityCreated(savedInstanceState);
      adapter = new ArrayAdapter<String>(getActivity(),R.layout.light_blue, R.id.gsearchtitle, groupNames);
      setListAdapter(adapter);
    }
    
}
