package com.tipp.group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tipp.R;

public class MenuFragment extends Fragment{

	Fragment frag;
	FragmentTransaction fragTransaction;
	
	public MenuFragment(){
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_menu, container, false);
		frag = new GroupFragment();
		fragTransaction = getFragmentManager().beginTransaction().add(R.id.container, frag);
		fragTransaction.commit();
		return view;
	}
}
