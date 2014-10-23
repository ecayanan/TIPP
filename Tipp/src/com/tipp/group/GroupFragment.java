package com.tipp.group;


import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tipp.R;

public class GroupFragment extends Fragment{

	public GroupFragment(){
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_group, container, false);
		Button btnAddGroup = (Button) view.findViewById(R.id.buttonAddGroup);
		btnAddGroup.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Button myButton = new Button(getActivity());
				myButton.setText("Group1");
				LinearLayout l1 = (LinearLayout)getActivity().findViewById(R.id.groupLayout);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				l1.addView(myButton, lp);
			}
		});
			
		return view;
	}
}
