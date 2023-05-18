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
                //携带业务数据打开连接蓝牙打印页
                Log.e("pageh==sss=", new Gson().toJson(mListBusiness.get(i).getServicetypeNo()));
                List<Map<String, Object>> map = new ArrayList<>();
                map.add(jishuju2(0)); map.add(jishuju2(1));
//                ControllerUtil.getInstance().openPrintPage(PrintActivity.this, mListBusiness.get(i).getServicetypeNo(), map);
                ControllerUtil.getInstance().printBitmapById(PrintActivity.this, mListBusiness.get(i).getServicetypeNo(), 2, map, "192.168.31.3", 9100, "ZT410");
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

    private Map<String, Object> jishuju2(int i) {

        Map<String, Object> map = new Gson().fromJson(jishujuData(i), new TypeToken<TreeMap<String, Object>>() {
        }.getType());
        Log.e("mapmap==", new Gson().toJson(map));
        return map;
    }

    private String jishujuData(int i) {
        String s = "";

        String s2 = "{\n" +
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

        String s1 = "{\n" +
                "    \"carno\":\"辽B22221\",\n" +
                "    \"deptName\":\"销售一部\",\n" +
                "    \"remark\":\"无\",\n" +
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
                "                        },\n" +
                "                        {\n" +
                "                            \"qty\":\"6.1133\",\n" +
                "                            \"wholepiece\":\"5\",\n" +
                "                            \"materialName\":\"镀锌方管\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qty\":\"6.1133\",\n" +
                "                            \"wholepiece\":\"5\",\n" +
                "                            \"materialName\":\"镀锌方管\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qty\":\"6.1133\",\n" +
                "                            \"wholepiece\":\"5\",\n" +
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
                "        },{\n" +
                "            \"contractForm\":[\n" +
                "                {\n" +
                "                    \"material\":[\n" +
                "                        {\n" +
                "                            \"qty\":\"2.9243\",\n" +
                "                            \"wholepiece\":\"22\",\n" +
                "                            \"materialName\":\"镀锌三角管\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"444\"\n" +
                "                }, {\n" +
                "                    \"material\":[\n" +
                "                        {\n" +
                "                            \"qty\":\"3.9243\",\n" +
                "                            \"wholepiece\":\"33\",\n" +
                "                            \"materialName\":\"镀锌三角管\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"555\"\n" +
                "                }, {\n" +
                "                    \"material\":[\n" +
                "                        {\n" +
                "                            \"qty\":\"4.9243\",\n" +
                "                            \"wholepiece\":\"44\",\n" +
                "                            \"materialName\":\"镀锌三角管\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"666\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"stockName\":\"北院成品自销库三角管\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        String s3 = "{\n" +
                "    \"carno\":\"辽B22221\",\n" +
                "    \"deptName\":\"销售一部\",\n" +
                "    \"remark\":\"无\",\n" +
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
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"111111\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"material\":[\n" +
                "                        {\n" +
                "                            \"qty\":\"4.4444\",\n" +
                "                            \"wholepiece\":\"2\",\n" +
                "                            \"materialName\":\"镀锌方管\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"222\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"stockName\":\"北院成品自销库方管\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String s4 = "{\n" +
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
                "                            \"materialName\":\"镀锌圆管0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qty\":\"0.9243\",\n" +
                "                            \"wholepiece\":\"2\",\n" +
                "                            \"materialName\":\"镀锌圆管1\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"合同333\"\n" +
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
                "                            \"materialName\":\"镀锌方管2\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qty\":\"1.1133\",\n" +
                "                            \"wholepiece\":\"2\",\n" +
                "                            \"materialName\":\"镀锌方管3\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"合同111111\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"material\":[\n" +
                "                        {\n" +
                "                            \"qty\":\"5.8745\",\n" +
                "                            \"wholepiece\":\"2\",\n" +
                "                            \"materialName\":\"镀锌方管4\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"合同222\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"stockName\":\"北院成品自销库方管\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"material\":[\n" +
                "        {\n" +
                "            \"qty\":\"4.4444\",\n" +
                "            \"wholepiece\":\"2\",\n" +
                "            \"materialName\":\"镀锌方管5\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"qty\":\"3.335\",\n" +
                "            \"wholepiece\":\"3\",\n" +
                "            \"materialName\":\"镀锌方管6\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"qty\":\"7.7777\",\n" +
                "            \"wholepiece\":\"3\",\n" +
                "            \"materialName\":\"镀锌方管7\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"qty\":\"8.8888\",\n" +
                "            \"wholepiece\":\"3\",\n" +
                "            \"materialName\":\"镀锌方管8\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"qty\":\"9.9999\",\n" +
                "            \"wholepiece\":\"3\",\n" +
                "            \"materialName\":\"镀锌方管9\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"remark\":\"无\"\n" +
                "}";
//        String s5 = "{\n" +
//                "    \"manufactor\":\"天应泰钢管\",\n" +
//                "    \"wholepiece\":\"444\",\n" +
//                "    \"qty\":\"123\",\n" +
//                "    \"specification\":\"40*30*20\",\n" +
//                "    \"batchno\":\"自华金销50*50\",\n" +
//                "    \"date\":\"2022/10/31\",\n" +
//                "    \"repair\":\"补\",\n" +
//                "    \"brCode\":\"123123123222\"" +
//                "}";

//        String s5="{\n" +
//                "    \"manufactor\":\"天应泰钢管\",\n" +
//                "    \"wholepiece\":123,\n" +
//                "    \"qty\":\"123\",\n" +
//                "    \"specification\":\"40*30*20\",\n" +
//                "    \"batchno\":\"自华金销50*50\",\n" +
//                "    \"date\":\"2022/10/31\",\n" +
//                "    \"repair\":\"补\",\n" +
//                "    \"brCode\":\"123123123222\"\n" +
//                "}";


        String s5 = "{\n" +
                "    \"tiaoma\":\"1234567811\",\n" +
                "    \"guige\":\"40*30*20\",\n" +
                "    \"jianyanyua\":\"1312\",\n" +
                "    \"pihao\":\"批号：132456\",\n" +
                "    \"xinghao\":\"999\",\n" +
                "    \"banzu\":\"新厂三车二组\"\n" +
                "}";
        String s55 = "{\n" +
                "    \"tiaoma\":\"1234567811\",\n" +
                "    \"guige\":\"40*30*20\",\n" +
                "    \"jianyanyua\":\"1312\",\n" +
                "    \"pihao\":\"批号：132456\",\n" +
                "    \"xinghao\":\"111\",\n" +
                "    \"banzu\":\"新厂三车二组\"\n" +
                "}";
        String s6 = "{\n" +
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
                "                            \"materialName\":\"镀锌圆管11111\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"batchno\":\"合同——--333\"\n" +
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
                "                    \"batchno\":\"合同——--111111\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"material\":[\n" +
                "                        {\n" +
                "                            \"qty\":\"5.8745\",\n" +
                "                            \"wholepiece\":\"2\",\n" +
                "                            \"materialName\":\"镀锌方管777777\"\n" +
                "                        }, {\n" +
                "                            \"qty\":\"5.8745\",\n" +
                "                            \"wholepiece\":\"2\",\n" +
                "                            \"materialName\":\"镀锌方管88888\"\n" +
                "                        }                    ],\n" +
                "                    \"batchno\":\"合同——--222\"\n" +
                "                }\n" +
                "            ],\n" +
                "                  \"material\":[\n" +
                "                        {\n" +
                "                            \"qty\":\"0.9243\",\n" +
                "                            \"wholepiece\":\"2\",\n" +
                "                            \"materialName\":\"镀锌圆管2222\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "            \"stockName\":\"北院成品自销库方管\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"material\":[\n" +
                "        {\n" +
                "            \"qty\":\"5.8745\",\n" +
                "            \"wholepiece\":\"2\",\n" +
                "            \"materialName\":\"镀锌方管\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"qty\":\"3.335\",\n" +
                "            \"wholepiece\":\"3\",\n" +
                "            \"materialName\":\"镀锌方管333\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"remark\":\"无\"\n" +
                "}";

//        s=s1;
//        s=s2;
//        s = s3;
//        s=s4;
        s = s5;
//        s = s6;
        List<String> list = new ArrayList<>();
        list.add(s5);
        list.add(s55);
        list.add(s6);

        return list.get(i);
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