package com.example.iems5722project;

import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iems5722project.util.StringUtil;

public class LoginActivity extends BaseActivity {
	
	private ImageView loginGo;
	private TextView nameTxtView;
	private TextView pwdTxtView;
	private String userId;
	private String password;
	private Context mContext;
	private TextView register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		mContext = this;
		nameTxtView = (TextView) findViewById(R.id.login_edit_name);
		pwdTxtView = (TextView) findViewById(R.id.login_edit_password);
		loginGo = (ImageView) findViewById(R.id.loginGo);
		register = (TextView) findViewById(R.id.register);
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
				HttpConnectionTask connTask = new HttpConnectionTask(); 
				connTask.execute(
						PATH_LOGIN, inputJson.toString(),
						getCurrentUserToken());
			}
		});
		
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
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
	
	class HttpConnectionTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			String requestPath = params[0];
			String inputStr = params[1];
			String userToken = params[2];
			String output = "";
			try {
				String url = BaseActivity.HOST + requestPath + PARAM_PREFIX + "=" +  URLEncoder.encode(inputStr, HTTP.UTF_8);
				HttpClient http_client = new DefaultHttpClient();
				HttpPost request = new HttpPost(url);
				if(!StringUtil.isNullOrEmpty(userToken)){
					request.setHeader(BaseActivity.SHARED_PRE_USER_TOKEN, userToken);
				}
				HttpResponse response = http_client.execute(request);
				HttpEntity entity = response.getEntity();
				output = EntityUtils.toString(entity, HTTP.UTF_8);
				userToken = getStringHeaderFromResponse(response,BaseActivity.SHARED_PRE_USER_TOKEN);
				if(!StringUtil.isNullOrEmpty(userToken)){
					output = output + StringUtil.AT + userToken;
				}
			} catch (Exception e) {
				System.out.println("Exception:" + e.getMessage());
			}
			return output;
		}

		@Override
		protected void onPostExecute(String outputText) {
			JSONObject jObj;
			try {
				if(outputText.indexOf(StringUtil.AT) > 0){
					String[] resultArray = outputText.split(StringUtil.AT);
					jObj = new JSONObject(resultArray[0]);
					jObj.put(SHARED_PRE_USER_TOKEN, resultArray[1]);
				} else{
					jObj = new JSONObject(outputText);
				}
				if(Boolean.valueOf(getStringValueFromJson(jObj,KEY_LOGIN_RESULT))){
					storeStringIntoSharedPreferences(SHARED_PRE_USER_ID, userId);
					storeStringIntoSharedPreferences(SHARED_PRE_USER_TOKEN, getStringValueFromJson(jObj, SHARED_PRE_USER_TOKEN));
					Intent intent = new Intent(LoginActivity.this, Tab_UI.class);
					startActivity(intent);
				}else{
					Toast.makeText(mContext, KEY_MESSAGE,
							Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		private String getStringHeaderFromResponse(HttpResponse response, String key){
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				if(header.getName().equals(key)){
					return header.getValue();
				}
			}
			return "";
		}

	}
	
}
