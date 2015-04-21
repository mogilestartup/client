package com.example.iems5722project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.iems5722project.util.StringUtil;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends BaseActivity {
	public static String CATEGORY_TYPE = "CATEGORY_TYPE";
	private LinearLayout mCancelText;
	private TextView inputTextView;
	private ListView listView;
	private Button  send;
	ArrayList<HashMap<String, Object>> datalist = new ArrayList<HashMap<String, Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!checkUserLoginStatus()){
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		setContentView(R.layout.comment_list);
		listView = (ListView) findViewById(R.id.listview_comment);
		String userId = getCurrentUserId();
		JSONObject params = new JSONObject();
		//TODO: construct params
		Intent intent = this.getIntent();
		String postId = " ";
		if(intent!=null)
		{
			postId = intent.getStringExtra("postId");
		}
		String lastCommentDate = getStringPreference("lastCommentDate");
		if(StringUtil.isNullOrEmpty(lastCommentDate))
		{
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			lastCommentDate = sDateFormat
					.format(new java.util.Date());
		}
		try {
			params.put("postId", postId);
		
		params.put("count", 10);
		params.put("lastCommentDate", lastCommentDate);
		
		JSONObject jObj = performHttpRequest(PATH_NEW_POST, params.toString(), getCurrentUserToken());
		//TODO: parse the result jObj and render UI
		JSONArray commentList = jObj.getJSONArray("comments");
		int commentlistLen = commentList.length();	
		
		int i = 0;           
        for (i = 0; i < commentlistLen; i++) 
        {
            JSONObject json_object = commentList.getJSONObject(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("postId", jObj.getString("postId"));
            map.put("content", jObj.getString("content"));
            map.put("createdDate", jObj.getString("createdDate"));
            map.put("userId", jObj.getString("userId"));
            if(commentlistLen-1==i)
            {
            	lastCommentDate = jObj.getString("createdDate");
            	storeStringIntoSharedPreferences("lastCommentDate",lastCommentDate);
            }
            datalist.add(map);
        }
		} catch (JSONException e) {
			e.printStackTrace();
		}
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,datalist,R.layout.category_item,new String[]{"content","createdDate","userId"},
        		new int[] {R.id.Comment_MainText,R.id.Comment_Date,R.id.Comment_UserName,
						   }  
				               );
		listView.setAdapter(mSimpleAdapter);
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String userInput=inputTextView.getText().toString();
				if (userInput.isEmpty()) 
				{
					Toast.makeText(getApplicationContext(),"input empty", Toast.LENGTH_SHORT).show();
				}
		        System.out.println(userInput);
		        SimpleDateFormat sDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				String create_date = sDateFormat
						.format(new java.util.Date());
				JSONObject params = new JSONObject();
				try {
					params.put("postId","123");
					params.put("content",userInput);
					params.put("createdDate", create_date);
					params.put("userId", getCurrentUserId());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONObject jObj = performHttpRequest(PATH_NEW_COMMENT, params.toString(), getCurrentUserToken());
				if(Boolean.valueOf(getStringValueFromJson(jObj, "result"))){
					Toast.makeText(getApplicationContext(), "Post successfully.",
							Toast.LENGTH_SHORT).show();
					finish();
				}else{
					Toast.makeText(getApplicationContext(), getStringValueFromJson(jObj,KEY_MESSAGE),
							Toast.LENGTH_SHORT).show();
					}
					inputTextView.setText("");
				}
				
		
	    });
	}
}

