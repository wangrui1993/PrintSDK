package com.handset.sdktool.net;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.net.ssl.SSLException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName: HttpLoggingInterceptor2
 * @author: wr
 * @date: 2023/10/27 16:44
 * @Description:作用描述
 */
public class HttpHttpsInterceptor2 implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl oldUrl = originalRequest.url();
        Request.Builder builder = originalRequest.newBuilder();
        try {
            return chain.proceed(originalRequest);
        } catch (SSLException e) {
            e.printStackTrace();
            String scheme = oldUrl.scheme();
            int port = oldUrl.port();
            if (oldUrl.isHttps()) {
                scheme = "http";
                if(oldUrl.host().contains("192.168")){
                    port = oldUrl.port();
                }else {
                    port = 80;
                }
            } else if (!oldUrl.isHttps()) {
                scheme = "https";
                port = oldUrl.port();
            }
            Log.e("实际url====",  oldUrl.port()+"=="+port);
            HttpUrl newHttpUrl = oldUrl.newBuilder()
                    .scheme(scheme) //http协议如：http或者https
                    .host(oldUrl.host()) //主机地址
                    .port(port) //端口
                    .build();

            Request newRequest = builder.url(newHttpUrl).build();
            Log.e("实际url====", newRequest.url().toString());
            return chain.proceed(newRequest);
        }

    }

}
