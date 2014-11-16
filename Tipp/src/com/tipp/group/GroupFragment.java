package com.tipp.group;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.tipp.R;
import com.tipp.groupname.GroupNameFragment;



public class GroupFragment extends ListFragment{
	ArrayAdapter<String> adapter;
    ArrayList <String> groupNames;
    ArrayList <Integer> groupIds;
	private int grp;
	private String currentUserId = "";
	
	   
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {

	        View view =  inflater.inflate(R.layout.fragment_group,container,false);
	        
	   
	   return view;
	   
    }
	
	
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
 
      //get bundle and get all groups array
      currentUserId = getArguments().getString("user_ID");
      //Log.d("groupFRagment userid = ", currentUserId);
	  groupIds = getArguments().getIntegerArrayList("groupIds");
      groupNames = getArguments().getStringArrayList("groupMemberStringArray");
      super.onActivityCreated(savedInstanceState);
      adapter = new ArrayAdapter<String>(getActivity(),R.layout.group_view, R.id.gsearchtitle, groupNames);
      setListAdapter(adapter);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
   	  super.onListItemClick(l,  v,  position, id);
	  FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
      GroupNameFragment gnf = new GroupNameFragment();
      //gnf.setArguments(this.getArguments());
      Bundle bundle = new Bundle();
      bundle.putInt("groupid", (Integer)groupIds.toArray()[position]);
      bundle.putString("user_ID", currentUserId);
      gnf.setArguments(bundle);
      fragmentTransaction.replace(R.id.main_container, gnf);
      fragmentTransaction.addToBackStack(null);
      fragmentTransaction.commit();
    }
    
}
