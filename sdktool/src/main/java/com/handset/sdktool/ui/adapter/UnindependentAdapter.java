package com.handset.sdktool.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.sdktool.R;
import com.handset.sdktool.dto.CompanyDTO;
import com.handset.sdktool.util.SPUtil;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IpAdapter
 * @author: wr
 * @date: 2023/9/16 17:14
 * @Description:作用描述
 */
public class UnindependentAdapter extends BaseAdapter {

    private List<CompanyDTO> list;
    private Context context;
    private OnItemClick onItemClick;
    Gson gson = new Gson();
    Type type = new TypeToken<Map<String, String>>() {
    }.getType();

    public UnindependentAdapter(List<CompanyDTO> data, Context context, OnItemClick onItemClick) {
        this.list = data;
        this.context = context;
        this.onItemClick = onItemClick;
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
            view = View.inflate(context, R.layout.item_unindependent, null);
            holder = new ViewHolder();
            holder.ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(holder);
        } else { //复用convertView
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        CompanyDTO bean = list.get(i);
        Log.e("bdad==", bean == null ? "ddd" : new Gson().toJson(bean));
        holder.tv_name.setText((bean==null||bean.getCompanyName() == null) ? "" : bean.getCompanyName());

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(bean,i);
            }
        });
        return view;
    }

    private static class ViewHolder {
        LinearLayout ll_item;
        TextView tv_name;
    }

    public interface OnItemClick {
        public void onItemClick(CompanyDTO companydto,int index);
    }
}

