package com.ihub.rangerapp.adapter;

import java.util.List;

import com.ihub.rangerapp.R;
import com.ihub.rangerapp.entity.SyncModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SyncAdapter extends ArrayAdapter<SyncModel> {
	
	public SyncAdapter(Context context, int resource, List<SyncModel> objects) {
		super(context, resource, objects);
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
		SyncModel model = getItem(position);
		
		View v = convertView;
		
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_sync, null);
        }
        
        TextView nameView = (TextView) v.findViewById(R.id.nameView);
        nameView.setText(model.getName());
        
        TextView countView = (TextView) v.findViewById(R.id.countView);
        countView.setText(model.getCount() + " " + getContext().getString(R.string.record_s));
        
        return v;
	}
}
