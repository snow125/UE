package com.yhd.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.expressage08.R;
import com.yhd.avtivity.DataActivity;
import com.yhd.data.JsonData;
import com.yhd.tools.Constant;
import com.yhd.tools.VolleyTool;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.support.v4.app.Fragment;
import android.text.AlteredCharSequence;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") 
public class SearchFragment extends Fragment{

	public JsonData jsonData = new JsonData();
	public static RequestQueue mQueue;
	public static final int WAIT = 0;
	private ProgressDialog pDialog;
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	private RelativeLayout mActionBarView;
	private VolleyTool mVolley;
	String postid;
	String type;
	
	public static void mainTab020(RequestQueue _mQueue){
		mQueue = _mQueue;
	}
	
	/*public static void toBottom(LinearLayout _mBottom){
		mBottom = _mBottom;
	}*/

	private Spinner spinner;
	private EditText editText1;
	private Button button;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.tab02, container, false);
		editText1 = (EditText) view.findViewById(R.id.number1);
		/*editText1.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					mBottom.setVisibility(View.GONE);
				}else{
					mBottom.setVisibility(View.VISIBLE);
				}
			}
		});*/
		
		initVolley();
		
		button = (Button)view.findViewById(R.id.look);
		
		spinner = (Spinner) view.findViewById(R.id.com);
		//customActionBar();
		
		//String[] comNames = {"中通","韵达","申通","圆通"};
		String[] comNames = getResources().getStringArray(R.array.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, comNames);
		adapter.setDropDownViewResource(R.layout.spinner_down_item);
		//Log.e("123", "here");
		
		spinner.setAdapter(adapter);
		
		/*spinner.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});*/
		
		
		//Log.e("123", type);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				regex();
			}
		});
		
		return view;
	}
	
	private void initVolley() {
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
				Toast.makeText(getActivity(), "请检查网络连接", 2000).show();
				handler.sendEmptyMessage(WAIT);
			}
		};
	}

	public void regex(){
		postid = editText1.getText().toString();
		type = Constant.getHashmapNameFlag().get(spinner.getSelectedItem().toString());
		
		Pattern p = Pattern.compile("\\d{10,13}");
		Matcher m = p.matcher(postid);
		if(m.matches()){
			if(postid.startsWith("6800")||postid.startsWith("2008")){
				type = "zhongtong";
				pDialog = ProgressDialog.show(getActivity(), "请稍等", "数据加载中……", true, false);
				mVolley.getObject(mQueue, Constant.URL+"type="+type+"&postid="+postid+"#result");	
			}else if(postid.startsWith("12")||postid.startsWith("10")){
				type = "yunda";
				pDialog = ProgressDialog.show(getActivity(), "请稍等", "数据加载中……", true, false);
				mVolley.getObject(mQueue, Constant.URL+"type="+type+"&postid="+postid+"#result");	
			}else if(postid.startsWith("1")||postid.startsWith("2")||postid.startsWith("8")){
				type = "yuantong";
				pDialog = ProgressDialog.show(getActivity(), "请稍等", "数据加载中……", true, false);
				mVolley.getObject(mQueue, Constant.URL+"type="+type+"&postid="+postid+"#result");
			}else if(type==null){
				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setMessage(R.string.choose_com);
				builder.setTitle(R.string.dialog_title);
				builder.setNeutralButton(R.string.tab02_com, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				});
				builder.create().show();
			}
			//getObject();
			mVolley.getObject(mQueue, Constant.URL+"type="+type+"&postid="+postid+"#result");
		}else{
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setMessage(R.string.right_num);
			builder.setTitle(R.string.dialog_title);
			builder.setNeutralButton(R.string.tab02_com, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
				}
			});
			builder.create().show();
		}
		
		

	}
	
	/*public void getObject(){
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
						Toast.makeText(getActivity(), "请检查网络连接", 2000).show();
						handler.sendEmptyMessage(WAIT);
					}
				});
    	mQueue.add(jsonObjectRequest);
    }*/
		
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
}
