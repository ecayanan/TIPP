package com.tipp.group;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.tipp.R;



public class GroupFragment extends Fragment{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group, container, false);
		ArrayList <String> groupList = getArguments().getStringArrayList("groupMemberStringArray");
        // Inflate the layout for this fragment
		LinearLayout linearLayoutSearch = (LinearLayout)view.findViewById(R.id.linearlayoutgroup);
		TextView textArr[] = new TextView[groupList.size()];
		for(int i = 0; i < groupList.size(); i++){
			TextView text = new TextView(view.getContext());
			text.setText((CharSequence) groupList.toArray()[i]);
			textArr[i] = text;
			textArr[i].setId(i);
			textArr[i].setTag(i);
			textArr[i].setTextSize(40);
			textArr[i].setClickable(true);
			textArr[i].setGravity(Gravity.CENTER);
			linearLayoutSearch.addView(textArr[i]);
		}
        return view;
    }
}
