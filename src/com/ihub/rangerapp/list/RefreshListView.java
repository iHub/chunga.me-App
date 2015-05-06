package com.ihub.rangerapp.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;
import com.ihub.rangerapp.R;
import com.ihub.rangerapp.RangerApp;
import com.ihub.rangerapp.adapter.AmazingAdapter;
import com.ihub.rangerapp.listener.DataLoadAdapter;
import com.ihub.rangerapp.loader.DataLoader;
import com.ihub.rangerapp.model.Model;
import com.ihub.rangerapp.util.PagingLoadConfig;
import com.ihub.rangerapp.view.AmazingListView;
import com.ihub.rangerapp.view.BaseView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public abstract class RefreshListView extends BaseView implements OnRefreshListener {
	
	SwipeRefreshLayout swipeLayout;
	
	DataLoader loader;
	
	ListView listView;
	
	Map<String, Object> loadParams = new HashMap<String, Object>();
	
	Boolean isRefresh = false;
	
	public RefreshListView() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.swiperefresh_list, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(getActivity() instanceof ActionBarActivity) {
			ActionBarActivity activity = (ActionBarActivity) getActivity();
			activity.supportInvalidateOptionsMenu();
		}
		
		swipeLayout = (SwipeRefreshLayout) this.getView().findViewById(R.id.swipe_list);
		swipeLayout.setOnRefreshListener(this);
	    swipeLayout.setColorScheme(
	    		R.color.color1, 
	            R.color.color2, 
	            R.color.color3,
	            R.color.color4
	    );
	    
	    listView = (ListView) this.getView().findViewById(android.R.id.list);
	    
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onItemSelected(parent, view, position, id);
			}
		});
	    
	    if(listView instanceof AmazingListView) {
			((AmazingListView)listView).setLoadingView(getActivity().getLayoutInflater().inflate(
					R.layout.load_more_view, null));
		}
	    
	    ListAdapter adptr = getAdapter(getActivity());
	    if(adptr != null)
	    	listView.setAdapter(getAdapter(getActivity()));
	    
	    if(getLoaderName() != null)
	    	loader = RangerApp.get().getLoader(getLoaderName());
	    	    
	    if(loader != null) {
	    	
	    	loader.addLoadListener(new DataLoadAdapter() {
				
				@Override
				public void onLoad(List<Model> data, Map<String, Object> params) {
										
					for (Model m : data) {
						addToAdapter(m);
					}
					
					BaseAdapter adapter = getAdapter(getActivity());
					
					adapter.notifyDataSetChanged();
					
					onLoadResult(params);
					
					hideSwipeProgress();
					
					isRefresh = false;
				}
				
				@Override
				public void onError(int statusCode, Throwable e, JSONObject errorResponse) {
					//ErrorHandler.onFailure(getActivity(), statusCode, e, errorResponse);
				}
			});
	    	
	    	//loader.load(new PagingLoadConfig(), getActivity());
	    	
	    	requestPage(1);
	    }
	}
	
//	@Override
//	public void onResume() {
//		super.onResume();
//		
//		showSwipeProgress();
//		if(loader != null)
//			loader.load(new PagingLoadConfig(), getActivity());
//	}
	
	private void clearAdapter() {
		BaseAdapter adapter = getAdapter(getActivity());
		
		if(adapter instanceof AmazingAdapter) {
			((AmazingAdapter)adapter).notifyNoMorePages();
			((AmazingAdapter)adapter).clear();
		}
		
		//TODO implement
		
	}
	
	protected void onLoadResult(Map<String, Object> params) {
		
		Set<String> keys = params.keySet();
		
		for(String key : keys) {
			loadParams.put(key, params.get(key));
		}
		
		int page = -1;
		int count = 0;
		int offset = -1;
		int limit = -1;
		
		if(params.containsKey("page"))
			page = Integer.valueOf(params.get("page").toString());
		
		if(params.containsKey("count"))
			count = Integer.valueOf(params.get("count").toString());
		
		if(params.containsKey("offset"))
			offset = Integer.valueOf(params.get("offset").toString());
		
		if(params.containsKey("limit"))
			limit = Integer.valueOf(params.get("limit").toString());
		
		if(limit > 0) {
			
			if(count > 0) {
				Double p = Math.ceil(Double.valueOf(count) / Double.valueOf(limit));
				int pages = p.intValue();
				
				Adapter adapter = getAdapter(getActivity());
				
				if(pages > page & page > 0) {
					
					if(adapter instanceof AmazingAdapter)
						((AmazingAdapter)adapter).notifyMayHaveMorePages();
					
				} else if(offset > -1) {
					
					Double cp = Math.floor(Double.valueOf(offset) / Double.valueOf(limit));
					
					page = cp.intValue() + 1;
					
					loadParams.put("page", page);
					
					Double u = Math.ceil((Double.valueOf(offset) + 1) / Double.valueOf(limit)) * limit;
					
					int upper = u.intValue();
					
					if(count > upper) {
						if(adapter instanceof AmazingAdapter)
							((AmazingAdapter)adapter).notifyMayHaveMorePages();
					} else {
						if(adapter instanceof AmazingAdapter)
							((AmazingAdapter)adapter).notifyNoMorePages();
					}
				} else {
					if(adapter instanceof AmazingAdapter)
						((AmazingAdapter)adapter).notifyNoMorePages();
				}
			}
		}
	}

	public abstract void onItemSelected(AdapterView<?> parent, View view, int position, long id);
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action buttons
		switch (item.getItemId()) {
//		case R.id.refresh:
//			refreshData();
//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	protected void refreshData() {
		isRefresh = true;
		
		Adapter adapter = getAdapter(getActivity());
		
		clearAdapter(); //TODO fix this. make refresh load only updates not first page
		
		
		showSwipeProgress();
		
		loadParams.put("query", null); //TODO
		requestPage(1);
	}

	public void showSwipeProgress() {
		swipeLayout.setRefreshing(true);
    }
 
    /**
     * It shows the SwipeRefreshLayout progress
     */
    public void hideSwipeProgress() {
    	swipeLayout.setRefreshing(false);
    }
 
    /**
     * Enables swipe gesture
     */
    public void enableSwipe() {
    	swipeLayout.setEnabled(true);
    }
 
    /**
     * Disables swipe gesture. It prevents manual gestures but keeps the option to show
     * refreshing programatically.
     */
    public void disableSwipe() {
    	swipeLayout.setEnabled(false);
    }
	
	@Override
	public void onRefresh() {
		refreshData();
	}
	
	public abstract String getLoaderName();
	
	public DataLoader getLoader() {
		return loader;
	}
	
	public abstract BaseAdapter getAdapter(Context context);
	
	protected boolean adapterHasModel(Model m) {
		return false;
	}
	
	private void addModelToAdapter(int pos, Model model) {

		Adapter adapter = getAdapter(getActivity());
		if (!adapterHasModel(model)) {
			if (adapter instanceof ArrayAdapter)
				((ArrayAdapter) adapter).insert(model, pos);
//			else if (adapter instanceof PagingAdapter)
//				((PagingAdapter) adapter).insert(model, pos);
		}
	}
	
	public void addToAdapter(Model model) {

		Adapter adapter = getAdapter(getActivity());
		
		if (!adapterHasModel(model)) {
			if (adapter instanceof ArrayAdapter)
				((ArrayAdapter) adapter).add(model);
			else if(adapter instanceof AmazingAdapter)
				((AmazingAdapter)adapter).add(model);
		}
	}
	
	public void onNextPage() {
		int cPage = -1;
		if(loadParams.containsKey("page"))
			cPage = (Integer) loadParams.get("page");
		else if(loadParams.containsKey("offset")) {
			int limit = (Integer) loadParams.get("limit");
			int offset = (Integer) loadParams.get("offset");
			Double p = Math.floor(Double.valueOf(offset) / Double.valueOf(limit));
			
			cPage = p.intValue() + 1;
		}
		
		if(cPage > 0) {
			requestPage(cPage + 1);
		}
			
	}
	
	public void requestPage(int page) {

		PagingLoadConfig config = new PagingLoadConfig();
		
		Set<String> keys = loadParams.keySet();
		
		List<String> excempts = Arrays.asList(new String[] {"page", "offset", "limit", "count"});
		
		for(String key : keys) {
			
			if(!excempts.contains(key))
				config.getData().put(key, loadParams.get(key));
		}
		
		config.setPage(page);
		
		showSwipeProgress();
		loader.load(config, getActivity());
		
	}
	
	public void filter(String query) {
		loadParams.put("query", "".equals(query.trim()) ? null : query);
		requestPage(1);
	}
}