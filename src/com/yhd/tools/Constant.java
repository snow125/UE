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
		hashMapName.put("tiantian", "����");
		hashMapName.put("zhaijisong", "լ����");
		hashMapName.put("quanfengkuaidi", "ȫ��");
		hashMapName.put("yunda", "�ϴ�");
		hashMapName.put("suer", "�ٶ�");
		hashMapName.put("zhongtong", "��ͨ");
		hashMapName.put("yuantong", "Բͨ");
	}
	public static HashMap<String, String> getHashmapName() {
		
		return hashMapName;
	} 
	
	public final static HashMap<String, String> hashMapNameFlag = new HashMap<>();
	
	public static HashMap<String, String> getHashmapNameFlag() {
		hashMapNameFlag.put("����", "tiantian");
		hashMapNameFlag.put("լ����", "zhaijisong");
		hashMapNameFlag.put("ȫ��", "quanfengkuaidi");
		hashMapNameFlag.put("�ϴ�", "yunda");
		hashMapNameFlag.put("�ٶ�", "suer");
		hashMapNameFlag.put("��ͨ", "zhongtong");
		hashMapNameFlag.put("Բͨ", "yuantong");
		return hashMapNameFlag;
	} 
	
}
