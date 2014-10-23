package com.tipp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tipp.group.OriginActivity;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	public void startOriginActivity(View v){
		Intent intent = new Intent(getApplicationContext(), OriginActivity.class);
		startActivity(intent);
	}
}
