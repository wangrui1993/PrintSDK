package com.handset.sdktool.net

/**
 * @ClassName: MoreBaseUrlInterceptor
 * @author: wr
 * @date: 2023/5/24 12:01
 * @Description:作用描述
 */
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.net.ssl.SSLException


class HttpHttpsInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取原始的originalRequest
        val originalRequest = chain.request()
        //获取老的url
        val oldUrl = originalRequest.url()
        //获取originalRequest的创建者builder
        val builder = originalRequest.newBuilder()
        try {
            chain.proceed(originalRequest)
        } catch (e: SSLException) {
            var scheme = oldUrl.scheme()
            var port = oldUrl.port()
            if (oldUrl.isHttps) {
                scheme = "http"
                port = 80
            } else if (!oldUrl.isHttps) {
                scheme = "https"
                port = oldUrl.port()
            }
            //重建新的HttpUrl，需要重新设置的url部分
            val newHttpUrl = oldUrl.newBuilder()
                .scheme(scheme) //http协议如：http或者https
                .host(oldUrl.host()) //主机地址
                .port(port) //端口
                .build()


            //获取处理后的新newRequest
            val newRequest = builder.url(newHttpUrl).build()
            Log.e("实际url====", newRequest.url().toString())
            return chain.proceed(newRequest)
        }
        return chain.proceed(originalRequest)

    }


}