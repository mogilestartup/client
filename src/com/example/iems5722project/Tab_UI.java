package com.example.iems5722project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;

public class Tab_UI extends BaseActivity implements OnClickListener {
	public static String KEY_USER_NAME = "Detail_UserName";
	public static String KEY_DATE = "Detail_Date";
	public static String KEY_STAR = "Detail_Star_text";
	public static String KEY_MAIN = "Detail_MainText";
	public static String KEY_TAG = "Detail_Tag_text";
	public static String KEY_COMMENT = "Detail_Comment_text";
	
	private ViewPager mViewPager;
	private PagerAdapter mAdapter;
	private List<View> mViews = new ArrayList<View>();

	// Tab
	private LinearLayout mTabHot;
	private LinearLayout mTabNewPost;
	private LinearLayout mTabCategory;
	private TextView mHotTxt;
	private TextView mNewPostTxt;
	private TextView mCategoryTxt;
	private ImageButton mHotImg;
	private ImageButton mNewPostImg;
	private ImageButton mCategoryImg;
	private View tab_hot;
	private View tab_new_post;
	private View tab_category;
	private ListView listViewCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvents();
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		mTabHot.setOnClickListener(this);
		mTabNewPost.setOnClickListener(this);
		mTabCategory.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(new OnPageChageListener());
	}

	@SuppressLint("InflateParams")
	private void initView() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		// Tabs
		mTabHot = (LinearLayout) findViewById(R.id.id_tab_hot);
		mTabNewPost = (LinearLayout) findViewById(R.id.id_tab_new_post);
		mTabCategory = (LinearLayout) findViewById(R.id.id_tab_category);
		// Image Button
		mHotImg = (ImageButton) findViewById(R.id.id_tab_hot_img);
		mNewPostImg = (ImageButton) findViewById(R.id.id_tab_new_post_img);
		mCategoryImg = (ImageButton) findViewById(R.id.id_tab_category_img);
		// TextView
		mHotTxt = (TextView) findViewById(R.id.id_tab_hot_text);
		mNewPostTxt = (TextView) findViewById(R.id.id_tab_new_post_text);
		mCategoryTxt = (TextView) findViewById(R.id.id_tab_category_text);
		// Inflate

		LayoutInflater mInflater = LayoutInflater.from(this);
		tab_hot = mInflater.inflate(R.layout.tab_hot, null);
		tab_new_post = mInflater.inflate(R.layout.tab_my_post, null);
		myPostNavigation(tab_new_post);
		tab_category = mInflater.inflate(R.layout.tab_category, null);
		tabCategoryNavigation(tab_category);
		mViews.add(tab_hot);
		mViews.add(tab_new_post);
		mViews.add(tab_category);

		mAdapter = new PagerAdapter() {
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				container.removeView(mViews.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				View view = mViews.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mViews.size();
			}
		};
		mViewPager.setAdapter(mAdapter);
		renderHotItems();
	}

	@SuppressLint("NewApi")
	private void tabCategoryNavigation(View tab_category) {
		List<Integer> categoryIdList = CategoryTypes.getCategoryIdList();
		for(int categoryId : categoryIdList){
			LinearLayout category = (LinearLayout) tab_category
					.findViewById(categoryId);
			category.setOnClickListener(new MyLovelyOnClickListener(categoryId, category));
			
		}
	}
	
	public class MyLovelyOnClickListener implements OnClickListener
	   {

	     int myLovelyVariable;
	     LinearLayout category;
		public MyLovelyOnClickListener(int myLovelyVariable, LinearLayout category) {
	    	 this.category = category;
	          this.myLovelyVariable = myLovelyVariable;
	     }

	
		@Override
	     public void onClick(View v)
	     {
			    //TextView button_text = (TextView)category.findViewById(R.id.Detail_Vc_Txt);
			    //button_text.setTextColor(Color.rgb(0, 0, 0));
				Intent intent = new Intent(Tab_UI.this, CategoryActivity.class);
				intent.putExtra(CategoryActivity.CATEGORY_TYPE, myLovelyVariable);
				startActivity(intent);
	     }

	  };

	private void myPostNavigation(View tab_new_post) {
		LinearLayout detailNewPost = (LinearLayout) tab_new_post
				.findViewById(R.id.Detail_New_Post);
		detailNewPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Tab_UI.this, EditPostActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		resetImg();
		switch (v.getId()) {
		case R.id.id_tab_hot:
			mViewPager.setCurrentItem(0);
			mHotImg.setImageResource(R.drawable.tab_hot_big_pressed);
			break;
		case R.id.id_tab_new_post:
			mViewPager.setCurrentItem(1);
			mNewPostImg.setImageResource(R.drawable.tab_new_post_big_pressed);
			break;
		case R.id.id_tab_category:
			mViewPager.setCurrentItem(2);
			mCategoryImg.setImageResource(R.drawable.tab_category_big_pressed);
			break;
		default:
			break;
		}
	}

	// Change Tab Logo
	private void resetImg() {
		// TODO Auto-generated method stub
		mHotImg.setImageResource(R.drawable.tab_hot_big);
		mNewPostImg.setImageResource(R.drawable.tab_new_post_big);
		mCategoryImg.setImageResource(R.drawable.tab_category_big);
	}

	private void resetTextColor() {
		// TODO Auto-generated method stub
		mHotTxt.setTextColor(Color.rgb(134, 137, 144));
		mNewPostTxt.setTextColor(Color.rgb(134, 137, 144));
		mCategoryTxt.setTextColor(Color.rgb(134, 137, 144));
	}
	
	public class OnPageChageListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			int currentItem = mViewPager.getCurrentItem();
			resetImg();
			resetTextColor();
			switch (currentItem) {
			case 0:
				mHotImg.setImageResource(R.drawable.tab_hot_big_pressed);
				mHotTxt.setTextColor(Color.rgb(90, 201, 159));
				renderHotItems();
				break;
			case 1:
				mNewPostImg
						.setImageResource(R.drawable.tab_new_post_big_pressed);
				mNewPostTxt.setTextColor(Color.rgb(90, 201, 159));
				break;
			case 2:
				mCategoryImg
						.setImageResource(R.drawable.tab_category_big_pressed);
				mCategoryTxt.setTextColor(Color.rgb(90, 201, 159));
				break;
			default:
				break;
			}
		}
		
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
				ArrayList<HashMap<String, Object>> datalist = new ArrayList<HashMap<String, Object>>();
				for(int i = 0;i < postList.length();i++){
					JSONObject jObj = postList.getJSONObject(i);
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put(KEY_USER_NAME, jObj.getString("userId"));
					map.put(KEY_DATE, jObj.getString("createdDate"));
					map.put(KEY_STAR, jObj.getString("score"));
					map.put(KEY_MAIN, jObj.getString("content"));
					map.put(KEY_POST_ID, jObj.getString(KEY_POST_ID));
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
					map.put(KEY_TAG, tagStr);
					map.put(KEY_COMMENT,jObj.getString("comments"));
					datalist.add(map);
				}
				
				// get data from the table by the ListAdapter
				ListAdapter customAdapter = new ListAdapter(Tab_UI.this, R.layout.tab_hot_item, datalist);
				listViewCategory.setAdapter(customAdapter);
				listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int arg2,
							long arg3) {
						Bundle bundle = new Bundle();
						bundle.putString(CategoryActivity.CATEGORY_POST_ID, getTextValueFromViewById(view, R.id.Detail_Postid));
						bundle.putString(CategoryActivity.CATEGORY_USER_NAME, getTextValueFromViewById(view, R.id.Detail_UserName));
						bundle.putString("title", "My post");
						bundle.putString("introduction", "Product Manager");
						bundle.putString("content", getTextValueFromViewById(view, R.id.Detail_MainText));
						bundle.putString("tag", getTextValueFromViewById(view, R.id.Detail_Tag_text));
						//bundle.putString("Vc_amount", getTextValueFromViewById(view, R.id.Detail_Vc_text));
						bundle.putString("Ui_amount", getTextValueFromViewById(view, R.id.Detail_Ui_text));
						//bundle.putString("Pm_amount", getTextValueFromViewById(view, R.id.Detail_Pm_text));
						bundle.putString("Dev_amount", getTextValueFromViewById(view, R.id.Detail_Dev_text));
						bundle.putString("Opn_amount", getTextValueFromViewById(view, R.id.Detail_Opn_text));
						bundle.putString("Star_amount", getTextValueFromViewById(view, R.id.Detail_Star_text));
						Intent intent = new Intent(Tab_UI.this, ViewPostActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
	
	private void renderHotItems(){
		listViewCategory = (ListView) tab_hot.findViewById(R.id.listview_hotpost);
		JSONObject params = new JSONObject();
		try {
			params.put("userId", getCurrentUserId());
			params.put("count", 10);
			HttpConnectionTask task = new HttpConnectionTask();
			task.execute(PATH_HOT_POST_LIST,
					params.toString(), getCurrentUserToken());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
