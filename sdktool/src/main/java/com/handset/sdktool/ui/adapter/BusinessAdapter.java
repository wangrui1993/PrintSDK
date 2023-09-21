package com.handset.sdktool.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.handset.sdktool.R;
import com.handset.sdktool.dto.BusinessDTO;

import java.util.List;

import kotlin.jvm.internal.Intrinsics;

/**
 * @ClassName: BusinessAdapter
 * @author: wr
 * @date: 2023/9/14 11:19
 * @Description:作用描述
 */
public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.Holder> {

    private List<BusinessDTO> list;
    private Context context;
    private int type = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BusinessAdapter(Context context, List<BusinessDTO> list) {
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
    public BusinessAdapter.Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Intrinsics.checkNotNullParameter(viewGroup, "parent");
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_business, viewGroup, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "view");
        TextView tv_text = inflate.findViewById(R.id.tv_name);
        ImageView iv_image = inflate.findViewById(R.id.iv_image);
        return new BusinessAdapter.Holder(this, inflate, (TextView) tv_text, (ImageView) iv_image);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public void onBindViewHolder(BusinessAdapter.Holder holder, int i) {

        holder.getTv_name().setText(list.get(i).getServicetype());
        if (type==0) {
            holder.getIv_image().setVisibility(View.GONE);
        }else {
            holder.getIv_image().setVisibility(View.VISIBLE);
            if (type==1) {
                holder.getIv_image().setImageResource(R.mipmap.shang);
            }else {
                holder.getIv_image().setImageResource(R.mipmap.xia);
            }
        }
    }

    public final class Holder extends RecyclerView.ViewHolder {
        private final TextView tv_name;
        private final ImageView iv_image;
        final BusinessAdapter this$0;

        public Holder(BusinessAdapter labelEditMenuAdapter, View view,
                      TextView tv_name, ImageView iv_image) {
            super(view);
            this.this$0 = labelEditMenuAdapter;
            this.tv_name = tv_name;
            this.iv_image = iv_image;
        }

        public ImageView getIv_image() {
            return iv_image;
        }

        public TextView getTv_name() {
            return tv_name;
        }
    }
}
