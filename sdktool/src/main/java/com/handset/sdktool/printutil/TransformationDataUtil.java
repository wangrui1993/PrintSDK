package com.handset.sdktool.printutil;

import android.util.Log;

import com.google.gson.Gson;
import com.handset.sdktool.data.BusinessData;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.event.Label;
import com.handset.sdktool.event.LabelBoard;
import com.handset.sdktool.util.CalculationUtil;
import com.handset.sdktool.util.DebugLog;
import com.handset.sdktool.util.LabelBoardAnalysisUtil;

import java.util.List;
import java.util.Map;

import cpcl.PrinterHelper;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:作用描述
 */
public class TransformationDataUtil {

    private static TransformationDataUtil mTransformationDataUtil = new TransformationDataUtil();

    public static TransformationDataUtil getInstance() {
        return mTransformationDataUtil;
    }

    private TransformationDataUtil() {
    }

    /**
     * 根据数据打印
     *
     * @throws Exception
     */
    public String getShowContent(ModleDTO.ComponentsBean componentsBean) {
        DebugLog.e("wccc=====" + componentsBean.getComponentTypeId());
        String result = "";
        if (componentsBean.getComponentTypeId().equals("1") || componentsBean.getComponentTypeId().equals("2") || componentsBean.getComponentTypeId().equals("3")) {
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_TEXT)) {
                result = componentsBean.getComponentContent();
                return result;
            }
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                result = componentsBean.getComponentContent();
                return result;
            }
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_E)) {
                DebugLog.e("wccc===1==" + componentsBean.getElement().getElementCode());
                if (componentsBean.getElement() != null && componentsBean.getElement().getElementCode() != null) {
                    DebugLog.e("wccc===2==" + componentsBean.getElement().getElementCode());
//                    if(componentsBean.getFather_elementCode()!=null&&componentsBean.getFather_elementCode().length()>0){
//                        List<Map<String, Object>> listmap = (List<Map<String, Object>>) BusinessData.getInstance().getMap().get(componentsBean.getFather_elementCode());
//                        getShowContent(componentsBean,listmap);
//                    }else {
//                        result = (String) BusinessData.getInstance().getMap().get(componentsBean.getElement().getElementCode());
//                    }
                    DebugLog.e("wccc===3==" + result);
                    return result;
                }

                result = componentsBean.getComponentContent();
                return result;
            }
        }
//        else if (componentsBean.getComponentTypeId().equals("5")) {
//            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_TEXT)) {
//                result = componentsBean.getComponentContent();
//                return result;
//            }
//            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_S)) {
//                result = componentsBean.getComponentContent();
//                return result;
//            }
//            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_E)) {
//                DebugLog.e("wccc===4==" + componentsBean.getElement().getElementCode());
//                if (componentsBean.getElement() != null && componentsBean.getElement().getElementCode() != null) {
//                    DebugLog.e("wccc===5==" + componentsBean.getElement().getElementCode());
//                    List<Map<String, Object>> list = (List<Map<String, Object>>) BusinessData.getInstance().getMap().get(componentsBean.getElement());
//
//
//                    DebugLog.e("wccc===6==" + BusinessData.getInstance().getMap().get(componentsBean.getElement()).toString());
//
//                    result = (String) list.get(0).get(componentsBean.getElement().getElementCode());
//                    DebugLog.e("wccc===7==" + result);
//                    return result;
//                }
//
//                result = componentsBean.getComponentContent();
//                return result;
//            }
//        }
        return result;
    }
    /**
     * 根据数据打印
     *
     * @throws Exception
     */
    public String getShowContent(ModleDTO.ComponentsBean componentsBean,List<Map<String, Object>> listmap) {
        DebugLog.e("wccc=====" + componentsBean.getComponentTypeId());
        String result = "";
        if (componentsBean.getComponentTypeId().equals("1") || componentsBean.getComponentTypeId().equals("2") || componentsBean.getComponentTypeId().equals("3")) {
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_TEXT)) {
                result = componentsBean.getComponentContent();
                return result;
            }
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                result = componentsBean.getComponentContent();
                return result;
            }
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_E)) {
                DebugLog.e("wccc===1==" + componentsBean.getElement().getElementCode());
                if (componentsBean.getElement() != null && componentsBean.getElement().getElementCode() != null) {
                    DebugLog.e("wccc===2==" + componentsBean.getElement().getElementCode());
//                    if(componentsBean.getFather_elementCode()!=null&&componentsBean.getFather_elementCode().length()>0){
//                        List<Map<String, Object>> list = (List<Map<String, Object>>) BusinessData.getInstance().getMap().get(componentsBean.getFather_elementCode());
//                    }else {
//                        result = (String) BusinessData.getInstance().getMap().get(componentsBean.getElement().getElementCode());
//                    }
                    DebugLog.e("wccc===3==" + result);
                    return result;
                }

                result = componentsBean.getComponentContent();
                return result;
            }
        }

        return result;
    }


}
