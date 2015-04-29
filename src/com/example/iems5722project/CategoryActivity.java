package com.example.iems5722project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.example.iems5722project.util.StringUtil;

public class CategoryActivity extends BaseActivity {
	public static String CATEGORY_TYPE = "CATEGORY_TYPE";
	public static String CATEGORY_POST_ID = "Category_Postid";
	public static String CATEGORY_USER_NAME = "Category_UserName";

	private LinearLayout mCancelText;
	private TextView titleTextView;
	private ListView listView;
	//private LinearLayout postItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!checkUserLoginStatus()) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		setContentView(R.layout.category_content);
		mCancelText = (LinearLayout) findViewById(R.id.post_cancel);
		listView = (ListView) findViewById(R.id.listview_category);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				Bundle bundle = new Bundle();
				bundle.putString(CATEGORY_POST_ID, getTextValueFromViewById(view, R.id.Category_Postid));
				bundle.putString(CATEGORY_USER_NAME, getTextValueFromViewById(view, R.id.Category_UserName));
				bundle.putString("title", "My post");
				bundle.putString("introduction", "Zhong Jiawen,1155053168");
				bundle.putString("content", getTextValueFromViewById(view, R.id.Category_MainText));
				bundle.putString("tag", getTextValueFromViewById(view, R.id.Detail_Tag_text));
				Intent intent = new Intent(CategoryActivity.this, ViewPostActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		int categoryId = this.getIntent().getIntExtra(CATEGORY_TYPE, 0);
		titleTextView = (TextView) this.findViewById(R.id.category_title_text);
		CategoryTypes category = CategoryTypes.getByCategoryId(categoryId);
		titleTextView.setText(category.getTitle());
		String position = category.name();
		String userId = getCurrentUserId();
		JSONObject params = new JSONObject();
		// TODO: construct params
		String lastPostDate = getStringPreference("lastPostDate");
		if (StringUtil.isNullOrEmpty(lastPostDate)) {
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			lastPostDate = sDateFormat.format(new java.util.Date());
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		lastPostDate = sDateFormat.format(new java.util.Date());

		try {
			params.put("userId", userId);
			params.put("count", 10);
			params.put("position", position);
			params.put("lastPostDate", lastPostDate);
			HttpConnectionTask connTask = new HttpConnectionTask();
			connTask.execute(PATH_POST_LIST_BY_CATEGORY, params.toString(),
					getCurrentUserToken());

			JSONArray postList = returnJobj.getJSONArray("posts");
			int postLen = postList.length();
			ArrayList<HashMap<String, Object>> datalist = new ArrayList<HashMap<String, Object>>();
			int i = 0;
			for (i = 0; i < postLen; i++) {
				JSONObject jObj = postList.getJSONObject(i);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("Category_Postid", jObj.getString("postId"));
				map.put("Category_UserName", jObj.getString("userId"));
				map.put("Category_Postdate", jObj.getString("createdDate"));
				map.put("Category_Star_text", jObj.getString("like"));
				map.put("Category_MainText", jObj.getString("content"));
				JSONArray postionArray = jObj.getJSONArray("position");
				CategoryTypes.initialDisplayAmount(map);
				for(int j = 0;j < postionArray.length();j++){
					JSONObject posObj = postionArray.getJSONObject(j);
					CategoryTypes.setDisplayAmount(map, posObj.getString(KEY_TITLE), posObj.getString(KEY_AMOUNT));
				}
				JSONArray tagArrary = jObj.getJSONArray("tag");
				String tagStr = "";
				for(int index = 0; index < tagArrary.length(); index++){
					tagStr += tagArrary.getJSONObject(index).getString("value") + " ";
				}
				map.put("Detail_Tag_text", tagStr);
				map.put("Detail_Comment_text",jObj.getString("comments"));
				if (i == postLen - 1) {
					lastPostDate = jObj.getString("createdDate");
					storeStringIntoSharedPreferences("lastPostDate",
							lastPostDate);
				}
				datalist.add(map);
			}
			SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, datalist,
					R.layout.category_item, new String[] { "Category_Postid", "Category_UserName", "Category_Postdate",
							"Category_Star_text", "Category_MainText",
							"Detail_Vc_text", "Detail_Ui_text",
							"Detail_Pm_text", "Detail_Dev_text",
							"Detail_Opn_text", "Detail_Tag_text",
							"Detail_Comment_text" }, new int[] {
							R.id.Category_Postid, R.id.Category_UserName, R.id.Category_Postdate, R.id.Category_Star_text,
							R.id.Category_MainText, R.id.Detail_Vc_text,
							R.id.Detail_Ui_text, R.id.Detail_Pm_text,
							R.id.Detail_Dev_text, R.id.Detail_Opn_text,
							R.id.Detail_Tag_text, R.id.Detail_Comment_text });
			mSimpleAdapter.setViewBinder(new ViewBinder() {
				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					if(view.getId() == R.id.Detail_Comment_text){
						TextView commentView = (TextView)view;
						commentView.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View view) {
								Intent intent = new Intent(CategoryActivity.this, CommentActivity.class);				
								View parent = (View)view.getParent().getParent().getParent();
								String postId = getTextValueFromViewById(parent, R.id.Category_Postid) ;
								intent.putExtra("postId", postId);
								startActivity(intent);
							}
							
						});
					}
					if(view instanceof ImageView && data instanceof Bitmap){
						ImageView i = (ImageView)view;
						i.setImageBitmap((Bitmap) data);
						return true;
					}
					return false;
				}
			});
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
	
	class HttpConnectionTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			return performHttpRequest(params);
		}

		@Override
		protected void onPostExecute(String result) {
			JSONObject returnJobj;
			try {
				returnJobj = new JSONObject(result);
				JSONArray postList = returnJobj.getJSONArray("posts");
				int postLen = postList.length();
				ArrayList<HashMap<String, Object>> datalist = new ArrayList<HashMap<String, Object>>();
				int i = 0;
				for (i = 0; i < postLen; i++) {
					JSONObject jObj = postList.getJSONObject(i);
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("Category_Postid", jObj.getString("postId"));
					//map.put("Category_UserName", jObj.getString("userId"));
					 switch(i)
			            {
			            case 4:
			            	 map.put("Category_UserName", "Steve Jobs");
			            	 map.put("Category_Star_text", "15");
			            	 map.put("Category_MainText", "Hey Guys. We are thinking to develop a sharing transportation system of our university, is there anyone who are interested in it");
			            	 //map.put("Comment_UserHead", "@drawable/star");
			            	 //mImageView.setImageDrawable(getResources().getDrawable(R.drawable.star));
			            	break;
			            case 3:
			            	//map.put("Comment_UserHead", "@drawable/user");
			            	map.put("Category_UserName", "Tim Cook");
			            	map.put("Category_Star_text", "38");
			            	map.put("Category_MainText", "Look for people who are interested in creating a new app "
			            			+ "like Wechat");
			            	break;
			            case 2:
			            	 map.put("Category_UserName", "Ma Yun");
			            	 map.put("Category_Star_text", "45");
			            	 map.put("Category_MainText", "Do you think creating a liabray app for our CUHK students is a good idea?");
			            	 //map.put("Comment_UserHead", "@drawable/wenwen");
			            	break;
			            case 1:
			            	 map.put("Category_UserName", "Ma Huateng");
			            	 map.put("Category_Star_text", "65");
			            	 map.put("Category_MainText", "Look for people who are interested in creating a new app ");
			            	 //map.put("Comment_UserHead", "@drawable/star");
			            	break;
			            case 0:
			            	 map.put("Category_UserName", "Zuckberg");
			            	 map.put("Category_Star_text", "72");
			            	 map.put("Category_MainText", "Hey Guys. We are thinking to develop a sharing transportation system of our university, is there anyone who are interested in it");
			            	 //map.put("Comment_UserHead", "@drawable/wenwen");
			            	break;
			            default:
			            	 map.put("Category_UserName", "Steve Jobs");
			            	 map.put("Category_MainText", jObj.getString("content"));
			            	break;
			            }
					map.put("Category_Postdate", jObj.getString("createdDate"));
					//map.put("Category_Star_text", jObj.getString("like"));
					//map.put("Category_Star_text", "45");
					//map.put("Category_MainText", jObj.getString("content"));
					JSONArray postionArray = jObj.getJSONArray("position");
					CategoryTypes.initialDisplayAmount(map);
					for(int j = 0;j < postionArray.length();j++){
						JSONObject posObj = postionArray.getJSONObject(j);
						CategoryTypes.setDisplayAmount(map, posObj.getString(KEY_TITLE), posObj.getString(KEY_AMOUNT));
					}
					JSONArray tagArrary = jObj.getJSONArray("tag");
					String tagStr = "";
					for(int index = 0; index < tagArrary.length(); index++){
						tagStr += tagArrary.getJSONObject(index).getString("value") + " ";
					}
					map.put("Detail_Tag_text", tagStr);
					map.put("Detail_Comment_text",jObj.getString("comments"));
					if (i == postLen - 1) {
						storeStringIntoSharedPreferences("lastPostDate",
								jObj.getString("createdDate"));
					}
					datalist.add(map);
				}
				SimpleAdapter mSimpleAdapter = new SimpleAdapter(CategoryActivity.this, datalist,
						R.layout.category_item, new String[] { "Category_Postid", "Category_UserName", "Category_Postdate",
								"Category_Star_text", "Category_MainText",
								"Detail_Vc_text", "Detail_Ui_text",
								"Detail_Pm_text", "Detail_Dev_text",
								"Detail_Opn_text", "Detail_Tag_text",
								"Detail_Comment_text" }, new int[] {
								R.id.Category_Postid, R.id.Category_UserName, R.id.Category_Postdate, R.id.Category_Star_text,
								R.id.Category_MainText, R.id.Detail_Vc_text,
								R.id.Detail_Ui_text, R.id.Detail_Pm_text,
								R.id.Detail_Dev_text, R.id.Detail_Opn_text,
								R.id.Detail_Tag_text, R.id.Detail_Comment_text });
				mSimpleAdapter.setViewBinder(new ViewBinder() {
					@Override
					public boolean setViewValue(View view, Object data,
							String textRepresentation) {
						if(view.getId() == R.id.Detail_Comment_text){
							TextView commentView = (TextView)view;
							commentView.setOnClickListener(new OnClickListener(){

								@Override
								public void onClick(View view) {
									Intent intent = new Intent(CategoryActivity.this, CommentActivity.class);				
									View parent = (View)view.getParent().getParent().getParent();
									String postId = getTextValueFromViewById(parent, R.id.Category_Postid) ;
									intent.putExtra("postId", postId);
									startActivity(intent);
								}
								
							});
						}
						if(view instanceof ImageView && data instanceof Bitmap){
							ImageView i = (ImageView)view;
							i.setImageBitmap((Bitmap) data);
							return true;
						}
						return false;
					}
				});
				listView.setAdapter(mSimpleAdapter);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

}