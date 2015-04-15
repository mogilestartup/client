package com.example.iems5722project;

import com.example.iems5722project.util.StringUtil;

public enum CategoryTypes {
	IDEA("Idea", R.id.Detail_Idea),
	DEV("Developer", R.id.Detail_Dev),
	OPN("Operation", R.id.Detail_Opn),
	UI("User Interface", R.id.Detail_Ui),
	PM("Product Manager", R.id.Detail_Pm),
	VC("Venture Capital", R.id.Detail_Vc);
	
	private String title;
	private int categoryId;
	
	CategoryTypes(String title, int categoryId){
		this.title = title;
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}
	
	public int getCategoryId() {
		return categoryId;
	}

	public static String getTitleByType(String type){
		for(CategoryTypes obj : CategoryTypes.values()){
			if(!StringUtil.isNullOrEmpty(type) && type.equals(obj.name())){
				return obj.getTitle();
			}
		}
		return "";
	}
	
	public static String getTitleByCategoryId(int id){
		for(CategoryTypes obj : CategoryTypes.values()){
			if(id == obj.getCategoryId()){
				return obj.getTitle();
			}
		}
		return "";
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
	
}
