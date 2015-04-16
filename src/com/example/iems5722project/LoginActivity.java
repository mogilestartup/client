package com.example.iems5722project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class LoginActivity extends BaseActivity {
	
	private ImageView loginGo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(checkUserLoginStatus()){
			Intent intent = new Intent(LoginActivity.this, Tab_UI.class);
			startActivity(intent);
		}
		setContentView(R.layout.login_activity);
		loginGo = (ImageView) findViewById(R.id.loginGo);
		loginGo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}

}
