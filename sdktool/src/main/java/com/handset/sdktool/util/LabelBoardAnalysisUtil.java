package com.handset.sdktool.util;

import android.content.Context;
import android.util.Log;

import com.google.android.material.internal.ViewUtils;
import com.google.gson.Gson;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.event.Label;
import com.handset.sdktool.event.LabelBarcode;
import com.handset.sdktool.event.LabelBoard;
import com.handset.sdktool.event.LabelItem;
import com.handset.sdktool.event.LabelQRCode;
import com.handset.sdktool.event.LabelShape;
import com.handset.sdktool.event.LabelText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: LabelBoardAnalysisUtil
 * @author: wr
 * @date: 2022/11/6 19:34
 * @Description:作用描述
 */
public class LabelBoardAnalysisUtil {


    /**
     * 视图数据转接口数据
     * 将labelBoard中的数据存入接口需要的数据格式中
     *
     * @param labelBoard
     */
    public static List<ModleDTO.ComponentsBean> setDataToModle(LabelBoard labelBoard, ModleDTO.ComponentsBean containerComponentsBean) {
        List<ModleDTO.ComponentsBean> componentsBeanList = new ArrayList<>();
        int lb_x = containerComponentsBean.getCoordX();
        int lb_y = containerComponentsBean.getCoordY();
        if (labelBoard != null) {
            componentsBeanList.clear();
            for (int i = 0; i < labelBoard.getLabelTexts().size(); i++) {
                LabelText labelText = labelBoard.getLabelTexts().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelText.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "1" + i + getNum()) : labelText.getComponentId();
                DebugLog.e("getLabelTexts==id===" + id);
                componentsBean.setId(id);
                componentsBean.setComponentTypeId("1");
                if (labelText.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || labelText.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                    DebugLog.e("getLabelTexts==id2===" + labelText.getContent());
                    componentsBean.setComponentContent(labelText.getContent());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode("");
                    elementsBean.setElementDesc("");
                    elementsBean.setElementName("");
                    componentsBean.setElement(elementsBean);
                } else {
                    DebugLog.e("getLabelTexts==id3===" + labelText.getElementId());
                    componentsBean.setComponentContent(labelText.getElementId());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode(labelText.getElementCode());
                    elementsBean.setElementDesc(labelText.getElementDesc());
                    elementsBean.setElementName(labelText.getElementName());
                    componentsBean.setElement(elementsBean);
                }
                componentsBean.setPrefix(labelText.getPrefix());
                componentsBean.setSuffix(labelText.getSuffix());
                componentsBean.setSize(String.valueOf(labelText.getFontSize()));
                componentsBean.setAlignType(labelText.getAlignType());
                componentsBean.setCoordX((int) labelText.getX() + lb_x);
                componentsBean.setCoordY((int) labelText.getY() + lb_y);
                componentsBean.setFontBold(labelText.isBold() ? "0" : "1");
                componentsBean.setComponentWidth((int) labelText.getWidth());
                componentsBean.setComponentHeight((int) labelText.getHeight());
                componentsBean.setContentSource(labelText.getContentSource());
                componentsBeanList.add(componentsBean);
            }
            DebugLog.e("getLabelTexts==id333===3=" + componentsBeanList.toString());
            for (int i = 0; i < labelBoard.getLabelBarcodes().size(); i++) {
                LabelBarcode labelBarcode = labelBoard.getLabelBarcodes().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelBarcode.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "2" + i + getNum()) : labelBarcode.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                if (labelBarcode.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || labelBarcode.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                    componentsBean.setComponentContent(labelBarcode.getContent());
                } else {
                    componentsBean.setComponentContent(labelBarcode.getElementId());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode(labelBarcode.getElementCode());
                    elementsBean.setElementDesc(labelBarcode.getElementDesc());
                    elementsBean.setElementName(labelBarcode.getElementName());
                    componentsBean.setElement(elementsBean);
                }
                componentsBean.setComponentTypeId("2");
                componentsBean.setCoordX((int) labelBarcode.getX() + lb_x);
                componentsBean.setCoordY((int) labelBarcode.getY() + lb_y);
                componentsBean.setComponentWidth((int) labelBarcode.getWidth());
                componentsBean.setComponentHeight((int) labelBarcode.getHeight());
                componentsBean.setEncodingType(labelBarcode.getEncodeType());
                componentsBean.setContentSource(labelBarcode.getContentSource());
                componentsBeanList.add(componentsBean);
            }
            for (int i = 0; i < labelBoard.getLabelQRCodes().size(); i++) {
                LabelQRCode labelQRCode = labelBoard.getLabelQRCodes().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelQRCode.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "3" + i + getNum()) : labelQRCode.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                if (labelQRCode.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || labelQRCode.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                    componentsBean.setComponentContent(labelQRCode.getContent());
                } else {
                    componentsBean.setComponentContent(labelQRCode.getElementId());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode(labelQRCode.getElementCode());
                    elementsBean.setElementDesc(labelQRCode.getElementDesc());
                    elementsBean.setElementName(labelQRCode.getElementName());
                    componentsBean.setElement(elementsBean);
                }
                componentsBean.setComponentTypeId("3");
                componentsBean.setCoordX((int) labelQRCode.getX() + lb_x);
                componentsBean.setCoordY((int) labelQRCode.getY() + lb_y);
                componentsBean.setComponentWidth((int) labelQRCode.getWidth());
                componentsBean.setComponentHeight((int) labelQRCode.getHeight());
                componentsBean.setEncodingType(labelQRCode.getEncodeType());
                componentsBean.setContentSource(labelQRCode.getContentSource());
                componentsBeanList.add(componentsBean);

            }
            for (int i = 0; i < labelBoard.getLabelShapes().size(); i++) {
                LabelShape labelShape = labelBoard.getLabelShapes().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelShape.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "4" + i + getNum()) : labelShape.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                componentsBean.setComponentTypeId("4");
                componentsBean.setCoordX((int) labelShape.getX() + lb_x);
                componentsBean.setCoordY((int) labelShape.getY() + lb_y);
                componentsBean.setComponentWidth((int) labelShape.getWidth());
                componentsBean.setComponentHeight((int) labelShape.getHeight() - 60);
                componentsBeanList.add(componentsBean);
            }
            for (int i = 0; i < labelBoard.getLabelItems().size(); i++) {
                LabelItem labelItem = labelBoard.getLabelItems().get(i);

                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelItem.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "5" + i + getNum()) : labelItem.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                componentsBean.setComponentTypeId("5");
                componentsBean.setCoordX((int) labelItem.getX() + lb_x);
                componentsBean.setCoordY((int) labelItem.getY() + lb_y);
                componentsBean.setComponentContent(labelItem.getDataJson());
                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                elementsBean.setElementCode(labelItem.getElementCode());
                elementsBean.setElementDesc(labelItem.getElementDesc());
                elementsBean.setElementName(labelItem.getElementName());
                componentsBean.setElement(elementsBean);
                DebugLog.e("labelItem====" + labelItem.getDataJson());
                componentsBean.setComponentWidth((int) labelItem.getWidth());
                componentsBean.setComponentHeight((int) labelItem.getHeight());
                componentsBean.setChildTemplateId(labelItem.getChildTemplateId());
                componentsBeanList.add(componentsBean);
            }
            DebugLog.e("getLabelTexts==id===3=" + componentsBeanList.toString());
        }
        return componentsBeanList;
    }

    public static List<ModleDTO.ComponentsBean> setDataToModle(LabelBoard labelBoard) {
        List<ModleDTO.ComponentsBean> componentsBeanList = new ArrayList<>();

        if (labelBoard != null) {
            componentsBeanList.clear();
            for (int i = 0; i < labelBoard.getLabelTexts().size(); i++) {
                LabelText labelText = labelBoard.getLabelTexts().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelText.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "1" + i + getNum()) : labelText.getComponentId();
                DebugLog.e("getLabelTexts==id===" + id);
                componentsBean.setId(id);
                componentsBean.setComponentTypeId("1");
                if (labelText.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || labelText.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                    DebugLog.e("getLabelTexts==id2===" + labelText.getContent());
                    componentsBean.setComponentContent(labelText.getContent());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode("");
                    elementsBean.setElementDesc("");
                    elementsBean.setElementName("");
                    componentsBean.setElement(elementsBean);
                } else {
                    DebugLog.e("getLabelTexts==id3===" + labelText.getElementId());
                    componentsBean.setComponentContent(labelText.getElementId());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode(labelText.getElementCode());
                    elementsBean.setElementDesc(labelText.getElementDesc());
                    elementsBean.setElementName(labelText.getElementName());
                    componentsBean.setElement(elementsBean);
                }
                if (componentsBean.getElement() == null || componentsBean.getElement().getElementCode() == null) {
                    DebugLog.e("getElement=nul====" + componentsBean.getComponentContent() + "===" + componentsBean.getId());
                }
                componentsBean.setPrefix(labelText.getPrefix());
                componentsBean.setSuffix(labelText.getSuffix());
                componentsBean.setSize(String.valueOf(labelText.getFontSize()));
                componentsBean.setAlignType(labelText.getAlignType());
                componentsBean.setCoordX((int) labelText.getX());
                componentsBean.setCoordY((int) labelText.getY());
                componentsBean.setFontBold(labelText.isBold() ? "0" : "1");
                componentsBean.setComponentWidth((int) labelText.getWidth());
                componentsBean.setComponentHeight((int) labelText.getHeight());
                componentsBean.setContentSource(labelText.getContentSource());
                componentsBeanList.add(componentsBean);
            }
            DebugLog.e("getLabelTexts==id333===3=" + componentsBeanList.toString());
            for (int i = 0; i < labelBoard.getLabelBarcodes().size(); i++) {
                LabelBarcode labelBarcode = labelBoard.getLabelBarcodes().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelBarcode.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "2" + i + getNum()) : labelBarcode.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                if (labelBarcode.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || labelBarcode.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                    componentsBean.setComponentContent(labelBarcode.getContent());
                } else {
                    componentsBean.setComponentContent(labelBarcode.getElementId());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode(labelBarcode.getElementCode());
                    elementsBean.setElementDesc(labelBarcode.getElementDesc());
                    elementsBean.setElementName(labelBarcode.getElementName());
                    componentsBean.setElement(elementsBean);
                }
                componentsBean.setComponentTypeId("2");
                componentsBean.setCoordX((int) labelBarcode.getX());
                componentsBean.setCoordY((int) labelBarcode.getY());
                componentsBean.setComponentWidth((int) labelBarcode.getWidth());
                componentsBean.setComponentHeight((int) labelBarcode.getHeight());
                componentsBean.setEncodingType(labelBarcode.getEncodeType());
                componentsBean.setContentSource(labelBarcode.getContentSource());
                componentsBeanList.add(componentsBean);
            }
            for (int i = 0; i < labelBoard.getLabelQRCodes().size(); i++) {
                LabelQRCode labelQRCode = labelBoard.getLabelQRCodes().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelQRCode.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "3" + i + getNum()) : labelQRCode.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                if (labelQRCode.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || labelQRCode.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                    componentsBean.setComponentContent(labelQRCode.getContent());
                } else {
                    componentsBean.setComponentContent(labelQRCode.getElementId());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode(labelQRCode.getElementCode());
                    elementsBean.setElementDesc(labelQRCode.getElementDesc());
                    elementsBean.setElementName(labelQRCode.getElementName());
                    componentsBean.setElement(elementsBean);
                }
                componentsBean.setComponentTypeId("3");
                componentsBean.setCoordX((int) labelQRCode.getX());
                componentsBean.setCoordY((int) labelQRCode.getY());
                componentsBean.setComponentWidth((int) labelQRCode.getWidth());
                componentsBean.setComponentHeight((int) labelQRCode.getHeight());
                componentsBean.setEncodingType(labelQRCode.getEncodeType());
                componentsBean.setContentSource(labelQRCode.getContentSource());
                componentsBeanList.add(componentsBean);

            }
            for (int i = 0; i < labelBoard.getLabelShapes().size(); i++) {
                LabelShape labelShape = labelBoard.getLabelShapes().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelShape.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "4" + i + getNum()) : labelShape.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                componentsBean.setComponentTypeId("4");
                componentsBean.setCoordX((int) labelShape.getX());
                componentsBean.setCoordY((int) labelShape.getY());
                componentsBean.setComponentWidth((int) labelShape.getWidth());
                componentsBean.setComponentHeight((int) labelShape.getHeight() - 60);
                componentsBeanList.add(componentsBean);
            }
            for (int i = 0; i < labelBoard.getLabelItems().size(); i++) {
                LabelItem labelItem = labelBoard.getLabelItems().get(i);

                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelItem.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "5" + i + getNum()) : labelItem.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                componentsBean.setComponentTypeId("5");
                componentsBean.setCoordX((int) labelItem.getX());
                componentsBean.setCoordY((int) labelItem.getY());
                componentsBean.setComponentContent(labelItem.getDataJson());
                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                elementsBean.setElementCode(labelItem.getElementCode());
                elementsBean.setElementDesc(labelItem.getElementDesc());
                elementsBean.setElementName(labelItem.getElementName());
                componentsBean.setElement(elementsBean);
                DebugLog.e("labelItem====" + labelItem.getDataJson());
                componentsBean.setComponentWidth((int) labelItem.getWidth());
                componentsBean.setComponentHeight((int) labelItem.getHeight());
                componentsBean.setChildTemplateId(labelItem.getChildTemplateId());
                componentsBeanList.add(componentsBean);
            }
            DebugLog.e("getLabelTexts==id===3=" + componentsBeanList.toString());
        }
        return componentsBeanList;
    }

    public static List<ModleDTO.ComponentsBean> setDataToModle3(LabelBoard labelBoard) {
        List<ModleDTO.ComponentsBean> componentsBeanList = new ArrayList<>();

        if (labelBoard != null) {
            componentsBeanList.clear();
            for (int i = 0; i < labelBoard.getLabelTexts().size(); i++) {
                LabelText labelText = labelBoard.getLabelTexts().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelText.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "1" + i + getNum()) : labelText.getComponentId();
                DebugLog.e("getLabelTexts==id===" + id);
                componentsBean.setId(id);
                componentsBean.setComponentTypeId("1");
                if (labelText.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || labelText.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                    DebugLog.e("getLabelTexts==id2===" + labelText.getContent());
                    componentsBean.setComponentContent(labelText.getContent());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode("");
                    elementsBean.setElementDesc("");
                    elementsBean.setElementName("");
                    componentsBean.setElement(elementsBean);
                } else {
                    DebugLog.e("getLabelTexts==id3===" + labelText.getElementId());
                    componentsBean.setComponentContent(labelText.getElementId());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode(labelText.getElementCode());
                    elementsBean.setElementDesc(labelText.getElementDesc());
                    elementsBean.setElementName(labelText.getElementName());
                    componentsBean.setElement(elementsBean);
                }
                if (componentsBean.getElement() == null || componentsBean.getElement().getElementCode() == null) {
                    DebugLog.e("getElement=nul====" + componentsBean.getComponentContent() + "===" + componentsBean.getId());
                }
                componentsBean.setPrefix(labelText.getPrefix());
                componentsBean.setSuffix(labelText.getSuffix());
                componentsBean.setSize(String.valueOf(labelText.getFontSize()));
                componentsBean.setAlignType(labelText.getAlignType());
                componentsBean.setCoordX((int) labelText.getX());
                componentsBean.setCoordY((int) labelText.getY());
                componentsBean.setFontBold(labelText.isBold() ? "0" : "1");
                componentsBean.setComponentWidth((int) labelText.getWidth());
                componentsBean.setComponentHeight((int) labelText.getHeight());
                componentsBean.setContentSource(labelText.getContentSource());
                componentsBeanList.add(componentsBean);
            }
            DebugLog.e("getLabelTexts==id333===3=" + componentsBeanList.toString());
            for (int i = 0; i < labelBoard.getLabelBarcodes().size(); i++) {
                LabelBarcode labelBarcode = labelBoard.getLabelBarcodes().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelBarcode.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "2" + i + getNum()) : labelBarcode.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                if (labelBarcode.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || labelBarcode.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                    componentsBean.setComponentContent(labelBarcode.getContent());
                } else {
                    componentsBean.setComponentContent(labelBarcode.getElementId());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode(labelBarcode.getElementCode());
                    elementsBean.setElementDesc(labelBarcode.getElementDesc());
                    elementsBean.setElementName(labelBarcode.getElementName());
                    componentsBean.setElement(elementsBean);
                }
                componentsBean.setComponentTypeId("2");
                componentsBean.setCoordX((int) labelBarcode.getX());
                componentsBean.setCoordY((int) labelBarcode.getY());
                componentsBean.setComponentWidth((int) labelBarcode.getWidth());
                componentsBean.setComponentHeight((int) labelBarcode.getHeight());
                componentsBean.setEncodingType(labelBarcode.getEncodeType());
                componentsBean.setContentSource(labelBarcode.getContentSource());
                componentsBeanList.add(componentsBean);
            }
            for (int i = 0; i < labelBoard.getLabelQRCodes().size(); i++) {
                LabelQRCode labelQRCode = labelBoard.getLabelQRCodes().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelQRCode.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "3" + i + getNum()) : labelQRCode.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                if (labelQRCode.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || labelQRCode.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                    componentsBean.setComponentContent(labelQRCode.getContent());
                } else {
                    componentsBean.setComponentContent(labelQRCode.getElementId());
                    ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                    elementsBean.setElementCode(labelQRCode.getElementCode());
                    elementsBean.setElementDesc(labelQRCode.getElementDesc());
                    elementsBean.setElementName(labelQRCode.getElementName());
                    componentsBean.setElement(elementsBean);
                }
                componentsBean.setComponentTypeId("3");
                componentsBean.setCoordX((int) labelQRCode.getX());
                componentsBean.setCoordY((int) labelQRCode.getY());
                componentsBean.setComponentWidth((int) labelQRCode.getWidth());
                componentsBean.setComponentHeight((int) labelQRCode.getHeight());
                componentsBean.setEncodingType(labelQRCode.getEncodeType());
                componentsBean.setContentSource(labelQRCode.getContentSource());
                componentsBeanList.add(componentsBean);

            }
            for (int i = 0; i < labelBoard.getLabelShapes().size(); i++) {
                LabelShape labelShape = labelBoard.getLabelShapes().get(i);
                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelShape.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "4" + i + getNum()) : labelShape.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                componentsBean.setComponentTypeId("4");
                componentsBean.setCoordX((int) labelShape.getX());
                componentsBean.setCoordY((int) labelShape.getY());
                componentsBean.setComponentWidth((int) labelShape.getWidth());
                componentsBean.setComponentHeight((int) labelShape.getHeight() - 60);
                componentsBeanList.add(componentsBean);
            }
            for (int i = 0; i < labelBoard.getLabelItems().size(); i++) {
                LabelItem labelItem = labelBoard.getLabelItems().get(i);

                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
                String id = labelItem.getComponentId() == null ? String.valueOf(System.currentTimeMillis() + "5" + i + getNum()) : labelItem.getComponentId();
                DebugLog.e("id====" + id);
                componentsBean.setId(id);
                componentsBean.setComponentTypeId("5");
                componentsBean.setCoordX((int) labelItem.getX());
                componentsBean.setCoordY((int) labelItem.getY());
                componentsBean.setComponentContent(labelItem.getDataJson());
                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();
                elementsBean.setElementCode(labelItem.getElementCode());
                elementsBean.setElementDesc(labelItem.getElementDesc());
                elementsBean.setElementName(labelItem.getElementName());
                componentsBean.setElement(elementsBean);
                DebugLog.e("labelItem====" + labelItem.getDataJson());
                componentsBean.setComponentWidth((int) labelItem.getWidth());
                componentsBean.setComponentHeight((int) labelItem.getHeight());
                componentsBean.setChildTemplateId(labelItem.getChildTemplateId());
                componentsBeanList.add(componentsBean);
            }
            DebugLog.e("getLabelTexts==id===3=" + componentsBeanList.toString());
        }
        return componentsBeanList;
    }

    /**
     * 接口数据转视图数据
     * 将ModleDTO中的数据轉換為LabelBoard 用于列表展示
     */
    public static LabelBoard setData(ModleDTO item) {
        LabelBoard labelBoard = new LabelBoard();
        if (item != null && item.getComponents() != null) {
            labelBoard.setWidth((int) item.getTemplate().getWidth());
            labelBoard.setHeight((int) item.getTemplate().getHeight());
            for (ModleDTO.ComponentsBean componentsBean : item.getComponents()) {
                if (componentsBean.getComponentTypeId().equals("1")) {//文字
                    LabelText labelText = new LabelText();
                    int x = Integer.valueOf(componentsBean.getCoordX());
                    labelText.setX(x);
                    int y = Integer.valueOf(componentsBean.getCoordY());
                    labelText.setY(y);
                    int w = Integer.valueOf(componentsBean.getComponentWidth());
                    int h = Integer.valueOf(componentsBean.getComponentHeight());
                    labelText.setWidth(w);
                    labelText.setHeight(h);
                    labelText.setContentSource(componentsBean.getContentSource());
                    labelText.setBold(componentsBean.getFontBold().equals("1") ? false : true);
                    labelText.setPrefix(componentsBean.getPrefix());
                    labelText.setSuffix(componentsBean.getSuffix());
                    labelText.setFontSize(Integer.valueOf(componentsBean.getSize()));
                    labelText.setComponentId(componentsBean.getId());
                    if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || componentsBean.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                        labelText.setContent(componentsBean.getComponentContent());
                    } else {
                        labelText.setElementId(componentsBean.getComponentContent());
                        if (componentsBean.getElement() != null) {
                            labelText.setElementCode(componentsBean.getElement().getElementCode());
                            labelText.setElementName(componentsBean.getElement().getElementName());
                            labelText.setElementDesc(componentsBean.getElement().getElementDesc());
                            if ((componentsBean.getPrefix() == null || componentsBean.getPrefix().length() == 0) &&
                                    componentsBean.getSuffix() == null || componentsBean.getSuffix().length() == 0) {
                                labelText.setContent("xxx(" + componentsBean.getElement().getElementName() + ")");
                            }
                        }
                    }
                    labelBoard.getLabelTexts().add(labelText);
                }
                if (componentsBean.getComponentTypeId().equals("2")) {//条码
                    LabelBarcode labelBarcode = new LabelBarcode();
                    int x = Integer.valueOf(componentsBean.getCoordX());
                    labelBarcode.setX(x);
                    int y = Integer.valueOf(componentsBean.getCoordY());
                    labelBarcode.setY(y);
                    int w = Integer.valueOf(componentsBean.getComponentWidth());
                    int h = Integer.valueOf(componentsBean.getComponentHeight());
                    labelBarcode.setWidth(w);
                    labelBarcode.setHeight(h);
                    labelBarcode.setContentSource(componentsBean.getContentSource());
                    labelBarcode.setEncodeType(componentsBean.getEncodingType());
                    if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || componentsBean.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                        labelBarcode.setElementId(null);
                        labelBarcode.setContent(componentsBean.getComponentContent() == null ? "123456" : componentsBean.getComponentContent());
                    } else {
                        labelBarcode.setElementId(componentsBean.getComponentContent());
                        labelBarcode.setContent(componentsBean.getComponentContent() == null ? "123456" : componentsBean.getComponentContent());
                        DebugLog.e("woow====1");
                        if (componentsBean.getElement() != null) {
                            DebugLog.e("woow====2");
                            labelBarcode.setElementCode(componentsBean.getElement().getElementCode());
                            labelBarcode.setElementName(componentsBean.getElement().getElementName());
                            labelBarcode.setElementDesc(componentsBean.getElement().getElementDesc());
                        }
                    }
                    DebugLog.e("woow====3" + labelBarcode.toString());
                    labelBoard.getLabelBarcodes().add(labelBarcode);
                }
                if (componentsBean.getComponentTypeId().equals("3")) {//二维码
                    LabelQRCode labelQRCode = new LabelQRCode();
                    int x = Integer.valueOf(componentsBean.getCoordX());
                    labelQRCode.setX(x);
                    int y = Integer.valueOf(componentsBean.getCoordY());
                    labelQRCode.setY(y);
                    int w = Integer.valueOf(componentsBean.getComponentWidth());
                    int h = Integer.valueOf(componentsBean.getComponentHeight());
                    labelQRCode.setWidth(w);
                    labelQRCode.setHeight(h);
                    labelQRCode.setContentSource(componentsBean.getContentSource());
                    labelQRCode.setEncodeType(componentsBean.getEncodingType());
                    if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_TEXT) || componentsBean.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                        labelQRCode.setElementId(null);
                        labelQRCode.setContent(componentsBean.getComponentContent() == null ? "123456" : componentsBean.getComponentContent());
                    } else {
                        labelQRCode.setElementId(componentsBean.getComponentContent());
                        labelQRCode.setContent(componentsBean.getComponentContent() == null ? "123456" : componentsBean.getComponentContent());
                        if (componentsBean.getElement() != null) {
                            labelQRCode.setElementCode(componentsBean.getElement().getElementCode());
                            labelQRCode.setElementName(componentsBean.getElement().getElementName());
                            labelQRCode.setElementDesc(componentsBean.getElement().getElementDesc());
                        }
                    }
                    labelQRCode.setComponentId(componentsBean.getId());
                    labelBoard.getLabelQRCodes().add(labelQRCode);
                }
                if (componentsBean.getComponentTypeId().equals("4")) {//形状
                    LabelShape labelShape = new LabelShape(5);
                    int x = Integer.valueOf(componentsBean.getCoordX());
                    labelShape.setX(x);
                    int y = Integer.valueOf(componentsBean.getCoordY());
                    labelShape.setY(y);
                    int w = Integer.valueOf(componentsBean.getComponentWidth());
                    int h = Integer.valueOf(componentsBean.getComponentHeight());
                    labelShape.setWidth(w);
                    labelShape.setHeight(h);
                    labelShape.setShapType(5);
                    labelShape.setBorderWidth((int) labelShape.getHeight() - 60);
                    labelShape.setComponentId(componentsBean.getId());
                    labelBoard.getLabelShapes().add(labelShape);
                }
                if (componentsBean.getComponentTypeId().equals("5")) {//子模板
                    LabelItem labelItem = new LabelItem();
                    labelItem.setDataJson(componentsBean.getComponentContent());

                    LabelBoard lb = new Gson().fromJson(labelItem.getDataJson(), LabelBoard.class);

                    int x = Integer.valueOf(componentsBean.getCoordX());
                    labelItem.setX(x);
                    int y = Integer.valueOf(componentsBean.getCoordY());
                    labelItem.setY(y);
                    int w = lb.getWidth();
                    int h = lb.getHeight();
                    labelItem.setWidth(w);
                    labelItem.setHeight(h);
                    labelItem.setComponentId(componentsBean.getId());
                    labelBoard.getLabelItems().add(labelItem);
                }
            }

//            analysis(context, labelBoard, draw_board, div);

//            for (LabelText labelText : labelBoard.getLabelTexts()) {
//                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
//                String id = String.valueOf(System.currentTimeMillis());
//                DebugLog.e("id====" + id);
//                componentsBean.setId(id);
//                componentsBean.setComponentTypeId("1");
//                componentsBean.setComponentContent(labelText.getComponentContent());
//                componentsBean.setPrefix(labelText.getPrefix());
//                componentsBean.setSuffix(labelText.getSuffix());
//                componentsBean.setSize(String.valueOf(labelText.getFontSize()));
//                componentsBean.setAlignType(labelText.getAlignType());
//                componentsBean.setCoordX((int) labelText.getX());
//                componentsBean.setCoordY((int) labelText.getY());
//                componentsBean.setFontBold(labelText.isBold() ? "0" : "1");
//                componentsBean.setComponentWidth((int) labelText.getWidth());
//                componentsBean.setComponentHeight((int) labelText.getHeight());
//                componentsBean.setContentSource(labelText.getContentSource());
//                componentsBeanList.add(componentsBean);
//            }
//            for (LabelBarcode labelBarcode : labelBoard.getLabelBarcodes()) {
//                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
//                String id = String.valueOf(System.currentTimeMillis());
//                DebugLog.e("id====" + id);
//                componentsBean.setId(id);
//                componentsBean.setComponentTypeId("2");
//                componentsBean.setCoordX((int) labelBarcode.getX());
//                componentsBean.setCoordY((int) labelBarcode.getY());
//                componentsBean.setComponentWidth((int) labelBarcode.getWidth());
//                componentsBean.setComponentHeight((int) labelBarcode.getHeight());
//                componentsBean.setEncodingType(labelBarcode.getEncodeType());
//                componentsBean.setContentSource(labelBarcode.getContentSource());
//                componentsBeanList.add(componentsBean);
//            }
//            for (LabelQRCode labelQRCode : labelBoard.getLabelQRCodes()) {
//                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
//                String id = String.valueOf(System.currentTimeMillis());
//                DebugLog.e("id====" + id);
//                componentsBean.setId(id);
//                componentsBean.setComponentTypeId("3");
//                componentsBean.setCoordX((int) labelQRCode.getX());
//                componentsBean.setCoordY((int) labelQRCode.getY());
//                componentsBean.setComponentWidth((int) labelQRCode.getWidth());
//                componentsBean.setComponentHeight((int) labelQRCode.getHeight());
//                componentsBean.setEncodingType(labelQRCode.getEncodeType());
//                componentsBean.setContentSource(labelQRCode.getContentSource());
//                componentsBeanList.add(componentsBean);
//
//            }
//            for (LabelShape labelShape : labelBoard.getLabelShapes()) {
//                ModleDTO.ComponentsBean componentsBean = new ModleDTO.ComponentsBean();
//                String id = String.valueOf(System.currentTimeMillis());
//                DebugLog.e("id====" + id);
//                componentsBean.setId(id);
//                componentsBean.setComponentTypeId("4");
//                componentsBean.setCoordX((int) labelShape.getX());
//                componentsBean.setCoordY((int) labelShape.getY());
//                componentsBean.setComponentWidth((int) labelShape.getWidth());
//                componentsBean.setComponentHeight((int) labelShape.getHeight());
//                componentsBeanList.add(componentsBean);
//            }
//            for (LabelItem labelItem : labelBoard.getLabelItems()) {
//            }
        }
        return labelBoard;
    }


    /**
     * 生成一个1 到 1000之间的随机数(不包含1000的随机数)
     *
     * @return
     */
    public static int getNum() {
        Random random = new Random();
        return random.nextInt(1000 - 1) + 1;
    }
}
