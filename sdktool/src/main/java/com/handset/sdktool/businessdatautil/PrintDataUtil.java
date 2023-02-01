package com.handset.sdktool.businessdatautil;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.sdktool.Config;
import com.handset.sdktool.bean.PrintPaperBean;
import com.handset.sdktool.dto.PrintPaperRelationshipDTO;
import com.handset.sdktool.dto.PaperDTO;
import com.handset.sdktool.dto.PrinterDTO;
import com.handset.sdktool.net.NetUtil;
import com.handset.sdktool.net.OnResponse;
import com.handset.sdktool.net.base.BaseBean;
import com.handset.sdktool.net.base.Bean;
import com.handset.sdktool.net.base.NetConfig;
import com.handset.sdktool.util.DebugLog;
import com.handset.sdktool.util.GetJsonDataUtil;
import com.handset.sdktool.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * @ClassName: PrintDataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:初始化打印机数据 纸张数据和 打印机纸张关系
 */
public class PrintDataUtil {
    List<PrinterDTO> printerDTOList = new ArrayList<>();
    List<PaperDTO> paperDTOList = new ArrayList<>();
    List<PrintPaperRelationshipDTO> printPaperRelationshipDTOList = new ArrayList<>();

    private PrintDataUtil() {
    }

    private static PrintDataUtil dataUtil = new PrintDataUtil();

    public static PrintDataUtil getInstance() {
        return dataUtil;
    }

    /**
     * 设置打印機数据（注意只有第一次或点击同步数据时会保存）
     */
    public void initPrintData(Context context) {


        String printpaper = new GetJsonDataUtil().getJson(context, "printpaper.json");
        DebugLog.e("printpaper===" + printpaper);


        List<PrintPaperBean> businessElementBeanList = new Gson().fromJson(printpaper,
                new TypeToken<List<PrintPaperBean>>() {
                }.getType());
        DebugLog.e("22printpaper===" + businessElementBeanList.toString());

        printerDTOList.clear();
        paperDTOList.clear();
        printPaperRelationshipDTOList.clear();

        for (PrintPaperBean printPaperBean : businessElementBeanList) {
            PrintPaperRelationshipDTO printPaperRelationshipDTO = new PrintPaperRelationshipDTO();
            if (printPaperBean.getPrinterDTO().getPrinterName() == null || printPaperBean.getPrinterDTO().getId() == null
                    || printPaperBean.getPrinterDTO().getPrinterType() == null || printPaperBean.getPrinterDTO().getConnectionType() == null) {
                Toast.makeText(context, "打印机数据同步失败，请为印机设置ID、名称、打印方式、连接方式", Toast.LENGTH_SHORT).show();
                return;
            }
            printerDTOList.add(printPaperBean.getPrinterDTO());
            printPaperRelationshipDTO.setPrinterId(printPaperBean.getPrinterDTO().getId());

            List<String> paperIdList = new ArrayList<>();
            for (PaperDTO paperDTO : printPaperBean.paperDTOList) {
                if (paperDTO.getId() == null || paperDTO.getPaperType() == null) {
                    Toast.makeText(context, "打印纸数据同步失败，请为纸设置名称、识别ID和类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                paperIdList.add(paperDTO.getId());
                paperDTOList.add(paperDTO);
            }
            printPaperRelationshipDTO.setPaperIdList(paperIdList);
            printPaperRelationshipDTOList.add(printPaperRelationshipDTO);
        }

//        if (SharedPreferenceUtil.get(context, Config.RELATIONSHIPPP, "").toString().length() == 0) {
            String businessdata = new Gson().toJson(businessElementBeanList);
            SharedPreferenceUtil.put(context, Config.RELATIONSHIPPP, businessdata);

            addPrinter();
//        }


    }

    /**
     * 添加打印機
     */
    public void addPrinter() {


        Gson gson = new Gson();
        String strEntity = gson.toJson(printerDTOList);
        DebugLog.e("jsonss===" + strEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);

        NetUtil.getInstance().api().addPrinter(NetConfig.IP,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean<Bean>>() {
                    @Override
                    public void onNext(BaseBean<Bean> listBaseBean) {
                        if (listBaseBean.isCodeSuccess()) {
                            DebugLog.e("onNext===111");
                            addPaper();
                        } else {
//                            showToast(listBaseBean.getResultMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugLog.e("onError===" + e.getMessage() + "===" + e.getLocalizedMessage());
//                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 添加紙張
     */
    public void addPaper() {

        Gson gson = new Gson();
        String strEntity = gson.toJson(paperDTOList);
        DebugLog.e("jsonsssss===" + strEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);

        NetUtil.getInstance().api().addPaper(NetConfig.IP,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean<Bean>>() {
                    @Override
                    public void onNext(BaseBean<Bean> listBaseBean) {
                        if (listBaseBean.isCodeSuccess()) {
//                            showToast(listBaseBean.getResultMessage());
                            addPrinterPaperRelationship();
                        } else {
//                            showToast(listBaseBean.getResultMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugLog.e("onError===" + e.getMessage() + "===" + e.getLocalizedMessage());
//                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 2.10.批量添加打印机纸张关系
     */
    private void addPrinterPaperRelationship() {

        Gson gson = new Gson();
        String strEntity = gson.toJson(printPaperRelationshipDTOList);
        DebugLog.e("1json===" + strEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);

        NetUtil.getInstance().api().addPrinterPaperRelInBatches(NetConfig.IP,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean<Bean>>() {
                    @Override
                    public void onNext(BaseBean<Bean> listBaseBean) {
                        if (listBaseBean.isCodeSuccess()) {
                            DebugLog.e("json===cc66c");
                        } else {
//                            Toast.makeText(context, listBaseBean.getResultMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugLog.e("onError===" + e.getMessage() + "===" + e.getLocalizedMessage());
//                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
