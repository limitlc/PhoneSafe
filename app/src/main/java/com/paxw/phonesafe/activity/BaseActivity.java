package com.paxw.phonesafe.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2015/12/11.
 */
public abstract class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initView();
    }

    protected abstract void initView();

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
