package com.example.iems5722project;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.iems5722project.connection.HttpConnectionTask;
import com.example.iems5722project.constant.SharedPreferenceUtil;
import com.example.iems5722project.util.StringUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class BaseActivity extends Activity {
	
	public static String USER_TOKEN = "token";
	public static String USER_NAME = "USER_NAME";
	protected static String PATH_GET_LOGIN_STATUS = "/getLoginStatus?";
	protected static String PATH_LOGIN = "/login?";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		gotoLoginPageIfInvalidLogin();
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
    	return getStringPreference(USER_NAME);
    }
    
    protected String getCurrentUserToken(){
    	return getStringPreference(USER_TOKEN);
    }
    
    protected String getStringPreference(String key){
    	return getPreferences().getString(key, "");
    }
    
    protected int getIntPreference(String key){
    	return getPreferences().getInt(key, 0);
    }
    
    protected void gotoLoginPageIfInvalidLogin(){
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
		try {
			String outputText = (String) (new HttpConnectionTask().execute(
					PATH_GET_LOGIN_STATUS, constructJsonInput(userId),
					userToken)).get();
			JSONObject jObj = new JSONObject(outputText);
			loginStatus = Boolean.valueOf(jObj.getString("loginResult"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginStatus;
	}
	
	private String constructJsonInput(String userId){
		JSONObject jObj = new JSONObject();
		try {
			jObj.put("userId", userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObj.toString();
	}
}
