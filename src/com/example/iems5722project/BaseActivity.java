package com.example.iems5722project;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.iems5722project.connection.HttpConnectionTask;
import com.example.iems5722project.constant.SharedPreferenceUtil;
import com.example.iems5722project.util.StringUtil;

public class BaseActivity extends Activity {
	
	public static final String HOST = "http://1.mobilestartup.sinaapp.com";
	public static String SHARED_PRE_USER_TOKEN = "token";
	public static String SHARED_PRE_USER_ID = "userid";
	public static String KEY_EMAIL = "email";
	public static String KEY_USER_ID = "userId";
	public static String KEY_MESSAGE = "message";
	public static String KEY_PASSWORD = "password";
	public static String KEY_REGISTER_RESULT = "registerResult";
	public static String KEY_LOGIN_RESULT = "loginResult";
	public static String KEY_POST_RESULT = "postResult";
	public static String KEY_REVERT_RESULT = "revertResult";
	public static String KEY_ACTION_RESULT = "actionResult";
	public static String KEY_TITLE = "title";
	public static String KEY_AMOUNT = "amount";
	public static String KEY_POST_ID = "postId";
	protected static String PATH_GET_LOGIN_STATUS = "/getLoginStatus?";
	protected static String PATH_REGISTER = "/registration?";
	protected static String PATH_LOGIN = "/login?";
	protected static String PATH_NEW_POST = "/newPost?";
	protected static String PATH_COMMENTS_BY_POST = "/commentListByPostId?";
	protected static String PATH_REVERT = "/revert?";
	protected static String PATH_ACTION = "/action?";
	protected static String PATH_NEW_COMMENT = "/newComment?";
	protected static String PATH_POST_LIST_BY_CATEGORY = "/postListByCategory?";
	protected static String PATH_HOT_POST_LIST = "/hotPostList?";
	protected static String PARAM_PREFIX = "paramString";
	protected SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		pageStatusChecking();
		super.onCreate(savedInstanceState);
	}
	
    protected SharedPreferences getPreferences() {
        return getSharedPreferences(SharedPreferenceUtil.NAMESPACE,Context.MODE_PRIVATE);
    }
    
    protected void storeStringIntoSharedPreferences(String key, String value){
    	SharedPreferences.Editor editor = getPreferences().edit();
    	editor.putString(key, value);
    	editor.commit();
    }
    
    protected void storeIntIntoSharedPreferences(String key, int value){
    	SharedPreferences.Editor editor = getPreferences().edit();
    	editor.putInt(key, value);
    	editor.commit();
    }
    
    protected String getCurrentUserId(){
    	return getStringPreference(SHARED_PRE_USER_ID);
    }
    
    protected String getCurrentUserToken(){
    	return getStringPreference(SHARED_PRE_USER_TOKEN);
    }
    
    protected String getStringPreference(String key){
    	return getPreferences().getString(key, "");
    }
    
    protected int getIntPreference(String key){
    	return getPreferences().getInt(key, 0);
    }
    
    protected void pageStatusChecking(){
		if(!checkUserLoginStatus()){
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
    }
    
	protected boolean checkUserLoginStatus() {
		boolean loginStatus = false;
		String userId = getCurrentUserId();
		String userToken = getCurrentUserToken();
		if (StringUtil.isNullOrEmpty(userId)
				|| StringUtil.isNullOrEmpty(userToken)) {
			return loginStatus;
		}
		JSONObject inputJson = new JSONObject();
		try {
			inputJson.put(KEY_USER_ID, userId);
		} catch (JSONException e) {
			e.printStackTrace();
			return loginStatus;
		}
		JSONObject jObj = checkTokenHttpRequest(PATH_GET_LOGIN_STATUS, inputJson.toString(), userToken);
		loginStatus = Boolean.valueOf(getStringValueFromJson(jObj,KEY_LOGIN_RESULT));
		return loginStatus;
	}
	
	protected String getStringValueFromJson(JSONObject jObj, String key){
		String value = "";
		try {
			value = jObj.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	protected Integer getIntValueFromJson(JSONObject jObj, String key){
		Integer value = 0;
		try {
			value = jObj.getInt(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	protected JSONObject checkTokenHttpRequest(String url, String inputStr, String userToken){
		JSONObject jObj = null;
		try{
			String outputText = (String) (new HttpConnectionTask().execute(
					url, inputStr,
					userToken)).get();
			jObj = new JSONObject(outputText);
		}catch (Exception e){
			e.printStackTrace();
		}
		return jObj;
	}
	
	protected String performHttpRequest(String... params) {
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
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}
		return output;
	}

	protected String getTextValueFromViewById(View view, int id){
		TextView txtView = (TextView) view.findViewById(id);
		return (String) txtView.getText();
	}
}
