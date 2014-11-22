package com.tipp.groupname;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tipp.R;

public class GroupNameFragment extends Fragment {
	
	static View change_header_View;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_name, container, false);
		change_header_View = view;
		startReviewFragment(view);
		
		final ImageButton btnReview = (ImageButton)view.findViewById(R.id.btnReviews);
		final ImageButton btnMember = (ImageButton)view.findViewById(R.id.btnMembers);
		/*final Button btnWrite = (Button)view.findViewById(R.id.btnWrite);
		btnWrite.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				//btnReview.setImageResource(R.drawable.reviewcurrent);
				//btnMember.setImageResource(R.drawable.members);
				startWriteFragment(v);
			}
			
		});*/
		btnReview.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				btnReview.setImageResource(R.drawable.reviewcurrent);
				btnMember.setImageResource(R.drawable.members);
				startReviewFragment(v);
			}
			
		});
		
		btnMember.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				btnReview.setImageResource(R.drawable.review);
				btnMember.setImageResource(R.drawable.memberscurrent);
				startMemberFragment(v);
			}
			
		});
        return view;
    }
	
	protected void startWriteFragment(View v) {
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        GroupWriteReviewFragment grf = new GroupWriteReviewFragment();
        grf.setArguments(this.getArguments());
        fragmentTransaction.replace(R.id.main_container, grf);
        fragmentTransaction.commit();
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
        gmf.setArguments(this.getArguments());
        fragmentTransaction.replace(R.id.group_name_container, gmf);
        fragmentTransaction.commit();
	}
}
