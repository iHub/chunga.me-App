package com.ihub.rangerapp.data.service;

import java.util.Date;
import java.util.Map;

public interface SyncService {

	public Date loadLastSyncDate(Integer rangerID);
	
	public Map<String, Integer> loadCounts(Integer rangerID);
}
