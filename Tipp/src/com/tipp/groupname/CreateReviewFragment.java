package com.tipp.groupname;

import com.tipp.R;
import com.tipp.R.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateReviewFragment extends Fragment {
	private int userid;
	private int groupid;
	private int memberid;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_create_review, container, false);
		userid = getArguments().getInt("userId");
		groupid = getArguments().getInt("groupId");
		memberid = getArguments().getInt("memberId");
		return view;
	}
	
	

}
