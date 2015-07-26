package com.ihub.rangerapp.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ihub.rangerapp.R;
import com.ihub.rangerapp.entity.SyncModel;
import com.ihub.rangerapp.entity.SyncTaskModel;

public class SyncTaskAdapter extends ArrayAdapter<SyncTaskModel> {
	
	public SyncTaskAdapter(Context context, int resource, List<SyncTaskModel> objects) {
		super(context, resource, objects);
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
		SyncTaskModel model = getItem(position);
		
		View v = convertView;
		
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_sync_task, null);
        }
        
        TextView nameView = (TextView) v.findViewById(R.id.nameView);
        nameView.setText(model.getName());
        
        TextView countView = (TextView) v.findViewById(R.id.countView);
        countView.setText(model.getProgress());
        
        return v;
	}
}