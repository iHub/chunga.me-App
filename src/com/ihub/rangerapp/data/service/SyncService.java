package com.ihub.rangerapp.data.service;

import java.util.Date;
import java.util.List;
import android.util.Pair;

public interface SyncService {
	
	public Integer startSync();
	
	public void endSync(Integer id);

	public Date loadLastSyncDate();
	
	public List<Pair<String, Integer>> loadCounts();
	
	public List<Integer> loadIds(String tableName);
	
	public String updateRecordLastSyncd(int syncID, int id, String tableName);
}
