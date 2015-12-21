package com.paxw.phonesafe.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paxw.phonesafe.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AppManagerActivity extends BaseActivity {

    private TextView tv_avail_rom;
    private TextView tv_avail_sd;
    private ListView lv_appmanager;
    private TextView tv_status;


    protected void initView() {
        setContentView(R.layout.activity_app_manager);
        tv_avail_rom = (TextView) findViewById(R.id.tv_avail_rom);
        tv_avail_sd = (TextView) findViewById(R.id.tv_avail_sd);
        tv_avail_sd.setText("SD卡可用："
                + getTotalSpace(Environment.getExternalStorageDirectory()
                .getAbsolutePath()));
        tv_avail_rom.setText("内存可用："
                + getTotalSpace(Environment.getDataDirectory()
                .getAbsolutePath()));
        lv_appmanager = (ListView) findViewById(R.id.lv_appmanager);
        tv_status = (TextView) findViewById(R.id.tv_status);
        list = getAppFromPhone();
        lv_appmanager.setAdapter(new AppAdapter());


    }
    private String getTotalSpace(String path){
        StatFs stat = new StatFs(path);
        long blockSizeLong = stat.getBlockSize();
        long availableBlocksLong = stat.getAvailableBlocks();
        return Formatter.formatFileSize(this,blockSizeLong*availableBlocksLong);


    }

    private List getAppFromPhone() {
        List<AppInfo> list = new ArrayList();
        PackageManager manager = this.getPackageManager();
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        //图标名称系统应用还是用户应用在内部还是在外部
        for (PackageInfo info : installedPackages) {
            String packname = info.packageName;
            AppInfo appInfo = new AppInfo();
            Drawable icon = info.applicationInfo.loadIcon(manager);
            String name = info.applicationInfo.loadLabel(manager).toString()+info.applicationInfo.uid;
            //应用程序的特征标志。 可以是任意标志的组合
            int flags = info.applicationInfo.flags;//应用交的答题卡
            if((flags & ApplicationInfo.FLAG_SYSTEM)  == 0){
                //用户应用
                appInfo.setIsSystemApp(false);
            }else{
                //系统应用
                appInfo.setIsSystemApp(true);

            }
            if((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE)  == 0){
                //手机内存
                appInfo.setIsInRom(true);
            }else{
                //外部存储
                appInfo.setIsInRom(false);
            }
            appInfo.setIcon(icon);
            appInfo.setName(name);
            appInfo.setPackageName(packname);
            list.add(appInfo);
        }
        return list;
    }
    class AppInfo{
        private String name;
        private boolean isSystemApp;
        private boolean isInRom;
        private Drawable icon;
        private String packageName;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSystemApp() {
            return isSystemApp;
        }

        public void setIsSystemApp(boolean isSystemApp) {
            this.isSystemApp = isSystemApp;
        }

        public boolean isInRom() {
            return isInRom;
        }

        public void setIsInRom(boolean isInRom) {
            this.isInRom = isInRom;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }
    }
    private List<AppInfo> list;
    class AppAdapter extends BaseAdapter{

        @Override
        public int getCount() {

            return null == list? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return null == list? null:list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AppInfo appInfo = list.get(position);

//            if (position == 0) {// 显示一个textview告诉用户有多少个用户应用
//                TextView tv = new TextView(getApplicationContext());
//                tv.setText("用户程序  (" + userAppInfos.size() + ")");
//                tv.setTextColor(Color.WHITE);
//                tv.setBackgroundColor(Color.GRAY);
//                return tv;
//            } else if (position == (userAppInfos.size() + 1)) {
//                TextView tv = new TextView(getApplicationContext());
//                tv.setText("系统程序  (" + systemAppInfos.size() + ")");
//                tv.setTextColor(Color.WHITE);
//                tv.setBackgroundColor(Color.GRAY);
//                return tv;
//            } else if (position <= userAppInfos.size()) {
//                // 用户程序。
//                int newposition = position - 1;
//                appInfo = userAppInfos.get(newposition);
//            } else {
//                // 系统程序
//                int newposition = position - 1 - userAppInfos.size() - 1;
//                appInfo = systemAppInfos.get(newposition);
//            }
            View view;
            ViewHolder holder;
            if (convertView != null && convertView instanceof RelativeLayout) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                view = View.inflate(getApplicationContext(),
                        R.layout.list_app_item, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) view.findViewById(R.id.iv_icon);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                holder.tv_location = (TextView) view
                        .findViewById(R.id.tv_location);
                view.setTag(holder);
            }

            holder.iv.setImageDrawable(appInfo.getIcon());
            holder.tv_name.setText(appInfo.getName());
            if (appInfo.isInRom()) {
                holder.tv_location.setText("手机内存");
            } else {
                holder.tv_location.setText("外部存储卡");
            }
            return view;
        }

    }
    static class ViewHolder {
        ImageView iv;
        TextView tv_name;
        TextView tv_location;
    }


}
