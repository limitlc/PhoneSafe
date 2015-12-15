package com.paxw.phonesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.paxw.phonesafe.myapplication.MyApplication;

public class SharedPreferenceUtil {
    private final static int MODE = Context.MODE_PRIVATE;
//    private static SharedPreferences mSharedPreferences; 
//    private static SharedPreferences.Editor mEditor;
    
    private final static String DEFAULT_STRING = "";

    public static String getString(String name, String key) {
    	Context context = MyApplication.getContext();
    	SharedPreferences mSharedPreferences = context.getSharedPreferences(name, MODE);
    	return mSharedPreferences.getString(key, DEFAULT_STRING);
    }
    
    public static int getInteger(String name, String key, int defValue) {
    	Context context = MyApplication.getContext();
    	SharedPreferences mSharedPreferences = context.getSharedPreferences(name, MODE);
    	return mSharedPreferences.getInt(key, defValue);
    }
    
    public static boolean setString(String name, String key, String value) {
    	Context context = MyApplication.getContext();
    	SharedPreferences mSharedPreferences = context.getSharedPreferences(name, MODE);
    	SharedPreferences.Editor mEditor = mSharedPreferences.edit();
    	mEditor.putString(key, value);
    	return mEditor.commit();
    }

    public static boolean setInteger(String name, String key, int value) {
    	Context context = MyApplication.getContext();
    	SharedPreferences mSharedPreferences = context.getSharedPreferences(name, MODE);
    	SharedPreferences.Editor mEditor = mSharedPreferences.edit();
    	mEditor.putInt(key, value);
    	return mEditor.commit();
    }
    
    public static boolean getBoolean(String name, String key) {
    	Context context = MyApplication.getContext();
    	SharedPreferences mSharedPreferences = context.getSharedPreferences(name, MODE);
    	return mSharedPreferences.getBoolean(key, false);
    }
    
    public static boolean setBoolean(String name, String key, boolean value) {
    	Context context = MyApplication.getContext();
    	SharedPreferences mSharedPreferences = context.getSharedPreferences(name, MODE);
    	SharedPreferences.Editor mEditor = mSharedPreferences.edit();
    	mEditor.putBoolean(key, value);
    	return mEditor.commit();
    }
    
    public static boolean delete(Context context, String name, String key) {
    	if (context == null) {
    		return false;
    	}
    	SharedPreferences mSharedPreferences = context.getSharedPreferences(name, MODE);
    	SharedPreferences.Editor mEditor = mSharedPreferences.edit();
    	mEditor.remove(key);
    	return mEditor.commit();
    }
    
    public static boolean clear(Context context, String name) {
    	if (context == null) {
    		return false;
    	}
    	SharedPreferences mSharedPreferences = context.getSharedPreferences(name, MODE);
    	SharedPreferences.Editor mEditor = mSharedPreferences.edit();
    	mEditor.clear();
    	return mEditor.commit();
    }


}
