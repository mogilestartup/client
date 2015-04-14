package com.example.iems5722project;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener{
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private List<View> mViews=new ArrayList<View>();
    
    // Tab
    private LinearLayout mTabHot;
    private LinearLayout mTabNewPost;
    private LinearLayout mTabCategory;
    
    private ImageButton mHotImg;
    private ImageButton mNewPostImg;
    private ImageButton mCategoryImg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
		mViewPager=(ViewPager) findViewById(R.id.id_viewpager);
		// Tabs
		mTabHot=(LinearLayout) findViewById(R.id.id_tab_hot);
		mTabNewPost=(LinearLayout) findViewById(R.id.id_tab_new_post);
		mTabCategory=(LinearLayout) findViewById(R.id.id_tab_category);
		// Image Button
		mHotImg=(ImageButton) findViewById(R.id.id_tab_hot_img);
		mNewPostImg=(ImageButton) findViewById(R.id.id_tab_new_post_img);
		mCategoryImg=(ImageButton) findViewById(R.id.id_tab_category_img);
		//
		LayoutInflater mInflater= LayoutInflater.from(this);
		View tab_hot = mInflater.inflate(R.layout.tab_hot, null);
		View tab_new_post = mInflater.inflate(R.layout.tab_new_post, null);
		View tab_category = mInflater.inflate(R.layout.tab_category, null);
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
	        	View view=mViews.get(position);
	        	container.addView(view);
	        	return view;
	        }
	        
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mViews.size();
			}
		};
		mViewPager.setAdapter(mAdapter);
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
         int currentItem=mViewPager.getCurrentItem();
         resetImg();
         switch (currentItem) {
		case 0:
			mHotImg.setImageResource(R.drawable.tab_hot_big_pressed);
			break;
        case 1:
        	mNewPostImg.setImageResource(R.drawable.tab_new_post_big_pressed);
			break;
        case 2:
        	mCategoryImg.setImageResource(R.drawable.tab_category_big_pressed);
			break;
		default:
			break;
		}
		}

	}
}
