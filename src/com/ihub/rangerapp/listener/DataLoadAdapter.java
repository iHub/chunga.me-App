package com.ihub.rangerapp.listener;

import java.util.List;
import java.util.Map;
import org.json.JSONObject;

import com.ihub.rangerapp.model.Model;

public interface DataLoadAdapter {

        public void onLoad(List<Model> data, Map<String, Object> params);

        public void onError(int statusCode, Throwable e, JSONObject errorResponse);
}
