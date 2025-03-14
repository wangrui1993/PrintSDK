package com.handset.sdktool.util;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PrinterDevices {

    //productid 33054 vendorid 4070
    public static final int SNYOU_PRODUCT_ID = 33054;
    public static final int SNYOU_VENDOR_ID = 4070;


    public static final int ICOD_PRODUCT_ID = 30016;

    public static final int ICOD_VENDOR_ID = 1155;

    public static boolean isSNYOUDevices(UsbDevice device) {
        return device.getProductId() == PrinterDevices.SNYOU_PRODUCT_ID && device.getVendorId() == PrinterDevices.SNYOU_VENDOR_ID;
    }

    public static boolean isICODDevice(UsbDevice device) {
        return device.getProductId() == PrinterDevices.ICOD_PRODUCT_ID && device.getVendorId() == PrinterDevices.ICOD_VENDOR_ID;
    }


    public static List<UsbDevice> getConnectedDevices(Context context) {

        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        List<UsbDevice> devices = new ArrayList<>();
        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            UsbInterface usbinterface = device.getInterface(0);
            if (usbinterface.getInterfaceClass() == 7) {
                devices.add(device);
            }
        }
        return devices;
    }


}
