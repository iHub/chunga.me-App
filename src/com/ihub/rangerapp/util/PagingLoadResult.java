package com.ihub.rangerapp.util;

import java.util.ArrayList;
import java.util.List;
import com.ihub.rangerapp.model.Model;

public class PagingLoadResult {

	public Integer page;

	public Integer count;

	public Integer offset;
	
	public Integer limit = 25; // DEFAULT

	public List<Model> data = new ArrayList<Model>();

	public PagingLoadResult() {
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<Model> getData() {
		return data;
	}

	public void setData(List<Model> data) {
		this.data = data;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer pages() {
		try {
			Double pages = Math.ceil(Double.valueOf(count)
					/ Double.valueOf(limit));
			return pages.intValue();
		} catch (Exception e) {

		}

		return 0;
	}

	public Boolean hasMorePages() {
		return page < pages();
	}
}