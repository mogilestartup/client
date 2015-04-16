package com.example.iems5722project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.category_content);
		mCancelText = (LinearLayout) findViewById(R.id.post_cancel);
		
		int categoryId = this.getIntent().getIntExtra(CATEGORY_TYPE, 0);
		titleTextView = (TextView) this.findViewById(R.id.category_title_text);
		titleTextView.setText(CategoryTypes.getTitleByCategoryId(categoryId));
		mCancelText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
	    });
	}
	
	
}
