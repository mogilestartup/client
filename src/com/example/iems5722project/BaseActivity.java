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
	
	public static String SHARED_PRE_USER_TOKEN = "token";
	public static String SHARED_PRE_USER_NAME = "user_name";
	public static String KEY_USER_NAME = "userName";
	public static String KEY_MESSAGE = "message";
	public static String KEY_PASSWORD = "password";
	public static String KEY_LOGIN_RESULT = "loginResult";
	protected static String PATH_GET_LOGIN_STATUS = "/getLoginStatus?";
	protected static String PATH_LOGIN = "/login?";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
    
    protected String getCurrentUserName(){
    	return getStringPreference(SHARED_PRE_USER_NAME);
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
		String userName = getCurrentUserName();
		String userToken = getCurrentUserToken();
		if (StringUtil.isNullOrEmpty(userName)
				|| StringUtil.isNullOrEmpty(userToken)) {
			return loginStatus;
		}
		String[] keys = {KEY_USER_NAME};
		String[] values = {userName};
		JSONObject jObj = performHttpRequest(PATH_GET_LOGIN_STATUS, keys, values, userToken);
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
	
	protected JSONObject performHttpRequest(String url, String[] keys, String[] values, String userToken){
		JSONObject jObj = null;
		try{
			String outputText = (String) (new HttpConnectionTask().execute(
					url, constructJsonInput(keys, values),
					userToken)).get();
			if(outputText.indexOf(StringUtil.AT) > 0){
				String[] resultArray = outputText.split(StringUtil.AT);
				jObj = new JSONObject(resultArray[0]);
				jObj.put(SHARED_PRE_USER_TOKEN, resultArray[1]);
			} else{
				jObj = new JSONObject(outputText);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return jObj;
	}
	
	protected String constructJsonInput(String[] keys, String[] values){
		JSONObject jObj = new JSONObject();
		try {
			for(int i = 0;i<keys.length;i++){
				jObj.put(keys[i], values[i]);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObj.toString();
	}
}
