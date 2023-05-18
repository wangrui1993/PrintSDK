package com.handset.sdktool.data;

import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.dto.PaperDTO;
import com.handset.sdktool.dto.PrinterDTO;
import com.handset.sdktool.listener.GetAllBusinessListener;
import com.handset.sdktool.listener.GetAllPrintListener;
import com.handset.sdktool.listener.GetAllTemplateListener;
import com.handset.sdktool.listener.GetElementByBusiness;
import com.handset.sdktool.listener.GetPaperByPrint;
import com.handset.sdktool.listener.GetTemplateByBusinessCode;
import com.handset.sdktool.net.NetUtil;
import com.handset.sdktool.net.OnResponse;
import com.handset.sdktool.net.base.ModleListBean;
import com.handset.sdktool.net.base.NetConfig;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:获取各类数据
 */
public class DataUtil {
    private DataUtil() {

    }

    private static DataUtil dataUtil = new DataUtil();

    public static DataUtil getInstance() {
        return dataUtil;
    }

    /**
     * 获取业务
     */
    public void getProfessionalWork(GetAllBusinessListener getAllBusinessListener) {
        NetUtil.getInstance().api().getAllService(NetConfig.IP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<List<BusinessDTO>>() {
                    @Override
                    public void onNext(List<BusinessDTO> listBaseBean) {
                        getAllBusinessListener.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAllBusinessListener.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取业务关联的元素
     *
     * @param code
     */
    public void getElementByBusiness(String code, GetElementByBusiness getElementByBusiness) {
        NetUtil.getInstance().api().getElementByBusiness(NetConfig.IP,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<List<ElementDTO>>() {
                    @Override
                    public void onNext(List<ElementDTO> listBaseBean) {
                        getElementByBusiness.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getElementByBusiness.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 2.13.获取所有模板
     */
    public void getAllTemplate(GetAllTemplateListener getAllTemplateListener) {
        NetUtil.getInstance().api().getAllTemplate(NetConfig.IP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<List<ModleListBean>>() {
                    @Override
                    public void onNext(List<ModleListBean> listBaseBean) {
                        getAllTemplateListener.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAllTemplateListener.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取所有打印机
     */
    public void getPrits(GetAllPrintListener getAllPrintListener) {
        NetUtil.getInstance().api().getAllPrinter(NetConfig.IP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<List<PrinterDTO>>() {
                    @Override
                    public void onNext(List<PrinterDTO> listBaseBean) {
                        getAllPrintListener.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAllPrintListener.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 根据打印機获取纸张
     */
    public void getPaper(String printerId, GetPaperByPrint getPaperByPrint) {
        NetUtil.getInstance().api().getPaperByPrinter(NetConfig.IP,printerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<List<PaperDTO>>() {
                    @Override
                    public void onNext(List<PaperDTO> listBaseBean) {
                        getPaperByPrint.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getPaperByPrint.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 2.14.根据业务获取启用模板
     *
     * @param code
     */
    public void getTemplateByBusinessCode(String code, GetTemplateByBusinessCode getTemplateByBusinessCode) {
        NetUtil.getInstance().api().getTemplateByBusinessCode(NetConfig.IP,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<ModleDTO>() {
                    @Override
                    public void onNext(ModleDTO listBaseBean) {
                        getTemplateByBusinessCode.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getTemplateByBusinessCode.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
