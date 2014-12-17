package com.yhd.data;

import android.os.Parcel;
import android.os.Parcelable;

public class JsonData {

	public String nu;
	public String com;
	public String status;
	public String state;

	public static int length;
	public Data[] dates;

	
	
	public Data[] getDates() {
		return dates;
	}



	public void setDates(Data[] dates) {
		this.dates = dates;
	}



	public int getLength() {
		return length;
	}



	public void setLength(int length) {
		this.length = length;
	}

	


	public JsonData(Parcel in) {
		String nu = in.readString();
		String com = in.readString();
		String status = in.readString();
		String state = in.readString();
		Data[] dates = new Data[length];
		for(int i=0;i<length;i++){
			dates[i].time = in.readString();
			dates[i].context = in.readString();
			dates[i].ftime = in.readString();
		}
	}



	public JsonData() {
		super();
	}



	public JsonData(String nu, String com, String status, String state,Data[] dates) {
		super();
		this.nu = nu;
		this.com = com;
		this.status = status;
		this.state = state;
		this.dates = dates;
	}



	public String getNu() {
		return nu;
	}



	public void setNu(String nu) {
		this.nu = nu;
	}



	public String getCom() {
		return com;
	}



	public void setCom(String com) {
		this.com = com;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}
	
	public void createData(){
		dates = new Data[length];
		for(int i=0;i<length;i++){
			dates[i] = new Data("", "", "");
		}
	}

	public class Data{
		private String time;
		private String context;
		private String ftime;			
		
		
		
		public Data() {
			super();
		}

		public Data(String time, String context, String ftime) {
			super();
			this.time = time;
			this.context = context;
			this.ftime = ftime;
		}
		
		public String getTime() {
			return time;
		}
		
		public void setTime(String time) {
			this.time = time;
		}
		
		public String getContext() {
			return context;
		}
		
		public void setContext(String context) {
			this.context = context;
		}
		
		public String getFtime() {
			return ftime;
		}
		
		public void setFtime(String ftime) {
			this.ftime = ftime;
		}  
		
	}	
	
}
