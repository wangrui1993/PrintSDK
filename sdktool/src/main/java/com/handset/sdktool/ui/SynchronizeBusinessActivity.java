package com.handset.sdktool.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handset.sdktool.R;
import com.handset.sdktool.bean.BusinessElementBean;
import com.handset.sdktool.businessdatautil.BusinessDataUtil;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.CompanyDTO;
import com.handset.sdktool.dto.DeleteDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.listener.AddCompanyListener;
import com.handset.sdktool.listener.DeleteCompanyListener;
import com.handset.sdktool.listener.GetAllBusinessListener;
import com.handset.sdktool.listener.GetBusinessServiceByCompanyIdListener;
import com.handset.sdktool.listener.GetElementByBusiness;
import com.handset.sdktool.listener.OnRecycleViewItemClickListener;
import com.handset.sdktool.net.base.BaseBean;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public class SynchronizeBusinessActivity extends BaseActivity {
    private RecyclerView recycle_view;
    private RecyclerView list_menu;
    private ImageView tv_nodata;
    private BusinessSelectAdapter mBusinessSelectAdapter;
    private SelectElementAdapter mSelectElementAdapter;
    private List<BusinessDTO> mListBusiness = new ArrayList<>();
    private List<ElementDTO> mListElement = new ArrayList<>();
    private TextView add;
    private TextView save;
    private List<BusinessElementBean> oldBusinessElementBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_business);
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
        list_menu = (RecyclerView) findViewById(R.id.list_menu);
        tv_nodata = (ImageView) findViewById(R.id.tv_nodata);
        add = (TextView) findViewById(R.id.add);
        save = (TextView) findViewById(R.id.save);
        mBusinessSelectAdapter = new BusinessSelectAdapter(this, mListBusiness);
        list_menu.setLayoutManager(new LinearLayoutManager(this));
        list_menu.setAdapter(mBusinessSelectAdapter);

        list_menu.addOnItemTouchListener(new OnRecycleViewItemClickListener(this, list_menu) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int i) {
                mBusinessSelectAdapter.setSelectPosition(i);
//                getElement(mListBusiness.get(i).getServicetypeNo(),oldBusinessElementBeanList.get(i).getBusinessDTO(),i);
                mListElement.clear();
                mListElement.addAll(oldBusinessElementBeanList.get(i).getElementDTOList());
                mSelectElementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i) {
                String[] strings = new String[2];
                strings[0] = "编辑";
                strings[1] = "删除";
                new XPopup.Builder(SynchronizeBusinessActivity.this)
                        .asCenterList("操作", strings, new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                if (position == 0) {
                                    mBusinessSelectAdapter.setSelectPosition(i);
                                    mListElement.clear();
                                    mListElement.addAll(oldBusinessElementBeanList.get(i).getElementDTOList());
                                    mSelectElementAdapter.notifyDataSetChanged();


                                    Intent intent = new Intent(SynchronizeBusinessActivity.this, AddBusinessActivity.class);
                                    intent.putExtra("companyId", getIntent().getStringExtra("companyId"));
                                    intent.putExtra("ip", getIntent().getStringExtra("ip"));
                                    intent.putExtra("list", new Gson().toJson(mListElement));
                                    intent.putExtra("title", mListBusiness.get(i).getServicetype());
                                    intent.putExtra("servicetypeNo", mListBusiness.get(i).getServicetypeNo());
                                    startActivity(intent);
                                } else {
                                    new XPopup.Builder(SynchronizeBusinessActivity.this).asConfirm("确认删除？", "是否确认删除该业务", (OnConfirmListener) () -> {
                                        List<BusinessDTO> businessDTOList =new ArrayList<>();
                                        businessDTOList.add(mListBusiness.get(i));
                                        DataUtil.getInstance().delServiceInBatches(businessDTOList, new DeleteCompanyListener() {
                                            @Override
                                            public void onSuccess(BaseBean listBaseBean) {
                                                oldBusinessElementBeanList.remove(i);
                                                mListBusiness.remove(i);
                                                mListElement.clear();
                                                mBusinessSelectAdapter.notifyDataSetChanged();
                                                mSelectElementAdapter.notifyDataSetChanged();
                                                if(mListBusiness.size()>0){
                                                    mBusinessSelectAdapter.setSelectPosition(0);
                                                    mListElement.clear();
                                                    mListElement.addAll(oldBusinessElementBeanList.get(0).getElementDTOList());
                                                    mSelectElementAdapter.notifyDataSetChanged();
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }
                                        });
//                                        BusinessDataUtil.getInstance().initBusinessData(SynchronizeBusinessActivity.this, getIntent().getStringExtra("companyId"), oldBusinessElementBeanList);
                                    }).show();
                                }
                            }
                        }).show();

            }
        });
        mSelectElementAdapter = new SelectElementAdapter(this, mListElement);
        recycle_view.setLayoutManager(new GridLayoutManager(this, 3));
        recycle_view.setAdapter(mSelectElementAdapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusinessDataUtil.getInstance().initBusinessData(SynchronizeBusinessActivity.this, getIntent().getStringExtra("companyId"), oldBusinessElementBeanList);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SynchronizeBusinessActivity.this, AddBusinessActivity.class);
                intent.putExtra("companyId", getIntent().getStringExtra("companyId"));
                intent.putExtra("ip", getIntent().getStringExtra("ip"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mListBusiness.clear();
        mBusinessSelectAdapter.notifyDataSetChanged();
        oldBusinessElementBeanList.clear();
        if (getIntent().getStringExtra("companyId") != null) {
            DataUtil.getInstance().getBusinessServiceByCompanyId(getIntent().getStringExtra("companyId"), new GetBusinessServiceByCompanyIdListener() {
                @Override
                public void onSuccess(List<BusinessDTO> listBaseBean) {
                    mListBusiness.addAll(listBaseBean);
                    if (mListBusiness.size() > 0) {
                        mBusinessSelectAdapter.setSelectPosition(0);
                        getElement(mListBusiness.get(0).getServicetypeNo(), mListBusiness.get(0), 0);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    dismissLoadingDialog();
                    if (e.getMessage().contains("End of input at line 1 column 1 path $")) {
                        Toast.makeText(SynchronizeBusinessActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SynchronizeBusinessActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * 获取元素
     */
    private void getElement(String id, BusinessDTO businessDTO, int index) {
        showLoadingDialog();
        Log.e("businessDTO==",businessDTO.getServicetype());
        DataUtil.getInstance().getElementByBusiness(id, new GetElementByBusiness() {
            @Override
            public void onSuccess(List<ElementDTO> listBaseBean) {
                dismissLoadingDialog();
                BusinessElementBean businessElementBean = new BusinessElementBean(businessDTO, listBaseBean);
                oldBusinessElementBeanList.add(businessElementBean);
                if (index == 0) {
                    mListElement.clear();
                    mListElement.addAll(listBaseBean);
                }

                mSelectElementAdapter.notifyDataSetChanged();
                if (mListElement.size() == 0) {
                    tv_nodata.setVisibility(View.VISIBLE);
                } else {
                    tv_nodata.setVisibility(View.GONE);
                }

                if (mListBusiness.size() > 0 && index < mListBusiness.size() - 1) {
                    getElement(mListBusiness.get(index + 1).getServicetypeNo(), mListBusiness.get(index + 1), index + 1);
                }

            }

            @Override
            public void onError(Throwable e) {
                dismissLoadingDialog();
                BusinessElementBean businessElementBean = new BusinessElementBean(businessDTO, new ArrayList<>());
                oldBusinessElementBeanList.add(businessElementBean);
                if (mListBusiness.size() > 0 && index < mListBusiness.size() - 1) {
                    getElement(mListBusiness.get(index + 1).getServicetypeNo(), mListBusiness.get(index + 1), index + 1);
                }
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
            private final ImageView iv_delete;
            final BusinessSelectAdapter this$0;

            public Holder(BusinessSelectAdapter labelEditMenuAdapter, View view, TextView drawableTextView, ImageView iv_delete,
                          ViewGroup viewGroup) {
                super(view);
                this.this$0 = labelEditMenuAdapter;
                this.textView = drawableTextView;
                this.iv_delete = iv_delete;
                this.parent = viewGroup;
            }


            public ViewGroup getParent() {
                return this.parent;
            }

            public TextView getTextView() {
                return this.textView;
            }

            public ImageView getIv_delete() {
                return iv_delete;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Intrinsics.checkNotNullParameter(viewGroup, "parent");
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_business, viewGroup, false);
            Intrinsics.checkNotNullExpressionValue(inflate, "view");
            TextView tv_name = inflate.findViewById(R.id.tv_name);
            ImageView iv_delete = inflate.findViewById(R.id.iv_delete);
            return new Holder(this, inflate, (TextView) tv_name, iv_delete, viewGroup);
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