package com.yhd.adapter;

import java.util.List;

import com.example.expressage08.R;
import com.yhd.data.ExpressageInfor;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyExpressageAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context context;
	private List<ExpressageInfor> mExpressage;
	
	private String peopleName;
	private String peopleTel;
	
	public MyExpressageAdapter(Context context, List<ExpressageInfor> mExpressage) {
		super();
		this.context = context;
		this.mExpressage = mExpressage;
		this.inflater = LayoutInflater.from(context);
	}

	public String getPeopleName() {
		return peopleName;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	public String getPeopleTel() {
		return peopleTel;
	}

	public void setPeopleTel(String peopleTel) {
		this.peopleTel = peopleTel;
	}



	@Override
	public int getCount() {
		return mExpressage.size();
	}

	@Override
	public Object getItem(int position) {
		return mExpressage.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.my_data, parent, false);
			mViewHolder = new ViewHolder();
			mViewHolder.timeDay = (TextView) convertView.findViewById(R.id.time_day);
			mViewHolder.timeHour = (TextView) convertView.findViewById(R.id.time_hour);
			mViewHolder.arrive = (ImageView) convertView.findViewById(R.id.arrive);
			mViewHolder.context = (TextView) convertView.findViewById(R.id.context);
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		String time = mExpressage.get(position).getfTime();
		char[] timeChar = time.toCharArray();
		String timeDay = time.copyValueOf(timeChar, 0, 10);
		String timeHour = time.copyValueOf(timeChar, 11, 8);
		mViewHolder.timeDay.setText(timeDay);
		mViewHolder.timeHour.setText(timeHour);
		if((mExpressage.get(position).getState().equals("3")) && (position==0)){
			mViewHolder.arrive.setBackgroundResource(R.drawable.m_03);
			mViewHolder.timeDay.setTextColor(Color.BLUE);
			mViewHolder.timeHour.setTextColor(Color.BLUE);
			mViewHolder.context.setTextColor(Color.BLUE);
		}else{
			mViewHolder.arrive.setBackgroundResource(R.drawable.n_03);
			mViewHolder.timeDay.setTextColor(Color.BLACK);
			mViewHolder.timeHour.setTextColor(Color.BLACK);
			mViewHolder.context.setTextColor(Color.BLACK);
		}
		String contextText = mExpressage.get(position).getContext();
		mViewHolder.context.setText(contextText);
		if(position==1){
			
			char[] textChar = contextText.toCharArray();
			int count=0;
			for(int i=0;i<textChar.length;i++){
				if(textChar[i]=='£º'){
					count++;
					if(count==1){
						peopleName = contextText.copyValueOf(textChar, i+1, 3);
					}else if(count==2){
						peopleTel = contextText.copyValueOf(textChar, i+1, 12);
					}
				}
			}
		}
		return convertView;
	}

	private class ViewHolder{
		TextView timeDay;
		TextView timeHour;
		ImageView arrive;
		TextView context;
	}
	
}
