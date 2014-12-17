package com.yhd.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.expressage08.R;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;
import com.yhd.avtivity.DataActivity;
import com.yhd.avtivity.MainActivity;
import com.yhd.data.JsonData;
import com.yhd.db.DatabaseHelper;
import com.yhd.tools.Constant;
import com.yhd.tools.VolleyTool;


@SuppressLint("NewApi") 
public class MineFragment extends Fragment implements OnRefreshListener {

	public static RequestQueue mQueue;
	public JsonData jsonData = new JsonData();
	public static final int WAIT = 0;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private RelativeLayout mActionBarView;
	private MainActivity mainActivity;
	private ProgressDialog pDialog;
	private ListView listView;
	private Button button2;
	private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	ViewPager myViewPager;
	
	private VolleyTool mVolley;
	
	public static void mainTab010(RequestQueue mQueue1){
		mQueue = mQueue1;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mainActivity = (MainActivity) activity;
		myViewPager =  mainActivity.getMyViewPager();
		mPullToRefreshAttacher = mainActivity.getPullToRefreshAttacher();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab01, container, false);
		customActionBar();
		
		listView = (ListView) view.findViewById(R.id.mine);
		
		//myViewPager.setOnTouchListener(new MyGestureListener(getActivity()));
		
		//mSwipeListView = (MyswipListView) view.findViewById(R.id.example_lv_list);
		//mSwipeListView.setViewPager(myViewPager);
		//mSwipeListView.setLongClickable(true);
		//myViewPager.setOnTouchListener(new MyGestureListener(getActivity()));
		//mSwipeListView.setOffsetLeft(getActivity().getResources().getDisplayMetrics().widthPixels*2/3);
		
		
		
		/*button2 = (Button) view.findViewById(R.id.delet);
		button2.setText("清空数据库");
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				delete();
			}
		});*/
		
		mVolley = new VolleyTool() {
			
			@Override
			public void myResponse(JSONObject arg0) {
				Iterator<String> it = arg0.keys();
				while(it.hasNext()){
					String key = it.next();
					if(!key.equals("data")){
						String value;
						try {	
							value = arg0.getString(key);
							
							if(key.equals("nu")){
								jsonData.setNu(value);	
							}else if(key.equals("com")){
								jsonData.setCom(value);
							}else if(key.equals("status")){
								jsonData.setStatus(value);
							}else if(key.equals("state")){
								jsonData.setState(value);
							}
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}else{
						try {
							JSONArray jsonArray = arg0.getJSONArray(key);
							jsonData.setLength(jsonArray.length());
							jsonData.createData();

							for(int i=0;i<jsonArray.length();i++){
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								Iterator<String> it2 = jsonObject.keys();
								
								
								while(it2.hasNext()){
									String key2 = it2.next();
									String value2 = jsonObject.getString(key2);
									if(key2.equals("time")){
										jsonData.dates[i].setTime(value2);
									}else if(key2.equals("context")){
										jsonData.dates[i].setContext(value2);
									}else if(key2.equals("ftime")){
										jsonData.dates[i].setFtime(value2);
									}
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
								
					}
						
				}
				handler.sendEmptyMessage(WAIT);
			}
			
			
			@Override
			public void myErrorResponse(VolleyError arg0) {
				Toast.makeText(getActivity(), "请连入网络！", 2000).show();
				handler.sendEmptyMessage(WAIT);
			}
		};
		
		list.clear();
		
		DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "jsondata.db");
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.query("json", new String[]{"com","id","state"}, null, 
        		null, null, null, null);
        
        while(c.moveToNext()){
        	HashMap<String, Object> hashMap = new HashMap<String, Object>();
        	hashMap.put("com", Constant.getHashmap().get(c.getString(c.getColumnIndex("com"))));
        	hashMap.put("name", Constant.getHashmapName().get(c.getString(c.getColumnIndex("com"))));
        	hashMap.put("id", c.getString(c.getColumnIndex("id")));
        	list.add(hashMap);
        }
		   
        //myViewPager.getParent().requestDisallowInterceptTouchEvent(true);
       /* adapter=new SwipeListViewAdapter(getActivity(), mSwipeListView, list,myViewPager);
		mSwipeListView.setAdapter(adapter);
		mSwipeListView.setSwipeListViewListener(new com.fortysevendeg.swipelistview.BaseSwipeListViewListener(){
			
			@Override
			public void onClickFrontView(int position) {
				super.onClickFrontView(position);
				mSwipeListView.closeOpenedItems();
				HashMap<String, String> hashMap = (HashMap<String, String>) mSwipeListView.getItemAtPosition(position);			
				String type = hashMap.get("com");
				String postid = hashMap.get("id");
				
				getObject(type, postid);
			}

			@Override
			public void onDismiss(int[] reverseSortedPositions) {
				super.onDismiss(reverseSortedPositions);
				
				for(int i=0;i<reverseSortedPositions.length;i++){
					
					final int position = i;
					AlertDialog.Builder builder = new Builder(getActivity());
					builder.setMessage("确认删除吗？");
					builder.setTitle(R.string.dialog_title);
					builder.setPositiveButton("必须的", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg4) {
							DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "jsondata.db");
					        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
					        HashMap<String, String> hashMap = (HashMap<String, String>) mSwipeListView.getItemAtPosition(position);
					        sqLiteDatabase.delete("json", "id=?", new String[]{hashMap.get("id")});
					        list.remove(position);
					        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, 
									R.layout.myexpressage, new String[] {"com","id","state"}, 
									new int[] {R.id.com, R.id.id, R.id.state});
					        listView.setAdapter(simpleAdapter);
							arg0.dismiss();
						}
					});
					builder.setNegativeButton("再想想", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							arg0.dismiss();
						}
					});
					builder.create().show();
					
				}
				adapter.notifyDataSetChanged();
			}
			
		});*/
        
    	/*SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, 
			R.layout.myexpressage, new String[] {"com","name","id"}, 
			new int[] {R.id.com, R.id.name, R.id.id});*/
        
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, 
    			R.layout.myexpressage, new String[] {"com","name","id"}, 
    			new int[] {R.id.com, R.id.name, R.id.id});
        
    	listView.setAdapter(simpleAdapter);
    	
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					HashMap<String, String> hashMap = (HashMap<String, String>) listView.getItemAtPosition(arg2);			
					String type = Constant.getHashmapNameFlag().get(hashMap.get("name"));
					String postid = hashMap.get("id");
					//Log.e("123", type+"-------------"+postid);
					pDialog = ProgressDialog.show(getActivity(), "请稍等", "数据加载中……", true, false);
					//getObject(type, postid);
					
					mVolley.getObject(mQueue, Constant.URL+"type="+type+"&postid="+postid+"#result");
			
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final int position = arg2;
				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setMessage("确认删除吗？");
				builder.setTitle(R.string.dialog_title);
				builder.setPositiveButton("必须的", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg4) {
						DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "jsondata.db");
				        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
				        HashMap<String, String> hashMap = (HashMap<String, String>) listView.getItemAtPosition(position);
				        sqLiteDatabase.delete("json", "id=?", new String[]{hashMap.get("id")});
				        list.remove(position);
				        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, 
								R.layout.myexpressage, new String[] {"com","id","state"}, 
								new int[] {R.id.com, R.id.id, R.id.state});
				        listView.setAdapter(simpleAdapter);
						arg0.dismiss();
					}
				});
				builder.setNegativeButton("再想想", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				});
				builder.create().show();
				return false;
			}
		});
		
		if(mPullToRefreshAttacher!=null){
			mPullToRefreshAttacher.setRefreshableView(listView/*mSwipeListView*/, this);
		}else
			Log.e("123", "null");
		
		return view;
	}
	
	private void customActionBar(){
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mActionBarView = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.actionbar,null);
		getActivity().getActionBar().setCustomView(mActionBarView,params);
		getActivity().getActionBar().setDisplayShowHomeEnabled(false); 
		getActivity().getActionBar().setDisplayShowCustomEnabled(true);
	}

	public void delete(){
		DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "jsondata.db");
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.delete("json", null, null);	
	}

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(msg.what==WAIT){
				pDialog.dismiss();
				if(jsonData.nu!=null){
					Intent intent = new Intent(getActivity(), DataActivity.class);		
					DataActivity.createJson(jsonData);
					getActivity().startActivity(intent);
				}
			}
		}
		
		
	};
	
	/*public void getObject(String type,String postid){
    	JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET,Constant.URL+"type="+type+"&postid="+postid+"#result", null, 
    			new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject arg0) {
						
						Iterator<String> it = arg0.keys();
						while(it.hasNext()){
							String key = it.next();
							if(!key.equals("data")){
								String value;
								try {	
									value = arg0.getString(key);
									
									if(key.equals("nu")){
										jsonData.setNu(value);	
									}else if(key.equals("com")){
										jsonData.setCom(value);
									}else if(key.equals("status")){
										jsonData.setStatus(value);
									}else if(key.equals("state")){
										jsonData.setState(value);
									}
									
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}else{
								try {
									JSONArray jsonArray = arg0.getJSONArray(key);
									jsonData.setLength(jsonArray.length());
									jsonData.createData();

									for(int i=0;i<jsonArray.length();i++){
										JSONObject jsonObject = jsonArray.getJSONObject(i);
										Iterator<String> it2 = jsonObject.keys();
										
										
										while(it2.hasNext()){
											String key2 = it2.next();
											String value2 = jsonObject.getString(key2);
											if(key2.equals("time")){
												jsonData.dates[i].setTime(value2);
											}else if(key2.equals("context")){
												jsonData.dates[i].setContext(value2);
											}else if(key2.equals("ftime")){
												jsonData.dates[i].setFtime(value2);
											}
										}
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
										
							}
								
						}
						handler.sendEmptyMessage(WAIT);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getActivity(), "请连入网络！", 2000).show();
						handler.sendEmptyMessage(WAIT);
					}
				});
    	mQueue.add(jsonObjectRequest);
    }*/

	@Override
	public void onRefreshStarted(View view) {
		mPullToRefreshAttacher.setRefreshComplete();
	}

}
