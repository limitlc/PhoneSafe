package com.paxw.phonesafe.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2015/12/11.
 */
public class MyApplication extends Application {
    private static Application mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getContext(){
        if (null!=mContext) {
            return mContext;
        }else{
            return null;
        }

    }
}
