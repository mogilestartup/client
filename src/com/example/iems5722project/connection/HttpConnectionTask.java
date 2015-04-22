package com.example.iems5722project.connection;

import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

import com.example.iems5722project.BaseActivity;
import com.example.iems5722project.util.StringUtil;

public class HttpConnectionTask extends AsyncTask<String, Void, String> {
	private static String HOST = "http://1.mobilestartup.sinaapp.com";
	private static String PARAM_PREFIX = "paramString";

	@Override
	protected String doInBackground(String... params) {
		String requestPath = params[0];
		String inputStr = params[1];
		String userToken = params[2];
		String output = "";
		try {
			String url = HOST + requestPath + PARAM_PREFIX + "=" +  URLEncoder.encode(inputStr, HTTP.UTF_8);
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
	protected void onPostExecute(String result) {
		
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
