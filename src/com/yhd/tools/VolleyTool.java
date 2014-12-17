package com.yhd.tools;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public abstract class VolleyTool {
	
	private RequestQueue mQueue;
	
	public void getObject(RequestQueue _mQueue, String url){
		mQueue = _mQueue;
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET, url, null, 
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject arg0) {
						myResponse(arg0);
					}
		}, new Response.ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError arg0) {
				myErrorResponse(arg0);
			}
			
		});
		mQueue.add(jsonObjectRequest);
	}
	
	public abstract void myResponse(JSONObject arg0);
	
	public abstract void myErrorResponse(VolleyError arg0);
	
}
