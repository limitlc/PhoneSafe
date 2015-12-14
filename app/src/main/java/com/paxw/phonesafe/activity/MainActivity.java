package com.paxw.phonesafe.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.paxw.phonesafe.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private List<String> mData = new ArrayList<>(Arrays.asList(
            "设置", "设备锁", "123", "456", "心形", "banner", "没有界面的Fragment"));
    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        grid = (ListView) findViewById(R.id.gridView);
        grid.setAdapter(new ArrayAdapter<>(this, R.layout.main_activity_list_item, mData));
    }

}
