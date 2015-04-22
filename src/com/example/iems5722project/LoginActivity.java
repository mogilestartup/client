package com.example.iems5722project;

import org.json.JSONException;
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
	private String userId;
	private String password;
	
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
				JSONObject inputJson = new JSONObject();
				userId =  nameTxtView.getText().toString();
				password = pwdTxtView.getText().toString();
				try {
					inputJson.put(KEY_USER_ID, userId);
					inputJson.put(KEY_PASSWORD, password);
				} catch (JSONException e) {
					e.printStackTrace();
					return;
				}
				JSONObject jObj = performHttpRequest(PATH_LOGIN, inputJson.toString(), null);
				if(Boolean.valueOf(getStringValueFromJson(jObj,KEY_LOGIN_RESULT))){
					storeStringIntoSharedPreferences(SHARED_PRE_USER_ID, userId);
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
