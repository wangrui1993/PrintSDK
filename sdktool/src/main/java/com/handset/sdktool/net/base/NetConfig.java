package com.handset.sdktool.net.base;

/**
 * @ClassName: Config
 * @Author: WR
 * @CreateDate: 2020/10/16 9:32
 * @Description:
 */
public class NetConfig {
    public static void init(String ip) {
        IP = ip;
    }

    public static String IP = "http://192.168.31.82:8090/";
    public static final String BASE_IP = "http://192.168.31.82:8090/";
    public static final String BASE_IP_PLUS = BASE_IP + "";

    public static String TOKEN;
    public static String COOKIE = "";

}
