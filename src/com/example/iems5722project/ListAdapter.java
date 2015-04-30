package com.example.iems5722project;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<HashMap<String, Object>> {
	private List<HashMap<String, Object>> list;

	public ListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public ListAdapter(Context context, int resource,
			List<HashMap<String, Object>> items) {
		super(context, resource, items);
		list = items;
	}

	@Override
	public View getView(int position, final View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.tab_hot_item, null);

		}

		for (int i = 0; i < list.size(); i++) {
			final HashMap<String, Object> p = list.get(position);

			if (p != null) {
				TextView userName = (TextView) v
						.findViewById(R.id.Detail_UserName);
				TextView date = (TextView) v.findViewById(R.id.Detail_Date);
				TextView star = (TextView) v.findViewById(R.id.Detail_Star_text);
				TextView postId = (TextView) v.findViewById(R.id.Detail_Postid);
				TextView mainText = (TextView) v
						.findViewById(R.id.Detail_MainText);
				TextView vcText = (TextView) v
						.findViewById(R.id.Detail_Vc_text);
				TextView uiText = (TextView) v
						.findViewById(R.id.Detail_Ui_text);
				TextView pmText = (TextView) v
						.findViewById(R.id.Detail_Pm_text);
				TextView devText = (TextView) v
						.findViewById(R.id.Detail_Dev_text);
				TextView opnText = (TextView) v
						.findViewById(R.id.Detail_Opn_text);
				TextView tagText = (TextView) v
						.findViewById(R.id.Detail_Tag_text);
				TextView commentText = (TextView) v
						.findViewById(R.id.Detail_Comment_text);
				commentText.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(convertView.getContext(), CommentActivity.class);				
						intent.putExtra("postId", (String) p.get("postId"));
						convertView.getContext().startActivity(intent);
					}
					
				});
				setTextViewValue(userName, p.get(Tab_UI.KEY_USER_NAME));
				setTextViewValue(date, p.get(Tab_UI.KEY_DATE));
				setTextViewValue(star, p.get(Tab_UI.KEY_STAR));
				setTextViewValue(postId, p.get(BaseActivity.KEY_POST_ID));
				setTextViewValue(mainText, p.get(Tab_UI.KEY_MAIN));
				setTextViewValue(vcText,
						p.get(CategoryTypes.VC.getDisplayStringId()));
				setTextViewValue(uiText,
						p.get(CategoryTypes.UI.getDisplayStringId()));
				setTextViewValue(pmText,
						p.get(CategoryTypes.PM.getDisplayStringId()));
				setTextViewValue(devText,
						p.get(CategoryTypes.DEV.getDisplayStringId()));
				setTextViewValue(opnText,
						p.get(CategoryTypes.OPN.getDisplayStringId()));
				setTextViewValue(tagText, p.get(Tab_UI.KEY_TAG));
				setTextViewValue(commentText, p.get(Tab_UI.KEY_COMMENT));
			}
		}

		return v;
	}

	private void setTextViewValue(TextView textView, Object value) {
		if (value != null) {
			textView.setText((CharSequence) value);
		}
	}
}