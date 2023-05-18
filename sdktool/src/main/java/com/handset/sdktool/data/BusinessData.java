package com.handset.sdktool.data;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:获取各类数据
 */
public class BusinessData {
    private static Map<String, Object> map = new HashMap<>();

    private BusinessData() {

    }

    private static BusinessData dataUtil = new BusinessData();

    public static BusinessData getInstance() {
        return dataUtil;
    }

    public static Map<String, Object> getMap() {
        return map;
    }

    public static void setMap(Map<String, Object> map) {
        BusinessData.map = map;
    }

    private static List<Map<String, Object>> listmap = new ArrayList<>();


    public static List<Map<String, Object>> getMaps() {
        return listmap;
    }

    public static void setMaps(List<Map<String, Object>> map) {
        BusinessData.listmap = map;
    }

}
