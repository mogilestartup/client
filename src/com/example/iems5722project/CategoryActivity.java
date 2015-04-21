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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryActivity extends BaseActivity {
	public static String CATEGORY_TYPE = "CATEGORY_TYPE";
	
	private LinearLayout mCancelText;
	private TextView titleTextView;
	private ListView listView;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!checkUserLoginStatus()){
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		setContentView(R.layout.category_content);
		mCancelText = (LinearLayout) findViewById(R.id.post_cancel);
		listView = (ListView) findViewById(R.id.listview_category);
		int categoryId = this.getIntent().getIntExtra(CATEGORY_TYPE, 0);
		titleTextView = (TextView) this.findViewById(R.id.category_title_text);
		CategoryTypes category = CategoryTypes.getByCategoryId(categoryId);
		titleTextView.setText(category.getTitle());
		String position = category.name();
		String userId = getCurrentUserId();
		JSONObject params = new JSONObject();
		//TODO: construct params
		String lastPostDate = getStringPreference("lastPostDate");
		if(StringUtil.isNullOrEmpty(lastPostDate))
		{
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			lastPostDate = sDateFormat
					.format(new java.util.Date());
		}
		try {
			params.put("userId", userId);
		params.put("count", 10);
		params.put("position", position);
		params.put("lastPostDate", lastPostDate);
		JSONObject jObj = performHttpRequest(PATH_NEW_POST, params.toString(), getCurrentUserToken());
		//TODO: parse the result jObj and render UI
		JSONArray postList = jObj.getJSONArray("posts");
		int postLen = postList.length();	
		ArrayList<HashMap<String, Object>> datalist = new ArrayList<HashMap<String, Object>>();
        int i = 0;           
        for (i = 0; i < postLen; i++) 
        {
            JSONObject json_object = postList.getJSONObject(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Category_UserName", jObj.getString("userId"));
            map.put("Category_Star_text", jObj.getString("createdDate"));
            map.put("Category_MainText", jObj.getString("content"));
            JSONArray postion=jObj.getJSONArray("posotion");
            map.put("Detail_Dev_text",postion.getJSONObject(0).getString("amount"));
            map.put("Detail_Opn_text",postion.getJSONObject(1).getString("amount"));
            map.put("Detail_Ui_text", postion.getJSONObject(2).getString("amount"));
            map.put("Detail_Pm_text", postion.getJSONObject(3).getString("amount"));
            map.put("Detail_Vc_text", postion.getJSONObject(4).getString("amount"));
            JSONArray tagArrary=jObj.getJSONArray("tag");
            map.put("Detail_Tag_text", tagArrary.getJSONObject(0).getString("php"));
            map.put("Detail_Comment_text", jObj.getString("todo..........."));
            if(i==postLen-1)
            {
            	lastPostDate = jObj.getString("createdDate");
            	storeStringIntoSharedPreferences("lastPostDate",lastPostDate);
            }
            datalist.add(map);
        }
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,datalist,R.layout.category_item,new String[]{"Category_UserName","Category_Star_text", "Category_MainText","Detail_Vc_text","Detail_Ui_text","Detail_Pm_text","Detail_Dev_text",
				   "Detail_Opn_text","Detail_Tag_text","Detail_Comment_text"},new int[] {R.id.Category_UserName,R.id.Category_Star_text,R.id.Category_MainText,R.id.Detail_Vc_text,R.id.Detail_Ui_text,
						   R.id.Detail_Pm_text, R.id.Detail_Dev_text, R.id.Detail_Opn_text, R.id.Detail_Tag_text, R.id.Detail_Comment_text}  
				               );
		listView.setAdapter(mSimpleAdapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCancelText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
	    });
	}
	    
}
