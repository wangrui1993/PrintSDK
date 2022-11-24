package com.handset.sdktool.data;

import android.content.Context;
import android.content.Intent;

import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.dto.PaperDTO;
import com.handset.sdktool.dto.PrinterDTO;
import com.handset.sdktool.listener.GetAllBusinessListener;
import com.handset.sdktool.listener.GetAllPrintListener;
import com.handset.sdktool.listener.GetAllTemplateListener;
import com.handset.sdktool.listener.GetElementByBusiness;
import com.handset.sdktool.listener.GetPaperByPrint;
import com.handset.sdktool.listener.GetTemplateByBusinessCode;
import com.handset.sdktool.modle.ModleData;
import com.handset.sdktool.net.NetUtil;
import com.handset.sdktool.net.OnResponse;
import com.handset.sdktool.net.base.ModleListBean;
import com.handset.sdktool.printutil.PrintUtil;
import com.handset.sdktool.ui.ConnectBlueToothActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
     * @param businessId
     * @param map
     */
    public void openPrintPage(Context context, String businessId, Map<String, Object> map) {
        BusinessData.getInstance().setMap(map);
        Intent intent = new Intent(context, ConnectBlueToothActivity.class);
        intent.putExtra("id", businessId);
        context.startActivity(intent);
    }

}
