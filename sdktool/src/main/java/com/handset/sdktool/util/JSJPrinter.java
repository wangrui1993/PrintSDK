package com.handset.sdktool.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

import com.szsicod.print.escpos.PrinterAPI;
import com.szsicod.print.io.USBAPI;
import com.szsicod.print.utils.BitmapUtils;

import java.util.HashMap;
import java.util.Iterator;

public class JSJPrinter {

    public static void printBitmap(Context context, UsbDevice device, Bitmap bitmap) {
        if (device != null) {
            USBAPI io = new USBAPI(context, device);
            PrinterAPI mPrinter = PrinterAPI.getInstance();
            int printWidth = (80 - 10) * 8;
            if (mPrinter.connect(io) == PrinterAPI.SUCCESS) {
                bitmap = BitmapUtils.reSize(
                        bitmap,
                        printWidth,
                        bitmap.getHeight() * printWidth / bitmap.getWidth()
                );
                byte[] bmpBytes = PrintImageUtils.parseBmpToByte(bitmap);

                mPrinter.printFeed();
                mPrinter.printFeed();

                mPrinter.sendOrder(bmpBytes);
                mPrinter.printFeed();
                mPrinter.printFeed();
                mPrinter.printFeed();
                mPrinter.fullCut();
                mPrinter.disconnect();
            }

        }

    }
}
