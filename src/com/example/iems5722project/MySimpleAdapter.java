package com.example.iems5722project;

import android.annotation.SuppressLint;

import android.content.Context;

import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class MySimpleAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<HashMap<String, Object>> list;
    private int[] itemlist;
    private int layout_id;
    private String[] flag;
    

    public MySimpleAdapter(Context context, List<HashMap<String, Object>> list,
        int layout_id, String[] flag, int[] itemlist) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.layout_id = layout_id;
        this.flag = flag;
        this.itemlist = itemlist;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub  
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub  
        return 0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub  
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convert_view, ViewGroup parent) {
    	convert_view = mInflater.inflate(layout_id, null);
        int i;
        int len = flag.length;
        // convertView = mInflater.inflate(layoutID, null);  
        for (i = 0; i < len; i++) { 
            if (convert_view.findViewById(itemlist[i]) instanceof ImageView)
            {
                ImageView image_view = (ImageView) convert_view.findViewById(itemlist[i]);
                image_view.setImageBitmap((Bitmap) list.get(position).get(flag[i])); 
            } else if (convert_view.findViewById(itemlist[i]) instanceof TextView)
            {
                TextView tv = (TextView) convert_view.findViewById(itemlist[i]);
                tv.setText((String) list.get(position).get(flag[i]));
            } else {
                
            }
        }
        return convert_view;
    }
}
