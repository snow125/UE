package com.yhd.avtivity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.expressage08.R;
import com.yhd.adapter.MyExpressageAdapter;
import com.yhd.data.ExpressageInfor;
import com.yhd.data.JsonData;
import com.yhd.db.DatabaseHelper;
import com.yhd.tools.Constant;
import com.yhd.tools.DensityTool;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DataActivity extends Activity{

	private ListView listView;
	private ArrayList<ExpressageInfor> list = new ArrayList<ExpressageInfor>();
	private ImageView picture;
	private ImageView returnlast;
	private ImageView add;
	private boolean addFlag = true;
	private ImageView call;
	private boolean callFlag = true;
	private TextView name;
	private TextView id;
	public static JsonData jsonData;
	private RelativeLayout mActionBarView;
	private MyExpressageAdapter adapter;
	private RelativeLayout comData;
	private PopupWindow mPopupWindow;
	private TextView poepleName;
	private TextView peopleTel;
	private String peoName;
	private String peoTel;
	private ImageView telCall;
	private ImageView telAdd;
	private DisplayMetrics metric;
	private int xOffset;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data);
		getWidth();
		customActionBar();
		listView = (ListView) findViewById(R.id.data);
		list.clear();
		for(int i=0;i<jsonData.length;i++){
			ExpressageInfor mExpressageInfor = new ExpressageInfor();
			mExpressageInfor.setfTime(jsonData.dates[i].getFtime());
			mExpressageInfor.setContext(jsonData.dates[i].getContext());
			mExpressageInfor.setState(jsonData.getState());
			list.add(mExpressageInfor);
		}
		
		comData = (RelativeLayout) findViewById(R.id.top);
		
		adapter = new MyExpressageAdapter(this, list);
		
	    listView.setAdapter(adapter);
		
	    initPop();
		initPopWeght();
	    
	    picture = (ImageView) findViewById(R.id.pic);
	    picture.setBackgroundResource(Constant.getHashmap().get(jsonData.getCom()));
	    name = (TextView) findViewById(R.id.name);
	    name.setText(Constant.getHashmapName().get(jsonData.getCom()));
	    id = (TextView) findViewById(R.id.id);
	    id.setText(jsonData.getNu());
	    add = (ImageView) findViewById(R.id.add);
	    add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(addFlag){
					add.setBackgroundResource(R.drawable.a_11);
					if(!isQuery()){
						ContentValues contentValues = new ContentValues();
						contentValues.put("com", jsonData.getCom());
						contentValues.put("id", jsonData.getNu());
						contentValues.put("state", "   "+jsonData.getState());
						DatabaseHelper databaseHelper = new DatabaseHelper(DataActivity.this, "jsondata.db");
				        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
				        sqLiteDatabase.insert("json", null, contentValues);
					}
				}else{
					add.setBackgroundResource(R.drawable.add);
				}
				addFlag = !addFlag;
			}
		});
	    
		call = (ImageView) findViewById(R.id.call);
		call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(callFlag){
					call.setBackgroundResource(R.drawable.a_12);
					if(!mPopupWindow.isShowing())
						mPopupWindow.showAsDropDown(comData, xOffset-mPopupWindow.getWidth()+DensityTool.dip2px(DataActivity.this, 50), 0);
						mPopupWindow.getWidth();
						peoName = adapter.getPeopleName();
						peoTel = adapter.getPeopleTel();
						poepleName.setText(peoName);
						peopleTel.setText(peoTel);
						//Log.e("123", peoName+"--------!!!!!!--------"+peoTel);
				}else{
					call.setBackgroundResource(R.drawable.b_04);
					if(mPopupWindow.isShowing()){
						mPopupWindow.dismiss();
					}
				}
				callFlag = !callFlag;
			}
		});
	}
	
	private void getWidth(){
		metric = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(metric);
    	xOffset = metric.widthPixels;
	}
	
	private void initPopWeght(){
		
		telCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+peoTel));
				 startActivity(intent);
			}
		});
		
		telAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				insert();
			}
		});
	}
	
    private void insert() {   
	    //首先插入空值，再得到rawContactsId ，用于下面插值   
	    ContentValues values = new ContentValues ();   
	    //insert a null value  
	    Uri rawContactUri = getContentResolver().insert(RawContacts.CONTENT_URI,values);   
	    long rawContactsId = ContentUris.parseId(rawContactUri);   
	  
	    //往刚才的空记录中插入姓名   
	    values.clear();   
	    //A reference to the _ID that this data belongs to  
	    values.put(StructuredName.RAW_CONTACT_ID,rawContactsId);   
	    //"CONTENT_ITEM_TYPE" MIME type used when storing this in data table  
	    values.put(Data.MIMETYPE,StructuredName.CONTENT_ITEM_TYPE);   
	    //The name that should be used to display the contact.  
	    values.put(StructuredName.DISPLAY_NAME,peoName);   
	    //insert the real values  
	    getContentResolver().insert(Data.CONTENT_URI,values);   
	    //插入电话   
	    values.clear();   
	    values.put(Phone.RAW_CONTACT_ID,rawContactsId);   
	    //String "Data.MIMETYPE":The MIME type of the item represented by this row  
	    //String "CONTENT_ITEM_TYPE": MIME type used when storing this in data table.  
	    values.put(Data.MIMETYPE,Phone.CONTENT_ITEM_TYPE);   
	    values.put(Phone.NUMBER,peoTel);   
	    getContentResolver().insert(Data.CONTENT_URI,values); 
    }  
	
	private void initPop(){
		View view = LayoutInflater.from(this).inflate(R.layout.popup_data, null, false);
		mPopupWindow = new PopupWindow(view, DensityTool.dip2px(this, 110)/*android.app.ActionBar.LayoutParams.WRAP_CONTENT*/, 
				DensityTool.dip2px(this, 120)/*android.app.ActionBar.LayoutParams.WRAP_CONTENT*/, false);
		mPopupWindow.setOutsideTouchable(true);
		poepleName = (TextView) view.findViewById(R.id.people_name);
		peopleTel = (TextView) view.findViewById(R.id.people_tel);
		telCall = (ImageView) view.findViewById(R.id.tel_call);
		telAdd = (ImageView) view.findViewById(R.id.tel_add);
	}
	
	private void customActionBar(){
		
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mActionBarView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.data_actionbar,null);
		
		if(getActionBar()!=null){
			getActionBar().setCustomView(mActionBarView,params);
			getActionBar().setDisplayShowHomeEnabled(false); 
			getActionBar().setDisplayShowCustomEnabled(true);
		}
		initBar();
	}
	
	private void initBar(){
		returnlast = (ImageView) mActionBarView.findViewById(R.id.returnlast);
		returnlast.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(DataActivity.this,MainActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
	
	public boolean isQuery(){
		boolean addFlag = false;
		DatabaseHelper databaseHelper = new DatabaseHelper(DataActivity.this, "jsondata.db");
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.query("json", new String[]{"com","id","state"}, "id=?",
        		new String[]{jsonData.getNu()}, null, null, null);
        while(c.moveToNext()){
        	addFlag = true;
        }
		return addFlag;
	}
	
	public static void createJson(JsonData jsonData1){
		jsonData = jsonData1;
	}
	
}
