package com.example.iems5722project;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ViewPostActivity extends BaseActivity {
	
	private LinearLayout mCancelText;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_main_text);
		mCancelText = (LinearLayout) findViewById(R.id.post_cancel);
		//this.getIntent().getExtras().getString("url");
		
		mCancelText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
}
