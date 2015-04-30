package com.example.iems5722project;

import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.iems5722project.RegisterActivity.HttpConnectionTask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPostActivity extends BaseActivity {
	public static String CATEGORY_TYPE = "CATEGORY_TYPE";
	private static final String ACTION_RESULT = "actionResult";
	
	private TextView titleTextView;
	private TextView usrNameView;
	private TextView UserIntroductionView;
	private TextView postContent;
	private TextView tagView;
	private TextView mCancelText;
	private TextView Detail_Vc_textView;
	private TextView Detail_Ui_textView;
	private TextView Detail_Pm_textView;
	private TextView Detail_Dev_textView;
	private TextView Detail_Opn_textView;
	private TextView Detail_Star_textView; 
	private LinearLayout upLayout;
	private LinearLayout downLayout;
	private Bundle data;
	String usrName;
	String postTitle;
	String post_UserIntroduction;
	String postId="";
	String userName="";
	String lastPostDate = null;
	SimpleDateFormat sDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_text_main);
		usrNameView=(TextView)findViewById(R.id.Detail_UserName);
		mCancelText=(TextView)findViewById(R.id.EditPost_Cancel_Txt);
		titleTextView = (TextView)findViewById(R.id.title);
		UserIntroductionView = (TextView)findViewById(R.id.Post_UserIntroduction);
		postContent = (TextView)findViewById(R.id.post_content);
		Detail_Vc_textView = (TextView)findViewById(R.id.Detail_Vc_text);
		Detail_Ui_textView = (TextView)findViewById(R.id.Detail_Ui_text);
		Detail_Pm_textView = (TextView)findViewById(R.id.Detail_Pm_text);
		Detail_Dev_textView = (TextView)findViewById(R.id.Detail_Dev_text);
		Detail_Opn_textView = (TextView)findViewById(R.id.Detail_Opn_text);
		Detail_Star_textView = (TextView)findViewById(R.id.Detail_Star_text);
		upLayout = (LinearLayout) findViewById(R.id.increase_star);
		downLayout = (LinearLayout) findViewById(R.id.decrease_star);
		Intent intent = getIntent();  
		data = null;
		if(intent!=null)
		{
			data = intent.getExtras();  
		}

		if(data!=null)
		{
			String title = data.getString("title");
			titleTextView.setText(title);
			String usrname = data.getString("Category_UserName");
			usrNameView.setText(usrname);
			String introduction = data.getString("introduction");
			UserIntroductionView.setText(introduction);
			String content = data.getString("content");
			postContent.setText(content);
			String Vc_amount = data.getString("Vc_amount");
			Detail_Vc_textView.setText(Vc_amount);
			String Ui_amount = data.getString("Ui_amount");
			Detail_Ui_textView.setText(Ui_amount);
			String Pm_amount = data.getString("Pm_amount");
			Detail_Pm_textView.setText(Pm_amount);
			String Dev_amount = data.getString("Dev_amount");
			Detail_Dev_textView.setText(Dev_amount);
			String Opn_amount = data.getString("Opn_amount");
			Detail_Opn_textView.setText(Opn_amount);
			String Star_amount = data.getString("Star_amount");
			Detail_Star_textView.setText(Star_amount);
		}
		
		mCancelText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		upLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					JSONObject inputJson = new JSONObject();
					inputJson.put("postId", data.getString(CategoryActivity.CATEGORY_POST_ID));
					inputJson.put("action", "1");
					inputJson.put(KEY_USER_ID, getCurrentUserId());
					inputJson.put("createdDate", sDateFormat.format(new java.util.Date()));
					NewActionTask task = new NewActionTask();
					task.execute(PATH_ACTION, inputJson.toString(), getCurrentUserToken());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
		downLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					JSONObject inputJson = new JSONObject();
					inputJson.put("postId", data.getString(CategoryActivity.CATEGORY_POST_ID));
					inputJson.put("action", "0");
					inputJson.put(KEY_USER_ID, getCurrentUserId());
					inputJson.put("createdDate", sDateFormat.format(new java.util.Date()));
					NewActionTask task = new NewActionTask();
					task.execute(PATH_ACTION, inputJson.toString(), getCurrentUserToken());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	class NewActionTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			return performHttpRequest(params);
		}

		@Override
		protected void onPostExecute(String result) {
			JSONObject jObj;
			try {
				jObj = new JSONObject(result);
				if (jObj != null) {
					if (Boolean.valueOf(getStringValueFromJson(jObj, ACTION_RESULT))) {
						Toast.makeText(getApplicationContext(),
								"Like successfully.", Toast.LENGTH_SHORT)
								.show();
						String newScore = String.valueOf(getIntValueFromJson(jObj, "score"));
						Detail_Star_textView.setText(newScore);
					} else {
						Toast.makeText(getApplicationContext(),
								getStringValueFromJson(jObj, KEY_MESSAGE),
								Toast.LENGTH_SHORT).show();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
}
