package com.paxw.phonesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by Administrator on 2015/12/11.
 */
public abstract class BaseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initView();
    }
    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
