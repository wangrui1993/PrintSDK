package com.handset.sdktool.net;


import com.handset.sdktool.net.base.BaseBean;
import com.handset.sdktool.net.base.Bean;
import com.handset.sdktool.net.base.NetConfig;
import com.handset.sdktool.net.base.ModleListBean;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.dto.PaperDTO;
import com.handset.sdktool.dto.PrinterDTO;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    /**
     * 批量添加业务
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("http://{domain}/printtemplate/config/addServiceInBatches")
    Observable<BaseBean<Bean>> addServiceInBatches(@Path("domain") String domain,@Body RequestBody body);

    /**
     * 获取业务
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @GET("http://{domain}/printtemplate/config/getAllService")
    Observable<List<BusinessDTO>> addServiceInBatches(@Path("domain") String domain);

    /**
     * 2.6.批量添加打印机
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("http://{domain}/printtemplate/config/addPrinterInBatches")
    Observable<BaseBean<Bean>> addPrinter(@Path("domain") String domain,@Body RequestBody body);


    /**
     * 2.5.获取所有有效打印机
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @GET("http://{domain}/printtemplate/config/getAllPrinter")
    Observable<List<PrinterDTO>> getAllPrinter(@Path("domain") String domain);

    /**
     * 2.8.批量添加纸张
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("http://{domain}/printtemplate/config/addPaperInBatches")
    Observable<BaseBean<Bean>> addPaper(@Path("domain") String domain,@Body RequestBody body);

    /**
     * 批量添加打印机纸张关系
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("http://{domain}/printtemplate/config/addPrinterPaperRelInBatches")
    Observable<BaseBean<Bean>> addPrinterPaperRelInBatches(@Path("domain") String domain,@Body RequestBody body);


    /**
     * 2.4.批量添加元素
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("http://{domain}/printtemplate/config/addElementInBatches")
    Observable<BaseBean<Bean>> addElementInBatches(@Path("domain") String domain,@Body RequestBody body);

    /**
     * 批量添加业务元素关系
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("http://{domain}/printtemplate/config/addBusinessElementRelInBatches")
    Observable<BaseBean<Bean>> addBusinessElementRelInBatches(@Path("domain") String domain,@Body RequestBody body);

    /**
     * 2.11.根据业务获取元素列表
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @GET("http://{domain}/printtemplate/config/getElementByBusiness")
    Observable<List<ElementDTO>> getElementByBusiness(@Path("domain") String domain,@Query("businessCode") String code);


    /**
     * 2.17.添加或更新模板
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("http://{domain}/printtemplate/template/saveTemplate")
    Observable<BaseBean<Bean>> saveTemplate(@Path("domain") String domain,@Body RequestBody body);

    /**
     * 2.20.根据打印机id获取纸张列表
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @GET("http://{domain}/printtemplate/config/getPaperByPrinter")
    Observable<List<PaperDTO>> getPaperByPrinter(@Path("domain") String domain,@Query("printerId") String printerId);


    /**
     * 2.14.根据业务获取启用模板
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @GET("http://{domain}/printtemplate/template/getTemplateByBusinessCode")
    Observable<ModleDTO> getTemplateByBusinessCode(@Path("domain") String domain,@Query("businessCode") String businessCode);

    /**
     * 2.13.获取所有模板
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @GET("http://{domain}/printtemplate/template/getAllTemplate")
    Observable<List<ModleListBean>> getAllTemplate(@Path("domain") String domain);

    /**
     * 2.18.删除模板
     *
     * @return
     */
    @DELETE("http://{domain}/printtemplate/template/deleteTemplate")
    Observable<BaseBean> deleteModle(@Path("domain") String domain,@Query("templateId") String templateId);


    /**
     * 2.15.根据模板id获取模板
     *
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @GET("http://{domain}/printtemplate/template/getTemplateById")
    Observable<ModleDTO> getTemplateById(@Path("domain") String domain,@Query("templateId") String templateId);

}
