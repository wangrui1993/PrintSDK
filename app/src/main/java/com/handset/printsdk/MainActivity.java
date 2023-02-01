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
import com.handset.sdktool.net.base.NetConfig;
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
        NetConfig.init("192.168.31.82:8090");

        //初始化业务和元素数据
        List<BusinessElementBean> businessElementBeanList = new ArrayList<>();
        BusinessDTO addProfessionalWorkDTO = new BusinessDTO();
        addProfessionalWorkDTO.setServicetype("装车单");
        addProfessionalWorkDTO.setServicetypeNo("jz05");

        List<ElementDTO> elementDTOList = new ArrayList<>();
        ElementDTO elementDTO1 = new ElementDTO();
        elementDTO1.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid01");
        elementDTO1.setElementDesc("如：镀锌圆管");
        elementDTO1.setElementName("物料名称");
        elementDTO1.setElementType("1");
        elementDTO1.setElementCode("materialName");

        ElementDTO elementDTO2 = new ElementDTO();
        elementDTO2.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid02");
        elementDTO2.setElementDesc("如：123");
        elementDTO2.setElementName("件数");
        elementDTO2.setElementType("1");
        elementDTO2.setElementCode("wholepiece");

        ElementDTO elementDTO3 = new ElementDTO();
        elementDTO3.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid03");
        elementDTO3.setElementDesc("如：0.4978");
        elementDTO3.setElementName("吨数");
        elementDTO3.setElementType("1");
        elementDTO3.setElementCode("qty");

        ElementDTO elementDTO4 = new ElementDTO();
        elementDTO4.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid04");
        elementDTO4.setElementDesc("如：辽B22221");
        elementDTO4.setElementName("车牌");
        elementDTO4.setElementType("1");
        elementDTO4.setElementCode("carno");

        ElementDTO elementDTO5 = new ElementDTO();
        elementDTO5.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid05");
        elementDTO5.setElementDesc("如：XX有限公司");
        elementDTO5.setElementName("客户");
        elementDTO5.setElementType("1");
        elementDTO5.setElementCode("customerName");

        ElementDTO elementDTO6 = new ElementDTO();
        elementDTO6.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid06");
        elementDTO6.setElementDesc("如：销售一部");
        elementDTO6.setElementName("销售部门");
        elementDTO6.setElementType("1");
        elementDTO6.setElementCode("deptName");

        ElementDTO elementDTO7 = new ElementDTO();
        elementDTO7.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid08");
        elementDTO7.setElementDesc("如：北院库");
        elementDTO7.setElementName("库房");
        elementDTO7.setElementType("1");
        elementDTO7.setElementCode("stockName");

        ElementDTO elementDTO9 = new ElementDTO();
        elementDTO9.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid09");
        elementDTO9.setElementDesc("如：40*30*20");
        elementDTO9.setElementName("规格");
        elementDTO9.setElementType("1");
        elementDTO9.setElementCode("specification");

//        ElementDTO elementDTO10 = new ElementDTO();
//        elementDTO10.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid010");
//        elementDTO10.setElementDesc("如：销售一部");
//        elementDTO10.setElementName("销售部门");
//        elementDTO10.setElementType("1");
//        elementDTO10.setElementCode("deptName");

        //
        ElementDTO elementDTO11 = new ElementDTO();
        elementDTO11.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid011");
        elementDTO11.setElementDesc("如：XXX");
        elementDTO11.setElementName("备注");
        elementDTO11.setElementType("1");
        elementDTO11.setElementCode("remark");

        ElementDTO elementDTO12 = new ElementDTO();
        elementDTO12.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid012");
        elementDTO12.setElementDesc("如：自销");
        elementDTO12.setElementName("合同号");
        elementDTO12.setElementType("1");
        elementDTO12.setElementCode("batchno");

        ElementDTO elementDTO13 = new ElementDTO();
        elementDTO13.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid013");
        elementDTO13.setElementDesc("如：1");
        elementDTO13.setElementName("小计（件）");
        elementDTO13.setElementType("1");
        elementDTO13.setElementCode("subtotal_piece");

        ElementDTO elementDTO18 = new ElementDTO();
        elementDTO18.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid018");
        elementDTO18.setElementDesc("如：1");
        elementDTO18.setElementName("小计（吨）");
        elementDTO18.setElementType("1");
        elementDTO18.setElementCode("subtotal_ton");

        ElementDTO elementDTO14 = new ElementDTO();
        elementDTO14.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid014");
        elementDTO14.setElementDesc("如：是/否");
        elementDTO14.setElementName("确认外观屋磕碰");
        elementDTO14.setElementType("1");
        elementDTO14.setElementCode("bump");

        ElementDTO elementDTO15 = new ElementDTO();
        elementDTO15.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid015");
        elementDTO15.setElementDesc("如：张三");
        elementDTO15.setElementName("制单人");
        elementDTO15.setElementType("1");
        elementDTO15.setElementCode("preparedBy");

        ElementDTO elementDTO16 = new ElementDTO();
        elementDTO16.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid016");
        elementDTO16.setElementDesc("如：2022/10/31 10:24:38");
        elementDTO16.setElementName("时间");
        elementDTO16.setElementType("1");
        elementDTO16.setElementCode("time");

        ElementDTO elementDTO17 = new ElementDTO();
        elementDTO17.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid017");
        elementDTO17.setElementDesc("如：xxx");
        elementDTO17.setElementName("二维码");
        elementDTO17.setElementType("1");
        elementDTO17.setElementCode("qrCode");

        ElementDTO elementDTO19 = new ElementDTO();
        elementDTO19.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid019");
        elementDTO19.setElementDesc("如：1");
        elementDTO19.setElementName("合计（件）");
        elementDTO19.setElementType("1");
        elementDTO19.setElementCode("total_piece");

        ElementDTO elementDTO20 = new ElementDTO();
        elementDTO20.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid020");
        elementDTO20.setElementDesc("如：1");
        elementDTO20.setElementName("合计（吨）");
        elementDTO20.setElementType("1");
        elementDTO20.setElementCode("total_ton");

        ElementDTO elementDTO21 = new ElementDTO();
        elementDTO21.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid021");
        elementDTO21.setElementDesc("如：xxx");
        elementDTO21.setElementName("条码");
        elementDTO21.setElementType("1");
        elementDTO21.setElementCode("brCode");

        ElementDTO elementDTO8 = new ElementDTO();
        elementDTO8.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid07");
        elementDTO8.setElementDesc("物料表（3级）");
        elementDTO8.setElementName("物料表");
        elementDTO8.setElementType("2");
        elementDTO8.setElementCode("material");

        ElementDTO elementDTO22 = new ElementDTO();
        elementDTO22.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid022");
        elementDTO22.setElementDesc("合同表（2级）");
        elementDTO22.setElementName("合同表");
        elementDTO22.setElementType("2");
        elementDTO22.setElementCode("contractForm");

        ElementDTO elementDTO23 = new ElementDTO();
        elementDTO23.setId(addProfessionalWorkDTO.getServicetypeNo() + "-eid023");
        elementDTO23.setElementDesc("仓库表（1级）");
        elementDTO23.setElementName("仓库");
        elementDTO23.setElementType("2");
        elementDTO23.setElementCode("warehouse");


        elementDTOList.add(elementDTO1);
        elementDTOList.add(elementDTO2);
        elementDTOList.add(elementDTO3);
        elementDTOList.add(elementDTO4);
        elementDTOList.add(elementDTO5);
        elementDTOList.add(elementDTO6);
        elementDTOList.add(elementDTO7);
        elementDTOList.add(elementDTO9);
        elementDTOList.add(elementDTO8);

//        elementDTOList.add(elementDTO10);
        elementDTOList.add(elementDTO11);
        elementDTOList.add(elementDTO12);
        elementDTOList.add(elementDTO13);
        elementDTOList.add(elementDTO14);
        elementDTOList.add(elementDTO15);
        elementDTOList.add(elementDTO16);
        elementDTOList.add(elementDTO17);

        elementDTOList.add(elementDTO18);
        elementDTOList.add(elementDTO19);
        elementDTOList.add(elementDTO20);
        elementDTOList.add(elementDTO21);

        elementDTOList.add(elementDTO22);
        elementDTOList.add(elementDTO23);

        BusinessElementBean businessElementBean = new BusinessElementBean(addProfessionalWorkDTO, elementDTOList);
        businessElementBeanList.add(businessElementBean);

//        for (int i = 10; i < 13; i++) {
//            BusinessDTO addProfessionalWorkDTO = new BusinessDTO();
//            addProfessionalWorkDTO.setServicetype("SDK测试业务" + i);
//            addProfessionalWorkDTO.setServicetypeNo("90" + i);
//            List<ElementDTO> elementDTOList = new ArrayList<>();
//            for (int j = 0; j < 5; j++) {
//                ElementDTO elementDTO4 = new ElementDTO();
//                elementDTO4.setId(String.valueOf(j));
//                elementDTO4.setElementDesc("采购物料名称");
//                elementDTO4.setElementName("名称" + j + i);
//                elementDTO4.setElementType("1");
//                elementDTO4.setElementCode(100+i+j+"");
//
//                ElementDTO elementDTO5 = new ElementDTO();
//                elementDTO5.setId(String.valueOf(j+10));
//                elementDTO5.setElementDesc("表");
//                elementDTO5.setElementName("列表" + j + i);
//                elementDTO5.setElementType("2");
//                elementDTO5.setElementCode(200+i+j+"");
//                elementDTOList.add(elementDTO5);
//                elementDTOList.add(elementDTO4);
//            }
//            BusinessElementBean businessElementBean = new BusinessElementBean(addProfessionalWorkDTO, elementDTOList);
//            businessElementBeanList.add(businessElementBean);
//        }


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