package com.paxw.phonesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paxw.phonesafe.bean.ContactsInfo;
import com.paxw.phonesafe.myapplication.R;

import java.util.List;

/**
 * Created by lichuang on 2015/12/17.
 */
public class CallSmsSafeAdapter extends BaseAdapter {
    private List<ContactsInfo> list;
    private Context mContext;
    public CallSmsSafeAdapter(Context context,ItemDeleteClicked listener){
        this.mContext  = context;
        this.listener = listener;
    }
    public void  setData(List<ContactsInfo> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        if (null == list)return 0;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.list_callsmssafe_item,null);
            holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            holder.tv_mode = (TextView) convertView.findViewById(R.id.tv_mode);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_number.setText(list.get(position).getNumber());
        String mode = list.get(position).getMode();
        if ("1".equals(mode)) {
            holder.tv_mode.setText("电话拦截");
        } else if ("2".equals(mode)) {
            holder.tv_mode.setText("短信拦截");
        } else if ("3".equals(mode)) {
            holder.tv_mode.setText("电话+短信拦截");
        }
       holder.iv_delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //这里是不是可以用反射啊
               // TODO: 2015/12/17  用反射试试
               if (null != listener){
                   listener.clickedDelete(position);
               }
           }
       });
        return convertView;
    }
    class ViewHolder {
        TextView tv_number;
        TextView tv_mode;
        ImageView iv_delete;
    }
    private ItemDeleteClicked listener;

    public interface ItemDeleteClicked{
        public void clickedDelete(int position );
    }
}
