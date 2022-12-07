package com.handset.sdktool.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handset.sdktool.R;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.listener.GetAllBusinessListener;
import com.handset.sdktool.listener.GetElementByBusiness;
import com.handset.sdktool.listener.OnRecycleViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public class SynchronizeBusinessActivity extends AppCompatActivity {
    private RecyclerView recycle_view;
    private RecyclerView list_menu;
    private ImageView tv_nodata;
    private BusinessSelectAdapter mBusinessSelectAdapter;
    private SelectElementAdapter mSelectElementAdapter;
    private List<BusinessDTO> mListBusiness = new ArrayList<>();
    private List<ElementDTO> mListElement = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_business);
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
        list_menu = (RecyclerView) findViewById(R.id.list_menu);
        tv_nodata = (ImageView) findViewById(R.id.tv_nodata);

        mBusinessSelectAdapter = new BusinessSelectAdapter(this, mListBusiness);
        list_menu.setLayoutManager(new LinearLayoutManager(this));
        list_menu.setAdapter(mBusinessSelectAdapter);

        list_menu.addOnItemTouchListener(new OnRecycleViewItemClickListener(this, list_menu) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int i) {
                mBusinessSelectAdapter.setSelectPosition(i);
                getElement(mListBusiness.get(i).getServicetypeNo());
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i) {
            }
        });
        mSelectElementAdapter = new SelectElementAdapter(this, mListElement);
        recycle_view.setLayoutManager(new GridLayoutManager(this, 3));
        recycle_view.setAdapter(mSelectElementAdapter);

        DataUtil.getInstance().getProfessionalWork(new GetAllBusinessListener() {
            @Override
            public void onSuccess(List<BusinessDTO> listBaseBean) {
                mListBusiness.addAll(listBaseBean);
                if (mListBusiness.size() > 0) {
                    mBusinessSelectAdapter.setSelectPosition(0);
                    getElement(listBaseBean.get(0).getServicetypeNo());
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SynchronizeBusinessActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取元素
     */
    private void getElement(String id) {
        DataUtil.getInstance().getElementByBusiness(id, new GetElementByBusiness() {
            @Override
            public void onSuccess(List<ElementDTO> listBaseBean) {
                mListElement.clear();
                mListElement.addAll(listBaseBean);
                mSelectElementAdapter.notifyDataSetChanged();
                if (mListElement.size() == 0) {
                    tv_nodata.setVisibility(View.VISIBLE);
                } else {
                    tv_nodata.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SynchronizeBusinessActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class BusinessSelectAdapter extends RecyclerView.Adapter<BusinessSelectAdapter.Holder> {

        private List<BusinessDTO> list;
        private Context context;

        public BusinessSelectAdapter(Context context, List<BusinessDTO> list) {
            this.list = list;
            this.context = context;
        }

        private int selectPosition = -1;//

        public int getSelectPosition() {
            return selectPosition;
        }

        public void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
            notifyDataSetChanged();
        }


        public final class Holder extends RecyclerView.ViewHolder {
            private final ViewGroup parent;
            private final TextView textView;
            final BusinessSelectAdapter this$0;

            public Holder(BusinessSelectAdapter labelEditMenuAdapter, View view, TextView drawableTextView,
                          ViewGroup viewGroup) {
                super(view);
                this.this$0 = labelEditMenuAdapter;
                this.textView = drawableTextView;
                this.parent = viewGroup;
            }

            public ViewGroup getParent() {
                return this.parent;
            }

            public TextView getTextView() {
                return this.textView;
            }

        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Intrinsics.checkNotNullParameter(viewGroup, "parent");
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_business, viewGroup, false);
            Intrinsics.checkNotNullExpressionValue(inflate, "view");
            TextView tv_name = inflate.findViewById(R.id.tv_name);

            return new Holder(this, inflate, (TextView) tv_name, viewGroup);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.list.size();
        }

        public void onBindViewHolder(Holder holder, int i) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            int height = holder.getParent().getHeight();
            int width = holder.getParent().getWidth();
            if (holder.itemView.getContext().getResources().getConfiguration().orientation == 2) {
                holder.itemView.setLayoutParams(new AbsListView.LayoutParams(width / 2, height / 4));
            }
            holder.getTextView().setText(list.get(i).getServicetype());
            if (selectPosition == i) {
                holder.getTextView().setTextColor(this.context.getResources().getColor(R.color.theme));
            } else {
                holder.getTextView().setTextColor(this.context.getResources().getColor(R.color.text_title));
            }

        }
    }

    class SelectElementAdapter extends RecyclerView.Adapter<SelectElementAdapter.Holder> {

        private List<ElementDTO> list;
        private Context context;

        public SelectElementAdapter(Context context, List<ElementDTO> list) {
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


        public final class Holder extends RecyclerView.ViewHolder {
            private final ViewGroup parent;
            private final TextView textView;
            private final TextView textView2;
            private final LinearLayout ll_item;
            final SelectElementAdapter this$0;

            public Holder(SelectElementAdapter labelEditMenuAdapter, View view, TextView drawableTextView,
                          TextView textView21, LinearLayout ll_item, ViewGroup viewGroup) {
                super(view);
                this.this$0 = labelEditMenuAdapter;
                this.textView = drawableTextView;
                this.parent = viewGroup;
                this.textView2 = textView21;
                this.ll_item = ll_item;
            }

            public ViewGroup getParent() {
                return this.parent;
            }

            public TextView getTextView() {
                return this.textView;
            }

            public TextView getTextView2() {
                return this.textView2;
            }

            public LinearLayout getLinearLayout() {
                return this.ll_item;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Intrinsics.checkNotNullParameter(viewGroup, "parent");
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_element, viewGroup, false);
            Intrinsics.checkNotNullExpressionValue(inflate, "view");
            TextView tv_name = inflate.findViewById(R.id.tv_name);
            TextView tv_describe = inflate.findViewById(R.id.tv_describe);
            LinearLayout ll_item = inflate.findViewById(R.id.ll_item);

            return new Holder(this, inflate, (TextView) tv_name, (TextView) tv_describe, (LinearLayout) ll_item, viewGroup);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.list.size();
        }

        public void onBindViewHolder(Holder holder, int i) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            int height = holder.getParent().getHeight();
            int width = holder.getParent().getWidth();
            if (holder.itemView.getContext().getResources().getConfiguration().orientation == 2) {
                holder.itemView.setLayoutParams(new AbsListView.LayoutParams(width / 2, height / 4));
            }
            holder.getTextView().setText(list.get(i).getElementName());
            holder.getTextView2().setText(list.get(i).getElementDesc());
            if (selectPosition == i) {
                holder.getTextView().setTextColor(this.context.getResources().getColor(R.color.white));
                holder.getTextView2().setTextColor(this.context.getResources().getColor(R.color.white));
                holder.getLinearLayout().setBackgroundResource(R.drawable.print_rect_no_radius2);
            } else {
                holder.getTextView().setTextColor(this.context.getResources().getColor(R.color.text_title));
                holder.getTextView2().setTextColor(this.context.getResources().getColor(R.color.text_title));
                holder.getLinearLayout().setBackgroundResource(R.drawable.print_rect_no_radius);
            }

        }
    }
}