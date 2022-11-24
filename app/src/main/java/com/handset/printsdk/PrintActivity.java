package com.handset.printsdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.printsdk.base.BaseActivity;
import com.handset.sdktool.bean.PrintPaperBean;
import com.handset.sdktool.data.BusinessData;
import com.handset.sdktool.data.ControllerUtil;
import com.handset.sdktool.modle.ModleData;
import com.handset.sdktool.ui.ConnectBlueToothActivity;
import com.handset.sdktool.util.DebugLog;
import com.handset.sdktool.util.GetJsonDataUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class PrintActivity extends BaseActivity {
    @BindView(R.id.tv_search_print)
    TextView tv_search_print;
    @BindView(R.id.tv_connect_print)
    TextView tv_connect_print;
    @BindView(R.id.tv_print_test)
    TextView tv_print_test;

    @Override
    public int getLayoutId() {
        return R.layout.activity_print;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_search_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControllerUtil.getInstance().openPrintPage(PrintActivity.this, "9010", jishuju());
            }
        });
        tv_connect_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControllerUtil.getInstance().openPrintPage(PrintActivity.this, "9010", jishuju());

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

//        list.add(mapList1);
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
//        list2.add(mapList7);
//        list2.add(mapList7);
//        list2.add(mapList7);
        map.put("213", list);
        map.put("214", list2);


        return map;
    }

    @Override
    public void initEvent() {

    }
}