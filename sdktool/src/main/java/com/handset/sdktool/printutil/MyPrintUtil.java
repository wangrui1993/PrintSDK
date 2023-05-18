package com.handset.sdktool.printutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.sdktool.R;
import com.handset.sdktool.data.BusinessData;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.event.Label;
import com.handset.sdktool.event.LabelBoard;
import com.handset.sdktool.printer.sunmi.SunmiPrintHelper;
import com.handset.sdktool.util.CalculationUtil;
import com.handset.sdktool.util.DebugLog;
import com.handset.sdktool.util.Device;
import com.handset.sdktool.util.DeviceUtil;
import com.handset.sdktool.util.LabelBoardAnalysisUtil;
import com.jz.zabersdk.bean.SteelPipeBarInfoBean;
import com.jz.zabersdk.util.ConnectState;
import com.jz.zabersdk.util.PrinterControllerBitmap;
import com.jz.zabersdk.util.PrinterControllerBitmap2;
import com.jz.zabersdk.util.SocketPrintBitmapController;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cpcl.PrinterHelper;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:作用描述
 */
public class MyPrintUtil {

    private ModleDTO modleDTO;
    private DisplayMetrics displayMetrics;

    //    public MyPrintUtil(ModleDTO modleDTO) {
//        this.modleDTO = modleDTO;
//    }
    public MyPrintUtil(ModleDTO modleDTO, DisplayMetrics displayMetrics) {
        this.modleDTO = modleDTO;
        this.displayMetrics = displayMetrics;
    }

    public void preview(ImageView imageView) {

        if (modleDTO == null || modleDTO.getTemplate() == null) {
            return;
        }
        if (modleDTO.getTemplate().getPrinterId().equals("1")) {//斑马打印机
            DeviceUtil.setDevive(Device.ZEBAR);
        }


        //200dpi 8 dot = 1mm dot-墨点  汉印1mm 8墨点
        int pagew = (int) modleDTO.getTemplate().getWidth() * 8;
        int pageh = PrintDataUtil.getPaperHight(modleDTO.getComponents(), BusinessData.getInstance().getMap(), (int) modleDTO.getTemplate().getHeight() * 8);

//        int pageh = 1000;
//        calculationHeight1(modleDTO.getComponents(),   BusinessData.getInstance().getMap(), 0, 0);
//        pageh = calculationHeight(modleDTO.getComponents(), pageh, 0, BusinessData.getInstance().getMap());
//        setBelowComponentY();
//        imageView.setImageBitmap(printByBitmap1(pagew, pageh));

        Log.e("pageh===", pageh + "===pagew===" + pagew);
        Bitmap bitmap = calculationHeight1(modleDTO.getComponents(), BusinessData.getInstance().getMap(), pagew, pageh);
        List<Bitmap> list = new ArrayList<>();
        list.add(bitmap);
        Log.e("pageh==ss=", new Gson().toJson(BusinessData.getInstance().getMap()));
        imageView.setImageBitmap(bitmap);
        if (modleDTO.getTemplate().getPrinterId().equals("1")) {//ZT410
            Log.e("getPrinterId", "");
            PrinterControllerBitmap2 printerControllerBitmap = new PrinterControllerBitmap2("打印机2", "192.168.31.3", 9100, "ZT410", new PrinterControllerBitmap2.OnConnectStateChanged() {
                @Override
                public void invoke(@NonNull ConnectState connectState, @NonNull String s) {
                    Log.e("getPrinterId", "s--" + s);
                }
            });
            printerControllerBitmap.print(bitmap,
                    1,
                    0,
                    0,
                    new PrinterControllerBitmap2.PrintStatus() {
                        @Override
                        public void onPrintSuccess(int i) {
                            Log.e("onPrintSuccess", "s--" + i);
                        }

                        @Override
                        public void onPrintComplete() {

                        }

                        @Override
                        public void onPrintError(int i, @NonNull String s) {
                            Log.e("onPrintError", "s--" + i);
                        }
                    });
        }
    }


    /**
     * 打印生产报工的标签
     *
     * @param printTime
     * @param datalist
     * @param ip
     * @param port
     * @param deviceModel
     */
    public void printTag(int printTime, List<Map<String, Object>> datalist, String ip, int port, String deviceModel) {
        if (modleDTO == null || modleDTO.getTemplate() == null) {
            return;
        }
        if (modleDTO.getTemplate().getPrinterId().equals("1")) {//斑马打印机
            DeviceUtil.setDevive(Device.ZEBAR);
        }

        //200dpi 8 dot = 1mm dot-墨点  汉印1mm 8墨点
        int pagew = (int) modleDTO.getTemplate().getWidth() * 8;
        int pageh = (int) modleDTO.getTemplate().getHeight() * 8;
        List<Bitmap> list = new ArrayList<>();
        for (Map<String, Object> bean : datalist) {
            for (int i = 0; i < printTime; i++) {
                Bitmap bitmap = calculationHeight1(modleDTO.getComponents(), bean, pagew, pageh);
                list.add(bitmap);
            }
        }
//        Log.e("pageh==ss=", new Gson().toJson(BusinessData.getInstance().getMap()));
//        Log.e("pageh==ss=", new Gson().toJson(BusinessData.getInstance().getMap()));
        toPrint(list, pagew, pageh, ip, port, deviceModel);

    }

    private void toPrint(List<Bitmap> list, int pagew, int pageh, String ip, int port, String deviceModel) {
        if (modleDTO.getTemplate().getPrinterId().equals("1")) {//ZT410
            Log.e("getPrinterId", "");
            PrinterControllerBitmap2 printerControllerBitmap = new PrinterControllerBitmap2("打印机", ip, port, deviceModel, new PrinterControllerBitmap2.OnConnectStateChanged() {
                @Override
                public void invoke(@NonNull ConnectState connectState, @NonNull String s) {
                    Log.e("getPrinterId", "s--" + s);
                }
            });
            printerControllerBitmap.print(list,
                    0,
                    0,
                    new PrinterControllerBitmap2.PrintStatus() {
                        @Override
                        public void onPrintSuccess(int i) {
                            Log.e("onPrintSuccess", "s--" + i);
                        }

                        @Override
                        public void onPrintComplete() {

                        }

                        @Override
                        public void onPrintError(int i, @NonNull String s) {
                            Log.e("onPrintError", "s--" + i);
                        }
                    });
        } else {
            if (DeviceUtil.CURRENTDEVICE.length() > 0) {
                if (DeviceUtil.CURRENTDEVICE.contains(Device.SUNMI)) {
                    SunmiPrintHelper.getInstance().printBitmap(printByBitmap(pagew, pageh));
                    SunmiPrintHelper.getInstance().feedPaper();
                } else if (DeviceUtil.CURRENTDEVICE.contains(Device.HM)) {
                    printXY(pagew, pageh);
                }
            }
        }
    }

//    public void print() {
//        if (modleDTO == null || modleDTO.getTemplate() == null) {
//            return;
//        }
//        if (modleDTO.getTemplate().getPrinterId().equals("1")) {//斑马打印机
//            DeviceUtil.setDevive(Device.ZEBAR);
//        }
//        //200dpi 8 dot = 1mm dot-墨点  汉印1mm 8墨点
//        int pagew = (int) modleDTO.getTemplate().getWidth() * 8;
//        int pageh = (int) modleDTO.getTemplate().getHeight() * 8;
//        pageh = calculationHeight(modleDTO.getComponents(), pageh, 0, BusinessData.getInstance().getMap());
//        setBelowComponentY();
//
//        Log.e("dfdcccsds===", DeviceUtil.CURRENTDEVICE);
//
//        if (DeviceUtil.CURRENTDEVICE.length() > 0) {
//            if (DeviceUtil.CURRENTDEVICE.contains(Device.SUNMI)) {
//                SunmiPrintHelper.getInstance().printBitmap(printByBitmap(pagew, pageh));
//                SunmiPrintHelper.getInstance().feedPaper();
//            } else if (DeviceUtil.CURRENTDEVICE.contains(Device.HM)) {
//                printXY(pagew, pageh);
//            }
//        }
//
//    }


    public Bitmap getPreBitmap() {
        //200dpi 8 dot = 1mm dot-墨点  汉印1mm 8墨点
        int pagew = (int) modleDTO.getTemplate().getWidth() * 8;
        int pageh = PrintDataUtil.getPaperHight(modleDTO.getComponents(), BusinessData.getInstance().getMap(), (int) modleDTO.getTemplate().getHeight() * 8);
        Log.e("pageh===", pageh + "");
        return calculationHeight1(modleDTO.getComponents(), BusinessData.getInstance().getMap(), pagew, pageh);
    }

    public Bitmap getBitmap() {
        //200dpi 8 dot = 1mm dot-墨点  汉印1mm 8墨点
        int pagew = (int) modleDTO.getTemplate().getWidth() * 8;
        int pageh = PrintDataUtil.getPaperHight(modleDTO.getComponents(), BusinessData.getInstance().getMap(), (int) modleDTO.getTemplate().getHeight() * 8);
        Log.e("pageh===", pageh + "===pagew===" + pagew);
        return calculationHeight1(modleDTO.getComponents(), BusinessData.getInstance().getMap(), pagew, pageh);
    }

    public Bitmap calculationHeight1(List<ModleDTO.ComponentsBean> l, Map<String, Object> map_in, int pagew, int pageh) {

        if (l.size() > 0) {
            double bili = l.get(0).getPaperCoordProportion().doubleValue();
            Log.e("11pageh===", pageh + "===11pageh===" + modleDTO.getTemplate().getHeight() * 8);
            pageh = Math.max((int) CalculationUtil.multiplication(Double.valueOf(pageh), bili), (int) (modleDTO.getTemplate().getHeight() * 8));
        }

        Bitmap finalBitmap = Bitmap.createBitmap(pagew, pageh, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(finalBitmap);
        canvas.drawColor(Color.WHITE);

//        Paint textPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
//        textPaint2.setColor(Color.GREEN);
//        textPaint2.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, displayMetrics));
//        textPaint2.setTextAlign(Paint.Align.LEFT);
//        Paint.FontMetrics metric = textPaint2.getFontMetrics();
//        int textHeight = (int) Math.ceil(metric.descent - metric.ascent);
//        int y = (int)(textHeight - metric.descent);
//        canvas.drawText("微軟", 0, y, textPaint2);


        Paint paint = new Paint();
        //线
        Paint paintDashLine = new Paint();
        paintDashLine.setStyle(Paint.Style.STROKE);
        paintDashLine.setColor(Color.BLACK);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);


        paixu(l);
        setElement(l);

//        int ku_Y = -1;
        int yiji_item_leiji_h_inmodle = 0;
        int yiji_list_leiji_height = 0;//一级列表累计高
        int yiji_jian = 0;//如果有两个 或多个列表 需要减去的高度
        //循环中累计的库item的高，就是当前库上面的 库的总高
        //整体组件（其中的一项 是一个component   如：天应泰发货通知单   时间   库列表）
        for (int j = 0; j < l.size(); j++) {

            int ku_Y = -1;
            int ku_item_h_inmodle = 0;
            int ku_list_height = 0;//库列表高

            ModleDTO.ComponentsBean componentsBean = l.get(j);

            double bili = componentsBean.getPaperCoordProportion().doubleValue();
            DebugLog.e("bili=====" + "==bili=" + bili);
            if (componentsBean.getComponentTypeId().equals("5")) {
                ku_Y = componentsBean.getIni_coordY() == -1 ? componentsBean.getCoordY() : componentsBean.getIni_coordY();
                ku_item_h_inmodle = componentsBean.getComponentHeight();
                yiji_item_leiji_h_inmodle = yiji_item_leiji_h_inmodle + componentsBean.getComponentHeight();
                //模板（其中的一项 是一个component   如：仓库名   规格   合同列表）
                List<ModleDTO.ComponentsBean> list = componentsBean.getChlieComponentList();
                //模板列表的数据列表
                List<Map<String, Object>> list_map_ku = (List<Map<String, Object>>) map_in.get(componentsBean.getElement().getElementCode());
                if (list_map_ku != null) {
                    componentsBean.getChlieComponentLists().clear();
                    for (int i = 0; i < list_map_ku.size(); i++) {
                        Map<String, Object> map = list_map_ku.get(i);//代表其中的一个仓库
                        ModleDTO.ComponentsBean componentsBean_ku_item = new ModleDTO.ComponentsBean();
                        componentsBean_ku_item.setComponentHeight(componentsBean.getComponentHeight());
                        componentsBean_ku_item.setElement(componentsBean.getElement());
                        componentsBean_ku_item.setId(componentsBean.getId());
                        //设置数据
                        creatNenData(componentsBean, componentsBean_ku_item, list);

                        //=====================================================================================================合同

                        setElement(list);
                        paixu(list);
                        int erji_item_leiji_h_inmodle = 0;
                        int erji_list_leiji_height = 0;//二级列表累计高

                        for (int j2 = 0; j2 < list.size(); j2++) {
                            //合同模板Y
                            int hetong_Y = -1;
                            //合同列表的高
                            int hetong_list_h = 0;
                            //合同列表在模板中的高
                            int hetong_item_h_inmodle = 0;

                            ModleDTO.ComponentsBean componentsBean2 = list.get(j2);
                            if (componentsBean2.getComponentTypeId().equals("5")) {
                                hetong_item_h_inmodle = componentsBean2.getComponentHeight();
                                erji_item_leiji_h_inmodle = erji_item_leiji_h_inmodle + componentsBean2.getComponentHeight();
                                hetong_Y = componentsBean2.getIni_coordY() == -1 ? componentsBean2.getCoordY() : componentsBean2.getIni_coordY();
                                List<ModleDTO.ComponentsBean> list_hetong = componentsBean2.getChlieComponentList();
                                List<Map<String, Object>> listmap_hetong = (List<Map<String, Object>>) map.get(componentsBean2.getElement().getElementCode());
                                if (listmap_hetong != null) {
                                    componentsBean2.getChlieComponentLists().clear();
                                    for (int i2 = 0; i2 < listmap_hetong.size(); i2++) {
                                        Map<String, Object> map_hetong = listmap_hetong.get(i2);//代表其中的一个合同
                                        ModleDTO.ComponentsBean componentsBean_hetong_item = new ModleDTO.ComponentsBean();
                                        componentsBean_hetong_item.setComponentHeight(componentsBean2.getComponentHeight());
                                        componentsBean_hetong_item.setElement(componentsBean2.getElement());
                                        componentsBean_hetong_item.setId(componentsBean2.getId());
                                        //设置数据
                                        creatNenData(componentsBean2, componentsBean_hetong_item, list_hetong);
                                        componentsBean_hetong_item.setCoordY(hetong_Y);

                                        //=====================================================================================================物料
                                        setElement(list_hetong);
                                        paixu(list_hetong);
                                        //物料模板的Y
                                        int wuliao_Y = -1;
                                        //物料列表高
                                        int wuliao_list_h = 0;
                                        //物料列表在模板中的高
                                        int wuliao_item_h_inmodle = 0;
                                        Log.e("00wuliao_list_hkk=", wuliao_list_h + "===" + map_hetong.get("batchno"));
                                        for (int j3 = 0; j3 < list_hetong.size(); j3++) {
                                            ModleDTO.ComponentsBean componentsBean3 = list_hetong.get(j3);
                                            if (componentsBean3.getComponentTypeId().equals("5")) {//物料 (模板)
                                                wuliao_Y = componentsBean3.getIni_coordY() == -1 ? componentsBean3.getCoordY() : componentsBean3.getIni_coordY();
                                                wuliao_item_h_inmodle = componentsBean3.getComponentHeight();
                                                List<ModleDTO.ComponentsBean> list_wuliao = componentsBean3.getChlieComponentList();
                                                List<Map<String, Object>> listmap_wuliao = (List<Map<String, Object>>) map_hetong.get(componentsBean3.getElement().getElementCode());

                                                if (listmap_wuliao != null) {
                                                    componentsBean3.getChlieComponentLists().clear();
                                                    for (int i3 = 0; i3 < listmap_wuliao.size(); i3++) {
                                                        Map<String, Object> map_wuliao = listmap_wuliao.get(i3);//代表其中的一个合同
                                                        ModleDTO.ComponentsBean componentsBean_wuliao_item = new ModleDTO.ComponentsBean();
                                                        componentsBean_wuliao_item.setComponentHeight(componentsBean3.getComponentHeight());
                                                        componentsBean_wuliao_item.setElement(componentsBean3.getElement());
                                                        componentsBean_wuliao_item.setId(componentsBean3.getId());
                                                        //设置数据
                                                        creatNenData(componentsBean3, componentsBean_wuliao_item, list_wuliao);
                                                        componentsBean_wuliao_item.setCoordY(wuliao_Y);

                                                        //=====================================================================================================下级
                                                        setElement(list_wuliao);
                                                        paixu(list_wuliao);
                                                        //物料模板的Y
                                                        int four_Y = -1;
                                                        //物料列表高
                                                        int four_h = 0;
                                                        //物料列表在模板中的高
                                                        int four_item_h_inmodle = 0;

                                                        for (int j4 = 0; j4 < list_wuliao.size(); j4++) {
                                                            ModleDTO.ComponentsBean componentsBean4 = list_wuliao.get(j4);
                                                            if (componentsBean4.getComponentTypeId().equals("5")) {//物料 (模板)

                                                            } else {
                                                                componentsBean4.setComponentContent(getShowContent(componentsBean4, map_wuliao, i3));
                                                                componentsBean_wuliao_item.getChlieComponentLists().add(componentsBean4);

                                                                if (componentsBean4.getIni_coordY() == -1) {
                                                                    componentsBean4.setIni_coordY(componentsBean4.getCoordY());
                                                                }

                                                                //是组件下面的 就把列表的高加上
//                                                                if (componentsBean4.getCoordY() > wuliao_Y && wuliao_Y > 0) {
                                                                int k_h = ku_list_height - ku_item_h_inmodle >= 0 ? ku_list_height : 0;
                                                                int h_h = hetong_list_h - hetong_item_h_inmodle >= 0 ? hetong_list_h : 0;
                                                                int w_h = wuliao_list_h - wuliao_item_h_inmodle >= 0 ? wuliao_list_h : 0;
                                                                componentsBean4.setCoordY(componentsBean4.getIni_coordY() + k_h + h_h + w_h);
//                                                                }
                                                                DebugLog.e("4--Y=后====" + componentsBean4.getCoordY());

                                                                if (componentsBean4.getComponentTypeId().equals("1")) {
                                                                    printTextBitmap(canvas, paint, componentsBean4, bili, 0);
                                                                } else if (componentsBean4.getComponentTypeId().equals("2")) {
                                                                    printBarBitmap(paint, canvas, componentsBean4, bili, 0);
                                                                } else if (componentsBean4.getComponentTypeId().equals("3")) {
                                                                    printQRBitmap(canvas, componentsBean4, bili, 0);
                                                                } else if (componentsBean4.getComponentTypeId().equals("4")) {
                                                                    printShapeBitmap(canvas, paintDashLine, componentsBean4, bili, 0);
                                                                }

                                                            }
                                                        }


                                                        if (i3 == 0) {
                                                            //coordY+外层item上方的item累计的高
                                                            componentsBean_wuliao_item.setCoordY(componentsBean_wuliao_item.getCoordY() + wuliao_list_h + hetong_list_h + ku_list_height);
                                                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                                                            componentsBean_wuliao_item.setComponentHeight(componentsBean_wuliao_item.getComponentHeight());
                                                        } else {
                                                            //上一个的Y+上一个的高+外层item上方的item累计的高
                                                            componentsBean_wuliao_item.setCoordY(componentsBean_wuliao_item.getCoordY() + wuliao_list_h + hetong_list_h + ku_list_height);
                                                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                                                            componentsBean_wuliao_item.setComponentHeight(componentsBean_wuliao_item.getComponentHeight());
                                                        }
                                                        Log.e("3级检验-H=", "===" + componentsBean_wuliao_item.getComponentHeight());
                                                        Log.e("3级检验-Y=", "===" + componentsBean_wuliao_item.getCoordY());
                                                        //设置物料列表的高度
                                                        wuliao_list_h = wuliao_list_h + componentsBean_wuliao_item.getComponentHeight();
                                                        //TODO 这里应该给仓库bean完善组件信息
                                                        componentsBean_hetong_item.getChlieComponentLists().add(componentsBean_wuliao_item);
                                                        componentsBean3.getChlieComponentLists().add(componentsBean_wuliao_item);
                                                        Log.e("3whatpp=", componentsBean3.getChlieComponentLists().size() + "");
                                                    }
                                                }
                                            } else {
                                                componentsBean3.setComponentContent(getShowContent(componentsBean3, map_hetong, 0));
                                                componentsBean_hetong_item.getChlieComponentLists().add(componentsBean3);
                                                if (componentsBean3.getIni_coordY() == -1) {
                                                    componentsBean3.setIni_coordY(componentsBean3.getCoordY());
                                                }
                                                DebugLog.e("3----------------------Y=前====" + componentsBean3.getComponentContent() + "=CoordY=" + componentsBean3.getIni_coordY() + "===wuliao_Y===" + wuliao_Y);

                                                DebugLog.e("3--Y=外层ku===ku_list_height=" + ku_list_height + "===ku_item_h_inmodle===" + ku_item_h_inmodle);
                                                DebugLog.e("3--Y=外层hetong===hetong_list_h=" + hetong_list_h + "===hetong_item_h_inmodle===" + hetong_item_h_inmodle);
                                                DebugLog.e("3--Y=外层wuliao===wuliao_list_h=" + wuliao_list_h + "===wuliao_item_h_inmodle===" + wuliao_item_h_inmodle);
                                                int k_h = ku_list_height - ku_item_h_inmodle >= 0 ? ku_list_height : 0;
                                                int h_h = hetong_list_h - hetong_item_h_inmodle >= 0 ? hetong_list_h : 0;
                                                int w_h = wuliao_list_h - wuliao_item_h_inmodle >= 0 ? wuliao_list_h : 0;
                                                int addH = k_h + h_h;

                                                //是组件下面的 就把列表的高加上
                                                if (componentsBean3.getIni_coordY() > wuliao_Y && wuliao_Y > 0) {
                                                    addH = addH + wuliao_list_h - wuliao_item_h_inmodle;
                                                }
                                                componentsBean3.setCoordY(componentsBean3.getIni_coordY() + addH);
                                                DebugLog.e("3--Y=后====" + componentsBean3.getCoordY());


                                                if (componentsBean3.getComponentTypeId().equals("1")) {
                                                    printTextBitmap(canvas, paint, componentsBean3, bili, 0);
                                                } else if (componentsBean3.getComponentTypeId().equals("2")) {
                                                    printBarBitmap(paint, canvas, componentsBean3, bili, 0);
                                                } else if (componentsBean3.getComponentTypeId().equals("3")) {
                                                    printQRBitmap(canvas, componentsBean3, bili, 0);
                                                } else if (componentsBean3.getComponentTypeId().equals("4")) {
                                                    printShapeBitmap(canvas, paintDashLine, componentsBean3, bili, 0);
                                                }

                                            }
                                        }

                                        if (i2 == 0) {
                                            //coordY+外层item上方的item累计的高
                                            componentsBean_hetong_item.setCoordY(componentsBean_hetong_item.getCoordY() + ku_list_height);
                                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                                            componentsBean_hetong_item.setComponentHeight(componentsBean_hetong_item.getComponentHeight() + wuliao_list_h - wuliao_item_h_inmodle);
                                        } else {
                                            //上一个的Y+上一个的高+外层item上方的item累计的高
                                            componentsBean_hetong_item.setCoordY(componentsBean_hetong_item.getCoordY() + ku_list_height + hetong_list_h);
                                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                                            componentsBean_hetong_item.setComponentHeight(componentsBean_hetong_item.getComponentHeight() + wuliao_list_h - wuliao_item_h_inmodle);

                                        }
                                        Log.e("2级检验-H=", "===" + componentsBean_hetong_item.getComponentHeight());
                                        Log.e("2级检验-Y=", "===" + componentsBean_hetong_item.getCoordY());
                                        hetong_list_h = hetong_list_h + componentsBean_hetong_item.getComponentHeight();
                                        erji_list_leiji_height = erji_list_leiji_height + componentsBean_hetong_item.getComponentHeight();
                                        //TODO 这里应该给仓库bean完善组件信息
                                        componentsBean_ku_item.getChlieComponentLists().add(componentsBean_hetong_item);
                                        componentsBean2.getChlieComponentLists().add(componentsBean_hetong_item);
                                        Log.e("2whatpp=", componentsBean2.getChlieComponentLists().size() + "");
                                    }
                                }
                            } else {
                                componentsBean2.setComponentContent(getShowContent(componentsBean2, map, i));
                                Log.e("1whatpp=", componentsBean.getChlieComponentLists().size() + "");
                                componentsBean_ku_item.getChlieComponentLists().add(componentsBean2);


                                if (componentsBean2.getIni_coordY() == -1) {
                                    componentsBean2.setIni_coordY(componentsBean2.getCoordY());
                                }
                                DebugLog.e("2---------------------Y=前====" + componentsBean2.getComponentContent() + "=CoordY=" + componentsBean2.getIni_coordY() + "===hetong_Y===" + hetong_Y);

                                DebugLog.e("2--Y=外层ku===ku_list_height=" + ku_list_height + "===ku_item_h_inmodle===" + ku_item_h_inmodle);
                                DebugLog.e("2--Y=外层hetong===hetong_list_h=" + hetong_list_h + "===hetong_item_h_inmodle===" + hetong_item_h_inmodle);

                                int k_h = yiji_list_leiji_height - yiji_item_leiji_h_inmodle >= 0 ? yiji_list_leiji_height : 0;
                                DebugLog.e("k_h=k_h====" + k_h);
                                int h_h = hetong_list_h - hetong_item_h_inmodle >= 0 ? hetong_list_h : 0;
                                int addH = k_h;
                                //是组件下面的 就把列表的高加上
//                                if (componentsBean2.getIni_coordY() > hetong_Y && hetong_Y > 0) {
                                addH = addH + erji_list_leiji_height - erji_item_leiji_h_inmodle - yiji_jian;
//                                }
                                componentsBean2.setCoordY(componentsBean2.getIni_coordY() + addH);//+ h_h   //TODO 加不加合同高取决于Y轴了  ，这里一定比较iniY
                                DebugLog.e("2--Y=后====" + componentsBean2.getCoordY());

                                componentsBean2.setComponentContent(PrintDataUtil.getShowContent(componentsBean2, map, 0));

                                if (componentsBean2.getComponentTypeId().equals("1")) {
                                    printTextBitmap(canvas, paint, componentsBean2, bili, 0);
                                } else if (componentsBean2.getComponentTypeId().equals("2")) {
                                    printBarBitmap(paint, canvas, componentsBean2, bili, 0);
                                } else if (componentsBean2.getComponentTypeId().equals("3")) {
                                    printQRBitmap(canvas, componentsBean2, bili, 0);
                                } else if (componentsBean2.getComponentTypeId().equals("4")) {
                                    printShapeBitmap(canvas, paintDashLine, componentsBean2, bili, 0);
                                }

                            }
                        }

                        if (i == 0) {
                            //coordY+外层item上方的item累计的高
                            componentsBean_ku_item.setCoordY(componentsBean_ku_item.getCoordY());
                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                            componentsBean_ku_item.setComponentHeight(componentsBean_ku_item.getComponentHeight() + erji_list_leiji_height - erji_item_leiji_h_inmodle);//第一个仓库组件的高 =本高+列表高-一项列表的高
                        } else {
                            //上一个的Y+上一个的高
                            componentsBean_ku_item.setCoordY(componentsBean.getChlieComponentLists().get(i - 1).getCoordY() + componentsBean.getChlieComponentLists().get(i - 1).getComponentHeight());
                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                            componentsBean_ku_item.setComponentHeight(componentsBean_ku_item.getComponentHeight() + erji_list_leiji_height - erji_item_leiji_h_inmodle);//第二个仓库组件的高 =本高+列表高-一项列表的高
                        }
                        //此列表总高 最后一个item的高加最后一个item 的Y轴
//                        if (i == listmap_ku.size() - 1) {
//                            ku_list_height = componentsBean_ku_item.getCoordY() + componentsBean_ku_item.getComponentHeight();
//                        }
                        ku_list_height = ku_list_height + componentsBean_ku_item.getComponentHeight();
                        yiji_list_leiji_height = yiji_list_leiji_height + componentsBean_ku_item.getComponentHeight();
                        Log.e("1级检验-H=", "===" + componentsBean_ku_item.getComponentHeight());
                        Log.e("1级检验-Y=", "===" + componentsBean_ku_item.getCoordY());

                        //TODO 这里应该给仓库bean完善组件信息
                        componentsBean.getChlieComponentLists().add(componentsBean_ku_item);
                        Log.e("1whatpp=", componentsBean.getChlieComponentLists().size() + "");
                    }

                }
                if (componentsBean.getIni_coordY() == -1) {
                    componentsBean.setIni_coordY(componentsBean.getCoordY());
                }
                int addH = 0;
                addH = addH + yiji_list_leiji_height - yiji_item_leiji_h_inmodle;
                componentsBean.setCoordY(componentsBean.getIni_coordY() + addH);
                yiji_jian = yiji_jian + componentsBean.getComponentHeight();
            } else {
                if (componentsBean.getIni_coordY() == -1) {
                    componentsBean.setIni_coordY(componentsBean.getCoordY());
                }
                int addH = 0;

                //是组件下面的 就把列表的高加上
//                if (componentsBean.getIni_coordY() > ku_Y && ku_Y > 0) {
                addH = addH + yiji_list_leiji_height - yiji_item_leiji_h_inmodle;
//                }
                componentsBean.setCoordY(componentsBean.getIni_coordY() + addH);
                if (componentsBean.getId().equals("1670471746084118") || componentsBean.getId().equals("167227999187212865")) {//联系人
                    DebugLog.e("componentsBean_getId4====" + addH + "==" + yiji_item_leiji_h_inmodle + "==" + componentsBean.getIni_coordY() + "==" + componentsBean.getCoordY());
//                    componentsBean.setCoordY(yiji_item_leiji_h_inmodle+componentsBean.getIni_coordY());
                }
                componentsBean.setComponentContent(getShowContent(componentsBean, map_in, 0));
                componentsBean.setComponentContent(getShowContent(componentsBean, map_in, 0));
                if (componentsBean.getComponentTypeId().equals("1")) {
                    componentsBean.setComponentContent(getShowContent(componentsBean, map_in, 0));
                    printTextBitmap(canvas, textPaint, componentsBean, bili, 0);
                } else if (componentsBean.getComponentTypeId().equals("2")) {
                    componentsBean.setComponentContent(getShowContent(componentsBean, map_in, 0));
                    printBarBitmap(paint, canvas, componentsBean, bili, 0);
                } else if (componentsBean.getComponentTypeId().equals("3")) {
                    componentsBean.setComponentContent(getShowContent(componentsBean, map_in, 0));
                    printQRBitmap(canvas, componentsBean, bili, 0);
                } else if (componentsBean.getComponentTypeId().equals("4")) {
                    printShapeBitmap(canvas, paintDashLine, componentsBean, bili, 0);
                }


                if (componentsBean.getId().equals("167227999187212865")) {//底部
                    DebugLog.e("componentsBean_getId2====" + componentsBean.getIni_coordY() + "===getCoordY===" + componentsBean.getCoordY());
                }
                if (componentsBean.getId().equals("1670471746084118")) {//联系人
                    DebugLog.e("componentsBean_getId3====" + componentsBean.getIni_coordY() + "===getCoordY===" + componentsBean.getCoordY());
                }
            }

        }
        canvas.save();
        canvas.restore();
        finalBitmap = compressQuality(finalBitmap, 100);
        return finalBitmap;
    }


    private int setYH1(Canvas canvas, List<ModleDTO.ComponentsBean> list, Map<String, Object> map, int above_list_h, ModleDTO.ComponentsBean componentsBean_ku_item) {
        double bili = 0.445;
        int pulsHeight = 0;
        Paint paint = new Paint();
        //线
        Paint paintDashLine = new Paint();
        paintDashLine.setStyle(Paint.Style.STROKE);
        paintDashLine.setColor(Color.BLACK);


        PrintDataUtil.setElement(list);
        PrintDataUtil.paixu(list);
        //合同模板Y
        int hetong_Y = -1;
        //合同列表的高
        int hetong_list_h = 0;
        Log.e("00hetong_list_hkk=", hetong_list_h + "===" + map.get("stockName"));
        //合同列表在模板中的高
        int hetong_item_h_inmodle = 0;
        for (int j2 = 0; j2 < list.size(); j2++) {
            ModleDTO.ComponentsBean componentsBean2 = list.get(j2);
//            if (componentsBean2.getComponentTypeId().equals("5")) {
//                Log.e("aiai==y=", "===" + componentsBean2.getCoordY());
//                Log.e("aiai==h=", "===" + componentsBean2.getComponentHeight());
//                componentsBean2.setComponentHeight(600);//TODO 这里模板传入的数据有问题，需要改高
//                componentsBean2.setCoordY(140);//TODO 这里模板传入的数据有问题，需要改Y
//            } else {
//                if (componentsBean2.getElement() == null || componentsBean2.getElement().getElementCode() == null) {
//                    Log.e("kanjieguoba===", "===" + componentsBean2.getCoordY() + "===" + componentsBean2.getComponentContent());
//                } else {
//                    Log.e("kanjieguoba===", "===" + componentsBean2.getCoordY() + "===" + componentsBean2.getElement().getElementCode() + "===" + componentsBean2.getComponentContent());
//                }
//            }

            if (componentsBean2.getComponentTypeId().equals("5")) {
                hetong_item_h_inmodle = componentsBean2.getComponentHeight();
                hetong_Y = componentsBean2.getCoordY();
                List<ModleDTO.ComponentsBean> list_hetong = componentsBean2.getChlieComponentList();
                List<Map<String, Object>> listmap_hetong = (List<Map<String, Object>>) map.get(componentsBean2.getElement().getElementCode());
                if (listmap_hetong != null) {
                    componentsBean2.getChlieComponentLists().clear();
                    for (int i2 = 0; i2 < listmap_hetong.size(); i2++) {
                        Map<String, Object> map_hetong = listmap_hetong.get(i2);//代表其中的一个合同
                        ModleDTO.ComponentsBean componentsBean_hetong_item = new ModleDTO.ComponentsBean();
                        componentsBean_hetong_item.setComponentHeight(componentsBean2.getComponentHeight());
                        componentsBean_hetong_item.setElement(componentsBean2.getElement());
                        componentsBean_hetong_item.setId(componentsBean2.getId());
                        //设置数据
                        PrintDataUtil.creatNenData2(componentsBean2, componentsBean_hetong_item, list_hetong);
                        componentsBean_hetong_item.setCoordY(hetong_Y);

                        if (i2 == 0) {
                            //coordY+外层item上方的item累计的高
                            componentsBean_hetong_item.setCoordY(componentsBean_hetong_item.getCoordY() + above_list_h);
                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                            componentsBean_hetong_item.setComponentHeight(componentsBean_hetong_item.getComponentHeight() + setYH1(canvas, list_hetong, map_hetong, hetong_list_h + above_list_h, componentsBean_hetong_item));
                        } else {
                            //上一个的Y+上一个的高+外层item上方的item累计的高
                            componentsBean_hetong_item.setCoordY(componentsBean_hetong_item.getCoordY() + above_list_h + hetong_list_h);
                            //本高+列表高-一项列表的高 //TODO 这里只考虑了一个列表，没考虑多个同级别列表 需要优化
                            componentsBean_hetong_item.setComponentHeight(componentsBean_hetong_item.getComponentHeight() + setYH1(canvas, list_hetong, map_hetong, hetong_list_h + above_list_h, componentsBean_hetong_item));

                        }
                        Log.e("2级检验-H=", "===" + componentsBean_hetong_item.getComponentHeight());
                        Log.e("2级检验-Y=", "===" + componentsBean_hetong_item.getCoordY());
                        hetong_list_h = hetong_list_h + componentsBean_hetong_item.getComponentHeight();

                        //TODO 这里应该给仓库bean完善组件信息
                        componentsBean_ku_item.getChlieComponentLists().add(componentsBean_hetong_item);
                        componentsBean2.getChlieComponentLists().add(componentsBean_hetong_item);
                        Log.e("2whatpp=", componentsBean2.getChlieComponentLists().size() + "");
                    }
                }
            } else {
                if (!componentsBean2.getComponentTypeId().equals("4")) {
                    componentsBean2.setComponentContent(getShowContent(componentsBean2, map, 0));
                }
                Log.e("11scxxxc===", "==Y轴=" + componentsBean2.getCoordY() + "==高度=" + componentsBean2.getComponentHeight() + "==内容=" + componentsBean2.getComponentContent() + "---shanggao--" + above_list_h + "---biaogao--" + hetong_list_h);
                //是组件下面的 就把列表的高加上
//                if (componentsBean2.getCoordY() > hetong_Y && hetong_Y > 0) {
                componentsBean2.setCoordY(componentsBean2.getCoordY() + above_list_h + hetong_list_h);
//                }
                Log.e("2scxxxc===", componentsBean2.getCoordY() + "===" + above_list_h + "===" + hetong_list_h);


                componentsBean2.setComponentContent(PrintDataUtil.getShowContent(componentsBean2, map, 0));
                componentsBean_ku_item.getChlieComponentLists().add(componentsBean2);

                if (componentsBean2.getComponentTypeId().equals("1")) {
                    printTextBitmap(canvas, paint, componentsBean2, bili, pulsHeight);
                } else if (componentsBean2.getComponentTypeId().equals("2")) {
                    printBarBitmap(paint, canvas, componentsBean2, bili, pulsHeight);
                } else if (componentsBean2.getComponentTypeId().equals("3")) {
                    printQRBitmap(canvas, componentsBean2, bili, pulsHeight);
                } else if (componentsBean2.getComponentTypeId().equals("4")) {
                    printShapeBitmap(canvas, paintDashLine, componentsBean2, bili, pulsHeight);
                }

            }
        }
        return hetong_list_h - hetong_item_h_inmodle;
    }


    /**
     * Bitmap打印机
     */
    public Bitmap printByBitmap1(int pagew, int pageh) {

        Bitmap finalBitmap = Bitmap.createBitmap(pagew, pageh, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(finalBitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        //线
        Paint paintDashLine = new Paint();
        paintDashLine.setStyle(Paint.Style.STROKE);
        paintDashLine.setColor(Color.BLACK);


        for (ModleDTO.ComponentsBean componentsBean : modleDTO.getComponents()) {
            Log.e("收到===111", componentsBean.getId());
            if (componentsBean.getComponentTypeId().equals("5")) {
                Log.e("listdata===", componentsBean.getComponentContent());
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
//                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);
                List<ModleDTO.ComponentsBean> list = componentsBean.getChlieComponentList();


                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);
                Log.e("收到===", new Gson().toJson(componentsBean.getElement().getElementCode()));
                //listmap根据 componentsBean的 elementCode 从getMap里取真实数据出来
                List<Map<String, Object>> listmap = (List<Map<String, Object>>) BusinessData.getInstance().getMap().get(componentsBean.getElement().getElementCode());
                if (listmap != null) {
                    Log.e("收到===", new Gson().toJson(list));
                    Log.e("收到==listmap=", new Gson().toJson(listmap));

                    int position = 0;
                    for (Map<String, Object> map : listmap) {
                        printAnalysisBitmap1(canvas, list, componentsBean, map, position, componentsBean.getComponentHeight());
                        position++;
                    }
                }

            } else if (componentsBean.getComponentTypeId().equals("1")) {
                double bili = componentsBean.getPaperCoordProportion().doubleValue();
                componentsBean.setComponentContent(getShowContent(componentsBean, BusinessData.getInstance().getMap(), 0));
                printTextBitmap(canvas, paint, componentsBean, bili, 0);
            } else if (componentsBean.getComponentTypeId().equals("2")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, BusinessData.getInstance().getMap(), 0));
                double bili = componentsBean.getPaperCoordProportion().doubleValue();
                printBarBitmap(paint, canvas, componentsBean, bili, 0);
            } else if (componentsBean.getComponentTypeId().equals("3")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, BusinessData.getInstance().getMap(), 0));
                double bili = componentsBean.getPaperCoordProportion().doubleValue();
                printQRBitmap(canvas, componentsBean, bili, 0);
            } else if (componentsBean.getComponentTypeId().equals("4")) {
                double bili = componentsBean.getPaperCoordProportion().doubleValue();
                printShapeBitmap(canvas, paintDashLine, componentsBean, bili, 0);
            }
        }
        canvas.save();
        canvas.restore();
        finalBitmap = compressQuality(finalBitmap, 100);
        return finalBitmap;
    }

    private void printAnalysisBitmap1(Canvas canvas, List<ModleDTO.ComponentsBean> list0, ModleDTO.ComponentsBean componentsBean_father, Map<String, Object> map, int position, int itemHeight) {
        double bili = 0.445;
        if (componentsBean_father.getPaperCoordProportion() != null) {
            bili = componentsBean_father.getPaperCoordProportion().doubleValue();
        } else {
            Log.e("benghkui===", componentsBean_father.getComponentContent() + "===" + componentsBean_father.getId());
        }
        int pulsHeight = 0;
        if (position == 0) {
            pulsHeight = 0;
        } else {
            pulsHeight = position * itemHeight;
        }
        Paint paint = new Paint();
        //线
        Paint paintDashLine = new Paint();
        paintDashLine.setStyle(Paint.Style.STROKE);
        paintDashLine.setColor(Color.BLACK);
        for (ModleDTO.ComponentsBean componentsBean : list0) {
            if (componentsBean.getComponentTypeId().equals("5")) {


                Log.e("222listdata===", componentsBean.getComponentContent());
                //把数据拿出来
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                //保存到集合并给列表元素完善数据
//                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);
                List<ModleDTO.ComponentsBean> list = componentsBean.getChlieComponentList();

                for (ModleDTO.ComponentsBean componentsBean1 : list) {
                    Log.e("3caha===", componentsBean1.getComponentTypeId() + componentsBean1.getComponentContent());
                    if (componentsBean1.getComponentTypeId().equals("5")) {
                        LabelBoard lb2 = new Gson().fromJson(componentsBean1.getComponentContent(), LabelBoard.class);
                        Log.e("4caha===", componentsBean1.getComponentContent());
                        ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                        elementsBean.setElementCode(lb2.getElementCode());
                        componentsBean1.setElement(elementsBean);
                    }
                }


                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean(); //这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);
                Log.e("1收到=getElementCode==", componentsBean.getElement().getElementCode());
                Log.e("2收到=getElementCode==", new Gson().toJson(map));
                List<Map<String, Object>> listmap = (List<Map<String, Object>>) map.get(componentsBean.getElement().getElementCode());
                if (listmap != null) {
                    Log.e("2222收到===", new Gson().toJson(list));
                    Log.e("收到==listmap2=", new Gson().toJson(listmap));

                    int position2 = 0;
                    for (Map<String, Object> map2 : listmap) {
                        printAnalysisBitmap1(canvas, list, componentsBean, map2, position2, componentsBean.getComponentHeight());
                    }
                }

            } else if (componentsBean.getComponentTypeId().equals("1")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                printTextBitmap(canvas, paint, componentsBean, bili, pulsHeight);
            } else if (componentsBean.getComponentTypeId().equals("2")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                printBarBitmap(paint, canvas, componentsBean, bili, pulsHeight);
            } else if (componentsBean.getComponentTypeId().equals("3")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                printQRBitmap(canvas, componentsBean, bili, pulsHeight);
            } else if (componentsBean.getComponentTypeId().equals("4")) {
                printShapeBitmap(canvas, paintDashLine, componentsBean, bili, pulsHeight);
            }
        }
    }

    /**
     * Bitmap打印机
     */
    public Bitmap printByBitmap(int pagew, int pageh) {

        Bitmap finalBitmap = Bitmap.createBitmap(pagew, pageh, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(finalBitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        //线
        Paint paintDashLine = new Paint();
        paintDashLine.setStyle(Paint.Style.STROKE);
        paintDashLine.setColor(Color.BLACK);


        for (ModleDTO.ComponentsBean componentsBean : modleDTO.getComponents()) {
            Log.e("收到===111", componentsBean.getId());
            if (componentsBean.getComponentTypeId().equals("5")) {
                Log.e("listdata===", componentsBean.getComponentContent());
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);

                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);

                List<Map<String, Object>> listmap = (List<Map<String, Object>>) BusinessData.getInstance().getMap().get(componentsBean.getElement().getElementCode());
                if (listmap != null) {
                    Log.e("收到===", new Gson().toJson(list));
                    Log.e("收到==listmap=", listmap.toString());


                    int position = 0;
                    for (Map<String, Object> map : listmap) {
                        printAnalysisBitmap(canvas, list, componentsBean, map, position, componentsBean.getComponentHeight());
                        position++;
                    }
                }

            } else if (componentsBean.getComponentTypeId().equals("1")) {
                double bili = componentsBean.getPaperCoordProportion().doubleValue();
                componentsBean.setComponentContent(getShowContent(componentsBean, BusinessData.getInstance().getMap(), 0));
                printTextBitmap(canvas, paint, componentsBean, bili, 0);
            } else if (componentsBean.getComponentTypeId().equals("2")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, BusinessData.getInstance().getMap(), 0));
                double bili = componentsBean.getPaperCoordProportion().doubleValue();
                printBarBitmap(paint, canvas, componentsBean, bili, 0);
            } else if (componentsBean.getComponentTypeId().equals("3")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, BusinessData.getInstance().getMap(), 0));
                double bili = componentsBean.getPaperCoordProportion().doubleValue();
                printQRBitmap(canvas, componentsBean, bili, 0);
            } else if (componentsBean.getComponentTypeId().equals("4")) {
                double bili = componentsBean.getPaperCoordProportion().doubleValue();
                printShapeBitmap(canvas, paintDashLine, componentsBean, bili, 0);
            }
        }
        canvas.save();
        canvas.restore();
        finalBitmap = compressQuality(finalBitmap, 100);
        return finalBitmap;
    }

    /**
     * 根据数据打印(子模板)
     *
     * @throws Exception
     */
    private void printAnalysisBitmap(Canvas canvas, List<ModleDTO.ComponentsBean> list0, ModleDTO.ComponentsBean componentsBean_father, Map<String, Object> map, int position, int itemHeight) {
        double bili = 0.445;
        if (componentsBean_father.getPaperCoordProportion() != null) {
            bili = componentsBean_father.getPaperCoordProportion().doubleValue();
        } else {
            Log.e("benghkui===", componentsBean_father.getComponentContent() + "===" + componentsBean_father.getId());
        }
        int pulsHeight = 0;
        if (position == 0) {
            pulsHeight = 0;
        } else {
            pulsHeight = position * itemHeight;
        }
        Paint paint = new Paint();
        //线
        Paint paintDashLine = new Paint();
        paintDashLine.setStyle(Paint.Style.STROKE);
        paintDashLine.setColor(Color.BLACK);
        for (ModleDTO.ComponentsBean componentsBean : list0) {
            if (componentsBean.getComponentTypeId().equals("5")) {


                Log.e("222listdata===", componentsBean.getComponentContent());
                //把数据拿出来
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                //保存到集合并给列表元素完善数据
//                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb);
                List<ModleDTO.ComponentsBean> list = componentsBean.getChlieComponentList();

                for (ModleDTO.ComponentsBean componentsBean1 : list) {
                    Log.e("3caha===", componentsBean1.getComponentTypeId() + componentsBean1.getComponentContent());
                    if (componentsBean1.getComponentTypeId().equals("5")) {
                        LabelBoard lb2 = new Gson().fromJson(componentsBean1.getComponentContent(), LabelBoard.class);
                        Log.e("4caha===", componentsBean1.getComponentContent());
                        ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                        elementsBean.setElementCode(lb2.getElementCode());
                        componentsBean1.setElement(elementsBean);
                    }
                }


                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean(); //这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);
                Log.e("1收到=getElementCode==", componentsBean.getElement().getElementCode());
                Log.e("2收到=getElementCode==", new Gson().toJson(map));
                List<Map<String, Object>> listmap = (List<Map<String, Object>>) map.get(componentsBean.getElement().getElementCode());
                if (listmap != null) {
                    Log.e("收到===", new Gson().toJson(list));
                    Log.e("收到==listmap2=", new Gson().toJson(listmap));

                    int position2 = 0;
                    for (Map<String, Object> map2 : listmap) {
                        printAnalysisBitmap(canvas, list, componentsBean, map2, position2, componentsBean.getComponentHeight());
                    }
                }

            } else if (componentsBean.getComponentTypeId().equals("1")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                printTextBitmap(canvas, paint, componentsBean, bili, pulsHeight);
            } else if (componentsBean.getComponentTypeId().equals("2")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                printBarBitmap(paint, canvas, componentsBean, bili, pulsHeight);
            } else if (componentsBean.getComponentTypeId().equals("3")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                printQRBitmap(canvas, componentsBean, bili, pulsHeight);
            } else if (componentsBean.getComponentTypeId().equals("4")) {
                printShapeBitmap(canvas, paintDashLine, componentsBean, bili, pulsHeight);
            }
        }
    }


    /**
     * XY打印机
     */
    public void printXY(int pagew, int pageh) {
        try {
            PrinterHelper.printAreaSize("0", String.valueOf(pagew), String.valueOf(pageh), String.valueOf(pageh), "1");
            printAnalysis();
            PrinterHelper.Form();
            PrinterHelper.Print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 循环计算高度 并为组件设置Y轴坐标（未缩放坐标）
     *
     * @param l 要循环的组件list
     * @param h 基础累加高度
     * @return
     */
    private int calculationHeight(List<ModleDTO.ComponentsBean> l, int h, int add_h, Map<String, Object> map_in) {
        for (ModleDTO.ComponentsBean componentsBean : l) {
            if (componentsBean.getComponentTypeId().equals("5")) {
                Log.e("listdata===", componentsBean.getComponentContent());
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                //列表item中元素的集合
                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);

                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);
                //listmap根据 componentsBean的 elementCode 从getMap里取真实数据出来
                List<Map<String, Object>> listmap = (List<Map<String, Object>>) map_in.get(componentsBean.getElement().getElementCode());
                if (listmap != null) {
                    Log.e("收到===", new Gson().toJson(list));
                    Log.e("收到==listmap=", listmap.toString());

                    double bili = 0.445;
                    if (componentsBean.getPaperCoordProportion() != null) {
                        bili = componentsBean.getPaperCoordProportion().doubleValue();
                    }
                    //一个view的高(纸张中)
                    int y_in_paper = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight()), bili);

                    Log.e("setBottomY===y", y_in_paper + "");
                    Log.e("setBottomY===getCoordY", componentsBean.getCoordY() + "");
                    int tottle_height_in_paper = y_in_paper;//list总高(纸张中)

                    //一个view的高(模板中)
                    int y_input = componentsBean.getComponentHeight();
                    int tottle_height_input = y_input;//list总高(模板中)


                    //listmap 列表的数据
                    for (Map<String, Object> map : listmap) {
                        calculationHeight(list, h, y_input, map);
                        Log.e("m-calculationHeight===", listmap.size() + "");
//                    if (listmap.size() > 1) {
                        tottle_height_in_paper = y_in_paper * listmap.size();//list总高(纸张中)
                        tottle_height_input = y_input * listmap.size();//list总高(模板中)
                        Log.e("setBottomY666=1==", y_input + "==");
                        Log.e("setBottomY666=2==", componentsBean.getCoordY() + "==");
                        Log.e("setBottomY666=3==", tottle_height_input + "==");

                        int top_y = 0;
                        for (ModleDTO.ComponentsBean componentsBeans2 : l) {
                            if (componentsBeans2.getId().equals(componentsBean.getAboveComponentId())) {
                                top_y = componentsBeans2.getBottomY();
                            }
                        }
                        Log.e("setBottomY666=4==", top_y + "==");
                        componentsBean.setBottomY(tottle_height_input + (top_y == 0 ? componentsBean.getCoordY() : top_y));
                        Log.e("setBottomY666=5==", componentsBean.getBottomY() + "==" + componentsBean.getId());
                        Log.e("setBottomY666=6==", listmap.size() + "==");
//                    }
                    }
                    h = h + tottle_height_in_paper - y_in_paper;//页面总高
                    Log.e("calculationHeight===", h + "");
                }
            }
            componentsBean.setCoordY(componentsBean.getCoordY() + add_h);
        }


//        for (ModleDTO.ComponentsBean componentsBean : l) {
//            if (componentsBean.getComponentTypeId().equals("5")) {
//
//                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
//                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);
//                String g=new Gson().toJson(list);
//                Log.e("calculationHeight===", g);
//                if (list.size() > 1) {//判断数据
//                    double bili = componentsBean.getPaperCoordProportion().doubleValue();
//                    int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight()), bili);//一个view的高
//                    Log.e("2calculationHeight===", y + "");
//                    int tottle_height = y * list.size();//总高
//                    Log.e("3calculationHeight===", tottle_height + "");
//                    componentsBean.setTotalHeight(tottle_height);
//                    h = h+tottle_height - y;
//                    Log.e("4calculationHeight===", h + "");
//                }
//                calculationHeight(list, h);
//            }
//        }
        return h;
    }


    /**
     * 根据嵌套关系重新给组件Y轴赋值
     *
     * @param l      组件集合
     * @param map_in 业务数据用于 根据实际数量计算高度
     * @return
     */
//    private int calculationHeight1(List<ModleDTO.ComponentsBean> l, Map<String, Object> map_in, int base_height, int layer) {
//        layer = layer + 1;
//        int leijiaH = 0;
//        int itemh = 0;
//        for (ModleDTO.ComponentsBean componentsBean : l) {
//            if (!componentsBean.getComponentTypeId().equals("5")) {
//                leijiaH = leijiaH + componentsBean.getComponentHeight();
//
//            }
//        }
//
//        for (ModleDTO.ComponentsBean componentsBean : l) {
//            if (componentsBean.getComponentTypeId().equals("5")) {
//                //获取列表中item的集合
//                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
//                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);
//
//                componentsBean.setChlieComponentList(list);
//
//                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
//                elementsBean.setElementCode(lb.getElementCode());
//                componentsBean.setElement(elementsBean);
//
//                //获取列表中item集合对应的数据  listmap根据 componentsBean的 elementCode 从getMap里取真实数据出来
//                List<Map<String, Object>> listmap = (List<Map<String, Object>>) map_in.get(componentsBean.getElement().getElementCode());
//                if (listmap != null) {
//
//                    //循环数据
//                    for (int i = 0; i < listmap.size(); i++) {//循环中的每一项都是一个仓库
//                        Map<String, Object> map = listmap.get(i);
//
//                        if (layer == 0 && i == 0) {
//                            base_height = componentsBean.getCoordY();
//                        }
//                        itemh = itemh + (i * componentsBean.getComponentHeight());
////                        if (layer == 0) {
////                            leijiaY = 0;
////                        } else {
////                            leijiaY = leijiaY + ((i + 1) * componentsBean.getCoordY());
////                        }
////                        leijiaH = leijiaH + (i * componentsBean.getComponentHeight());
//                        leijiaH = leijiaH + calculationHeight1(list, map, base_height, layer);
//                        Log.e("1checha==Y=", componentsBean.getCoordY() + "");
//                        Log.e("1checha==H=", componentsBean.getComponentHeight() + "");
//                    }
//                    componentsBean.setCoordY(base_height + componentsBean.getCoordY() + itemh);
//                }
//
//            }else {
//                componentsBean.setCoordY(base_height + componentsBean.getCoordY() + itemh);
//            }
//
//            if (layer == 4) {
//                Log.e("44dsjkfjsdk===", componentsBean.getComponentContent() + "==y==" + componentsBean.getCoordY());
//            } else if (layer == 3) {
//                Log.e("33dsjkfjsdk===", componentsBean.getComponentContent() + "==y==" + componentsBean.getCoordY());
//            } else if (layer == 2) {
//                Log.e("22dsjkfjsdk===", componentsBean.getComponentContent() + "==y==" + componentsBean.getCoordY());
//            }
//        }
//
////        for(ModleDTO.ComponentsBean componentsBean : l){
////            if(layer==4){
////                Log.e("44dsjkfjsdk===",componentsBean.getComponentContent()+"==y=="+componentsBean.getCoordY());
////            } if(layer==3){
////                Log.e("33dsjkfjsdk===",componentsBean.getComponentContent()+"==y=="+componentsBean.getCoordY());
////            }if(layer==2){
////                Log.e("22dsjkfjsdk===",componentsBean.getComponentContent()+"==y=="+componentsBean.getCoordY());
////            }
////        }
//        return leijiaH;
//    }
    private int calculationHeight2(List<ModleDTO.ComponentsBean> l, int y, int h, Map<String, Object> map_in) {
        int list_h = 0;
        for (ModleDTO.ComponentsBean componentsBean : l) {
            if (componentsBean.getComponentTypeId().equals("5")) {

                //获取列表中item的集合
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);


                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);

                //获取列表中item集合对应的数据  listmap根据 componentsBean的 elementCode 从getMap里取真实数据出来
                List<Map<String, Object>> listmap = (List<Map<String, Object>>) map_in.get(componentsBean.getElement().getElementCode());
                Log.e("2checha===", new Gson().toJson(listmap));
                if (listmap != null) {
                    //循环数据
                    int leijiaY = 0;
                    int leijiaH = 0;
                    for (int i = 0; i < listmap.size(); i++) {
                        if (i == 0) {
                            Log.e("2checha==Y=", componentsBean.getCoordY() + "");
                            Log.e("2checha==H=", componentsBean.getComponentHeight() + "");
                        }
                        Map<String, Object> map = listmap.get(i);
                        leijiaY = leijiaY + ((i + 1) * componentsBean.getCoordY());
                        leijiaH = leijiaH + (i * componentsBean.getComponentHeight());
                        componentsBean.setCoordY(leijiaY + leijiaH);
                        calculationHeight3(list, leijiaY + y, leijiaH + h, map);
                    }
                }
            }
        }
        return list_h;
    }

    private int calculationHeight3(List<ModleDTO.ComponentsBean> l, int y, int h, Map<String, Object> map_in) {
        int list_h = 0;
        for (ModleDTO.ComponentsBean componentsBean : l) {
            if (componentsBean.getComponentTypeId().equals("5")) {
                //获取列表中item的集合
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);


                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);

                //获取列表中item集合对应的数据  listmap根据 componentsBean的 elementCode 从getMap里取真实数据出来
                List<Map<String, Object>> listmap = (List<Map<String, Object>>) map_in.get(componentsBean.getElement().getElementCode());
                list_h = (int) CalculationUtil.multiplication(listmap.size(), componentsBean.getComponentHeight());

                Log.e("3checha===", new Gson().toJson(listmap));

                if (listmap != null) {
                    int leijiaY = 0;
                    int leijiaH = 0;
                    for (int i = 0; i < listmap.size(); i++) {
                        if (i == 0) {
                            Log.e("3checha==Y=", componentsBean.getCoordY() + "");
                            Log.e("3checha==H=", componentsBean.getComponentHeight() + "");
                        }
                        Map<String, Object> map = listmap.get(i);
                        leijiaY = leijiaY + ((i + 1) * componentsBean.getCoordY());
                        leijiaH = leijiaH + (i * componentsBean.getComponentHeight());
                        componentsBean.setCoordY(leijiaY + y + leijiaH + h);
                        Log.e("4checha===", componentsBean.getCoordY() + "==" + componentsBean.getComponentContent());
                        calculationHeight4(list, leijiaY + y, leijiaH + h, map);
                    }
                }
            }
        }
        return list_h;
    }

    private int calculationHeight4(List<ModleDTO.ComponentsBean> l, int y, int h, Map<String, Object> map_in) {
        int list_h = 0;
        for (ModleDTO.ComponentsBean componentsBean : l) {
            if (componentsBean.getComponentTypeId().equals("5")) {
                //获取列表中item的集合
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);


                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);

                //获取列表中item集合对应的数据  listmap根据 componentsBean的 elementCode 从getMap里取真实数据出来
                List<Map<String, Object>> listmap = (List<Map<String, Object>>) map_in.get(componentsBean.getElement().getElementCode());
                list_h = (int) CalculationUtil.multiplication(listmap.size(), componentsBean.getComponentHeight());

                Log.e("5checha===", new Gson().toJson(listmap));
                if (listmap != null) {
                    int leijiaY = 0;
                    int leijiaH = 0;
                    for (int i = 0; i < listmap.size(); i++) {
                        if (i == 0) {
                            Log.e("4checha==Y=", componentsBean.getCoordY() + "");
                            Log.e("4checha==H=", componentsBean.getComponentHeight() + "");
                        }
                        Map<String, Object> map = listmap.get(i);
                        leijiaY = leijiaY + ((i + 1) * componentsBean.getCoordY());
                        leijiaH = leijiaH + (i * componentsBean.getComponentHeight());
                        componentsBean.setCoordY(leijiaY + y + leijiaH + h);

//                    calculationHeight3(list, leijiaY, leijiaH, map);
                    }
                }
            } else {
                Log.e("4checha==Y=", componentsBean.getCoordY() + "");
                Log.e("4checha==H=", componentsBean.getComponentHeight() + "");
//                componentsBean.setCoordY(componentsBean.getCoordY() + y + h);
                componentsBean.setCoordY(362);
                Log.e("6checha===", componentsBean.getCoordY() + "==" + componentsBean.getComponentContent());
            }
        }
        return list_h;
    }
//    private int calculationHeight(List<ModleDTO.ComponentsBean> l, int h) {
//        for (ModleDTO.ComponentsBean componentsBean : l) {
//            if (componentsBean.getComponentTypeId().equals("5")) {
//                Log.e("listdata===", componentsBean.getComponentContent());
//                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
//                //列表item中元素的集合
//                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);
//
//                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
//                elementsBean.setElementCode(lb.getElementCode());
//                componentsBean.setElement(elementsBean);
//
//                List<Map<String, Object>> listmap = (List<Map<String, Object>>) BusinessData.getInstance().getMap().get(componentsBean.getElement().getElementCode());
//                if (listmap != null) {
//                    Log.e("收到===", new Gson().toJson(list));
//                    Log.e("收到==listmap=", listmap.toString());
//
//
//                    double bili = componentsBean.getPaperCoordProportion().doubleValue();
//                    int y_in_paper = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight()), bili);//一个view的高(纸张中)
//
//                    Log.e("setBottomY===y", y_in_paper + "");
//                    Log.e("setBottomY===getCoordY", componentsBean.getCoordY() + "");
//                    int tottle_height_in_paper = y_in_paper;//list总高(纸张中)
//
//
//                    int y_input = componentsBean.getComponentHeight();//一个view的高(模板中)
//                    int tottle_height_input = y_input;//list总高(模板中)
//                    //listmap 列表的数据
//                    for (Map<String, Object> map : listmap) {
//                        calculationHeight(list, h);
//                        Log.e("m-calculationHeight===", listmap.size() + "");
////                    if (listmap.size() > 1) {
//                        tottle_height_in_paper = y_in_paper * listmap.size();//list总高(纸张中)
//                        tottle_height_input = y_input * listmap.size();//list总高(模板中)
//                        Log.e("setBottomY666=1==", y_input + "==");
//                        Log.e("setBottomY666=2==", componentsBean.getCoordY() + "==");
//                        Log.e("setBottomY666=3==", tottle_height_input + "==");
//
//                        int top_y = 0;
//                        for (ModleDTO.ComponentsBean componentsBeans2 : l) {
//                            if (componentsBeans2.getId().equals(componentsBean.getAboveComponentId())) {
//                                top_y = componentsBeans2.getBottomY();
//                            }
//                        }
//                        Log.e("setBottomY666=4==", top_y + "==");
//                        componentsBean.setBottomY(tottle_height_input + (top_y == 0 ? componentsBean.getCoordY() : top_y));
//                        Log.e("setBottomY666=5==", componentsBean.getBottomY() + "==" + componentsBean.getId());
//                        Log.e("setBottomY666=6==", listmap.size() + "==");
////                    }
//                    }
//                    h = h + tottle_height_in_paper - y_in_paper;//页面总高
//                    Log.e("calculationHeight===", h + "");
//                }
//            }
//        }
//
//
////        for (ModleDTO.ComponentsBean componentsBean : l) {
////            if (componentsBean.getComponentTypeId().equals("5")) {
////
////                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
////                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);
////                String g=new Gson().toJson(list);
////                Log.e("calculationHeight===", g);
////                if (list.size() > 1) {//判断数据
////                    double bili = componentsBean.getPaperCoordProportion().doubleValue();
////                    int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight()), bili);//一个view的高
////                    Log.e("2calculationHeight===", y + "");
////                    int tottle_height = y * list.size();//总高
////                    Log.e("3calculationHeight===", tottle_height + "");
////                    componentsBean.setTotalHeight(tottle_height);
////                    h = h+tottle_height - y;
////                    Log.e("4calculationHeight===", h + "");
////                }
////                calculationHeight(list, h);
////            }
////        }
//        return h;
//    }

    private List<ModleDTO.ComponentsBean> setBelowComponentY() {

        List<ModleDTO.ComponentsBean> list = new ArrayList<>();

        for (ModleDTO.ComponentsBean componentsBean : modleDTO.getComponents()) {
            Log.e("1SBC===", componentsBean.getBottomY() + "");
            //如果 有上方组件
            if (componentsBean.getAboveComponentId() != null && componentsBean.getAboveComponentId().length() > 0) {

                Log.e("2SBC===", componentsBean.getAboveComponentId() + "");
                for (ModleDTO.ComponentsBean componentsBean2 : modleDTO.getComponents()) {
                    Log.e("3SBC===", componentsBean2.getId() + "===" + componentsBean.getAboveComponentId()
                            + "===setBottomY===" + componentsBean2.getBottomY());

                    //如果 找到上方组件并且Y坐标有值
                    if (componentsBean.getAboveComponentId().equals(componentsBean2.getId())) {
                        if (componentsBean2.getBottomY() > 0) {
                            componentsBean.setCoordY(componentsBean2.getBottomY());
                            componentsBean.setBottomY(componentsBean.getCoordY() + componentsBean.getComponentHeight());
                            list.add(componentsBean);
                            Log.e("4SBC===", componentsBean.getCoordY() + "===" + componentsBean.getId() + "===" + componentsBean.getAboveComponentId() + "===" + componentsBean2.getId());
                        } else {
//                            componentsBean.setCoordY(componentsBean2.getCoordY());
                        }


                    }

                }

            }
        }
        return list;
    }


    /**
     * 根据数据打印
     *
     * @throws Exception
     */
    private void printAnalysis() throws Exception {
        for (ModleDTO.ComponentsBean componentsBean : modleDTO.getComponents()) {
            Log.e("dfdccc===112", componentsBean.getComponentTypeId());
            if (componentsBean.getComponentTypeId().equals("5")) {
                Log.e("listdata===", componentsBean.getComponentContent());
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);

                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);

                List<Map<String, Object>> listmap = (List<Map<String, Object>>) BusinessData.getInstance().getMap().get(componentsBean.getElement().getElementCode());
                if (listmap != null) {
                    Log.e("收到===", new Gson().toJson(list));
                    Log.e("收到==listmap=", listmap.toString());

                    int position = 0;
//                int y = componentsBean.getCoordY();
                    for (Map<String, Object> map : listmap) {
//                    componentsBean.setCoordY(y);
//                    y = y + y;
                        printAnalysis(list, componentsBean, map, position, componentsBean.getComponentHeight());
                        position++;
                    }
                }
            }
            if (componentsBean.getComponentTypeId().equals("1")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, BusinessData.getInstance().getMap(), 0));
                printText(componentsBean);
            }
            if (componentsBean.getComponentTypeId().equals("2")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, BusinessData.getInstance().getMap(), 0));
                printBar(componentsBean);
            }
            if (componentsBean.getComponentTypeId().equals("3")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, BusinessData.getInstance().getMap(), 0));
                printQR(componentsBean);
            }
            if (componentsBean.getComponentTypeId().equals("4")) {
                printShape(componentsBean);
            }
        }
    }

    /**
     * 根据数据打印二维码
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printQRBitmap(Canvas canvas, ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) {
        int height = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight()), bili);//
        int width = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentWidth()), bili);//
        int x = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX() + DeviceUtil.LEFTPADING), bili);///
        int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight + DeviceUtil.TOPPADING), bili);///
        String data = componentsBean.getComponentContent();//二维码数据  TransformationDataUtil.getInstance().getShowContent(componentsBean)
        DebugLog.e("PrinterHelper===PrintQR==" + data + "==width==" + width + "==height==" + height +
                "===Height()=" + componentsBean.getComponentHeight() + "==Width()==" + componentsBean.getComponentWidth() + "==bili==" + bili);
        Bitmap htmlBitmap = ZxingUtil.generateBitmap(data, width, height, false);
        DebugLog.e("PrinterHelper===PrintQR==" + data + "==y==" + y + "==getCoordY==" + componentsBean.getCoordY() + "==width==" + htmlBitmap.getWidth() + "==height==" + htmlBitmap.getHeight());
        DebugLog.e("3==PrintQR==DeviceUtil.LEFTPADING==" + DeviceUtil.LEFTPADING + "== DeviceUtil.TOPPADING==" + DeviceUtil.TOPPADING
                + "==plusHeight==" + plusHeight);

        canvas.drawBitmap(htmlBitmap, x, y, null);//TODO 这个y轴会不准  貌似大，总之显示的bitmap靠下
    }

    /**
     * 根据数据打印条码
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printBarBitmap(Paint paint, Canvas canvas, ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) {
        if (!isNumeric(componentsBean.getComponentContent())) {
            return;
        }
        int barTextSize = 9;
        int barTextSizeh = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, barTextSize, displayMetrics);
        int height = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight()) - (barTextSizeh * 2), bili);//
        int width = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentWidth()), bili);//
        int x = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX() + DeviceUtil.LEFTPADING), bili);///
        int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight), bili);///
        String data = componentsBean.getComponentContent();//条码数据   TransformationDataUtil.getInstance().getShowContent(componentsBean)
        DebugLog.e("1PrinterHelper===Barcode==" + componentsBean.getComponentWidth() + "====" + componentsBean.getComponentHeight());
        DebugLog.e("2PrinterHelper===Barcode==" + data + "==" + x + "===" + y + "===" + componentsBean.getId());
        DebugLog.e("3PrinterHelper===Barcode==" + width + "====" + height);
        Bitmap codeBitmap = ZxingUtil.getBarCodeWithoutPadding(width, width, height, data);
//        codeBitmap = ImageUtils.scale(codeBitmap, width, height);
        canvas.drawBitmap(codeBitmap, x, y, null);
        componentsBean.setSize(String.valueOf(barTextSize));
        componentsBean.setFontBold("1");
        componentsBean.setSuffix("");
        componentsBean.setPrefix("");
        componentsBean.setAlignType("center");
        printTextBitmap(canvas, paint, componentsBean, bili, plusHeight + height + 10);
    }

    /**
     * 根据数据打印文字
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printTextBitmap(Canvas canvas, Paint textPaint3, ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) {
        Log.e("cddcdzz==", new Gson().toJson(componentsBean));
        Paint textPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint2.setColor(Color.BLACK);
        textPaint2.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, Integer.valueOf(componentsBean.getSize()), displayMetrics));
        textPaint2.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetrics metric = textPaint2.getFontMetrics();
        int textHeight = (int) Math.ceil(metric.descent - metric.ascent);
        int y2 = (int) (textHeight - metric.descent);

        if (componentsBean.getFontBold().equals("0")) {
            Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
            textPaint2.setTypeface(font);
        } else {
            Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
            textPaint2.setTypeface(font);
        }
//        paint.setTextAlign(Paint.Align.CENTER);
//        if (componentsBean.getAlignType()!=null&&componentsBean.getAlignType().equals("center")) {
//            paint.setTextAlign(Paint.Align.CENTER);
//        } else if (componentsBean.getAlignType()!=null&&componentsBean.getAlignType().equals("right")) {
//            paint.setTextAlign(Paint.Align.RIGHT);
//        } else if (componentsBean.getAlignType()!=null&&componentsBean.getAlignType().equals("left")) {
//            paint.setTextAlign(Paint.Align.LEFT);
//        }
        int x = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX() + DeviceUtil.LEFTPADING), bili);
        int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight + DeviceUtil.TOPPADING), bili);
        String data = componentsBean.getPrefix() + componentsBean.getComponentContent() + componentsBean.getSuffix();
        DebugLog.e("PrinterHelper===printText==" + data + "=y=" + y + "==getCoordY=" + componentsBean.getCoordY() + "====" + componentsBean.getAboveComponentId() + "===" + x + "==bili=" + bili);
        DebugLog.e("PrinterHelper===printText==" + data + "=y=" + y + "==x=" + x + "==plusHeight=" + plusHeight + "=== TOPPADING=" + DeviceUtil.TOPPADING + "==bili=" + bili);
        canvas.drawText(data == null ? "" : data, x, y + y2, textPaint2);
    }


    /**
     * 根据数据打印横线
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printShapeBitmap(Canvas canvas, Paint paintDashLine, ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) {
        int X0 = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX() + DeviceUtil.LEFTPADING), bili);///起始的X坐标。（单位：dot）
        int Y0 = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight), bili);///起始的Y坐标。（单位：dot）

        int X1lineTo = (int) (CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentWidth()), bili));///终结的X坐标。（单位：dot）

        paintDashLine.setStrokeWidth(componentsBean.getComponentHeight() == 0 ? 1 : componentsBean.getComponentHeight());

        Path linePath = new Path();
        linePath.reset();
//        linePath.moveTo(X0, Y0);
//        linePath.lineTo(X1, Y1);
        DebugLog.e("printShapeBitmap===printShape==" + "==Y0=" + Y0 + "==X0=" + X0 + "==X1lineTo=" + X1lineTo + "==plusHeight=" + plusHeight);
        linePath.moveTo(X0, Y0 + componentsBean.getComponentHeight() / 2);
        linePath.lineTo(X1lineTo, Y0 + componentsBean.getComponentHeight() / 2);
        DebugLog.e("printShapeBitmap===printShape==" + componentsBean.toString());
        DebugLog.e("printShapeBitmap===printShape=x=" + componentsBean.getCoordX() + "==y=" + componentsBean.getCoordY() +
                "==Width=" + componentsBean.getComponentWidth() + "===Height=" + componentsBean.getComponentHeight() + "==" + paintDashLine.getStrokeWidth());
        canvas.drawPath(linePath, paintDashLine);
    }

    /**
     * 根据数据打印(子模板)
     *
     * @throws Exception
     */
    private void printAnalysis(List<ModleDTO.ComponentsBean> list0, ModleDTO.ComponentsBean componentsBean_father, Map<String, Object> map, int position, int itemHeight) throws Exception {
        double bili = componentsBean_father.getPaperCoordProportion().doubleValue();
        int pulsHeight = 0;
        if (position == 0) {
            pulsHeight = 0;
        } else {
            pulsHeight = position * itemHeight;
        }

        for (ModleDTO.ComponentsBean componentsBean : list0) {
            if (componentsBean.getComponentTypeId().equals("5")) {

                //把数据拿出来
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                //保存到集合并给列表元素完善数据
                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb);
                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean(); //这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);


                List<Map<String, Object>> listmap = (List<Map<String, Object>>) BusinessData.getInstance().getMap().get(componentsBean.getElement().getElementCode());
                if (listmap != null) {
                    Log.e("收到===", new Gson().toJson(list));
                    Log.e("收到==listmap2=", listmap.toString());

                    int position2 = 0;
//                int y = componentsBean.getCoordY();
                    for (Map<String, Object> map2 : listmap) {
//                    componentsBean.setCoordY(y);
//                    y = y + y;
                        printAnalysis(list, componentsBean, map2, position2, componentsBean.getComponentHeight());

                    }
                }
            } else if (componentsBean.getComponentTypeId().equals("1")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                printText(componentsBean, bili, pulsHeight);
            } else if (componentsBean.getComponentTypeId().equals("2")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                printBar(componentsBean, bili, pulsHeight);
            } else if (componentsBean.getComponentTypeId().equals("3")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                printQR(componentsBean, bili, pulsHeight);
            } else if (componentsBean.getComponentTypeId().equals("4")) {
                printShape(componentsBean, bili, pulsHeight);
            }
        }

    }

    /**
     * 根据数据打印文字
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printText(ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) throws Exception {
        int font = Integer.valueOf(componentsBean.getSize());//16：16x16或8x16，视中英⽂⽽定。  24：24x24或12x24，视中英⽂⽽定。  32：32x32或16x32，由ID3字体宽⾼各放⼤2倍
        if (font < 12) {
            font = 20;
        } else if (font >= 12 && font < 14) {
            font = 3;
        } else if (font >= 14 && font < 24) {
            font = 8;
        } else if (font >= 24) {
            font = 4;
        }
        int x = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX()), bili);
        int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight), bili);
        String data = componentsBean.getPrefix() + componentsBean.getComponentContent() + componentsBean.getSuffix();

        String command = PrinterHelper.TEXT;
        String size = "0";
        DebugLog.e("PrinterHelper===printText==" + data + "==" + y + "===" + componentsBean.getId() + "====" + componentsBean.getAboveComponentId() + "===" + y);
        PrinterHelper.Text(command, String.valueOf(font), size, String.valueOf(x), String.valueOf(y),
                data);
    }

    /**
     * 根据数据打印文字
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printText(ModleDTO.ComponentsBean componentsBean) throws Exception {
        double bili = componentsBean.getPaperCoordProportion().doubleValue();
        printText(componentsBean, bili, 0);
    }

    /**
     * 根据数据打印条码
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printBar(ModleDTO.ComponentsBean componentsBean) throws Exception {
        double bili = componentsBean.getPaperCoordProportion().doubleValue();
        printBar(componentsBean, bili, 0);
    }

    /**
     * 根据数据打印条码
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printBar(ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) throws Exception {
        String command = PrinterHelper.BARCODE;//PrinterHelper.BARCODE：⽔平⽅向 PrinterHelper.VBARCODE：垂直⽅向
        String type = getBarType(componentsBean.getEncodingType());//
        int width = 2;//
        //宽条窄条的⽐例
        //0=1.5:1 , 1=2.0:1 , 2=2.5:1 , 3=3.0:1 , 4=3.5:1 ,
        //20=2.0:1 , 21=2.1:1 , 22=2.2:1 , 23=2.3:1 , 24=2.4:1 , 25=2.5:1
        //26=2.6:1 , 27=2.7:1 , 28=2.8:1 , 29=2.9:1 , 30=3.0:1 ,
        String ratio = "1";//
        int height = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight()), bili);//
        int x = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX()), bili);///
        int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight), bili);///
        boolean undertext = true;//条码下⽅的数据是否可⻅。ture：可⻅，false：不可⻅。
        String number = componentsBean.getComponentContent();//字体的类型 (undertext=true才⽣效)
        String size = "12";//字体的⼤⼩(undertext=true才⽣效)
        String offset = "10";//条码与⽂字间的距离(undertext=true才⽣效)
        String data = componentsBean.getComponentContent();//条码数据   TransformationDataUtil.getInstance().getShowContent(componentsBean)
        DebugLog.e("PrinterHelper===Barcode==" + data + "==" + y + "===" + componentsBean.getId() + "====" + componentsBean.getAboveComponentId() + "===" + y);
        PrinterHelper.Barcode(command, type, String.valueOf(width), ratio,
                String.valueOf(height), String.valueOf(x), String.valueOf(y), undertext, number, size, offset, data);
    }

    /**
     * 根据数据打印二维码
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printQR(ModleDTO.ComponentsBean componentsBean) throws Exception {
        double bili = componentsBean.getPaperCoordProportion().doubleValue();
        printQR(componentsBean, bili, 0);
    }

    /**
     * 根据数据打印二维码
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printQR(ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) throws Exception {
        String command = PrinterHelper.BARCODE;//PrinterHelper.BARCODE：⽔平⽅向  PrinterHelper.VBARCODE：垂直⽅向

        int x = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX()), bili);///
        int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight), bili);///
        String M = "1";//QR的类型：  1：普通类型   2：在类型1的基础上增加了个别的符号
        int U = 6;////单位宽度/模块的单元⾼度,范围是1到32默认为6
        String data = componentsBean.getComponentContent();//二维码数据  TransformationDataUtil.getInstance().getShowContent(componentsBean)
        DebugLog.e("PrinterHelper===PrintQR==" + data + "==x==" + x + "==y==" + y +
                "===Height()=" + componentsBean.getComponentHeight() + "==Width()==" + componentsBean.getComponentWidth() + "==bili==" + bili);
        PrinterHelper.PrintQR(command, String.valueOf(x), String.valueOf(y), M, String.valueOf(U), data);
    }

    /**
     * 根据数据打印横线
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printShape(ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) throws Exception {
        int X0 = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX()), bili);///起始的X坐标。（单位：dot）
        int Y0 = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight), bili);///起始的Y坐标。（单位：dot）

        int X1 = (int) (X0 + CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentWidth()), bili));///终结的X坐标。（单位：dot）
        int Y1 = (int) (Y0 + CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight() + plusHeight), bili));///终结的Y坐标。（单位：dot）

        int width = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight()), bili);///

        PrinterHelper.Line(String.valueOf(X0), String.valueOf(Y0), String.valueOf(X1), String.valueOf(Y1), String.valueOf(width));
    }

    /**
     * 根据数据打印横线
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printShape(ModleDTO.ComponentsBean componentsBean) throws Exception {
        double bili = componentsBean.getPaperCoordProportion().doubleValue();
        printShape(componentsBean, bili, 0);
    }

    public String getBarType(String s) {
        String encodeType = "";
        if (s.equals("CODE128")) {
            encodeType = PrinterHelper.code128;
        }
//        PrinterHelper.UPCA, PrinterHelper.UPCA2, PrinterHelper.UPCA5,
//                PrinterHelper.UPCE, PrinterHelper.UPCE2, PrinterHelper.UPCE5,
//                PrinterHelper.EAN13, PrinterHelper.EAN132, PrinterHelper.EAN135,
//                PrinterHelper.EAN8, PrinterHelper.EAN82, PrinterHelper.EAN85,
//                PrinterHelper.code39, PrinterHelper.code39C, PrinterHelper.F39,
//                PrinterHelper.F39C, PrinterHelper.code93, PrinterHelper.I2OF5,
//                PrinterHelper.I2OF5C, PrinterHelper.I2OF5G, PrinterHelper.code128,
//                PrinterHelper.UCCEAN128, PrinterHelper.CODABAR, PrinterHelper.CODABAR16,
//                PrinterHelper.MSI, PrinterHelper.MSI10, PrinterHelper.MSI1010,
//                PrinterHelper.MSI1110, PrinterHelper.POSTNET, PrinterHelper.FIM
        return encodeType;
    }

    /**
     * 根据数据打印
     *
     * @throws Exception
     */
    public String getShowContent(ModleDTO.ComponentsBean componentsBean, Map<String, Object> map, int position) {
        if (componentsBean.getComponentTypeId().equals("4")) {
            return "";
        }
        String result = componentsBean.getComponentContent();
        Log.e("===sdfsd===2", componentsBean.getComponentContent());
        if (componentsBean.getComponentTypeId().equals("1") || componentsBean.getComponentTypeId().equals("2") || componentsBean.getComponentTypeId().equals("3")) {
            Log.e("===sdfsd===3", componentsBean.getComponentTypeId());
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_TEXT)) {
                Log.e("===sdfsd===4", componentsBean.getComponentContent());
                result = componentsBean.getComponentContent();
                return result;
            }
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_S)) {
                return String.valueOf(position + 1) + ".";
            }
            if (componentsBean.getContentSource().equals(Label.CONTENTSOURCE_E)) {
                if (componentsBean.getElement() != null && componentsBean.getElement().getElementCode() != null) {
                    if (map.get(componentsBean.getElement().getElementCode()) instanceof Integer ||
                            map.get(componentsBean.getElement().getElementCode()) instanceof Double ||
                            map.get(componentsBean.getElement().getElementCode()) instanceof Float) {
                        result = map.get(componentsBean.getElement().getElementCode()).toString();
                    } else {
                        result = (String) map.get(componentsBean.getElement().getElementCode());
                    }

                    if (result == null) {
                        result = componentsBean.getComponentContent() == null ? "" : componentsBean.getComponentContent();
                    }
                    return result;
                } else {
                    Log.e("===sdfsd=null==4", componentsBean.getComponentContent());
                }
                result = componentsBean.getComponentContent();
                return result;
            }
        }
        return result;
    }

    private static Bitmap compressQuality(Bitmap src, int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, quality, os);
        byte[] bytes = os.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void paixu(List<ModleDTO.ComponentsBean> l) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            l.sort(Comparator.comparing(ModleDTO.ComponentsBean::getCoordY));
        }

    }

    private static Gson gson = new Gson();

    public static void setElement(List<ModleDTO.ComponentsBean> l) {
        for (ModleDTO.ComponentsBean componentsBean : l) {
            if (componentsBean.getComponentTypeId().equals("5")) {
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);
                componentsBean.setChlieComponentList(list);
                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);

            }
        }
    }

    public static void creatNenData(ModleDTO.ComponentsBean componentsBean, ModleDTO.ComponentsBean componentsBean_ku_item, List<ModleDTO.ComponentsBean> list) {
        List<ModleDTO.ComponentsBean> businessElementBeanList = gson.fromJson(gson.toJson(list),
                new TypeToken<List<ModleDTO.ComponentsBean>>() {
                }.getType());
        Log.e("1sssccc===", businessElementBeanList.size() + "");
        componentsBean_ku_item.setId(componentsBean.getId());
        componentsBean_ku_item.setComponentContent(componentsBean.getComponentContent());
        componentsBean_ku_item.setComponentTypeId(componentsBean.getComponentTypeId());
        componentsBean_ku_item.setCoordX(componentsBean.getCoordX());
        componentsBean_ku_item.setAlignType(componentsBean.getAlignType());
        componentsBean_ku_item.setAboveComponentId(componentsBean.getAboveComponentId());
    }
}
