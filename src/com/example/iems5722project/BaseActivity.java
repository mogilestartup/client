package com.example.iems5722project;

import com.example.iems5722project.constant.SharedPreferenceUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class BaseActivity extends Activity {
	
	public static String USER_TOKEN = "USER_TOKEN";
	public static String USER_NAME = "USER_NAME";
	
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
    
    protected String getStringPreference(String key){
    	return getPreferences().getString(key, "");
    }
    
    protected int getIntPreference(String key){
    	return getPreferences().getInt(key, 0);
    }
}
