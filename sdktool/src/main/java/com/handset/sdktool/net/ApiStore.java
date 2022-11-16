package com.handset.sdktool.net;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handset.sdktool.net.base.NetConfig;
import com.handset.sdktool.util.DebugLog;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * api retrofit 工具类
 *
 * @fileName: ApiStore
 * @date: 2019/11/5
 * @author: wr
 * @QQ:820139338
 */

public class ApiStore {

    private static Retrofit retrofit;

    public static String baseUrl = NetConfig.BASE_IP;

    public static <T> T createApi(Class<T> service) {
        return retrofit.create(service);
    }

    static {
        createProxy();
    }

    /**
     * 创建 retrofit 客户端
     */
    private static void createProxy() {

        Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd HH:mm:ss").create();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder().
                connectTimeout(120, TimeUnit.SECONDS).
                readTimeout(120, TimeUnit.SECONDS).
                writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                                    @Override
                                    public Response intercept(@NonNull Chain chain) throws IOException {
                                        Request original = chain.request();
                                        Request.Builder requestBuilder = original.newBuilder();
                                        Request request = requestBuilder.build();
                                        return chain.proceed(request);
                                    }
                                }
                ).addInterceptor(new HttpLoggingInterceptor());

        builder.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // 获取 Cookie
                Response resp = chain.proceed(chain.request());
                List cookies = resp.headers("Set-Cookie");
                String cookieStr = "";
                if (cookies != null && cookies.size() > 0) {
                    for (int i = 0; i < cookies.size(); i++) {
                        cookieStr += cookies.get(i);
                    }
//                    UserUtil.saveUserCookieId(cookieStr);
                    NetConfig.COOKIE = cookieStr;
                    DebugLog.e("cookieStr===" + NetConfig.COOKIE);
                }
                return resp;
            }
        });
        builder.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // 设置 Cookie
                String cookieStr = NetConfig.COOKIE;
                DebugLog.e("222cookieStr===" + cookieStr);
                if (cookieStr != null && cookieStr.length() > 0) {
                    return chain.proceed(chain.request().newBuilder().header("Cookie", cookieStr).build());
                }
                return chain.proceed(chain.request());
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)   // urlTag == 0 ? baseUrl : loginUrl  怕搞事麻烦改 , 宁愿分一个出来
                .addConverterFactory(GsonConverterFactory.create(gson))  //   gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //   配置rxjava
                .client(builder.build())   //builder.build()
                .build();
    }

    /**
     * 初始化 OkHttpClient
     *
     * @return
     */
    private static OkHttpClient getClient() {
        OkHttpClient.Builder client = new OkHttpClient().newBuilder();

        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // 获取 Cookie
                Response resp = chain.proceed(chain.request());
                List cookies = resp.headers("Set-Cookie");
                String cookieStr = "";
                if (cookies != null && cookies.size() > 0) {
                    for (int i = 0; i < cookies.size(); i++) {
                        cookieStr += cookies.get(i);
                    }
//                    UserUtil.saveUserCookieId(cookieStr);
                    NetConfig.COOKIE = cookieStr;
                }
                return resp;
            }
        });
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // 设置 Cookie
                String cookieStr = NetConfig.COOKIE;
                if (cookieStr != null && cookieStr.length() > 0) {
                    return chain.proceed(chain.request().newBuilder().header("Cookie", cookieStr).build());
                }
                return chain.proceed(chain.request());
            }
        });
        client.connectTimeout(120, TimeUnit.MILLISECONDS);
        client.writeTimeout(120, TimeUnit.MILLISECONDS);
        client.readTimeout(120, TimeUnit.MILLISECONDS);
        return client.build();
    }

}
