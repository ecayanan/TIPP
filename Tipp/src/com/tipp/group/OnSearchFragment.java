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




public class OnSearchFragment extends Fragment{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_on_search, container, false);
		int groupSize = getArguments().getInt("groupSize", 0);
		ArrayList <String> groupList = getArguments().getStringArrayList("groupStringArray");
        // Inflate the layout for this fragment
		LinearLayout linearLayoutSearch = (LinearLayout)view.findViewById(R.id.linearlayoutsearch);
		TextView textArr[] = new TextView[groupSize];
		for(int i = 0; i < groupSize; i++){
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
