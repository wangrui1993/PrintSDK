package com.handset.sdktool.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.sdktool.R;
import com.handset.sdktool.bean.BusinessElementBean;
import com.handset.sdktool.businessdatautil.BusinessDataUtil;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.listener.GetAllBusinessListener;
import com.handset.sdktool.listener.GetAllTemplateListener;
import com.handset.sdktool.listener.GetBusinessServiceByCompanyIdListener;
import com.handset.sdktool.listener.GetElementByBusiness;
import com.handset.sdktool.net.base.ModleListBean;
import com.handset.sdktool.util.HanZiToPinYin;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public class AddBusinessActivity extends AppCompatActivity {
    private RecyclerView recycle_view;
    private TextView save, add, close;
    private EditText et_name;
    private Spinner spinner2;
    private SelectElementAdapter mSelectElementAdapter;
    private List<BusinessDTO> mListBusiness = new ArrayList<>();
    private List<ElementDTO> mListElement = new ArrayList<>();
    List<BusinessElementBean> oldBusinessElementBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
        add = (TextView) findViewById(R.id.add);
        save = (TextView) findViewById(R.id.save);
        close = (TextView) findViewById(R.id.close);
        et_name = (EditText) findViewById(R.id.et_name);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        mSelectElementAdapter = new SelectElementAdapter(this, mListElement);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        recycle_view.setAdapter(mSelectElementAdapter);

        if (getIntent().getStringExtra("title") != null) {
            et_name.setText(getIntent().getStringExtra("title"));
        }
        if (getIntent().getStringExtra("list") != null) {
            Gson gson = new Gson();
            List<ElementDTO> businessElementBeanList = gson.fromJson(getIntent().getStringExtra("list"),
                    new TypeToken<List<ElementDTO>>() {
                    }.getType());
            mListElement.addAll(businessElementBeanList);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListElement.add(new ElementDTO(String.valueOf(System.currentTimeMillis()), "", "", "1"));
                mSelectElementAdapter.notifyDataSetChanged();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_name.getText().toString().isEmpty()) {
                    Toast.makeText(AddBusinessActivity.this, "请填写业务名称", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (ElementDTO bean : mListElement) {
                    if (bean.getElementName() != null && !bean.getElementName().isEmpty() &&
                            (bean.getElementCode() == null || bean.getElementCode().isEmpty())) {
                        bean.setElementCode(HanZiToPinYin.getPinyin(bean.getElementName()));
                    }
                }
                BusinessDTO addProfessionalWorkDTO = new BusinessDTO();
                addProfessionalWorkDTO.setServicetype(et_name.getText().toString().trim());
                addProfessionalWorkDTO.setServicetypeNo(HanZiToPinYin.getIPSample(getIntent().getStringExtra("ip"))
                        + HanZiToPinYin.getAllFirstLetter(et_name.getText().toString().trim()) +
                        (oldBusinessElementBeanList.size() + 1));//业务的唯一标识
                BusinessElementBean businessElementBean = new BusinessElementBean(addProfessionalWorkDTO, mListElement);

//                List<BusinessElementBean> businessElementBeanList = new ArrayList<>();
//                businessElementBeanList.add(businessElementBean);
//                BusinessDataUtil.getInstance().initBusinessData(AddBusinessActivity.this, businessElementBeanList);
                oldBusinessElementBeanList.add(businessElementBean);
                BusinessDataUtil.getInstance().initBusinessData(AddBusinessActivity.this, getIntent().getStringExtra("companyId"), oldBusinessElementBeanList);
            }
        });
        getBusinessServiceByCompanyId();
    }

    /**
     * 获取业务（此处不合理，莹后台统一返回，现无接口）
     */
    private void getBusinessServiceByCompanyId() {
        DataUtil.getInstance().getBusinessServiceByCompanyId(getIntent().getStringExtra("companyId"), new GetBusinessServiceByCompanyIdListener() {
            @Override
            public void onSuccess(List<BusinessDTO> listBaseBean) {
                mListBusiness.addAll(listBaseBean);
                if (mListBusiness.size() > 0) {
                    for (BusinessDTO businessDTO : mListBusiness) {
                        if (getIntent().getStringExtra("servicetypeNo") == null ||
                                !getIntent().getStringExtra("servicetypeNo").equals(businessDTO.getServicetypeNo())) {
                            getElement(businessDTO.getServicetypeNo(), businessDTO);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e.getMessage().contains("End of input at line 1 column 1 path $")) {
                    Toast.makeText(AddBusinessActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddBusinessActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 根据业务获取元素（此处不合理，莹后台统一返回，现无接口）
     */
    private void getElement(String id, BusinessDTO businessDTO) {
        DataUtil.getInstance().getElementByBusiness(id, new GetElementByBusiness() {
            @Override
            public void onSuccess(List<ElementDTO> listBaseBean) {
                BusinessElementBean businessElementBean = new BusinessElementBean(businessDTO, listBaseBean);
                oldBusinessElementBeanList.add(businessElementBean);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(AddBusinessActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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


        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Intrinsics.checkNotNullParameter(viewGroup, "parent");
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_element, viewGroup, false);
            Intrinsics.checkNotNullExpressionValue(inflate, "view");
            TextView tv_text = inflate.findViewById(R.id.tv_text);
            TextView tv_list = inflate.findViewById(R.id.tv_list);
            TextView tv_delete = inflate.findViewById(R.id.tv_delete);
            EditText et_name = inflate.findViewById(R.id.et_name);
            EditText et_code = inflate.findViewById(R.id.et_code);
            EditText et_des = inflate.findViewById(R.id.et_des);
            LinearLayout ll_item = inflate.findViewById(R.id.ll_item);

            return new Holder(this, inflate, (TextView) tv_text, (TextView) tv_list, (LinearLayout) ll_item, viewGroup
                    , et_name, et_code, et_des, tv_delete);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.list.size();
        }

        public void onBindViewHolder(Holder holder, int i) {

            if (holder.getEt_name().getTag() instanceof TextWatcher) {
                holder.getEt_name().removeTextChangedListener((TextWatcher) holder.getEt_name().getTag());
            }
            if (holder.getEt_code().getTag() instanceof TextWatcher) {
                holder.getEt_code().removeTextChangedListener((TextWatcher) holder.getEt_code().getTag());
            }
            if (holder.getEt_des().getTag() instanceof TextWatcher) {
                holder.getEt_des().removeTextChangedListener((TextWatcher) holder.getEt_des().getTag());
            }
            Intrinsics.checkNotNullParameter(holder, "holder");
            if (list.get(i).getElementType().equals("1")) {
                holder.getTextView().setTextColor(this.context.getResources().getColor(R.color.teal_200));
                holder.getTextView2().setTextColor(this.context.getResources().getColor(R.color.theme));
            } else {
                holder.getTextView2().setTextColor(this.context.getResources().getColor(R.color.teal_200));
                holder.getTextView().setTextColor(this.context.getResources().getColor(R.color.theme));
            }

            holder.getEt_name().setText(list.get(i).getElementName());
            holder.getEt_des().setText(list.get(i).getElementDesc());
            holder.getEt_code().setText(list.get(i).getElementCode());
            holder.getTextView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(i).setElementType("1");
                    notifyDataSetChanged();
                }
            });
            holder.getTextView2().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.get(i).setElementType("2");
                    notifyDataSetChanged();
                }
            });

            TextWatcher urltextWatcher_name = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable != null && !TextUtils.isEmpty(editable.toString())) {
                        list.get(i).setElementName(editable.toString());
                    } else {
                        list.get(i).setElementName("");
                    }
                }
            };


            TextWatcher urltextWatcher_code = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable != null && !TextUtils.isEmpty(editable.toString())) {
                        list.get(i).setElementCode(editable.toString());
                    } else {
                        list.get(i).setElementCode("");
                    }
                }
            };


            TextWatcher urltextWatcher_des = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable != null && !TextUtils.isEmpty(editable.toString())) {
                        list.get(i).setElementDesc(editable.toString());
                    } else {
                        list.get(i).setElementDesc("");
                    }
                }
            };
            ((Holder) holder).getTv_delete().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(i);
                    notifyDataSetChanged();
                }
            });

            ((Holder) holder).getEt_code().addTextChangedListener(urltextWatcher_code);
            ((Holder) holder).getEt_code().setTag(urltextWatcher_code);

            ((Holder) holder).getEt_name().addTextChangedListener(urltextWatcher_name);
            ((Holder) holder).getEt_name().setTag(urltextWatcher_name);

            ((Holder) holder).getEt_des().addTextChangedListener(urltextWatcher_des);
            ((Holder) holder).getEt_des().setTag(urltextWatcher_des);
        }

        public final class Holder extends RecyclerView.ViewHolder {
            private final ViewGroup parent;
            private final TextView textView;
            private final TextView textView2;
            private final TextView tv_delete;
            private final EditText et_name;
            private final EditText et_code;
            private final EditText et_des;

            private final LinearLayout ll_item;
            final SelectElementAdapter this$0;

            public Holder(SelectElementAdapter labelEditMenuAdapter, View view, TextView drawableTextView,
                          TextView textView21, LinearLayout ll_item, ViewGroup viewGroup, EditText et_name, EditText et_code, EditText et_des, TextView tv_delete) {
                super(view);
                this.this$0 = labelEditMenuAdapter;
                this.textView = drawableTextView;
                this.parent = viewGroup;
                this.textView2 = textView21;
                this.ll_item = ll_item;
                this.et_name = et_name;
                this.et_code = et_code;
                this.et_des = et_des;
                this.tv_delete = tv_delete;
            }

            public EditText getEt_name() {
                return et_name;
            }

            public EditText getEt_code() {
                return et_code;
            }

            public EditText getEt_des() {
                return et_des;
            }

            public TextView getTv_delete() {
                return tv_delete;
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
    }
}