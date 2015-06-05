package com.ihub.rangerapp.adapter;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.ihub.rangerapp.CharcoalBurningActivity;
import com.ihub.rangerapp.ElephantPoachingActivity;
import com.ihub.rangerapp.GameMeatActivity;
import com.ihub.rangerapp.IncidentsActivity;
import com.ihub.rangerapp.R;
import com.ihub.rangerapp.SuspiciousActivitiesActivity;
import com.ihub.rangerapp.entity.MenuItem;

public class IncidencesMenuAdapter extends RecyclerView.Adapter<IncidencesMenuAdapter.ViewHolder> {
	
    private List<MenuItem> menuItems;
    private int rowLayout;
    private IncidentsActivity mAct;
    
    public IncidencesMenuAdapter(List<MenuItem> applications, int rowLayout, IncidentsActivity act) {
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
        
        if(mAct.getString(R.string.game_meat).equals(item.getName())) {
        	viewHolder.image.setImageResource(R.drawable.game_meat);
        } else if(mAct.getString(R.string.charcoal_burning).equals(item.getName())) {
        	viewHolder.image.setImageResource(R.drawable.charcoal);
        } else if(mAct.getString(R.string.elephant_poaching).equals(item.getName())) {
        	viewHolder.image.setImageResource(R.drawable.elephant_poaching);
        } else if(mAct.getString(R.string.suspicious_activities).equals(item.getName())) {
        	viewHolder.image.setImageResource(R.drawable.suspicious_act);
        }
    }
    
    @Override
    public int getItemCount() {
        return menuItems == null ? 0 : menuItems.size();
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public ImageView image;
        
        IncidentsActivity hAct;
        
        public ViewHolder(View itemView, IncidentsActivity mAct) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.countryName);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            hAct = mAct;
            name.setOnClickListener(this);
        }
        
        @Override
		public void onClick(View v) {			
			
			Intent intent = null;
			
			switch (getPosition()) {
				case 0:
					intent = new Intent(hAct, GameMeatActivity.class);
					break;
				case 1:
					intent = new Intent(hAct, CharcoalBurningActivity.class);
					break;
				case 2:
					intent = new Intent(hAct, ElephantPoachingActivity.class);
					break;
				case 3:
					intent = new Intent(hAct, SuspiciousActivitiesActivity.class);
					break;
				default:
					break;
				}
			
			if(intent != null) {				
				hAct.open(intent);
			}
		}
        
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		private void startActivity16(Intent i){
	    	ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(hAct);
	    	hAct.startActivity(i, transitionActivityOptions.toBundle());
	    }
    }
}
