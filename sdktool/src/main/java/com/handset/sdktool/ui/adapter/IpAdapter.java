package com.handset.sdktool.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.handset.sdktool.R;
import com.handset.sdktool.bean.IpBean;

import java.util.List;

/**
 * @ClassName: IpAdapter
 * @author: wr
 * @date: 2023/9/16 17:14
 * @Description:作用描述
 */
public class IpAdapter extends BaseAdapter {

    private List<IpBean> list;
    private Context context;

    public IpAdapter(List<IpBean> data, Context context) {
        this.list = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_ip, null);
            holder = new ViewHolder();

            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_ip = (TextView) view.findViewById(R.id.tv_ip);

            view.setTag(holder);
        } else { //复用convertView
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        IpBean bean = list.get(i);
        holder.tv_ip.setText(bean.ip);
        holder.tv_name.setText(bean.name);

        return view;
    }

    private static class ViewHolder {
        TextView tv_name;
        TextView tv_ip;
    }
}

