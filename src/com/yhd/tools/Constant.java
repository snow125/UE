package com.yhd.tools;

import java.util.HashMap;

import com.example.expressage08.R;

public class Constant {
	
	private Constant(){
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public final static String URL = "http://m.kuaidi100.com/query?";
	
	public final static HashMap<String, Integer> hashMap = new HashMap<>();

	public static HashMap<String, Integer> getHashmap() {
		hashMap.put("tiantian", R.drawable.tiantian);
		hashMap.put("zhaijisong", R.drawable.zjs);
		hashMap.put("quanfengkuaidi", R.drawable.quanfeng);
		hashMap.put("yunda", R.drawable.yunda);
		hashMap.put("suer", R.drawable.sure);
		hashMap.put("zhongtong", R.drawable.zto);
		hashMap.put("yuantong", R.drawable.yto);
		return hashMap;
	} 
	
	public final static HashMap<String, String> hashMapName = new HashMap<>();
	static{
		hashMapName.put("tiantian", "天天");
		hashMapName.put("zhaijisong", "宅急送");
		hashMapName.put("quanfengkuaidi", "全峰");
		hashMapName.put("yunda", "韵达");
		hashMapName.put("suer", "速尔");
		hashMapName.put("zhongtong", "中通");
		hashMapName.put("yuantong", "圆通");
	}
	public static HashMap<String, String> getHashmapName() {
		
		return hashMapName;
	} 
	
	public final static HashMap<String, String> hashMapNameFlag = new HashMap<>();
	
	public static HashMap<String, String> getHashmapNameFlag() {
		hashMapNameFlag.put("天天", "tiantian");
		hashMapNameFlag.put("宅急送", "zhaijisong");
		hashMapNameFlag.put("全峰", "quanfengkuaidi");
		hashMapNameFlag.put("韵达", "yunda");
		hashMapNameFlag.put("速尔", "suer");
		hashMapNameFlag.put("中通", "zhongtong");
		hashMapNameFlag.put("圆通", "yuantong");
		return hashMapNameFlag;
	} 
	
}
