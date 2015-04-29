package com.example.iems5722project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.EditText;

import com.example.iems5722project.util.StringUtil;

public enum CategoryTypes {
	IDEA("Idea", R.id.Detail_Idea, -1, -1, ""),
	DEV("Developer", R.id.Detail_Dev, R.id.Detail_Dev_text, R.id.Detail_Dev_text, "Detail_Dev_text"),
	OPN("Operation", R.id.Detail_Opn, R.id.Detail_Opn_text_no, R.id.Detail_Opn_text, "Detail_Opn_text"),
	UI("User Interface", R.id.Detail_Ui, R.id.Detail_Ui_text_no, R.id.Detail_Ui_text, "Detail_Ui_text"),
	PM("Product Manager", R.id.Detail_Pm, R.id.Detail_Pm_text_no, R.id.Detail_Pm_text, "Detail_Pm_text"),
	VC("Venture Capital", R.id.Detail_Vc, R.id.Detail_Vc_text_no, R.id.Detail_Vc_text, "Detail_Vc_text"),
	NULL("NULL", -1, -1, -1, "");
	
	private String title;
	private int categoryId;
	private int inputAmountId;
	private int displayIntId;
	private String displayStringId;
	
	CategoryTypes(String title, int categoryId, int inputAmountId, int displayIntId, String displayStringId){
		this.title = title;
		this.categoryId = categoryId;
		this.inputAmountId = inputAmountId;
		this.displayIntId = displayIntId;
		this.displayStringId = displayStringId;
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
	
	public int getDisplayIntId() {
		return displayIntId;
	}

	public String getDisplayStringId() {
		return displayStringId;
	}

	public static CategoryTypes getByCategoryId(int id){
		for(CategoryTypes obj : CategoryTypes.values()){
			if(id == obj.getCategoryId()){
				return obj;
			}
		}
		return NULL;
	}
	
	public static List<Integer> getCategoryIdList(){
		List<Integer> idLst = new ArrayList<Integer>();
		
		for(CategoryTypes obj : CategoryTypes.values()){
			if(obj.getCategoryId() == -1){
				continue;
			}
			idLst.add(obj.getCategoryId());
		}
		return idLst;
	}
	
	public void extractInputAmountFromOnline(JSONArray wantvalue, EditText editText) throws JSONException{
		if(this.getInputAmountId() == -1){
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
	
	public static void initialDisplayAmount(HashMap<String, Object> map){
		for(CategoryTypes value : CategoryTypes.values()){
			if(StringUtil.isNullOrEmpty(value.getDisplayStringId())){
				continue;
			}
			map.put(value.getDisplayStringId(), "0");
		}
	}

	public static void setDisplayAmount(HashMap<String, Object> map,
			String title, String displayAmount) {
		for(CategoryTypes value : CategoryTypes.values()){
			if(value.name().equals(title)){
				map.put(value.getDisplayStringId(), displayAmount);
				break;
			}
		}
	}
}
