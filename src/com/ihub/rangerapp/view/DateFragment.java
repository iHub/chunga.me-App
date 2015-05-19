package com.ihub.rangerapp.view;

import java.lang.reflect.Method;

import com.ihub.rangerapp.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

public class DateFragment extends Fragment {
	
	DatePicker datePicker;
	Button goBtn;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.date_view, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		datePicker = (DatePicker) view.findViewById(R.id.datePicker);
		goBtn = (Button) view.findViewById(R.id.goBtn);
		
        if(android.os.Build.VERSION.SDK_INT >= 11) {
        	try {
        		Method m = datePicker.getClass().getMethod("setCalendarViewShown", boolean.class);
        		m.invoke(datePicker, false);//
        	} catch(Exception e) {
        		e.printStackTrace();
        	}
        }
        
        goBtn.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				Activity act = getActivity();
			}
		});
	}
}