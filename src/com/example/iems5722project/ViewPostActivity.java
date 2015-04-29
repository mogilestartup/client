package com.example.iems5722project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.iems5722project.util.StringUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.LinearLayout;

public class ViewPostActivity extends BaseActivity {
	public static String CATEGORY_TYPE = "CATEGORY_TYPE";
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
		Intent intent = getIntent();  
		Bundle data = null;
        //获取该intent所携带的数据  
		if(intent!=null)
		{
			data = intent.getExtras();  
		}

		//this.getIntent().getExtras().getString("url");

		if(data!=null)
		{
			//String title = data.getString("title");
			String title = "Call for new StarUps!";
			titleTextView.setText(title);
			String usrname = data.getString("Category_UserName");
			//usrNameView.setText(usrname);
			usrNameView.setText("Carmen");
			String introduction = data.getString("introduction");
			UserIntroductionView.setText(introduction);
			String content = data.getString("content");
			postContent.setText(content);
			Detail_Vc_textView.setText("3");
			Detail_Ui_textView.setText("0");
			Detail_Pm_textView.setText("0");
			Detail_Dev_textView.setText("0");
			Detail_Opn_textView.setText("0");
			Detail_Star_textView.setText("45");
		}
		
		mCancelText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		

	}
}
