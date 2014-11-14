package com.tipp.groupname;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tipp.R;

public class GroupNameFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_name, container, false);
		startReviewFragment(view);
		Button btnReview = (Button)view.findViewById(R.id.btnReviews);
		btnReview.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startReviewFragment(v);
			}
			
		});
		Button btnMember = (Button)view.findViewById(R.id.btnMembers);
		btnMember.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startMemberFragment(v);
			}
			
		});
        return view;
    }
	
	public void startReviewFragment(View v){
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        GroupReviewFragment grf = new GroupReviewFragment();
        grf.setArguments(this.getArguments());
        fragmentTransaction.replace(R.id.group_name_container, grf);
        fragmentTransaction.commit();
	}
	
	public void startMemberFragment(View v){
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        GroupMemberFragment gmf = new GroupMemberFragment();
        //grf.setArguments(this.getArguments());
        fragmentTransaction.replace(R.id.group_name_container, gmf);
        fragmentTransaction.commit();
	}
}
