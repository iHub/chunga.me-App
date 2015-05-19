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

public class SummaryFragment extends ListFragment {
	
    private List<SummaryItem> mCurrentReviewItems = new ArrayList<SummaryItem>();
    
    private ReviewAdapter mReviewAdapter;
    
    public SummaryFragment() {
    	
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReviewAdapter = new ReviewAdapter();
        
        //load items
        mCurrentReviewItems.clear();
        
        if(getActivity() instanceof ReportViewerActivity) {
        	
        	ReportViewerActivity activity = (ReportViewerActivity) getActivity();
        	mCurrentReviewItems.addAll(activity.getReviewItems());
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
						Intent intent = new Intent(getActivity(), PhotoActivity.class);
						intent.putExtra("path", reviewItem.getDisplayValue());
						startActivity(intent);
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
}