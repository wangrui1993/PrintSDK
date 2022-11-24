package com.handset.sdktool.printutil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;

import com.blankj.utilcode.util.ImageUtils;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.event.Label;
import com.handset.sdktool.printer.sunmi.SunmiPrintHelper;
import com.handset.sdktool.util.CalculationUtil;
import com.handset.sdktool.util.DebugLog;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cpcl.PrinterHelper;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:作用描述
 */
public class MyPrintHeaper {

    private static MyPrintHeaper mMyPrintHeaper;

    public static MyPrintHeaper getInstance() {
        return mMyPrintHeaper;
    }

    private MyPrintHeaper() {
        if (mMyPrintHeaper == null) {
            mMyPrintHeaper = new MyPrintHeaper();
        }
    }


    /**
     * 根据数据打印文字
     *
     * @param componentsBean
     * @throws Exception
     */
    private void printText(String content,int x ,int y,int font,boolean isBold,boolean isUnderLine) throws Exception {





        SunmiPrintHelper.getInstance().printText("content", 14, true, false, null);
        SunmiPrintHelper.getInstance().feedPaper();




//        int font = Integer.valueOf(componentsBean.getSize());//16：16x16或8x16，视中英⽂⽽定。  24：24x24或12x24，视中英⽂⽽定。  32：32x32或16x32，由ID3字体宽⾼各放⼤2倍
//        if (font < 12) {
//            font = 20;
//        } else if (font >= 12 && font < 14) {
//            font = 3;
//        } else if (font >= 14 && font < 24) {
//            font = 8;
//        } else if (font >= 24) {
//            font = 4;
//        }
//        int x = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordX()), bili);
//        int y = (int) CalculationUtil.multiplication(Double.valueOf(componentsBean.getCoordY() + plusHeight), bili);
//        String data = componentsBean.getPrefix() + componentsBean.getComponentContent() + componentsBean.getSuffix();
//
//        String command = PrinterHelper.TEXT;
//        String size = "0";
//        DebugLog.e("PrinterHelper===printText==" + data + "==" + y + "===" + componentsBean.getId() + "====" + componentsBean.getAboveComponentId() + "===" + y);
//        PrinterHelper.Text(command, String.valueOf(font), size, String.valueOf(x), String.valueOf(y),
//                data);
    }
//    public static Bitmap getBitmap(ModleDTO.ComponentsBean componentsBean, String carNo) {
//
//
//
//        //大标题画笔
//        Paint paintTitle = new Paint();
//        paintTitle.setColor(Color.BLACK);
//        paintTitle.setTextSize(42);
//        paintTitle.setTextAlign(Paint.Align.CENTER);
//        //菜单画笔
//        Paint paintMenuText = new Paint();
//        paintMenuText.setColor(Color.BLACK);
//        paintMenuText.setTextSize(28);
//        //表格画笔
//        Paint paintFormText = new Paint();
//        paintFormText.setColor(Color.BLACK);
//        paintFormText.setTextSize(28);
//        //表格画笔
//        Paint paintFormMenuText = new Paint();
//        paintFormMenuText.setColor(Color.BLACK);
//        paintFormMenuText.setTextSize(28);
//        paintFormMenuText.setTextAlign(Paint.Align.CENTER);
//        //表格粗体画笔
//        Paint paintFormBoldText = new Paint();
//        paintFormBoldText.setColor(Color.BLACK);
//        paintFormBoldText.setTextSize(28);
////        paintFormBoldText.setFakeBoldText(true);
//        //右下角时间画笔
//        Paint paintTime = new Paint();
//        paintTime.setColor(Color.BLACK);
//        paintTime.setTextSize(24);
//        //表格线画笔
//        Paint paintLine = new Paint();
//        paintLine.setColor(Color.BLACK);
//        paintLine.setStrokeWidth(1f);
//        //虚线
//        Paint paintDashLine = new Paint();
//        paintDashLine.setStyle(Paint.Style.STROKE);
//        paintDashLine.setColor(Color.BLACK);
//        paintDashLine.setStrokeWidth(1f);
//        paintDashLine.setPathEffect(new DashPathEffect(new float[]{5, 5, 5, 5}, 0));
//
//        int pageWidth = 384;
//        Bitmap finalBitmap = Bitmap.createBitmap(pageWidth, figureZCDBitmapHeight(data.getPrintListSortByName(), carTypeBean), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(finalBitmap);
//        canvas.drawColor(Color.WHITE);
//
//        int posY = 0;
//        //生成一维条码
//        Bitmap codeBitmap = ZxingUtil.getBarCodeWithoutPadding(pageWidth, pageWidth, 100, carTypeBean.getOrderNo(data));
//        codeBitmap = ImageUtils.scale(codeBitmap, pageWidth, 100);
//
//        canvas.drawBitmap(codeBitmap, 0, 0, null);
//        posY += 130;
//        canvas.drawText(carTypeBean.getOrderNo(data), 0, posY, paintMenuText);
//        posY += 60;
//
//        canvas.drawText(carTypeBean.getOrderPrintTitle(data.getZcdNewListEntities()), pageWidth / 2f, posY, paintTitle);
//        posY += 60;
//
//        canvas.drawText(carNo, pageWidth / 2f, posY, paintTitle);
//        posY += 50;
//
////        if (carTypeBean.isSellOrder() || carTypeBean.isReturnExchangeOrder()) {
////            canvas.drawText("订单类型：" + data.getClassName(), 0, posY, paintMenuText);
////            posY += 40;
////        }
//        canvas.drawText("备  注  二：" + data.getRemarks2(), 0, posY, paintMenuText);
//
//        posY += 10;
//
//        //表格行高
//        int ROW_HEIGHT = TABLE_ROW_HEIGHT;
//        int textOffsetX = 8;
//        int textOffsetY = 8;
//        int[] xLines = new int[]{0, 230, 290, 350, pageWidth - 1};
//        int[] xLinesBlank = new int[]{0, pageWidth - 1};
//
//        canvas.drawLine(xLines[0], posY, xLines[xLines.length - 1], posY, paintLine);
//
//        for (HotPrintBean stockBean : data.getPrintListSortByName()) {
//            if ("合计".equals(stockBean.getName())) {
//
//            } else {
//                posY += ROW_HEIGHT;
//                //库名
//                canvas.drawText("【" + stockBean.getName() + "】", xLines[0], posY - textOffsetY, paintFormText);
//
//                //画线
//                canvas.drawLine(xLines[0], posY, xLines[xLines.length - 1], posY, paintLine);
//                //画表格
//                drawTable(canvas, paintLine, posY, xLines, ROW_HEIGHT);
//                posY += ROW_HEIGHT;
//
//                canvas.drawText("规格型号", (xLines[0] + xLines[1]) / 2f, posY - textOffsetY, paintFormMenuText);
//                canvas.drawText("件", (xLines[1] + xLines[2]) / 2f, posY - textOffsetY, paintFormMenuText);
//                canvas.drawText("支", (xLines[2] + xLines[3]) / 2f, posY - textOffsetY, paintFormMenuText);
//            }
//
//            //合同号
//            List<HotPrintBean> produceNoList = stockBean.getListSortByProduceNo();
//            for (HotPrintBean produceNoBean : produceNoList) {
//                if (!"合计".equals(stockBean.getName()) && !"小计".equals(produceNoBean.getName())) {
//                    drawTable(canvas, paintLine, posY, xLinesBlank, ROW_HEIGHT);
//                    posY += ROW_HEIGHT;
//                    canvas.drawText("合同号：" + produceNoBean.getName(), xLines[0] + textOffsetX, posY - textOffsetY, paintFormText);
//                }
//
//                for (int i = 0; i < produceNoBean.getPrintList().size(); i++) {
////                //画表格
//                    drawTable(canvas, paintLine, posY, xLines, ROW_HEIGHT);
//
//                    posY += ROW_HEIGHT;
//
//                    OrderArriveBean.Print print = produceNoBean.getPrintList().get(i);
//                    if (print.getSpec() != null) {
//                        if (TextUtils.equals("合计", print.getSpec()) || TextUtils.equals("小计", print.getSpec())) {
//                            canvas.drawText(print.getSpec(), xLines[0] + textOffsetX, posY - textOffsetY, paintFormBoldText);
//                        } else {
//                            canvas.drawText(print.getSpec(), xLines[0] + textOffsetX, posY - textOffsetY, paintFormText);
//                        }
//                    }
//                    canvas.drawText(print.getUdf1n(), xLines[1] + textOffsetX / 2f, posY - textOffsetY, paintFormText);
//                    canvas.drawText(print.getUdf2n(), xLines[2] + textOffsetX / 2f, posY - textOffsetY, paintFormText);
//                }
//            }
//        }
//        //画线
//        canvas.drawLine(xLines[0], posY, xLines[xLines.length - 1], posY, paintLine);
//        posY += ROW_HEIGHT;
//        canvas.drawText("制单人：" + data.getOperatorName(), 0, posY - textOffsetY, paintMenuText);
//        posY += 30;
//
//        canvas.drawText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA).format(System.currentTimeMillis()), 0, posY - textOffsetY, paintTime);
//        posY += ROW_HEIGHT;
//        canvas.drawText("● 确认外观无磕碰", 0, posY - textOffsetY, paintFormBoldText);
//        canvas.drawLine(304, posY - 36, 336, posY - 36, paintLine);
//        canvas.drawLine(304, posY - 36, 304, posY - 4, paintLine);
//        canvas.drawLine(336, posY - 4, 336, posY - 36, paintLine);
//        canvas.drawLine(336, posY - 4, 304, posY - 4, paintLine);
//
//        posY+=ROW_HEIGHT;
//
//        Path linePath = new Path();
//        linePath.reset();
//        linePath.moveTo(10, posY);
//        linePath.lineTo(pageWidth-10, posY);
//        canvas.drawPath(linePath, paintDashLine);
//
//        posY+=ROW_HEIGHT/2;
//
//        if (carTypeBean.isSellOrder()) {
//            //装车进度二维码  tytzcjd.jingzhi-tj.com
//            Bitmap htmlBitmap = ZxingUtil.createQRImage("http://tytzcjd.jingzhi-tj.com/#/pages/index/index?carNo="+carNo+"&&"+ AppConfig.user,150,150);
//
//            canvas.drawBitmap(htmlBitmap, 0, posY, null);
//
//            canvas.drawText("请扫描左侧二维码", 150, posY+60, paintMenuText);
//            canvas.drawText("获取实时装车进度", 150, posY+100, paintMenuText);
//        }
//
//        canvas.save();
//        canvas.restore();
//
//        finalBitmap = compressQuality(finalBitmap, 95);
//        return finalBitmap;
//    }
}
