package com.ihub.rangerapp.adapter;

import com.ihub.rangerapp.R;
import com.ihub.rangerapp.entity.IvoryTypeModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class IvoryTypeAdapter extends ArrayAdapter<IvoryTypeModel> {
	
	public IvoryTypeAdapter(Context context, int resource) {
		super(context, resource);
	}
	
	@Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    
    public View getCustomView(int position, View convertView, ViewGroup parent) {
    	
    	IvoryTypeModel model = getItem(position);
    	
    	if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.partial_ivory_type, null);
		}
    	
    	TextView text = (TextView) convertView.findViewById(R.id.text);
    	text.setText(model.getName());
    	
    	return convertView;
    }
 }