package com.paxw.phonesafe.im.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.mobileim.YWChannel;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.login.YWLoginCode;
import com.alibaba.mobileim.login.YWLoginState;
import com.alibaba.mobileim.utility.IMPrefsTools;
import com.paxw.phonesafe.activity.BaseActivity;
import com.paxw.phonesafe.im.utils.LoginSampleHelper;
import com.paxw.phonesafe.im.utils.NotificationInitSampleHelper;
import com.paxw.phonesafe.myapplication.R;
import com.paxw.phonesafe.utils.ToastUtil;

public class LoginActivity extends BaseActivity {
    @Override
    protected void initView() {

    }

    private static final int GUEST = 1;
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String TAG = LoginActivity.class.getSimpleName();
    private LoginSampleHelper loginHelper;
    private EditText userIdView;
    private EditText passwordView;
    private EditText appKeyView;
    private ProgressBar progressBar;
    private Button loginButton;
    private Handler handler = new Handler(Looper.getMainLooper());
    private ImageView lg;
//    public static String APPKEY = "23285121";

    public static final String AUTO_LOGIN_STATE_ACTION = "com.openim.autoLoginStateActionn";

    private BroadcastReceiver mAutoLoginStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final int state = intent.getIntExtra("state", -1);
            YWLog.i(TAG, "mAutoLoginStateReceiver, loginState = " + state);
            if (state == -1){
                return;
            }
            handleAutoLoginState(state);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_login);
        loginHelper = LoginSampleHelper.getInstance();
        userIdView = (EditText) findViewById(R.id.account);
        passwordView = (EditText) findViewById(R.id.password);
        appKeyView = (EditText) findViewById(R.id.appkey);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        //读取本地保存的账号信息
        String localId = IMPrefsTools.getStringPrefs(LoginActivity.this, USER_ID, "");
        if (!TextUtils.isEmpty(localId)) {
            userIdView.setText(localId);
            String localPassword = IMPrefsTools.getStringPrefs(LoginActivity.this, PASSWORD, "");
            if (!TextUtils.isEmpty(localPassword)) {
                passwordView.setText(localPassword);
            }
        }
        //不需要生成随机账号
        //也不需要生成随机账号的密码
        //appKey就更用不到了有自己的appkey
        //初始化账号信息
        init(userIdView.getText().toString());
        myRegisterReceiver();

        loginButton = (Button) findViewById(R.id.login);

        Bitmap logo = BitmapFactory.decodeResource(getResources(),
                R.mipmap.splash_middle_pic);

        lg = (ImageView) findViewById(R.id.logo);
        lg.setImageBitmap(logo);
        userIdView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    passwordView.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前网络状态，若当前无网络则提示用户无网络
                if (YWChannel.getInstance().getNetWorkState().isNetWorkNull()) {
                    Toast.makeText(LoginActivity.this, "网络已断开，请稍后再试哦~", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginButton.setClickable(false);
                final Editable userId = userIdView.getText();
                final Editable password = passwordView.getText();

                if (userId == null || userId.toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    loginButton.setClickable(true);
                    return;
                }
                if (password == null || password.toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    loginButton.setClickable(true);
                    return;
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userIdView.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(passwordView.getWindowToken(), 0);
                init(userId.toString());
                progressBar.setVisibility(View.VISIBLE);
                loginHelper.login_Sample(userId.toString(), password.toString(), LoginSampleHelper.APP_KEY, new IWxCallback() {

                    @Override
                    public void onSuccess(Object... arg0) {
                        saveIdPasswordToLocal(userId.toString(), password.toString());

                        loginButton.setClickable(true);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "登录成功",
                                Toast.LENGTH_SHORT).show();
                        YWLog.i(TAG, "login success!");
                        // FIXME: 2015/12/16 登录成功跳转到哪里去
//                        Intent intent = new Intent(LoginActivity.this, FragmentTabs.class);
//                        intent.putExtra(FragmentTabs.LOGIN_SUCCESS, "loginSuccess");
//                        LoginActivity.this.startActivity(intent);
//                        LoginActivity.this.finish();
//						  YWIMKit mKit = LoginSampleHelper.getInstance().getIMKit();
//						  EServiceContact contact = new EServiceContact("qn店铺测试账号001:找鱼");
//						  LoginActivity.this.startActivity(mKit.getChattingActivityIntent(contact));
//                        mockConversationForDemo();
                        //登录成功了可以发送一个消息试试
                        IYWConversationService conversationService = LoginSampleHelper.getInstance().getIMKit().getConversationService();
                        YWConversation conversation = conversationService.getConversationCreater().createConversationIfNotExist("123");
                        YWMessage msg = YWMessageChannel.createTextMessage("hello");
                        if (conversation.getLastestMessage() == null) {
                            conversation.getMessageSender().sendMessage(msg, 120, null);
                        }

                    }

                    @Override
                    public void onProgress(int arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        progressBar.setVisibility(View.GONE);
                        if (errorCode == YWLoginCode.LOGON_FAIL_INVALIDUSER) { //若用户不存在，则提示使用游客方式登陆
                            showDialog(GUEST);
                        } else {
                            loginButton.setClickable(true);
                            YWLog.w(TAG, "登录失败，错误码：" + errorCode + "  错误信息：" + errorMessage);
                           ToastUtil.showToast("登录失败，错误码：" + errorCode + "  错误信息：" + errorMessage);
                        }
                    }
                });
            }


        });

    }


    private void init(String userId){
        //初始化imkit
        LoginSampleHelper.getInstance().initIMKit(userId, LoginSampleHelper.APP_KEY);
        // FIXME: 2015/12/16 自定义头像和昵称回调初始化(如果不需要自定义头像和昵称，则可以省去)
        //通知栏相关的初始化
        NotificationInitSampleHelper.init();

    }

    /**
     * 保存登录的用户名密码到本地
     *
     * @param userId
     * @param password
     */
    private void saveIdPasswordToLocal(String userId, String password) {
        IMPrefsTools.setStringPrefs(LoginActivity.this, USER_ID, userId);
        IMPrefsTools.setStringPrefs(LoginActivity.this, PASSWORD, password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myUnregisterReceiver();
    }

    /**
     * 根据状态改变ui
     */
    private void myRegisterReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(AUTO_LOGIN_STATE_ACTION);
        LocalBroadcastManager.getInstance(YWChannel.getApplication()).registerReceiver(mAutoLoginStateReceiver, filter);
    }

    private void myUnregisterReceiver(){
        LocalBroadcastManager.getInstance(YWChannel.getApplication()).unregisterReceiver(mAutoLoginStateReceiver);
    }

    private void handleAutoLoginState(int loginState){
        /*
        * logining
        * success
        *
        *
        *
        * */
        if (loginState == YWLoginState.logining.getValue()){
            if (progressBar.getVisibility() != View.VISIBLE){
                progressBar.setVisibility(View.VISIBLE);
            }
            loginButton.setClickable(false);
        }else if (loginState == YWLoginState.success.getValue()){
            loginButton.setClickable(true);
            progressBar.setVisibility(View.GONE);
            // TODO: 2015/12/16 登录成功将要跳转了
//            Intent intent = new Intent(LoginActivity.this, FragmentTabs.class);
//            LoginActivity.this.startActivity(intent);
//            LoginActivity.this.finish();
        } else {
            YWIMKit ywimKit = LoginSampleHelper.getInstance().getIMKit();
            if (ywimKit != null) {
                if (ywimKit.getIMCore().getLoginState() == YWLoginState.success) {
                    loginButton.setClickable(true);
                    progressBar.setVisibility(View.GONE);
                    // TODO: 2015/12/16 重连成功进入界面
//                    Intent intent = new Intent(LoginActivity.this, FragmentTabs.class);
//                    LoginActivity.this.startActivity(intent);
//                    LoginActivity.this.finish();
                    return;
                }
            }
            //当作失败处理
            progressBar.setVisibility(View.GONE);
            loginButton.setClickable(true);
        }
    }

}
