package com.paxw.phonesafe.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.paxw.phonesafe.myapplication.R;
import com.paxw.phonesafe.utils.ToastUtil;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity{

    private ImageView image;
    private TextView version;
    private TimerTask task;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateResponse) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        timer.cancel();
                        UmengUpdateAgent.showUpdateDialog(SplashActivity.this, updateResponse);
                        break;
                    case UpdateStatus.No: // has no update
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        break;
                    case UpdateStatus.Timeout: // time out
                        break;
                }
            }
        });
        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {

            @Override
            public void onClick(int status) {
                switch (status) {
                    case UpdateStatus.Update:
//                        Toast.makeText(mContext, "User chooses update.", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.Ignore:
                    case UpdateStatus.NotNow:
                       intoMainActivity();
                        break;
                }
            }
        });

        UmengUpdateAgent.update(this);

    }


    protected void initView() {


        setContentView(R.layout.activity_first);
        image = (ImageView) findViewById(R.id.splash_image_view);
        version = (TextView) findViewById(R.id.version_text);
        version.setText("版本号:" + getVersion());
        timer = new Timer();
        task = new TimerTask(){

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
                        intoMainActivity();
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
           return packageInfo.versionCode+"";
//            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    private void intoMainActivity(){
        //// TODO: 2015/12/14  tiaozhuan
        ToastUtil.showToast("马上就要跳转了啊");
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
