package com.handset.sdktool.net;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @ClassName: NetUtil
 * @author: wr
 * @date: 2022/11/15 16:02
 * @Description:作用描述
 */
public class NetUtil {
    private NetUtil(){

    };
    private static NetUtil netUtil= new NetUtil();

    public static NetUtil getInstance() {
        return netUtil;
    }

    public static void setNetUtil(NetUtil netUtil) {
        NetUtil.netUtil = netUtil;
    }
    /**
     * Retrofit请求
     * 传入接口配置文件
     *
     * @return
     */
    public ApiService api() {
        return ApiStore.createApi(ApiService.class);
    }
}
