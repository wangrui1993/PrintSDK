package com.handset.sdktool.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
