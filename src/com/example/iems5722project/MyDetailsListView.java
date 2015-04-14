package com.example.iems5722project;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 计算listview的高度加载到scrollview�?
 * 
 * @author 534429149
 * 
 */

public class MyDetailsListView extends ListView {
	public MyDetailsListView(Context context) {
		super(context);
	}

	public MyDetailsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyDetailsListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
