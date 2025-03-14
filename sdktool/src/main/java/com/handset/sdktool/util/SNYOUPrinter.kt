package com.handset.sdktool.util;

import android.content.Context
import android.graphics.Bitmap
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import androidx.appcompat.app.AppCompatActivity
import com.csnprintersdk.csnio.CSNPOS
import com.csnprintersdk.csnio.CSNUSBPrinting
import com.csnprintersdk.csnio.csnbase.CSNIOCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SNYOUPrinter {


    companion object {

        suspend fun printBitmap(context: Context, device: UsbDevice, bitmap: Bitmap) {
            val mPos = CSNPOS()
            val mUsb = CSNUSBPrinting()

            mPos.Set(mUsb)
            mUsb.SetCallBack(object : CSNIOCallBack {
                override fun OnOpen() {
//                showToast("打印机已连接")
                }

                override fun OnOpenFailed() {
//                    showToast("打印机连接失败")
                }

                override fun OnClose() {
//                    showToast("打印机连接关闭")
                }

            })
            val mUsbManager =
                context.getSystemService(AppCompatActivity.USB_SERVICE) as UsbManager
            withContext(Dispatchers.IO) {
                val isOpen = mUsb.Open(mUsbManager, device, context)
                if (isOpen) {
                    val bPrintResult: Int = printBitmapCmd(bitmap, mPos)
                    withContext(Dispatchers.Main){
//                        showToast(
//                            if (bPrintResult >= 0) {
//                                "打印成功${resultCodeToString(bPrintResult)}"
//                            } else {
//                                "打印失败,${resultCodeToString(bPrintResult)}"
//                            },
//                        )
                    }
                }
                mUsb.Close()
            }


        }

        private fun printBitmapCmd(
            bitmap: Bitmap, mPos: CSNPOS
        ): Int {
            var bPrintResult = 0
            val status = ByteArray(1)
            if (mPos.POS_RTQueryStatus(status, 3, 1000, 2)) {
                if (status[0].toInt() and 0x08 == 0x08) //判断切刀是否异常
                    return (-2).also { bPrintResult = it }
                if (status[0].toInt() and 0x40 == 0x40) //判断打印头是否在正常值范围内
                    return (-3).also { bPrintResult = it }
                if (mPos.POS_RTQueryStatus(status, 2, 1000, 2)) {
                    if (status[0].toInt() and 0x04 == 0x04) //判断合盖是否正常
                        return (-6).also { bPrintResult = it }
                    if (status[0].toInt() and 0x20 == 0x20) //判断是否缺纸
                        return (-5).also { bPrintResult = it } else {

                        if (!mPos.GetIO().IsOpened()) return -11

                        mPos.POS_Reset()
                        mPos.POS_PrintPicture(bitmap, 384, 1, 0)

                        try {
                            Thread.currentThread()
                            Thread.sleep(500)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                        mPos.POS_FeedLine()
                        mPos.POS_FeedLine()
                        mPos.POS_FeedLine()

                        mPos.POS_FullCutPaper()

                    }

                }
            } else {
                return (-8).also {
                    bPrintResult = it //查询失败
                }
            }
            return 0.also { bPrintResult = it }
        }

        private fun resultCodeToString(code: Int) = when (code) {
            3 -> "出纸口有未取小票，请注意及时取走小票"
            2 -> "紙将尽 且 出纸口有未取小票，请注意更换纸卷 和 及时取走小票"
            1 -> "紙将尽，请注意更换纸卷"
            0 -> " "
            -1 -> "未打印小票，请检查是否卡纸"
            -2 -> "切刀异常，请手动排除"
            -3 -> "打印头过热，请等待打印机冷却"
            -4 -> "打印机脱机"
            -5 -> "打印机缺纸"
            -6 -> "上盖打开"
            -7 -> "实时状态查询失败"
            -8 -> "查询状态失败，请检查通讯端口是否连接正常"
            -9 -> "打印过程中缺纸，请检查单据完整性"
            -10 -> "打印过程中上盖开启，请重新打印"
            -11 -> "连接中断，请确认打印机是否连线"
            -12 -> "请取走打印完的票据后，再进行打印！"
            -13 -> "未知错误"
            else -> "未知错误"
        }
    }


}