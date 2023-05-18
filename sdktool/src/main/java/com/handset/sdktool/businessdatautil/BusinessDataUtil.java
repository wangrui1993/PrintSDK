package com.handset.sdktool.businessdatautil;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handset.sdktool.Config;
import com.handset.sdktool.bean.BusinessElementBean;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.BusinessElementRelationshipDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.dto.PaperDTO;
import com.handset.sdktool.dto.PrinterDTO;
import com.handset.sdktool.dto.PrinterPaperRelationshipDTO;
import com.handset.sdktool.net.NetUtil;
import com.handset.sdktool.net.OnResponse;
import com.handset.sdktool.net.base.BaseBean;
import com.handset.sdktool.net.base.Bean;
import com.handset.sdktool.net.base.NetConfig;
import com.handset.sdktool.util.DebugLog;
import com.handset.sdktool.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:作用描述
 */
public class BusinessDataUtil {
    List<BusinessDTO> businessDTOList = new ArrayList<>();
    List<ElementDTO> elementDTOList = new ArrayList<>();
    List<BusinessElementRelationshipDTO> businessElementRelationshipDTOList = new ArrayList<>();

    private BusinessDataUtil() {

    }

    private static BusinessDataUtil dataUtil = new BusinessDataUtil();

    public static BusinessDataUtil getInstance() {
        return dataUtil;
    }

    /**
     * 设置业务数据（注意只有第一次或点击同步数据时会保存）
     */
    public void initBusinessData(Context context, List<BusinessElementBean> businessElementBeanList) {

        businessDTOList.clear();
        elementDTOList.clear();
        businessElementRelationshipDTOList.clear();

        for (BusinessElementBean businessElementBean : businessElementBeanList) {
            BusinessElementRelationshipDTO businessElementRelationshipDTO = new BusinessElementRelationshipDTO();
            if (businessElementBean.getBusinessDTO().getServicetype() == null || businessElementBean.getBusinessDTO().getServicetypeNo() == null) {
                Toast.makeText(context, "打印业务数据同步失败，请为业务设置名称和识别ID", Toast.LENGTH_SHORT).show();
                return;
            }
            businessDTOList.add(businessElementBean.getBusinessDTO());
            businessElementRelationshipDTO.setServiceId(businessElementBean.getBusinessDTO().getServicetypeNo());

            List<String> elementIdList = new ArrayList<>();
            for (ElementDTO elementDTO : businessElementBean.elementDTOList) {
                if (elementDTO.getElementName() == null || elementDTO.getElementCode() == null || elementDTO.getElementType() == null
                        || elementDTO.getId() == null) {
                    Toast.makeText(context, "打印元素数据同步失败，请为元素设置名称、识别ID、类型和Code", Toast.LENGTH_SHORT).show();
                    return;
                }
                elementIdList.add(elementDTO.getId());
                elementDTOList.add(elementDTO);
            }
            businessElementRelationshipDTO.setElementIdList(elementIdList);
            businessElementRelationshipDTOList.add(businessElementRelationshipDTO);
        }

//        if (SharedPreferenceUtil.get(context, Config.RELATIONSHIPMAPDATA, "").toString().length() == 0) {
        String businessdata = new Gson().toJson(businessElementBeanList);
        Log.e("dddd===", businessdata + "");
        SharedPreferenceUtil.put(context, Config.RELATIONSHIPMAPDATA, businessdata);

        addProfessionalWork(context, businessDTOList);
//        }


    }

    /**
     * 添加業務
     */
    private void addProfessionalWork(Context context, List<BusinessDTO> businessDTOList) {
        Gson gson = new Gson();
        String strEntity = gson.toJson(businessDTOList);
        DebugLog.e("json===" + strEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        NetUtil.getInstance().api().addServiceInBatches(NetConfig.IP, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean<Bean>>() {
                    @Override
                    public void onNext(BaseBean<Bean> listBaseBean) {
                        if (listBaseBean.isCodeSuccess()) {
                            DebugLog.e("json===cc");
                            addElementInBatches(context, elementDTOList);
                        } else {
                            Toast.makeText(context, listBaseBean.getResultMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugLog.e("onError===" + e.getMessage() + "===" + e.getLocalizedMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 2.4.批量添加元素
     */
    private void addElementInBatches(Context context, List<ElementDTO> elementDTOList) {

        Gson gson = new Gson();
        String strEntity = gson.toJson(elementDTOList);
        DebugLog.e("1json===" + strEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);

        NetUtil.getInstance().api().addElementInBatches(NetConfig.IP, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean<Bean>>() {
                    @Override
                    public void onNext(BaseBean<Bean> listBaseBean) {
                        if (listBaseBean.isCodeSuccess()) {
                            DebugLog.e("json===vv");
                            addBusinessElementRelInBatches(context, businessElementRelationshipDTOList);
                        } else {
                            Toast.makeText(context, listBaseBean.getResultMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugLog.e("onError===" + e.getMessage() + "===" + e.getLocalizedMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 2.9.批量添加业务元素关系
     */
    private void addBusinessElementRelInBatches(Context context, List<BusinessElementRelationshipDTO> businessElementRelationshipDTOList) {
        Gson gson = new Gson();
        String strEntity = gson.toJson(businessElementRelationshipDTOList);
        DebugLog.e("1json===" + strEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        NetUtil.getInstance().api().addBusinessElementRelInBatches(NetConfig.IP, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean<Bean>>() {
                    @Override
                    public void onNext(BaseBean<Bean> listBaseBean) {
                        if (listBaseBean.isCodeSuccess()) {
                            Toast.makeText(context, "添加业务元素关系成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, listBaseBean.getResultMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugLog.e("onError===" + e.getMessage() + "===" + e.getLocalizedMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 添加打印機
     */
    private void addPrinter(Context context) {
        PrinterDTO printerDTO = new PrinterDTO();
        printerDTO.setId("1");
        printerDTO.setPrinterName("打印机1");
        printerDTO.setPrinterType("激光");
        printerDTO.setConnectionType("蓝牙");
        printerDTO.setSupportWidth(80.8);


        PrinterDTO printerDTO2 = new PrinterDTO();
        printerDTO2.setId("2");
        printerDTO2.setPrinterName("打印机2");
        printerDTO2.setPrinterType("熱敏");
        printerDTO2.setConnectionType("蓝牙");
        printerDTO2.setSupportWidth(60.8);


        List<PrinterDTO> list = new ArrayList<>();
        list.add(printerDTO);
        list.add(printerDTO2);

        Gson gson = new Gson();
        String strEntity = gson.toJson(list);
        DebugLog.e("json===" + strEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);

        NetUtil.getInstance().api().addPrinter(NetConfig.IP, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean<Bean>>() {
                    @Override
                    public void onNext(BaseBean<Bean> listBaseBean) {
                        if (listBaseBean.isCodeSuccess()) {
                            DebugLog.e("onNext===111");
                            addPaper(context);
                        } else {
                            Toast.makeText(context, listBaseBean.getResultMessage(), Toast.LENGTH_SHORT).show();
//                            showToast(listBaseBean.getResultMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugLog.e("onError===" + e.getMessage() + "===" + e.getLocalizedMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 添加紙張
     */
    private void addPaper(Context context) {
        PaperDTO paperDTO = new PaperDTO();
        paperDTO.setPaperType("A4");
        paperDTO.setPaperHeight(21.0);
        paperDTO.setPaperWidth(29.7);
        paperDTO.setId("1");

        PaperDTO paperDTO2 = new PaperDTO();
        paperDTO2.setPaperType("A1");
        paperDTO2.setPaperHeight(25.0);
        paperDTO2.setPaperWidth(21.7);
        paperDTO2.setId("2");

        PaperDTO paperDTO3 = new PaperDTO();
        paperDTO3.setPaperType("A2");
        paperDTO3.setPaperHeight(30.0);
        paperDTO3.setPaperWidth(20.7);
        paperDTO3.setId("3");

        List<PaperDTO> list = new ArrayList<>();
        list.add(paperDTO);
        list.add(paperDTO2);
        list.add(paperDTO3);
        paperDTO.setId("1");
        Gson gson = new Gson();
        String strEntity = gson.toJson(list);
        DebugLog.e("json===" + strEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);

        NetUtil.getInstance().api().addPaper(NetConfig.IP, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean<Bean>>() {
                    @Override
                    public void onNext(BaseBean<Bean> listBaseBean) {
                        if (listBaseBean.isCodeSuccess()) {

                            addPrinterPaperRelationship(context);
                        } else {
                            Toast.makeText(context, listBaseBean.getResultMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugLog.e("onError===" + e.getMessage() + "===" + e.getLocalizedMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 2.10.批量添加打印机纸张关系
     */
    private void addPrinterPaperRelationship(Context context) {
        List<String> listPaper1 = new ArrayList<>();
        listPaper1.add("1");
        listPaper1.add("2");
        List<String> listPaper2 = new ArrayList<>();
        listPaper2.add("3");


        List<PrinterPaperRelationshipDTO> list = new ArrayList<>();
        PrinterPaperRelationshipDTO printerPaperRelationshipDTO = new PrinterPaperRelationshipDTO();
        printerPaperRelationshipDTO.setPrinterId("1");
        printerPaperRelationshipDTO.setPaperIdList(listPaper1);

        PrinterPaperRelationshipDTO printerPaperRelationshipDTO2 = new PrinterPaperRelationshipDTO();
        printerPaperRelationshipDTO2.setPrinterId("2");
        printerPaperRelationshipDTO2.setPaperIdList(listPaper2);

        list.add(printerPaperRelationshipDTO);
        list.add(printerPaperRelationshipDTO2);

        Gson gson = new Gson();
        String strEntity = gson.toJson(list);
        DebugLog.e("1json===" + strEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);

        NetUtil.getInstance().api().addPrinterPaperRelInBatches(NetConfig.IP, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean<Bean>>() {
                    @Override
                    public void onNext(BaseBean<Bean> listBaseBean) {
                        if (listBaseBean.isCodeSuccess()) {
                            DebugLog.e("json===ccc");
                        } else {
                            Toast.makeText(context, listBaseBean.getResultMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugLog.e("onError===" + e.getMessage() + "===" + e.getLocalizedMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
