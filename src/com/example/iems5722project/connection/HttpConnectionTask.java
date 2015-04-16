package com.example.iems5722project.connection;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

import com.example.iems5722project.BaseActivity;

public class HttpConnectionTask extends AsyncTask<String, Void, String> {
	private static String HOST = "http://2.mobilestartup.sinaapp.com";
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
			request.setHeader(BaseActivity.USER_TOKEN, userToken);
			HttpResponse response = http_client.execute(request);
			HttpEntity entity = response.getEntity();
			output = EntityUtils.toString(entity, HTTP.UTF_8);
			output = output.substring(0, output.indexOf("<"));
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}
		return output;
	}

}
