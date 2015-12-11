package com.paxw.phonesafe.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.paxw.phonesafe.myapplication.R;
import com.paxw.phonesafe.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity{

    private ImageView image;
    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    protected void initView() {
        setContentView(R.layout.activity_first);
        image = (ImageView) findViewById(R.id.splash_image_view);
        version = (TextView) findViewById(R.id.version_text);
        version.setText("版本号:" + getVersion());
        Timer timer = new Timer();
        TimerTask task =new TimerTask(){

            /**
             * The task to run should be specified in the implementation of the {@code run()}
             * method.
             */
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //// TODO: 跳转到下一个界面
                        ToastUtil.showToast("时间到了要执行下一个界面了");
                    }
                });
            }
        };
        timer.schedule(task,3000);

    }
    public String getVersion() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
