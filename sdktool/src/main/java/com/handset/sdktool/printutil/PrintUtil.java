package com.handset.sdktool.printutil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.ImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handset.sdktool.Config;
import com.handset.sdktool.R;
import com.handset.sdktool.bean.PrintPaperBean;
import com.handset.sdktool.data.BusinessData;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.dto.PaperDTO;
import com.handset.sdktool.dto.PrintPaperRelationshipDTO;
import com.handset.sdktool.dto.PrinterDTO;
import com.handset.sdktool.event.Label;
import com.handset.sdktool.event.LabelBoard;
import com.handset.sdktool.net.NetUtil;
import com.handset.sdktool.net.OnResponse;
import com.handset.sdktool.net.base.BaseBean;
import com.handset.sdktool.net.base.Bean;
import com.handset.sdktool.printer.sunmi.SunmiPrintHelper;
import com.handset.sdktool.util.Bluetooth;
import com.handset.sdktool.util.CalculationUtil;
import com.handset.sdktool.util.DebugLog;
import com.handset.sdktool.util.DeviceUtil;
import com.handset.sdktool.util.GetJsonDataUtil;
import com.handset.sdktool.util.LabelBoardAnalysisUtil;
import com.handset.sdktool.util.SharedPreferenceUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpcl.PrinterHelper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import rx.functions.Action1;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:作用描述
 */
public class PrintUtil {

    private ModleDTO modleDTO;

    public PrintUtil(ModleDTO modleDTO) {
        this.modleDTO = modleDTO;
    }

    public void print() {
        //200dpi 8 dot = 1mm dot-墨点  汉印1mm 8墨点
        int pagew = (int) modleDTO.getTemplate().getWidth() * 8;
        int pageh = (int) modleDTO.getTemplate().getHeight() * 8;
        pageh = calculationHeight(modleDTO.getComponents(), pageh);
        setBelowComponentY();

        Log.e("dfdcccsds===", DeviceUtil.CURRENTDEVICE);

        if (DeviceUtil.CURRENTDEVICE.length() > 0) {
            if (DeviceUtil.CURRENTDEVICE.contains(DeviceUtil.SUNMI)) {
                SunmiPrintHelper.getInstance().printBitmap(printByBitmap(pagew, pageh));
                SunmiPrintHelper.getInstance().feedPaper();
            } else if (DeviceUtil.CURRENTDEVICE.contains(DeviceUtil.HM)) {
                printXY(pagew, pageh);
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
                //画笔
                paint.setColor(Color.BLACK);
                paint.setTextSize(Integer.valueOf(componentsBean.getSize()));
                printTextBitmap(canvas, paint, componentsBean, bili, 0);
            } else if (componentsBean.getComponentTypeId().equals("2")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, BusinessData.getInstance().getMap(), 0));
                double bili = componentsBean.getPaperCoordProportion().doubleValue();
                printBarBitmap(canvas, componentsBean, bili, 0);
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
        finalBitmap = compressQuality(finalBitmap, 95);
        return finalBitmap;
    }

    /**
     * 根据数据打印(子模板)
     *
     * @throws Exception
     */
    private void printAnalysisBitmap(Canvas canvas, List<ModleDTO.ComponentsBean> list0, ModleDTO.ComponentsBean componentsBean_father, Map<String, Object> map, int position, int itemHeight) {
        double bili = componentsBean_father.getPaperCoordProportion().doubleValue();
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
                    for (Map<String, Object> map2 : listmap) {
                        printAnalysisBitmap(canvas, list, componentsBean, map2, position2, componentsBean.getComponentHeight());
                    }
                }

            } else if (componentsBean.getComponentTypeId().equals("1")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                //画笔
                paint.setColor(Color.BLACK);
                paint.setTextSize(Integer.valueOf(componentsBean.getSize()));
                printTextBitmap(canvas, paint, componentsBean, bili, pulsHeight);
            } else if (componentsBean.getComponentTypeId().equals("2")) {
                componentsBean.setComponentContent(getShowContent(componentsBean, map, position));
                printBarBitmap(canvas, componentsBean, bili, pulsHeight);
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

    private int calculationHeight(List<ModleDTO.ComponentsBean> l, int h) {
        for (ModleDTO.ComponentsBean componentsBean : l) {
            if (componentsBean.getComponentTypeId().equals("5")) {
                Log.e("listdata===", componentsBean.getComponentContent());
                LabelBoard lb = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);
                //列表item中元素的集合
                List<ModleDTO.ComponentsBean> list = LabelBoardAnalysisUtil.setDataToModle(lb, componentsBean);

                ModleDTO.ComponentsBean.ElementsBean elementsBean = new ModleDTO.ComponentsBean.ElementsBean();//这两行代码 是因为模板的element存不上 我存在了LabelBoard中
                elementsBean.setElementCode(lb.getElementCode());
                componentsBean.setElement(elementsBean);

                List<Map<String, Object>> listmap = (List<Map<String, Object>>) BusinessData.getInstance().getMap().get(componentsBean.getElement().getElementCode());
                if (listmap != null) {
                    Log.e("收到===", new Gson().toJson(list));
                    Log.e("收到==listmap=", listmap.toString());


                    double bili = componentsBean.getPaperCoordProportion().doubleValue();
                    int y_in_paper = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight()), bili);//一个view的高(纸张中)

                    Log.e("setBottomY===y", y_in_paper + "");
                    Log.e("setBottomY===getCoordY", componentsBean.getCoordY() + "");
                    int tottle_height_in_paper = y_in_paper;//list总高(纸张中)


                    int y_input = componentsBean.getComponentHeight();//一个view的高(传入的)
                    int tottle_height_input = y_input;//list总高(传入的)
                    //listmap 列表的数据
                    for (Map<String, Object> map : listmap) {
                        calculationHeight(list, h);
                        Log.e("m-calculationHeight===", listmap.size() + "");
//                    if (listmap.size() > 1) {
                        tottle_height_in_paper = y_in_paper * listmap.size();//list总高(纸张中)
                        tottle_height_input = y_input * listmap.size();//list总高(传入的)
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
        int x = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX()+ DeviceUtil.LEFTPADING), bili);///
        int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight + DeviceUtil.TOPPADING), bili);///
        String data = componentsBean.getComponentContent();//二维码数据  TransformationDataUtil.getInstance().getShowContent(componentsBean)
        DebugLog.e("PrinterHelper===PrintQR==" + data + "==width==" + width + "==height==" + height +
                "===Height()=" + componentsBean.getComponentHeight() + "==Width()==" + componentsBean.getComponentWidth());
        Bitmap htmlBitmap = ZxingUtil.createQRImage(data, width, height);
        canvas.drawBitmap(htmlBitmap, x, y, null);
    }

    /**
     * 根据数据打印条码
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printBarBitmap(Canvas canvas, ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) {
        if (!isNumeric(componentsBean.getComponentContent())) {
            return;
        }
        int height = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentHeight()), bili);//
        int width = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentWidth()), bili);//
        int x = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX()+ DeviceUtil.LEFTPADING), bili);///
        int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight + DeviceUtil.TOPPADING), bili);///
        String data = componentsBean.getComponentContent();//条码数据   TransformationDataUtil.getInstance().getShowContent(componentsBean)
        DebugLog.e("1PrinterHelper===Barcode==" + componentsBean.getComponentWidth() + "====" + componentsBean.getComponentHeight());
        DebugLog.e("2PrinterHelper===Barcode==" + data + "==" + x + "===" + y + "===" + componentsBean.getId());
        DebugLog.e("3PrinterHelper===Barcode==" + width + "====" + height);
        Bitmap codeBitmap = ZxingUtil.getBarCodeWithoutPadding(width, width, height, data);
        codeBitmap = ImageUtils.scale(codeBitmap, width, height);
        canvas.drawBitmap(codeBitmap, x, y, null);
    }

    /**
     * 根据数据打印文字
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printTextBitmap(Canvas canvas, Paint paint, ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) {
        int x = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX()+ DeviceUtil.LEFTPADING), bili);
        int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight + DeviceUtil.TOPPADING), bili);
        String data = componentsBean.getPrefix() + componentsBean.getComponentContent() + componentsBean.getSuffix();
        DebugLog.e("PrinterHelper===printText==" + data + "==" + y + "===" + componentsBean.getId() + "====" + componentsBean.getAboveComponentId() + "===" + x);
        canvas.drawText(data == null ? "" : data, x, y, paint);

    }


    /**
     * 根据数据打印横线
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printShapeBitmap(Canvas canvas, Paint paintDashLine, ModleDTO.ComponentsBean componentsBean, double bili, int plusHeight) {
        int X0 = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX()+ DeviceUtil.LEFTPADING), bili);///起始的X坐标。（单位：dot）
        int Y0 = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight ), bili);///起始的Y坐标。（单位：dot）

        int X1lineTo = (int) (CalculationUtil.multiplication(Double.valueOf(componentsBean.getComponentWidth()), bili));///终结的X坐标。（单位：dot）

        paintDashLine.setStrokeWidth(componentsBean.getComponentHeight()==0?1:componentsBean.getComponentHeight());

        Path linePath = new Path();
        linePath.reset();
//        linePath.moveTo(X0, Y0);
//        linePath.lineTo(X1, Y1);

        linePath.moveTo(X0, Y0);
        linePath.lineTo(X1lineTo, Y0);
        DebugLog.e("printShapeBitmap===printShape==" + componentsBean.toString());
        DebugLog.e("printShapeBitmap===printShape=x=" + componentsBean.getCoordX() + "==y=" + componentsBean.getCoordY() +
                "==Width=" + componentsBean.getComponentWidth() + "===Height=" + componentsBean.getComponentHeight()+"=="+paintDashLine.getStrokeWidth());
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
        DebugLog.e("PrinterHelper===PrintQR==" + data + "==" + y + "===" + componentsBean.getId() + "====" + componentsBean.getAboveComponentId() + "===" + y);
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
                    result = (String) map.get(componentsBean.getElement().getElementCode());
                    if (result == null) {
                        result = componentsBean.getComponentContent() == null ? "" : componentsBean.getComponentContent();
                    }
                    return result;
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
}
