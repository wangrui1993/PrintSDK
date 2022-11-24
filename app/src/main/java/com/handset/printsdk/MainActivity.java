package com.handset.printsdk;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.handset.printsdk.base.BaseActivity;
import com.handset.sdktool.bean.BusinessElementBean;
import com.handset.sdktool.businessdatautil.BusinessDataUtil;
import com.handset.sdktool.businessdatautil.PrintDataUtil;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.ui.SynchronizeBusinessActivity;
import com.handset.sdktool.ui.SynchronizePrintActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_business_data)
    TextView tv_business_data;
    @BindView(R.id.tv_print)
    TextView tv_print;
    @BindView(R.id.tv_print_test)
    TextView tv_print_test;

    @Override
    public int getLayoutId() {
        return com.handset.sdktool.R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //初始化业务和元素数据
        List<BusinessElementBean> businessElementBeanList = new ArrayList<>();


        for (int i = 10; i < 13; i++) {
            BusinessDTO addProfessionalWorkDTO = new BusinessDTO();
            addProfessionalWorkDTO.setServicetype("SDK测试业务" + i);
            addProfessionalWorkDTO.setServicetypeNo("90" + i);
            List<ElementDTO> elementDTOList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                ElementDTO elementDTO4 = new ElementDTO();
                elementDTO4.setId(String.valueOf(j));
                elementDTO4.setElementDesc("采购物料名称");
                elementDTO4.setElementName("名称" + j + i);
                elementDTO4.setElementType("1");
                elementDTO4.setElementCode(100+i+j+"");

                ElementDTO elementDTO5 = new ElementDTO();
                elementDTO5.setId(String.valueOf(j+10));
                elementDTO5.setElementDesc("表");
                elementDTO5.setElementName("列表" + j + i);
                elementDTO5.setElementType("2");
                elementDTO5.setElementCode(200+i+j+"");
                elementDTOList.add(elementDTO5);
                elementDTOList.add(elementDTO4);
            }
            BusinessElementBean businessElementBean = new BusinessElementBean(addProfessionalWorkDTO, elementDTOList);
            businessElementBeanList.add(businessElementBean);
        }


        BusinessDataUtil.getInstance().initBusinessData(this, businessElementBeanList);
        PrintDataUtil.getInstance().initPrintData(this);

        tv_business_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(SynchronizeBusinessActivity.class);
            }
        });
        tv_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(SynchronizePrintActivity.class);
            }
        });
        tv_print_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(PrintActivity.class);
            }
        });
    }

    @Override
    public void initEvent() {

    }
}