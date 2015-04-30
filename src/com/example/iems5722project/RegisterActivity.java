package com.example.iems5722project;

import java.net.URLEncoder;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {
	private TextView email;
	private TextView name;
	private TextView password;
	private Context mContext;
	private TextView startlogin;
	private RelativeLayout next;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		mContext = this;
		email = (TextView) findViewById(R.id.Redit_email);
		name = (TextView) findViewById(R.id.Redit_name);
		password = (TextView) findViewById(R.id.Redit_password);
		startlogin = (TextView) findViewById(R.id.startlogin);
		next = (RelativeLayout) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					JSONObject inputJson = new JSONObject();
					inputJson.put(KEY_EMAIL, email.getText());
					inputJson.put(KEY_USER_ID, name.getText());
					inputJson.put(KEY_PASSWORD, password.getText());
					HttpConnectionTask task = new HttpConnectionTask();
					task.execute(PATH_REGISTER, inputJson.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
		startlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
    protected void pageStatusChecking(){
		
    }
	
	class HttpConnectionTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			String requestPath = params[0];
			String inputStr = params[1];
			String output = "";
			try {
				String url = BaseActivity.HOST + requestPath + PARAM_PREFIX + "=" +  URLEncoder.encode(inputStr, HTTP.UTF_8);
				HttpClient http_client = new DefaultHttpClient();
				HttpPost request = new HttpPost(url);
				HttpResponse response = http_client.execute(request);
				HttpEntity entity = response.getEntity();
				output = EntityUtils.toString(entity, HTTP.UTF_8);
			} catch (Exception e) {
				System.out.println("Exception:" + e.getMessage());
			}
			return output;
		}

		@Override
		protected void onPostExecute(String outputText) {
			JSONObject jObj;
			try {
				jObj = new JSONObject(outputText);
				if(Boolean.valueOf(getStringValueFromJson(jObj,KEY_REGISTER_RESULT))){
					setContentView(R.layout.register_activity_info);
					//TODO: due to time limitation, the user profile setting is not finished.
					ImageView imageView = (ImageView) findViewById(R.id.loginGo);
					imageView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Toast.makeText(mContext, "Register successfully, please check your email to active the account",Toast.LENGTH_LONG).show();
							Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
							startActivity(intent);
						}
					});
				}else{
					Toast.makeText(mContext, KEY_MESSAGE,
							Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}
}
