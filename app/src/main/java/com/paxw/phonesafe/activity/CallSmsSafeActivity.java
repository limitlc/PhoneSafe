package com.paxw.phonesafe.activity;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.paxw.phonesafe.adapter.CallSmsSafeAdapter;
import com.paxw.phonesafe.bean.ContactsInfo;
import com.paxw.phonesafe.dao.NotSafeNumberDao;
import com.paxw.phonesafe.myapplication.R;
import com.paxw.phonesafe.utils.Logs;
import com.paxw.phonesafe.utils.ToastUtil;

import java.util.List;


/**
 * Created by lichuang on 2015/12/17.
 */
public class CallSmsSafeActivity extends BaseActivity implements AbsListView.OnScrollListener, CallSmsSafeAdapter.ItemDeleteClicked {

    private ListView lvCallSmsSafe;

    private LinearLayout llLoading;
    private NotSafeNumberDao dao;
    private int total;
    private boolean isLoading;
    private List<ContactsInfo> list;
    private CallSmsSafeAdapter adapter = null;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            llLoading.setVisibility(View.INVISIBLE);
            adapter.setData(list);
            adapter.notifyDataSetChanged();// 通知数据适配器更新下界面。

            isLoading = false;
        }
    };


    @Override
    protected void initView() {
        setContentView(R.layout.activity_call_sms_safe);
        // FIXME: 2015/12/17测试用ApplicationContext试试   当前的数据库操作是在主线程的是不是要考虑放到子线程
        lvCallSmsSafe = (ListView) findViewById(R.id.lv_call_sms_safe);
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        dao = new NotSafeNumberDao(this);
        adapter = new CallSmsSafeAdapter(this ,this);
        lvCallSmsSafe.setAdapter(adapter);
        total = dao.getTotalCount();
        lvCallSmsSafe.setOnScrollListener(this);
        fillData();
    }

    private void fillData() {
        llLoading.setVisibility(View.VISIBLE);
        isLoading = true;
        new Thread() {
            @Override
            public void run() {
                Logs.e();
                list = dao.findAll();
                for (ContactsInfo info:list) {
                    Logs.e(info.toString());
                }
                //模拟查询时间
                handler.sendEmptyMessageDelayed(0,1000);
            }
        }.start();

    }

    //滚动的状态发生改变的时候执行
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING://惯性滑动
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://静止状态
                // TODO: 2015年12月17日15:16:58 //执行分页查询

                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸滑动
                break;

        }

    }

    //滚动的时候执行的方法
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void clickedDelete(int position) {
        ContactsInfo remove = list.remove(position);
        dao.delete(remove.getNumber());
        total--;
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * 添加一条黑名单号码 弹出对话框
     *
     * @param view
     */
    public void addBlackNumber(View view) {
        AlertDialog.Builder buidler = new AlertDialog.Builder(this);
        final AlertDialog dialog = buidler.create();
        View contentView = View.inflate(this, R.layout.dialog_add_blacknumber,
                null);
        dialog.setView(contentView, 0, 0, 0, 0);
        final EditText et_blacknumber = (EditText) contentView
                .findViewById(R.id.et_blacknumber);
        final RadioGroup rg_mode = (RadioGroup) contentView
                .findViewById(R.id.rg_mode);
        Button bt_ok = (Button) contentView.findViewById(R.id.bt_ok);
        Button bt_cancel = (Button) contentView.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blacknumber = et_blacknumber.getText().toString().trim();
                if (TextUtils.isEmpty(blacknumber)) {
                    ToastUtil.showToast("号码不能为空");
                    return;
                }
                int id = rg_mode.getCheckedRadioButtonId();
                String mode = "3";
                switch (id) {
                    case R.id.rb_all:
                        mode ="3";
                        break;
                    case R.id.rb_phone:
                        mode ="1";
                        break;
                    case R.id.rb_sms:
                        mode = "2";
                        break;
                }
                dao.add(blacknumber, mode);//加载到数据库 。
                total++;
                dialog.dismiss();
                //刷新界面。
                ContactsInfo blacknumberInfo = new ContactsInfo();
                blacknumberInfo.setMode(mode);
                blacknumberInfo.setNumber(blacknumber);
                //把新的黑名单信息加入到当前界面的集合里面
                list.add(0, blacknumberInfo);
                adapter.setData(list);
                adapter.notifyDataSetChanged();//通知界面更新
            }
        });
        dialog.show();
    }

    class  MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

}
