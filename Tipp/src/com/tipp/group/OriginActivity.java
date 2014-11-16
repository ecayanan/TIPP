package com.tipp.group;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.tipp.R;
     
     
public class OriginActivity extends Activity{
 
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      Bundle bundle = new Bundle();
	      bundle = getIntent().getExtras();
	      setContentView(R.layout.activity_origin);
		  FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
          OriginFragment of = new OriginFragment();
          of.setArguments(bundle);
          fragmentTransaction.add(R.id.main_container, of);
          fragmentTransaction.commit();
	  }
	  
}

