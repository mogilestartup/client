package com.example.iems5722project;

import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PostEditActivity extends BaseActivity {
	
	private TextView mCancelText;
	private TextView mSubmitText;
	private EditText mPostTitle;
	private EditText mPostContent;
	private EditText mWantVCAmount;
	private EditText mWantPMAmount;
	private EditText mWantDEVAmount;
	private EditText mWantUIAmount;
	private EditText mWantOperationAmount;
	private 
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_posts_content);
		//find layout componment
		mPostTitle = (EditText) findViewById(R.id.post_title);
		mPostContent=(EditText) findViewById(R.id.post_content);
		mWantVCAmount=(EditText) findViewById(R.id.Detail_Vc_text_no);
		mWantPMAmount=(EditText) findViewById(R.id.Detail_Pm_text_no);
		mWantDEVAmount=(EditText) findViewById(R.id.Detail_Dev_text);
		mWantUIAmount=(EditText) findViewById(R.id.Detail_Ui_text_no);
		mWantOperationAmount=(EditText) findViewById(R.id.Detail_Opn_text_no);
		
		mCancelText = (TextView) findViewById(R.id.EditPost_Cancel_Txt);
		mCancelText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
	    });
		mSubmitText = (TextView)findViewById(R.id.EditPost_Submit_Txt);
		mSubmitText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String titletext=mPostTitle.getText().toString();
				if (titletext.isEmpty()) 
				{
					Toast.makeText(mContext, R.string.emptyinput, Toast.LENGTH_SHORT).show();
				}
				String postcontent=mPostContent.getText().toString();
				if (postcontent.isEmpty()) 
				{
					Toast.makeText(mContext, R.string.emptyinput, Toast.LENGTH_SHORT).show();
				}
				
				String wantVCAmount= mWantVCAmount.getText().toString();
				String wantPMAmount= mWantPMAmount.getText().toString();
				String wantDEVAmount= mWantDEVAmount.getText().toString();
				String wantUIAmount= mWantUIAmount.getText().toString();
				String wantOperationAmount= mWantOperationAmount.getText().toString();

			    JSONObject vcobj = new JSONObject();
				try {
					vcobj.put("title", "VC").put("amount", wantVCAmount);
				
				
				JSONObject pmobj = new JSONObject();
				pmobj.put("title", "PM").put("amount", wantPMAmount);
				
				JSONObject devobj = new JSONObject();
				devobj.put("title", "Developer").put("amount", wantDEVAmount);
				
				JSONObject uiobj = new JSONObject();
				uiobj.put("title", "UI").put("amount", wantUIAmount);
				
				JSONObject operationobj = new JSONObject();
				operationobj.put("title", "Operation").put("amount", wantOperationAmount);
				
				JSONArray wantvalue = new JSONArray();
				wantvalue.put(vcobj).put(pmobj).put(devobj).put(uiobj).put(operationobj);
				
				JSONObject tagphp = new JSONObject();
				tagphp.put("value", "php");  				
				JSONObject tagjava = new JSONObject();
				tagjava.put("title", "java");  
				
				JSONArray tagobject = new JSONArray();
				tagobject.put(tagphp).put(tagjava);
				String imageurl = "";
				String create_user="liqian";
				SimpleDateFormat  sDateFormat  = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");       
				String create_date  =  sDateFormat.format(new  java.util.Date());
				String last_udate_date = create_date;
				//packaging tag
		        JSONObject Params = new JSONObject();
				Params.put("createdBy", create_user);
				Params.put("createdDate", create_date);
				Params.put("lastUpdatedDate", last_udate_date);
				Params.put("title", titletext);
				Params.put("content", postcontent);
				Params.put("tag", tagobject);
				Params.put("posotion", wantvalue);
				Params.put("image", imageurl);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				//packaging position
						
				mWantVCAmount.setText("");
				mWantPMAmount.setText("");
				mWantDEVAmount.setText("");
				mWantUIAmount.setText("");
				mWantOperationAmount.setText("");
				mPostTitle.setText("");
				mPostContent.setText("");
				finish();
			}
	    });
	}
	
}
