package com.example.iems5722project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.EditText;

import com.example.iems5722project.util.StringUtil;

public enum CategoryTypes {
	IDEA("Idea", R.id.Detail_Idea, -1),
	DEV("Developer", R.id.Detail_Dev, R.id.Detail_Dev_text),
	OPN("Operation", R.id.Detail_Opn, R.id.Detail_Opn_text_no),
	UI("User Interface", R.id.Detail_Ui, R.id.Detail_Ui_text_no),
	PM("Product Manager", R.id.Detail_Pm, R.id.Detail_Pm_text_no),
	VC("Venture Capital", R.id.Detail_Vc, R.id.Detail_Vc_text_no),
	NULL("NULL", -1, -1);
	
	private String title;
	private int categoryId;
	private int inputAmountId;
	
	CategoryTypes(String title, int categoryId, int inputAmountId){
		this.title = title;
		this.categoryId = categoryId;
		this.inputAmountId = inputAmountId;
	}

	public String getTitle() {
		return title;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public int getInputAmountId() {
		return inputAmountId;
	}

	public static CategoryTypes getByCategoryId(int id){
		for(CategoryTypes obj : CategoryTypes.values()){
			if(id == obj.getCategoryId()){
				return obj;
			}
		}
		return NULL;
	}
	
	public static int[] getCategoryIdList(){
		int[] ids = new int[6];
		int index = 0;
		for(CategoryTypes obj : CategoryTypes.values()){
			ids[index] = obj.getCategoryId();
			index++;
		}
		return ids;
	}
	
	public void extractInputAmountFromOnline(JSONArray wantvalue, EditText editText) throws JSONException{
		if(this.getInputAmountId() != -1){
			return;
		}
		String amount = editText.getText().toString();
		if(StringUtil.isNullOrEmpty(amount)){
			return;
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("title", this.name());
		jsonObj.put("amount", amount);
		wantvalue.put(jsonObj);
	}
	
}
