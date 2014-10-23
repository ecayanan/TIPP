package com.tipp.group;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.tipp.R;

public class OriginActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_origin);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.menuContainer, new MenuFragment()).commit();
		}
	}
}