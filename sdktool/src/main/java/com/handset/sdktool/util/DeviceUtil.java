package com.handset.sdktool.util;

import android.content.Context;
import android.util.Log;

import com.handset.sdktool.printer.sunmi.SunmiPrintHelper;

import java.math.BigDecimal;

public class DeviceUtil {

    public static String SUNMI = "SUNMI";//商米
    public static String HM = "HM";//汉印
    public static String LOCATIONDEVICE = "";//
    public static int TOPPADING = 0;//上边距
    public static int LEFTPADING = 0;//左边距
    public static String CURRENTDEVICE = "";//当前连接的设备

    /**
     * 设置设备
     **/
    public static void setDevive(String device) {

        CURRENTDEVICE = device;
        if (device.contains(SUNMI)) {
            TOPPADING = 50;
            LEFTPADING = 30;
        } else if (device.contains(HM)) {
            TOPPADING = 0;
            LEFTPADING = 0;
        }

    }

    /**
     * 本机设备初始化
     **/
    public static String deviceInit(Context context) {
        String deviceName = "";

        Log.e("Device===Manufacturer", getDeviceManufacturer());
        Log.e("Device===Product", getDeviceProduct());

        Log.e("Device===Brand", getDeviceBrand());
        Log.e("Device===Model", getDeviceModel());
        Log.e("Device===Board", getDeviceBoard());
        Log.e("Device===Device", getDeviceDevice());
        Log.e("Device===Fubgerprint", getDeviceFubgerprint());

        if (getDeviceManufacturer().equals(SUNMI)) {
            deviceName = SUNMI + getDeviceProduct();
            LOCATIONDEVICE = getDeviceProduct();
            CURRENTDEVICE = deviceName;
            TOPPADING = 50;
            LEFTPADING = 30;
            SunmiPrintHelper.getInstance().initSunmiPrinterService(context);
            SunmiPrintHelper.getInstance().initPrinter();
        }

        return deviceName;
    }

    /**
     * 获取厂商名
     **/
    public static String getDeviceManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取产品名
     **/
    public static String getDeviceProduct() {
        return android.os.Build.PRODUCT;
    }

    /**
     * 获取手机品牌
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机主板名
     */
    public static String getDeviceBoard() {
        return android.os.Build.BOARD;
    }

    /**
     * 设备名
     **/
    public static String getDeviceDevice() {
        return android.os.Build.DEVICE;
    }

    /**
     * fingerprit 信息
     **/
    public static String getDeviceFubgerprint() {
        return android.os.Build.FINGERPRINT;
    }
}
