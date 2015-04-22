package com.example.iems5722project;

import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditPostActivity extends BaseActivity {

	private TextView mCancelText;
	private TextView mSubmitText;
	private EditText mPostTitle;
	private EditText mPostContent;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.edit_posts_content);
		// find layout componment
		mPostTitle = (EditText) findViewById(R.id.post_title);
		mPostContent = (EditText) findViewById(R.id.post_content);
		mCancelText = (TextView) findViewById(R.id.EditPost_Cancel_Txt);
		mCancelText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mSubmitText = (TextView) findViewById(R.id.EditPost_Submit_Txt);
		mSubmitText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String titletext = mPostTitle.getText().toString();
				if (titletext.isEmpty()) {
					Toast.makeText(mContext, R.string.emptyinput,
							Toast.LENGTH_SHORT).show();
				}
				String postcontent = mPostContent.getText().toString();
				if (postcontent.isEmpty()) {
					Toast.makeText(mContext, R.string.emptyinput,
							Toast.LENGTH_SHORT).show();
				}

				JSONObject params = new JSONObject();

				try {

					JSONArray wantvalue = new JSONArray();
					for(CategoryTypes enumObj : CategoryTypes.values()){
						enumObj.extractInputAmountFromOnline(wantvalue, (EditText) findViewById(enumObj.getInputAmountId()));
					}

					JSONObject tagphp = new JSONObject();
					tagphp.put("value", "php");
					JSONObject tagjava = new JSONObject();
					tagjava.put("value", "java");

					JSONArray tagobject = new JSONArray();
					tagobject.put(tagphp).put(tagjava);
					String imageurl = "";
					SimpleDateFormat sDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					String create_date = sDateFormat
							.format(new java.util.Date());
					String last_udate_date = create_date;
					// packaging tag
					
					params.put("userId", getCurrentUserId());
					params.put("createdDate", create_date);
					params.put("lastUpdatedDate", last_udate_date);
					params.put("title", titletext);
					params.put("content", postcontent);
					params.put("tag", tagobject);
					params.put("position", wantvalue);
					params.put("image", imageurl);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// packaging position
				JSONObject jObj = performHttpRequest(PATH_NEW_POST, params.toString(), getCurrentUserToken());
				if(Boolean.valueOf(getStringValueFromJson(jObj,KEY_POST_RESULT))){
					Toast.makeText(mContext, "Post successfully.",
							Toast.LENGTH_SHORT).show();
					finish();
				}else{
					Toast.makeText(mContext, getStringValueFromJson(jObj,KEY_MESSAGE),
							Toast.LENGTH_SHORT).show();
					for(CategoryTypes enumObj : CategoryTypes.values()){
						((EditText) findViewById(enumObj.getInputAmountId())).setText("");
					}
					mPostTitle.setText("");
					mPostContent.setText("");
				}
			}
		});
	}
	
}
