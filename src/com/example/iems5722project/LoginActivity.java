package com.example.iems5722project;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class LoginActivity extends BaseActivity {
	
	private ImageView loginGo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		loginGo = (ImageView) findViewById(R.id.loginGo);
		loginGo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	@Override
    protected void gotoLoginPageIfInvalidLogin(){
		
    }
}
