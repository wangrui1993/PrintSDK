package com.handset.printsdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.printsdk.base.BaseActivity;
import com.handset.sdktool.bean.PrintPaperBean;
import com.handset.sdktool.data.BusinessData;
import com.handset.sdktool.data.ControllerUtil;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.listener.GetAllBusinessListener;
import com.handset.sdktool.listener.OnRecycleViewItemClickListener;
import com.handset.sdktool.modle.ModleData;
import com.handset.sdktool.ui.ConnectBlueToothActivity;
import com.handset.sdktool.ui.SynchronizeBusinessActivity;
import com.handset.sdktool.util.DebugLog;
import com.handset.sdktool.util.GetJsonDataUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;

public class PrintActivity extends BaseActivity {
    @BindView(R.id.tv_search_print)
    TextView tv_search_print;
    @BindView(R.id.tv_connect_print)
    TextView tv_connect_print;
    @BindView(R.id.tv_print_test)
    TextView tv_print_test;
    @BindView(R.id.rv)
    RecyclerView rv;
    private BusinessSelectAdapter mBusinessSelectAdapter;
    private List<BusinessDTO> mListBusiness = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_print;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBusinessSelectAdapter = new BusinessSelectAdapter(this, mListBusiness);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mBusinessSelectAdapter);

        rv.addOnItemTouchListener(new OnRecycleViewItemClickListener(this, rv) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int i) {
                ControllerUtil.getInstance().openPrintPage(PrintActivity.this, mListBusiness.get(i).getServicetypeNo(), jishuju2());
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i) {
            }
        });
        tv_search_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ControllerUtil.getInstance().openPrintPage(PrintActivity.this, "9010", jishuju());
            }
        });
        tv_connect_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ControllerUtil.getInstance().openPrintPage(PrintActivity.this, "9010", jishuju());

            }
        });
        DataUtil.getInstance().getProfessionalWork(new GetAllBusinessListener() {
            @Override
            public void onSuccess(List<BusinessDTO> listBaseBean) {
                mListBusiness.addAll(listBaseBean);
                if (mListBusiness.size() > 0) {
                    mBusinessSelectAdapter.setSelectPosition(0);
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(PrintActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Map<String, Object> jishuju() {
        Map<String, Object> map = new HashMap<>();
        map.put("115", "123");
        map.put("116", "这就412元素code116的内容");

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> mapList1 = new HashMap<>();
        mapList1.put("113", "卫生纸维达卷纸18卷装");
        mapList1.put("112", "1包");

        Map<String, Object> mapList2 = new HashMap<>();
        mapList2.put("113", "达利园蛋黄派");
        mapList2.put("112", "3包");

        Map<String, Object> mapList3 = new HashMap<>();
        mapList3.put("113", "笔记本电脑1台");
        mapList3.put("112", "3台");

        Map<String, Object> mapList4 = new HashMap<>();
        mapList4.put("113", "麦当劳全家桶100桶");
        mapList4.put("112", "100桶");
        list.add(mapList2);
        list.add(mapList3);
        list.add(mapList4);

        List<Map<String, Object>> list2 = new ArrayList<>();

        Map<String, Object> mapList5 = new HashMap<>();
        mapList5.put("115", "笔记本电脑1台");
        mapList5.put("112", "3台");

        Map<String, Object> mapList6 = new HashMap<>();
        mapList6.put("115", "麦当劳全家桶100桶");
        mapList6.put("112", "100桶");
        Map<String, Object> mapList7 = new HashMap<>();
        mapList7.put("115", "桶装水");
        mapList7.put("112", "100桶");
        list2.add(mapList5);
        list2.add(mapList6);
        list2.add(mapList7);
        map.put("213", list);
        map.put("214", list2);


        return map;
    }

    private Map<String, Object> jishuju2() {

        String s = "{\n" +
                "    \"deptName\":\"销售一部\",\n" +
                "    \"carno\":\"辽B22221\",\n" +
                "    \"warehouse\":[\n" +
                "        {\n" +
                "            \"contractForm\":[\n" +
                "                {\n" +
                "                    \"material\":[\n" +
                "                        {\n" +
                "                            \"qty\":\"0.9243\",\n" +
                "                            \"wholepiece\":\"2\",\n" +
                "                            \"materialName\":\"镀锌圆管\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"333\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"stockName\":\"北院成品自销库圆管\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"contractForm\":[\n" +
                "                {\n" +
                "                    \"material\":[\n" +
                "                        {\n" +
                "                            \"qty\":\"0.4978\",\n" +
                "                            \"wholepiece\":\"1\",\n" +
                "                            \"materialName\":\"镀锌方管\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qty\":\"1.1133\",\n" +
                "                            \"wholepiece\":\"2\",\n" +
                "                            \"materialName\":\"镀锌方管\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"111111\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"material\":[\n" +
                "                        {\n" +
                "                            \"qty\":\"5.8745\",\n" +
                "                            \"wholepiece\":\"2\",\n" +
                "                            \"materialName\":\"镀锌方管\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"222\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"stockName\":\"北院成品自销库方管\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"remark\":\"无\"\n" +
                "}";

        Map<String, Object> map = new Gson().fromJson(s, new TypeToken<TreeMap<String, Object>>() {
        }.getType());
        Log.e("mapmap==",new Gson().toJson(map));
        return map;
    }

    @Override
    public void initEvent() {

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
        public BusinessSelectAdapter.Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(com.handset.sdktool.R.layout.item_select_business, viewGroup, false);
            TextView tv_name = inflate.findViewById(com.handset.sdktool.R.id.tv_name);

            return new BusinessSelectAdapter.Holder(this, inflate, (TextView) tv_name, viewGroup);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.list.size();
        }

        public void onBindViewHolder(BusinessSelectAdapter.Holder holder, int i) {
            int height = holder.getParent().getHeight();
            int width = holder.getParent().getWidth();
            if (holder.itemView.getContext().getResources().getConfiguration().orientation == 2) {
                holder.itemView.setLayoutParams(new AbsListView.LayoutParams(width / 2, height / 4));
            }
            holder.getTextView().setText(list.get(i).getServicetype());
            if (selectPosition == i) {
                holder.getTextView().setTextColor(this.context.getResources().getColor(com.handset.sdktool.R.color.theme));
            } else {
                holder.getTextView().setTextColor(this.context.getResources().getColor(com.handset.sdktool.R.color.text_title));
            }

        }
    }
}