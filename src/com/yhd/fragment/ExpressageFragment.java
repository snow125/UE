package com.yhd.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.expressage08.R;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("NewApi") 
public class ExpressageFragment extends Fragment{

	private ListView listView;
	private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	private View view;
	private ImageView picture;
	private TextView name;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tab03, container, false);
		listView = (ListView) view.findViewById(R.id.all_expressage);
		
		findViews();
		
		list.clear();

		HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
		hashMap1.put("pic", R.drawable.tiantian);
		hashMap1.put("com", "天天");
		hashMap1.put("tel", "88695238");
		list.add(hashMap1);
		
		HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
		hashMap2.put("pic", R.drawable.zjs);
		hashMap2.put("com", "宅急送");
		hashMap2.put("tel", "67998795");
		list.add(hashMap2);
		
		HashMap<String, Object> hashMap3 = new HashMap<String, Object>();
		hashMap3.put("pic", R.drawable.quanfeng);
		hashMap3.put("com", "全峰");
		hashMap3.put("tel", "83459864");
		list.add(hashMap3);
		
		HashMap<String, Object> hashMap4 = new HashMap<String, Object>();
		hashMap4.put("pic", R.drawable.yunda);
		hashMap4.put("com", "韵达");
		hashMap4.put("tel", "85269632");
		list.add(hashMap4);
		
		HashMap<String, Object> hashMap5 = new HashMap<String, Object>();
		hashMap5.put("pic", R.drawable.sure);
		hashMap5.put("com", "速尔");
		hashMap5.put("tel", "80953653");
		list.add(hashMap5);
		
		HashMap<String, Object> hashMap6 = new HashMap<String, Object>();
		hashMap6.put("pic", R.drawable.zto);
		hashMap6.put("com", "中通");
		hashMap6.put("tel", "85635764");
		list.add(hashMap6);
		
		HashMap<String, Object> hashMap7 = new HashMap<String, Object>();
		hashMap7.put("pic", R.drawable.yto);
		hashMap7.put("com", "圆通");
		hashMap7.put("tel", "89063167");
		list.add(hashMap7);
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, 
				R.layout.all_expressage, new String[] {"pic", "com", "tel"}, 
				new int[] {R.id.expressage_pic, R.id.com_text, R.id.com_tel});
		
		listView.setAdapter(simpleAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				HashMap<String, Object> hashMapPosition = (HashMap<String, Object>) listView.getItemAtPosition(position);
				
			}
		});
		
		return view;
	}
	
	private void findViews(){
		picture = (ImageView) view.findViewById(R.id.expressage_pic);
		name = (TextView) view.findViewById(R.id.com_text);
	}
	
}
