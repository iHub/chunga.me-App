package com.ihub.rangerapp.adapter;

import java.util.List;

import com.ihub.rangerapp.AnimalReportsActivity;
import com.ihub.rangerapp.CharcoalBurningReportActivity;
import com.ihub.rangerapp.IncidentsActivity;
import com.ihub.rangerapp.R;
import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.SightingsActivity;
import com.ihub.rangerapp.entity.MenuItem;
import com.ihub.rangerapp.view.reports.CharcoalBurningBagsReport;
import com.ihub.rangerapp.view.reports.CharcoalBurningKilnsReport;
import com.ihub.rangerapp.view.reports.ElephantPoachingReport;
import com.ihub.rangerapp.view.reports.GameMeatReport;
import com.ihub.rangerapp.view.reports.HerdsReport;
import com.ihub.rangerapp.view.reports.IndividualAnimalsReport;
import com.ihub.rangerapp.view.reports.SuspiciousActivitiesReport;
import com.ihub.rangerapp.view.reports.WaterholesReport;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {
	
	private List<MenuItem> menuItems;
    private int rowLayout;
    private Activity activity;
    
    public ReportsAdapter(List<MenuItem> items, int rowLayout, Activity activity) {
    	this.menuItems = items;
    	this.rowLayout = rowLayout;
    	this.activity = activity;
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
    
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public ImageView image;

        Activity activity;
        
        public ViewHolder(View itemView, Activity activity) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.countryName);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            this.activity = activity;
            
            LinearLayout r = (LinearLayout) ((ViewGroup) name.getParent());
            r.setOnClickListener(this);
        }
        
        @Override
		public void onClick(View v) {
        	
			Intent intent = null;
			
			String viewClass = null;
			String title = "Report";
			
			try {
				title = ((TextView)v).getText().toString();
			} catch (Exception e) {}
			
			switch (getPosition()) {
			
				case 0:
					viewClass = GameMeatReport.class.getSimpleName();
					break;
				case 1:
					intent = new Intent(activity, CharcoalBurningReportActivity.class);
					break;
				case 2:
					viewClass = ElephantPoachingReport.class.getSimpleName();
					break;
				case 3:
					viewClass = SuspiciousActivitiesReport.class.getSimpleName();
					break;
				case 4:
					intent = new Intent(activity, AnimalReportsActivity.class);
					break;
				case 5:
					viewClass = WaterholesReport.class.getSimpleName();
					break;
				default:
					break;
				}
			
			
			if(intent == null) {
				intent = new Intent(activity, ReportViewerActivity.class);
				
				if(viewClass != null) {
					intent.putExtra("viewClass", viewClass);
					intent.putExtra("title", title);
				}
			}
			
			if(intent != null) {				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					startActivity16(intent);
				} else
					activity.startActivity(intent);
			}
		}
        
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		private void startActivity16(Intent i){
	    	ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
	    	activity.startActivity(i, transitionActivityOptions.toBundle());
	    }
    }

	public void addItems(List<MenuItem> applications) {
        this.menuItems.addAll(applications);
        this.notifyItemRangeInserted(0, applications.size() - 1);
    }
    
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v, activity);
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final MenuItem item = menuItems.get(i);
        viewHolder.name.setText(item.getName());
        
        switch (i) {
		case 0:
			viewHolder.image.setImageResource(R.drawable.game_meat);
			break;
		case 1:
			viewHolder.image.setImageResource(R.drawable.charcoal);
			break;
		case 2:
			viewHolder.image.setImageResource(R.drawable.elephant_poaching);
			break;
		case 3:
			viewHolder.image.setImageResource(R.drawable.suspicious_act);
			break;
		case 4:
			viewHolder.image.setImageResource(R.drawable.lion);
			break;
		case 5:
			viewHolder.image.setImageResource(R.drawable.bucket);
			break;
		default:
			break;
		}
    }
    
    @Override
    public int getItemCount() {
        return menuItems == null ? 0 : menuItems.size();
    }

}