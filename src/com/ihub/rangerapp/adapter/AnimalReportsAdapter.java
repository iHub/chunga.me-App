package com.ihub.rangerapp.adapter;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import com.ihub.rangerapp.AnimalReportsActivity;
import com.ihub.rangerapp.R;
import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.entity.MenuItem;
import com.ihub.rangerapp.view.reports.HerdsReport;
import com.ihub.rangerapp.view.reports.IndividualAnimalsReport;

public class AnimalReportsAdapter extends RecyclerView.Adapter<AnimalReportsAdapter.ViewHolder> {
	
    private List<MenuItem> menuItems;
    private int rowLayout;
    private AnimalReportsActivity mAct;
    
    public AnimalReportsAdapter(List<MenuItem> applications, int rowLayout, AnimalReportsActivity act) {
    	
    	this.menuItems = applications;
        this.rowLayout = rowLayout;
        this.mAct = act;
    }
    
    public void clearApplications() {
        int size = this.menuItems.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                menuItems.remove(0);
            }
            
            this.notifyItemRangeRemoved(0, size);
        }
    }
    
    public void addItems(List<MenuItem> applications) {
        this.menuItems.addAll(applications);
        this.notifyItemRangeInserted(0, applications.size() - 1);
    }
    
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v, mAct);
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final MenuItem item = menuItems.get(i);
        viewHolder.name.setText(item.getName());
    }
    
    @Override
    public int getItemCount() {
        return menuItems == null ? 0 : menuItems.size();
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
    	public TextView name;
        
        AnimalReportsActivity hAct;
        
        public ViewHolder(View itemView, AnimalReportsActivity mAct) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.countryName);
            hAct = mAct;
            name.setOnClickListener(this);
        }
        
        @Override
		public void onClick(View v) {			
			
			Intent intent = null;
			String viewClass = null;
			String title = "Report";
			
			switch (getPosition()) {
				case 0:
					viewClass = IndividualAnimalsReport.class.getSimpleName();
					break;
				case 1:
					viewClass = HerdsReport.class.getSimpleName();
					break;
				default:
					break;
				}
			
			intent = new Intent(hAct, ReportViewerActivity.class);
			
			intent.putExtra("viewClass", viewClass);
			intent.putExtra("title", title);
			
			if(intent != null) {				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					startActivity16(intent);
				} else
					hAct.startActivity(intent);
			}
		}
        
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		private void startActivity16(Intent i){
	    	ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(hAct);
	    	hAct.startActivity(i, transitionActivityOptions.toBundle());
	    }
    }
}