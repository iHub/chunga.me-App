package com.ihub.rangerapp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.ihub.rangerapp.CameraGPSActionBarActivity;
import com.ihub.rangerapp.PhotoActivity;
import com.ihub.rangerapp.R;
import com.ihub.rangerapp.ReportViewerActivity;
import com.ihub.rangerapp.entity.SummaryItem;
import com.ihub.rangerapp.view.reports.ReportFragment;

public class SummaryFragment extends ListFragment {
	
    private List<SummaryItem> mCurrentReviewItems = new ArrayList<SummaryItem>();
    
    private ReviewAdapter mReviewAdapter;
    
    Boolean isEditable = false;
    
    public SummaryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setHasOptionsMenu(true);
        
        mReviewAdapter = new ReviewAdapter();
        
        //load items
        mCurrentReviewItems.clear();
        
        if(getActivity() instanceof ReportViewerActivity) {
        	
        	ReportViewerActivity activity = (ReportViewerActivity) getActivity();
        	mCurrentReviewItems.addAll(activity.getReviewItems());
        	
        	if(activity.getCurrentReport() != null) {
        		ReportFragment currentReport = activity.getCurrentReport();
        		
        		isEditable = currentReport.getIsSelectedEditable();
        		getActivity().supportInvalidateOptionsMenu();
        	}
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        TextView titleView = (TextView) rootView.findViewById(android.R.id.title);
        titleView.setText(R.string.summary);
        
        ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        setListAdapter(mReviewAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class ReviewAdapter extends BaseAdapter {
        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public Object getItem(int position) {
            return mCurrentReviewItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mCurrentReviewItems.get(position).hashCode();
        }

        @Override
        public View getView(int position, View view, ViewGroup container) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View rootView = inflater.inflate(R.layout.list_item_review, container, false);

            final SummaryItem reviewItem = mCurrentReviewItems.get(position);
            String value = reviewItem.getDisplayValue();
            
            if (TextUtils.isEmpty(value)) {
                value = "(None)";
            }           

            if("Image".equals(reviewItem.getTitle())) {
            	
            	rootView = inflater.inflate(R.layout.list_item_review_image, container, false);
            	
            	ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            	imageView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if(reviewItem.getDisplayValue() != null) {
							if(!TextUtils.isEmpty(reviewItem.getDisplayValue())) {
								Intent intent = new Intent(getActivity(), PhotoActivity.class);
								intent.putExtra("path", reviewItem.getDisplayValue());
								startActivity(intent);
							}
						}
					}
				});
            	
            	Bitmap myBitmap = null;
        		
        		try {
        			myBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(value), 60, 60);
        		} catch (Exception e) {}
        		
        		if(myBitmap != null)
        			imageView.setImageBitmap(myBitmap);
        		
            } else {
            	((TextView) rootView.findViewById(android.R.id.text2)).setText(value);
            }
            
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(reviewItem.getTitle());
            
            return rootView;
        }
        
        @Override
        public int getCount() {
            return mCurrentReviewItems.size();
        }
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu, menu);
        menu.getItem(0).setVisible(isEditable);
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	if(item.getItemId() == R.id.action_edit) {
    		openEditView();
    	}
    	return super.onOptionsItemSelected(item);
    }
    
	private void openEditView() {
		if(getActivity() instanceof ReportViewerActivity) {
			
        	ReportViewerActivity activity = (ReportViewerActivity) getActivity();
        	
        	if(activity.getCurrentReport() != null) {
        		activity.getCurrentReport().startEdit();
        	}
        }
	}
}