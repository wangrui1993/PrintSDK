package com.handset.sdktool.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;
//import com.example.lc_print_sdk.PrintUtil;
import com.google.gson.Gson;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.event.LabelBoard;
import com.handset.sdktool.event.LabelItem;
import com.handset.sdktool.listener.GetTemplateByBusinessCode;
import com.handset.sdktool.printutil.MyPrintUtil;
import com.handset.sdktool.ui.ConnectBlueToothActivity;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:作用描述
 */
public class ControllerUtil {
    private ControllerUtil() {

    }

    private static ControllerUtil dataUtil = new ControllerUtil();

    public static ControllerUtil getInstance() {
        return dataUtil;
    }


    /**
     * 携带业务数据打开连接蓝牙打印页
     *
     * @param context
     * @param businessId 根据业务id来获取启用模板
     * @param map
     */
    public void openPrintPage(Context context, String businessId, List<Map<String, Object>> map) {
        BusinessData.getInstance().setMaps(map);
        Intent intent = new Intent(context, ConnectBlueToothActivity.class);
        intent.putExtra("id", businessId);
        context.startActivity(intent);
    }

    /**
     * 根据业务ID和数据生成bitmap
     *
     * @param context
     * @param businessId
     * @param map
     */
    public void printBitmapById(Context context, String businessId,int printTime, List<Map<String, Object>> map,String ip,int port,String deviceModel) {
        BusinessData.getInstance().setMaps(map);
        DataUtil.getInstance().getTemplateByBusinessCode(businessId, new GetTemplateByBusinessCode() {
            @Override
            public void onSuccess(ModleDTO listBaseBean) {
                Log.e("MyPrintUtil---","1");
                MyPrintUtil printUtil = new MyPrintUtil(listBaseBean,context.getResources().getDisplayMetrics());
                printUtil.printTag(printTime, BusinessData.getInstance().getMaps(),ip,port,deviceModel);
//                printBitmap(context, printUtil.getBitmap());
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "获取打印模板接口失败", Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void printBitmap(Context context, Bitmap bitmap) {
//        if (bitmap != null) {
//            PrintUtil printUtil = PrintUtil.getInstance(context);
//            printUtil.setPrintEventListener(new PrintUtil.PrinterBinderListener() {
//                @Override
//                public void onPrintCallback(int i) {
//
//                }
//
//                @Override
//                public void onVersion(String s) {
//
//                }
//            });
//            printUtil.setUnwindPaperLen(70);
//            printUtil.printEnableMark(true);
//            printUtil.printConcentration(39);
//            printUtil.printBitmap(bitmap);
//            printUtil.getInstance(Utils.getApp()).printGoToNextMark();
//        } else {
//            Log.e("bitmapnull---", "");
//        }
//    }
}
