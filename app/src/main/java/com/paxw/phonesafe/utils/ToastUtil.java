package com.paxw.phonesafe.utils;

import android.widget.Toast;

import com.paxw.phonesafe.myapplication.MyApplication;

/**
 * Created by Administrator on 2015/12/11.
 */
public class ToastUtil {
    public static void showToast(String content){
        Toast.makeText(MyApplication.getContext(), content, Toast.LENGTH_SHORT).show();
    }
}
