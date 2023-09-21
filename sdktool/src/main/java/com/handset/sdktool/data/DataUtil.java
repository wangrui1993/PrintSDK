package com.handset.sdktool.data;

import com.google.gson.Gson;
import com.handset.sdktool.Config;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.CompanyAssociationDTO;
import com.handset.sdktool.dto.CompanyDTO;
import com.handset.sdktool.dto.DelCompanyDTO;
import com.handset.sdktool.dto.DeleteDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.dto.PaperDTO;
import com.handset.sdktool.dto.PrinterDTO;
import com.handset.sdktool.listener.AddCompanyListener;
import com.handset.sdktool.listener.CompanyAsListener;
import com.handset.sdktool.listener.DeleteCompanyListener;
import com.handset.sdktool.listener.GetAllBusinessListener;
import com.handset.sdktool.listener.GetAllCompanyListener;
import com.handset.sdktool.listener.GetAllPrintListener;
import com.handset.sdktool.listener.GetAllTemplateListener;
import com.handset.sdktool.listener.GetBusinessServiceByCompanyIdListener;
import com.handset.sdktool.listener.GetElementByBusiness;
import com.handset.sdktool.listener.GetPaperByPrint;
import com.handset.sdktool.listener.GetTemplateByBusinessCode;
import com.handset.sdktool.net.NetUtil;
import com.handset.sdktool.net.OnResponse;
import com.handset.sdktool.net.base.BaseBean;
import com.handset.sdktool.net.base.ModleListBean;
import com.handset.sdktool.net.base.NetConfig;
import com.handset.sdktool.util.DebugLog;
import com.handset.sdktool.util.SharedPreferenceUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

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
        NetUtil.getInstance().api().getElementByBusiness(NetConfig.IP, code)
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
        NetUtil.getInstance().api().getPaperByPrinter(NetConfig.IP, printerId)
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
        NetUtil.getInstance().api().getTemplateByBusinessCode(NetConfig.IP, code)
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

    /**
     * 保存公司信息
     */
    public void saveCompanyInfoDomain(CompanyDTO companyDTO, AddCompanyListener addCompanyListener) {
        Gson gson = new Gson();
        String strEntity = gson.toJson(companyDTO);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        NetUtil.getInstance().api().saveCompanyInfoDomain(NetConfig.IP, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean>() {
                    @Override
                    public void onNext(BaseBean listBaseBean) {
                        addCompanyListener.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        addCompanyListener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 删除公司信息
     */
    public void deleteCompanyInfoDomain(DelCompanyDTO ids, DeleteCompanyListener deleteCompanyListener) {
        Gson gson = new Gson();
        String strEntity = gson.toJson(ids);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        NetUtil.getInstance().api().delCompanyInfoDomain(NetConfig.IP, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean>() {
                    @Override
                    public void onNext(BaseBean listBaseBean) {
                        deleteCompanyListener.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        deleteCompanyListener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 获取公司信息-ip域名（所有）
     */
    public void getCompanyInfoDomain(GetAllCompanyListener getAllCompanyListener) {
        NetUtil.getInstance().api().getCompanyInfoDomain(NetConfig.IP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<List<CompanyDTO>>() {
                    @Override
                    public void onNext(List<CompanyDTO> listBaseBean) {
                        getAllCompanyListener.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAllCompanyListener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 公司关联业务
     */
    public void updateCompanyTemplRel(CompanyAssociationDTO companyAssociationDTO, CompanyAsListener companyAsListener) {

        Gson gson = new Gson();
        String strEntity = gson.toJson(companyAssociationDTO);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        NetUtil.getInstance().api().updateCompanyTemplRel(NetConfig.IP, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean>() {
                    @Override
                    public void onNext(BaseBean listBaseBean) {
                        companyAsListener.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        companyAsListener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 根据公司id获取对应的所有的业务
     */
    public void getBusinessServiceByCompanyId(String companyId, GetBusinessServiceByCompanyIdListener getBusinessServiceByCompanyIdListener) {

        NetUtil.getInstance().api().getBusinessServiceByCompanyId(NetConfig.IP, companyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<List<BusinessDTO>>() {
                    @Override
                    public void onNext(List<BusinessDTO> listBaseBean) {
                        getBusinessServiceByCompanyIdListener.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getBusinessServiceByCompanyIdListener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 根据公司id获取对应的所有的业务
     */
    public void getBusinessServiceByCompanyId(GetBusinessServiceByCompanyIdListener getBusinessServiceByCompanyIdListener) {
        NetUtil.getInstance().api().getBusinessServiceByCompanyId(NetConfig.IP, NetConfig.COMPANYID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<List<BusinessDTO>>() {
                    @Override
                    public void onNext(List<BusinessDTO> listBaseBean) {
                        getBusinessServiceByCompanyIdListener.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getBusinessServiceByCompanyIdListener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 删除业务数据
     */
    public void delServiceInBatches(List<BusinessDTO> businessDTOList, DeleteCompanyListener deleteCompanyListener) {
        Gson gson = new Gson();
        String strEntity = gson.toJson(businessDTOList);
        DebugLog.e("json===" + strEntity);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        NetUtil.getInstance().api().delServiceInBatches(NetConfig.IP, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<BaseBean>() {
                    @Override
                    public void onNext(BaseBean listBaseBean) {
                        deleteCompanyListener.onSuccess(listBaseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        deleteCompanyListener.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
