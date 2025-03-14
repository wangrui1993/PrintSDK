package com.jingzhi.commerce.tablet.util

import android.hardware.usb.UsbDevice
import com.handset.sdktool.util.PrinterDevices

fun UsbDevice.isInnerDevice() =
    PrinterDevices.isICODDevice(this) || PrinterDevices.isSNYOUDevices(this)

fun UsbDevice.isSNYOUDevice() = PrinterDevices.isSNYOUDevices(this)
fun UsbDevice.isICODDevice() = PrinterDevices.isICODDevice(this)
