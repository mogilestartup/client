package com.example.iems5722project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;

import com.example.iems5722project.util.StringUtil;

public class CommentActivity extends BaseActivity {
	public static String CATEGORY_TYPE = "CATEGORY_TYPE";
	private LinearLayout mCancelText;
	private EditText inputTextView;
	private ListView listView;
	private Button  send;
	//private ImageView mImageView;
	private String lastCommentDate = null;
	String postId = " ";
	SimpleDateFormat sDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");
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
		//mImageView = (ImageView)this.findViewById(R.id.Comment_UserHead); 
		mCancelText = (LinearLayout) findViewById(R.id.post_cancel);
		send = (Button) findViewById(R.id.send);
		inputTextView=(EditText)findViewById(R.id.input);
		String userId = getCurrentUserId();
		JSONObject params = new JSONObject();
		//TODO: construct params
		Intent intent = getIntent();
		
		if(intent!=null)
		{
			postId = intent.getStringExtra("postId");
		}

		if(StringUtil.isNullOrEmpty(lastCommentDate))
		{

			lastCommentDate = sDateFormat
					.format(new java.util.Date());
		}
		try {
		params.put("postId", postId);
		params.put("userId",getCurrentUserId());
		params.put("count", 10);
		params.put("lastCommentDate", lastCommentDate);
		
		JSONObject jObj = performHttpRequest(PATH_COMMENTS_BY_POST, params.toString(), getCurrentUserToken());
		//TODO: parse the result jObj and render UI
		JSONArray commentList = jObj.getJSONArray("comments");
		int commentlistLen = commentList.length();	
		
		int i = 0;           
        for (i = 1; i < commentlistLen; i++) 
        {
            JSONObject json_object = commentList.getJSONObject(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("postId", json_object.getString("postId"));
            map.put("content", json_object.getString("content"));
            map.put("createdDate", json_object.getString("createdDate"));
            
            switch(i)
            {
            case 4:
            	 map.put("userId", "Steve Jobs");
            	 //map.put("Comment_UserHead", "@drawable/star");
            	 //mImageView.setImageDrawable(getResources().getDrawable(R.drawable.star));
            	break;
            case 3:
            	//map.put("Comment_UserHead", "@drawable/user");
            	map.put("userId", "Tim Cook");
            	break;
            case 2:
            	 map.put("userId", "Ma Yun");
            	 //map.put("Comment_UserHead", "@drawable/wenwen");
            	break;
            case 1:
            	 map.put("userId", "Ma Huateng");
            	 //map.put("Comment_UserHead", "@drawable/star");
            	break;
            case 0:
            	 map.put("userId", "Zuckberg");
            	 //map.put("Comment_UserHead", "@drawable/wenwen");
            	break;
            default:
            	 map.put("userId", "Steve Jobs");
            	break;
            }
            //map.put("userId", json_object.getString("userId"));
            if(commentlistLen-1==i)
            {
            	lastCommentDate = json_object.getString("createdDate");
            }
            datalist.add(map);
        }
		} catch (JSONException e) {
			e.printStackTrace();
		}
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,datalist,R.layout.comment_item,new String[]{"content","createdDate","userId"},
        		new int[] {R.id.Comment_MainText,R.id.Comment_Date,R.id.Comment_UserName
						   }  
				               );
		mSimpleAdapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if(view instanceof ImageView && data instanceof Bitmap){
					ImageView i = (ImageView)view;
					i.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(mSimpleAdapter);
	
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String commentInput=inputTextView.getText().toString();
				if (commentInput.isEmpty()) 
				{
					Toast.makeText(getApplicationContext(),"input empty", Toast.LENGTH_SHORT).show();
				}

				String create_date = sDateFormat
						.format(new java.util.Date());
				JSONObject params = new JSONObject();
				try {
					params.put("postId",postId);
					params.put("content",commentInput);
					params.put("createdDate", create_date);
					params.put("userId", getCurrentUserId());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONObject jObj = performHttpRequest(PATH_NEW_COMMENT, params.toString(), getCurrentUserToken());
				if(jObj!=null)
				{
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
			}
		
	    });
		
		mCancelText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}

