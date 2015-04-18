package com.example.iems5722project;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {
	
	private ImageView loginGo;
	private TextView nameTxtView;
	private TextView pwdTxtView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		nameTxtView = (TextView) findViewById(R.id.login_edit_name);
		pwdTxtView = (TextView) findViewById(R.id.login_edit_password);
		loginGo = (ImageView) findViewById(R.id.loginGo);
		loginGo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] keys = {KEY_USER_NAME, KEY_PASSWORD};
				String userName =  nameTxtView.getText().toString();
				String password = pwdTxtView.getText().toString();
				String[] values = {userName, password};
				JSONObject jObj = performHttpRequest(PATH_LOGIN, keys, values, null);
				if(Boolean.valueOf(getStringValueFromJson(jObj,KEY_LOGIN_RESULT))){
					storeStringIntoSharedPreferences(SHARED_PRE_USER_NAME, userName);
					storeStringIntoSharedPreferences(SHARED_PRE_USER_TOKEN, getStringValueFromJson(jObj, SHARED_PRE_USER_TOKEN));
					Intent intent = new Intent(LoginActivity.this, Tab_UI.class);
					startActivity(intent);
				}else{
					nameTxtView.setText(getStringValueFromJson(jObj, KEY_MESSAGE));
				}
			}
		});
	}

	@Override
    protected void pageStatusChecking(){
		if(checkUserLoginStatus()){
			Intent intent = new Intent(this, Tab_UI.class);
			startActivity(intent);
		}
    }
}
