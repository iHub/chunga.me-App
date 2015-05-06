package com.ihub.rangerapp.util;

import java.util.HashMap;
import java.util.Map;

public class PagingLoadConfig {

	Integer page = 1;

	Integer limit = 15; // DEFAULT

	Map<String, Object> data = new HashMap<String, Object>();

	public PagingLoadConfig() {
	}

	public PagingLoadConfig(Integer page) {
		this.page = page;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer offset() {
		return (page * limit) - limit;
	}
	
	public Integer limit() {
		return offset() + limit;
	}
}