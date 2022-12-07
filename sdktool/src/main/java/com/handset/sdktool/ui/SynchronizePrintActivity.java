package com.handset.sdktool.ui;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.handset.sdktool.R;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.dto.PaperDTO;
import com.handset.sdktool.dto.PrinterDTO;
import com.handset.sdktool.listener.GetAllBusinessListener;
import com.handset.sdktool.listener.GetAllPrintListener;
import com.handset.sdktool.listener.GetElementByBusiness;
import com.handset.sdktool.listener.GetPaperByPrint;
import com.handset.sdktool.listener.OnRecycleViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public class SynchronizePrintActivity extends AppCompatActivity {
    private RecyclerView recycle_view;
    private RecyclerView list_menu;
    private TextView title;
    private ImageView tv_nodata;
    private PrintSelectAdapter mPrintSelectAdapter;
    private PaperAdapter mPaperAdapter;
    private List<PrinterDTO> mListBusiness = new ArrayList<>();
    private List<PaperDTO> mListElement = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_business);
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
        list_menu = (RecyclerView) findViewById(R.id.list_menu);
        tv_nodata = (ImageView) findViewById(R.id.tv_nodata);
        title = (TextView) findViewById(R.id.title);
        title.setText("打印机纸张数据");
        mPrintSelectAdapter = new PrintSelectAdapter(this, mListBusiness);
        list_menu.setLayoutManager(new LinearLayoutManager(this));
        list_menu.setAdapter(mPrintSelectAdapter);

        list_menu.addOnItemTouchListener(new OnRecycleViewItemClickListener(this, list_menu) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int i) {
                mPrintSelectAdapter.setSelectPosition(i);
                getPaper(mListBusiness.get(i).getId());
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i) {
            }
        });
        mPaperAdapter = new PaperAdapter(this, mListElement);
        recycle_view.setLayoutManager(new GridLayoutManager(this, 3));
        recycle_view.setAdapter(mPaperAdapter);

        DataUtil.getInstance().getPrits(new GetAllPrintListener() {
            @Override
            public void onSuccess(List<PrinterDTO> listBaseBean) {
                mListBusiness.addAll(listBaseBean);
                if (mListBusiness.size() > 0) {
                    mPrintSelectAdapter.setSelectPosition(0);
                    getPaper(listBaseBean.get(0).getId());
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SynchronizePrintActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取纸张
     */
    private void getPaper(String id) {
        DataUtil.getInstance().getPaper(id, new GetPaperByPrint() {
            @Override
            public void onSuccess(List<PaperDTO> listBaseBean) {
                mListElement.clear();
                mListElement.addAll(listBaseBean);
                mPaperAdapter.notifyDataSetChanged();
                if (mListElement.size() == 0) {
                    tv_nodata.setVisibility(View.VISIBLE);
                } else {
                    tv_nodata.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SynchronizePrintActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class PrintSelectAdapter extends RecyclerView.Adapter<PrintSelectAdapter.Holder> {

        private List<PrinterDTO> list;
        private Context context;

        public PrintSelectAdapter(Context context, List<PrinterDTO> list) {
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
            final PrintSelectAdapter this$0;

            public Holder(PrintSelectAdapter labelEditMenuAdapter, View view, TextView drawableTextView,
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
            holder.getTextView().setText(list.get(i).getPrinterName());
            if (selectPosition == i) {
                holder.getTextView().setTextColor(this.context.getResources().getColor(R.color.theme));
            } else {
                holder.getTextView().setTextColor(this.context.getResources().getColor(R.color.text_title));
            }

        }
    }

    class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.Holder> {

        private List<PaperDTO> list;
        private Context context;

        public PaperAdapter(Context context, List<PaperDTO> list) {
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
            final PaperAdapter this$0;

            public Holder(PaperAdapter labelEditMenuAdapter, View view, TextView drawableTextView,
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
            holder.getTextView().setText(list.get(i).getPaperType());
            holder.getTextView2().setText(list.get(i).getPaperWidth() + "*" + list.get(i).getPaperHeight());
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