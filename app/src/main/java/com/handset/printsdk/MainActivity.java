package com.handset.printsdk;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.handset.printsdk.base.BaseActivity;
import com.handset.sdktool.bean.BusinessElementBean;
import com.handset.sdktool.businessutil.BusinessUtil;
import com.handset.sdktool.businessutil.PrintUtil;
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

    @Override
    public int getLayoutId() {
        return com.handset.sdktool.R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //初始化业务和元素数据
        List<BusinessElementBean> businessElementBeanList = new ArrayList<>();


        for (int i = 0; i < 3; i++) {
            BusinessDTO addProfessionalWorkDTO = new BusinessDTO();
            addProfessionalWorkDTO.setServicetype("SDK测试业务" + i);
            addProfessionalWorkDTO.setServicetypeNo("1" + i);
            List<ElementDTO> elementDTOList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                ElementDTO elementDTO4 = new ElementDTO();
                elementDTO4.setId(String.valueOf(j));
                elementDTO4.setElementDesc("采购物料名称");
                elementDTO4.setElementName("名称" + j + i);
                elementDTO4.setElementType("1");
                elementDTO4.setElementCode(String.valueOf(System.currentTimeMillis()));
                elementDTOList.add(elementDTO4);
            }
            BusinessElementBean businessElementBean = new BusinessElementBean(addProfessionalWorkDTO, elementDTOList);
            businessElementBeanList.add(businessElementBean);
        }

        BusinessUtil.getInstance().initBusinessData(this, businessElementBeanList);
        PrintUtil.getInstance().initBusinessData(this);

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
//        goActivity(SetBusinessActivity.class);
//        List<PrinterDTO> list = DataUtil.getInstance().getPrits(this);
    }

    @Override
    public void initEvent() {

    }
}