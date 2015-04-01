package com.ihub.rangerapp.data.service;

import java.util.Map;

public interface ShiftService {

	public Map<String, Object> startShift(
			String station, 
			String ranch, 
			String leader, 
			String noOfMembers,
			String route,
			String mode,
			String weather,
			String startWP,
			String endWP,
			String purpose);
}