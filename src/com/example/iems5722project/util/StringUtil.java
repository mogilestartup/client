package com.example.iems5722project.util;

public class StringUtil {
	public static String AT = "@";
	
	public static boolean isNullOrEmpty(String str){
		return str == null || str.trim().length() == 0;
	}
}
