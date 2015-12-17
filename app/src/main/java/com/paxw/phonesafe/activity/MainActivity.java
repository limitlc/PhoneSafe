package com.paxw.phonesafe.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.paxw.phonesafe.im.activity.LoginActivity;
import com.paxw.phonesafe.myapplication.R;
import com.paxw.phonesafe.utils.Constants;
import com.paxw.phonesafe.utils.MD5Util;
import com.paxw.phonesafe.utils.SharedPreferenceUtil;
import com.paxw.phonesafe.utils.ToastUtil;

import static com.paxw.phonesafe.utils.Constants.KEYPASSWORD;

public class MainActivity extends BaseActivity {

    EditText etPwd;
    EditText etPwdConfirm;
    Button btOk;
    Button btCancel;
    EditText enter_psd;
    Button enterOk;
    Button enterCancel;
    private GridView gv_home;
    private SharedPreferences sp;
    private static final String[] names = {"手机防盗", "通讯卫士", "软件管理", "进程管理",
            "哈哈聊天", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
    private static final int[] icons = {
            R.mipmap.safe, R.mipmap.callmsgsafe, R.mipmap.app,
            R.mipmap.taskmanager, R.mipmap.netmanager, R.mipmap.trojan,
            R.mipmap.sysoptimize, R.mipmap.atools, R.mipmap.settings};

    private AlertDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_home);
        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter());
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0://   shoujifangdao
                        showLostFindDialog();
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this ,CallSmsSafeActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                }
            }
        });
    }

    private void showLostFindDialog() {
        if (isSetupPwd()) {
            showEnterDialog();
        } else {
            showSetupPwdDialog();
        }
    }

    private void showEnterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_enter_password, null);
        enterCancel = (Button) view.findViewById(R.id.enter_cancel);
        enterOk = (Button) view.findViewById(R.id.enter_ok);
        enter_psd = (EditText) view.findViewById(R.id.enter_pwd);
        enterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        enterOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = enter_psd.getText().toString().trim();
                String saved_pwd = sp.getString("password", "");//加密后的md5
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showToast("密码不能为空 ");
                    return;
                }
                if (MD5Util.hexdigest(pwd).equals(saved_pwd)) {
                    //密码正确 进入手机防盗界面

                    dialog.dismiss();
                    Intent intent = new Intent(MainActivity.this, null);
                    startActivity(intent);
                } else {
                    ToastUtil.showToast("密码不争取");
                    return;
                }
            }
        });
        dialog = builder.create();
        dialog.setView(view,0,0,0,0);
        dialog.show();
    }

    private void showSetupPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_setup_password, null);
        btCancel = (Button) view.findViewById(R.id.bt_cancel);
        btOk = (Button) view.findViewById(R.id.bt_ok);
        etPwd = (EditText) view.findViewById(R.id.et_pwd);
        etPwdConfirm = (EditText) view.findViewById(R.id.et_pwd_confirm);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != dialog)
                    dialog.dismiss();
            }
        });
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String psd = etPwd.getText().toString();
                String psdConfirm = etPwdConfirm.getText().toString();
                if (psd.isEmpty() || psdConfirm.isEmpty()) {
                    ToastUtil.showToast("密码不能为空");
                    return;
                }
                if (!TextUtils.equals(psd, psdConfirm)) {
                    ToastUtil.showToast("两次密码输入不一样");
                    etPwd.setText("");
                    etPwdConfirm.setText("");
                    return;
                } else {
                    SharedPreferenceUtil.setString(Constants.SETTINGPASSWORD, Constants.KEYPASSWORD, MD5Util.hexdigest(psd));
                    dialog.dismiss();
                    // TODO: 2015/12/15 跳转到那个界面
                    Intent intent = new Intent(MainActivity.this, null);

                    startActivity(intent);


                }
            }
        });
        dialog  = builder.create();
        dialog.setView(view,0,0,0,0);
        dialog.show();


    }

    private boolean isSetupPwd() {
        String password = SharedPreferenceUtil.getString(Constants.SETTINGPASSWORD, KEYPASSWORD);

        return !TextUtils.isEmpty(password);
    }

    private class HomeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
        }

        // 返回每个位置对应的view对象。
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this,
                    R.layout.list_home_item, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_home_item);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_home_item);
            tv_name.setText(names[position]);
            iv_icon.setImageResource(icons[position]);
            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

}
