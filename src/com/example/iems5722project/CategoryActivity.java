package com.example.iems5722project;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryActivity extends BaseActivity {
	public static String CATEGORY_TYPE = "CATEGORY_TYPE";
	
	private LinearLayout mCancelText;
	private TextView titleTextView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!checkUserLoginStatus()){
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		setContentView(R.layout.category_content);
		mCancelText = (LinearLayout) findViewById(R.id.post_cancel);
		
		int categoryId = this.getIntent().getIntExtra(CATEGORY_TYPE, 0);
		titleTextView = (TextView) this.findViewById(R.id.category_title_text);
		CategoryTypes category = CategoryTypes.getByCategoryId(categoryId);
		titleTextView.setText(category.getTitle());
		String position = category.name();
		String userId = getCurrentUserId();
		JSONObject params = new JSONObject();
		//TODO: construct params
		JSONObject jObj = performHttpRequest(PATH_NEW_POST, params.toString(), getCurrentUserToken());
		//TODO: parse the result jObj and render UI
		mCancelText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
	    });
	}
	
	
}
