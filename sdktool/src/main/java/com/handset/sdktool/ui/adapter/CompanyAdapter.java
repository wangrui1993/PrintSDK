package com.handset.sdktool.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.sdktool.R;
import com.handset.sdktool.dto.CompanyDTO;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import kotlin.jvm.internal.Intrinsics;

/**
 * @ClassName: CompanyAdapter
 * @author: wr
 * @date: 2023/9/14 11:19
 * @Description:作用描述
 */
public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.Holder> {

    private List<CompanyDTO> list;
    private Context context;
    Gson gson = new Gson();
    Type type = new TypeToken<Map<String, String>>() {}.getType();
    public CompanyAdapter(Context context, List<CompanyDTO> list) {
        this.list = list;
        this.context = context;
    }

    private int selectPosition = -1;//

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        if (selectPosition == this.selectPosition) {
            this.selectPosition = -1;
        } else {
            this.selectPosition = selectPosition;
        }
        notifyDataSetChanged();
    }


    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CompanyAdapter.Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Intrinsics.checkNotNullParameter(viewGroup, "parent");
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_seller, viewGroup, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "view");
        TextView tv_text = inflate.findViewById(R.id.tv_name);
        TextView tv_ip = inflate.findViewById(R.id.tv_ip);

        return new CompanyAdapter.Holder(this, inflate, (TextView) tv_text, (TextView) tv_ip);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public void onBindViewHolder(CompanyAdapter.Holder holder, int i) {
        Map<String, String> map = gson.fromJson(list.get(i).getDomain(), type);
        StringBuilder sb = new StringBuilder();
        for(String key:map.keySet()){//keySet获取map集合key的集合  然后在遍历key
            sb.append(key).append(":").append(map.get(key)).append("  ");
        }
        holder.getTv_name().setText(list.get(i).getCompanyName());

        holder.getTv_ip().setText(list.get(i).getIp()+"    |    "+sb.toString());
  
    }

    public final class Holder extends RecyclerView.ViewHolder {
        private final TextView tv_name;
        private final TextView tv_ip;
        final CompanyAdapter this$0;

        public Holder(CompanyAdapter labelEditMenuAdapter, View view,
                      TextView tv_name, TextView tv_ip ) {
            super(view);
            this.this$0 = labelEditMenuAdapter;
            this.tv_name = tv_name;
            this.tv_ip = tv_ip;
        }

        public TextView getTv_ip() {
            return tv_ip;
        }

        public TextView getTv_name() {
            return tv_name;
        }
    }
}
