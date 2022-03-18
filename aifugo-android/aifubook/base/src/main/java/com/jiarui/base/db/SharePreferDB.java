package com.jiarui.base.db;

import android.content.Context;
import android.content.SharedPreferences;


import com.jiarui.base.utils.StringUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharePreferDB {

	private static final String TAG = "SharedPreferDB";
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	private String mFileName;
	private SharedPreferences.Editor mEditor ;
	private SharePreferDB(){}

	@Inject
	public SharePreferDB(Context context,String fileName){   
		this.mContext = context;
		this.mFileName = fileName;
		mSharedPreferences = mContext.getSharedPreferences(StringUtil.checkStr(mFileName)?mFileName:"fileName", Context.MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();  
	}
	
	/*
	 * 存数据
	 */
	public void saveData(Map<String ,String> maps){
		if(null == maps || maps.size() == 0)return;
		for(Map.Entry<String, String> map:maps.entrySet()){
			String key = map.getKey();
			String value = map.getValue();
			mEditor.putString(key, value);
		}
		mEditor.commit();
	}
	
	public void saveData(String key,String value){
		mEditor.putString(key, value);
		mEditor.commit();
	}

	public void saveBooleanData(String key,boolean value){
		mEditor.putBoolean(key,value);
		mEditor.commit();
	}
	public boolean getBooleanData(String key){
		if(null==key)
			return false;
		return mSharedPreferences.getBoolean(key,false);
	}

	public void putStringSet(String key,Set<String> values){
		mEditor.putStringSet(key,values);
		mEditor.commit();
	}
	public Set<String> getStringSet(String key){
		if(null==key)
			return null;
		return mSharedPreferences.getStringSet(key, new HashSet<String>());
	}

	public String getValue(String key){
		if(null==key)
			return null;
		return mSharedPreferences.getString(key, null);
	}
	public String getValue(String key,String defValue){
		if(null==key)
			return null;
		return mSharedPreferences.getString(key, defValue);
	}
	/*
	 * 读数据，返回一个Map<String, String>
	 */
	public Map<String, String> readData(){
		return (Map<String, String>)mSharedPreferences.getAll();
	} 
	
	/*
	 * 根据文件名删除文件里的数据
	 */
	public void deletePreference(){
		mSharedPreferences.getAll().clear();
		mEditor.clear().commit();
	}
}
