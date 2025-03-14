package com.handset.sdktool.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: Printer
 * @Package: com.jingzhi.vehiclelogisticssystem.utils
 * @Author: Lau
 * @CreateTime: 2021/6/4 9:31
 * @Description: //测试
 * USBPrinter.initPrinter(mContext);
 * <p>
 * Printer printer = new Printer(mActivity);
 * printer.connect();
 * printer.hardware.print();
 */
public class ESCPrinter {
    private static final String TAG = ESCPrinter.class.getSimpleName();
    private UsbManager usbManager;
    private Context ctx;
    public static final byte LF = 10;
    public static final byte FF = 12;
    public static final byte ESC = 27;
    public static final byte GS = 29;
    private UsbDevice myUsbDevice;// 满足的设备
    private UsbInterface usbInterface;// usb接口
    private UsbEndpoint epControl;// 控制端点
    private UsbDeviceConnection myDeviceConnection;// 连接
    /**
     * 块输出端
     */
    private UsbEndpoint epBulkOut;
    private UsbEndpoint epBulkIn;
    /**
     * 中断端点
     */
    private UsbEndpoint epIntEndpointOut;
    private UsbEndpoint epIntEndpointIn;

    public ESCPrinter(Context ctx) {
        this.ctx = ctx;
    }

    public void close() {
        try {
            myDeviceConnection.close();
        } catch (Exception e) {

        }

    }

    public boolean connect() {
        // 1)创建usbManager
        usbManager = (UsbManager) ctx.getSystemService(Context.USB_SERVICE);
        // 2)获取到所有设备 选择出满足的设备
        enumeraterDevices();
        if (usbInterface == null || myUsbDevice == null) {
            return false;
        }
        // 4)获取设备endpoint
        assignEndpoint();
        // 5)打开conn连接通道
        openDevice();

        return true;
    }


    public void createData(List<String> line) {
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 160);
        buffer.put(init_printer());
//        buffer.put(print_linefeed());

        for (int i = 0; i < line.size(); i++) {
            try {
                buffer.put((line.get(i) + "\n").getBytes("GBK"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
//        buffer.put(feedPaperCut());
        buffer.put(print_f_feed());
        int len = buffer.position();
        byte[] bytes = new byte[len];
        buffer.rewind();
        buffer.get(bytes, 0, len);
        buffer.clear();
        send(bytes);
    }

    public void send(final byte[] buffer) {
        if (!connect()) {
            Log.e(TAG, "连接失败!");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMessageToPoint(buffer);
                } catch (Exception e) {
                }
            }
        }).start();
    }

    /**
     * 枚举设备
     */
    public void enumeraterDevices() {
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            UsbInterface usbinterface = device.getInterface(0);
            if (usbinterface.getInterfaceClass() == 7
                    && !PrinterDevices.isSNYOUDevices(device)
                    && !PrinterDevices.isICODDevice(device)) {
                myUsbDevice = device;
                usbInterface = usbinterface;
            }
        }
    }

    /**
     * 分配端点，IN | OUT，即输入输出；可以�?�过判断
     */
    private void assignEndpoint() {
        if (usbInterface != null) {
            for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
                UsbEndpoint ep = usbInterface.getEndpoint(i);
                switch (ep.getType()) {
                    case UsbConstants.USB_ENDPOINT_XFER_BULK:// �??
                        if (UsbConstants.USB_DIR_OUT == ep.getDirection()) {// 输出
                            epBulkOut = ep;
                            System.out.println("Find the BulkEndpointOut," + "index:" + i + "," + "使用端点号：" + epBulkOut.getEndpointNumber());
                        } else {
                            epBulkIn = ep;
                            System.out.println("Find the BulkEndpointIn:" + "index:" + i + "," + "使用端点号：" + epBulkIn.getEndpointNumber());
                        }
                        break;
                    case UsbConstants.USB_ENDPOINT_XFER_CONTROL:// 控制
                        epControl = ep;
                        System.out.println("find the ControlEndPoint:" + "index:" + i + "," + epControl.getEndpointNumber());
                        break;
                    case UsbConstants.USB_ENDPOINT_XFER_INT:// 中断
                        if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {// 输出
                            epIntEndpointOut = ep;
                            System.out.println("find the InterruptEndpointOut:" + "index:" + i + "," + epIntEndpointOut.getEndpointNumber());
                        }
                        if (ep.getDirection() == UsbConstants.USB_DIR_IN) {
                            epIntEndpointIn = ep;
                            System.out.println("find the InterruptEndpointIn:" + "index:" + i + "," + epIntEndpointIn.getEndpointNumber());
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 连接设备
     */
    public void openDevice() {
        if (usbInterface != null) {// 接口是否为null
            // 在open前判断是否有连接权限；对于连接权限可以静态分配，也可以动态分配权�??
            UsbDeviceConnection conn = null;
            if (usbManager.hasPermission(myUsbDevice)) {
                // 有权限，那么打开
                conn = usbManager.openDevice(myUsbDevice);
            }
            if (null == conn) {
                Toast.makeText(ctx, "无法连接设备！", Toast.LENGTH_SHORT).show();
                return;
            }
            // 打开设备
            if (conn.claimInterface(usbInterface, true)) {
                myDeviceConnection = conn;
                if (myDeviceConnection != null)// 到此你的android设备已经连上zigbee设备
                    System.out.println("打开设备成功！");
                final String mySerial = myDeviceConnection.getSerial();
            } else {
                Toast.makeText(ctx, "打开设备失败！", Toast.LENGTH_SHORT).show();
                conn.close();
            }
        }
    }

    /**
     * @param buffer
     */
    public void sendMessageToPoint(byte[] buffer) {
        if (myDeviceConnection.bulkTransfer(epBulkOut, buffer, buffer.length, 0) >= 0) {
            // myUsbDevice.
            Toast.makeText(ctx, "send success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "send failed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Print and line feed LF
     * 换行
     *
     * @return bytes for this command
     */
    public static byte[] print_linefeed() {
        byte[] result = new byte[1];
        result[0] = LF;

        return result;
    }

    /**
     * 换页
     *
     * @return
     */
    public static byte[] print_f_feed() {
        byte[] result = new byte[1];
        result[0] = FF;

        return result;
    }

    /**
     * Initialize printer Clears the data in the hardware.print buffer and resets the printer modes to the modes that were in effect when the power was turned on. ESC @
     * ESC @（初始化打印机命令）
     *
     * @return bytes for this command
     */
    public byte[] init_printer() {
        byte[] result = new byte[2];
        result[0] = ESC;
        result[1] = 64;
        return result;
    }

    /**
     * feed paper and cut Feeds paper to ( cutting position + n x vertical motion unit ) and executes a full cut ( cuts the paper completely )
     *
     * @return bytes for this command
     */
    public byte[] feedPaperCut() {
        byte[] result = new byte[4];
        result[0] = GS;
        result[1] = 86;
        result[2] = 65;
        result[3] = 0;
        return result;
    }

    /*************************************************************************************************************************/


    public static final byte[] ALIGN_LEFT = {ESC, 0x61, 0x00};
    private static final byte[] RESET = {27, '@'};
    // 纸张大小设置-定义单位
    private static final byte[] PAPER_UNIT = {27, 40, 85, 1, 0, 60};
    // 纸张大小设置-页长-按照定义单位设置页长
    private static final byte[] PAPER_LENGHT_1 = {27, 40, 67, 2, 0, 88, 2};
    //退纸
    private static final byte[] OUT = {0x0c};
    //结束初始化
    private byte[] over = new byte[]{0x1b, '@', 0x0d};

    private boolean write(byte[] byte1, byte[]... bytes) {
        if (myDeviceConnection != null) {
            byte[] newbyte = byte1;
            for (byte[] aByte : bytes) {
                newbyte = ArrayUtils.add(newbyte, aByte);
            }
//            byte[] newbyte = concat(byte1, bytes);
//            byte[] newbyte = ArrayUtils.add(byte1,bytes)
            //usb输出byte
            int b = myDeviceConnection.bulkTransfer(epBulkOut, newbyte, newbyte.length, 10000);
            LogUtils.e("bulkTransfer，b-->" + b);
            return b > 0;
        } else {
            ToastUtils.showShort("未发现可用的打印机");
            return false;
        }
    }


    private void printBitmap(Bitmap bmp) {
        Log.e(TAG, "printBitmap: bmpOri = " + bmp.getWidth() + "  ,  " + bmp.getHeight());
        bmp = compressPic(bmp);
        Log.e(TAG, "printBitmap: bmpRemote = " + bmp.getWidth() + "  ,  " + bmp.getHeight());
        byte[] bmpByteArray = draw2PxPoint(bmp);
        write(bmpByteArray);
    }


    /**
     * 对图片进行压缩（去除透明度）
     *
     * @param bitmapOrg
     */
    public static Bitmap compressPic(Bitmap bitmapOrg) {
        // 获取这个图片的宽和高
        int oriWidth = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        // 定义预转换成的图片的宽度和高度
        int width = oriWidth;
        if (oriWidth > 1200) {
            width = 1200;
        }

        int newWidth = (width + 23) / 24 * 24;
        int newHeight = (((int) (newWidth * height / (float) oriWidth)) + 23) / 24 * 24;
        Bitmap targetBmp = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas targetCanvas = new Canvas(targetBmp);
        targetCanvas.drawColor(0xffffffff);
        targetCanvas.drawBitmap(bitmapOrg, new Rect(0, 0, width, height), new Rect(0, 0, newWidth, newHeight), null);
        return targetBmp;
    }

    public static byte[] draw2PxPoint(Bitmap bmp) {
        //先设置一个足够大的size，最后在用数组拷贝复制到一个精确大小的byte数组中
        Log.e(TAG, "draw2PxPoint: " + bmp.getWidth() + " , " + bmp.getHeight());
        int size = bmp.getWidth() * bmp.getHeight();/// 8 + 1000;
        byte[] tmp = new byte[size];
        int k = 0;
        // 设置行距为0
        tmp[k++] = 0x1B;
        tmp[k++] = 0x33;
        tmp[k++] = 0x00;
        // 居中打印
        tmp[k++] = 0x1B;
        tmp[k++] = 0x61;
        tmp[k++] = 0x00; //1居中 0左 2右
        for (int j = 0; j < bmp.getHeight() / 24f; j++) {
            //-------------ESC $ nL nH 设置绝对打印位置
            tmp[k++] = 0x1B;//设置水平绝对位置
            tmp[k++] = 0x24;
//            tmp[k++] = (byte) 0x0c;
//            tmp[k++] = 0x01;
            tmp[k++] = (byte) (bmp.getWidth() % 256); // nL
            tmp[k++] = (byte) (bmp.getWidth() / 256); // nH
            //-------------

            tmp[k++] = 0x1B;
            tmp[k++] = 0x2A;// 0x1B 2A 表示图片打印指令
            tmp[k++] = 33; // m=33时，选择24点密度打印
            tmp[k++] = (byte) (bmp.getWidth() % 256); // nL
            tmp[k++] = (byte) (bmp.getWidth() / 256); // nH
            for (int i = 0; i < bmp.getWidth(); i++) {
                for (int m = 0; m < 3; m++) {
                    for (int n = 0; n < 8; n++) {
                        byte b = px2Byte(i, j * 24 + m * 8 + n, bmp);
                        tmp[k] += tmp[k] + b;
                    }
                    k++;
                }
            }
            //tmp[k++] = 10;// 换行
            tmp[k++] = 0x1B;
            tmp[k++] = 0x4A;
            tmp[k++] = 0x18;
            tmp[k++] = 0x0D;
        }
        // 恢复默认行距
        tmp[k++] = 0x1B;
        tmp[k++] = 0x32;

        byte[] result = new byte[k];
        System.arraycopy(tmp, 0, result, 0, k);
        return result;
    }

    /**
     * 图片二值化，黑色是1，白色是0
     *
     * @param x   横坐标
     * @param y   纵坐标
     * @param bit 位图
     * @return
     */
    public static byte px2Byte(int x, int y, Bitmap bit) {
        byte b;
        int pixel = bit.getPixel(x, y);
        int red = (pixel & 0x00ff0000) >> 16; // 取高两位
        int green = (pixel & 0x0000ff00) >> 8; // 取中两位
        int blue = pixel & 0x000000ff; // 取低两位
        int gray = RGB2Gray(red, green, blue);
        if (gray < 128) {
            b = 1;
        } else {
            b = 0;
        }
        return b;
    }

    /**
     * 图片灰度的转化
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    private static int RGB2Gray(int r, int g, int b) {
        int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b);  //灰度转化公式
        return gray;
    }

    public void escPrintBitmap(Bitmap bitmap) throws Exception {
        write(RESET);
        write(PAPER_UNIT);//定义单位
        write(PAPER_LENGHT_1);//设置页长
        printBitmap(bitmap);
//        printData();
//        byte[] cmd = BitmapUtil.printBitmap(bitmap);
//        byte[] cmd = Commands.decodeBitmap(bitmap);
//        write(cmd);
        write(OUT, over);
    }


    /******************************************************************************************************************/

    private final int _ESC = 0x1B;//模式设定
    private final int _FS = 0x1C;//汉字模式设定
    private final int _SI = 0x0F;//选择压缩打印
    private final int _DC2 = 0x12;//解除压缩模式
    private final int _SO = 0x0E;//设定倍宽打印

    private final byte[][] ENABLE = {{0}, {1}};//0解除 1设定

    private final byte[][] LQPrintData = {
            {_ESC, '@'}, {2},//打印机初始化
            {_FS, '&'}, {2},//设中文模式
            {_FS, '.'}, {2},//设英文模式
            {_FS, 'U'}, {2},//设定半角字符间距调整
            {_FS, 'V'}, {2},//取消半角字符间距调整
            {_FS, 'S'}, {2},//设定全角汉字字间距
            {_FS, 'T'}, {2},//设定半角字符间距
            {_FS, _SO}, {2},//设定单行倍宽
            {_FS, 'W'}, {2},//设定/解除四倍角打印
            {_ESC, 'W'}, {2},//设定/解除倍宽打印
            {_FS, _SI}, {2},//设定汉字半角
            {_FS, _DC2}, {2},//解除汉字半角
            {_FS, 'r'}, {2},//设定汉字上/下标打印
            {_FS, 'J'}, {2},//设定纵向打印
            {_FS, 'K'}, {2},//设定横向打印
            {_FS, 'D'}, {2},//纵向半角两字符并列打印
            {_FS, '-'}, {2},//设定解除汉字下划线
            {_FS, '!'}, {2},//汉字打印模式组合
            {_FS, 'v'}, {2},//汉字打印模式组合
    };

    public void print() {
        write(RESET);
        write(PAPER_UNIT);//定义单位
        write(PAPER_LENGHT_1);//设置页长
        printData();
        write(OUT, over);
    }

    private void printData() {
//        write(LQPrintData[0]);//初始化
        addText("                                                                                                    Page 1/1\n");

        write(LQPrintData[16]);
        setEnable(true);
        addText("                     结算单\n");
        write(LQPrintData[16]);
        setEnable(false);

        addText("\n  购货单位：天津德顺昌               ");
        addText("购货日期：2021/6/10               ");
        addText("销货单号：S20210610123456");
        addText("\n");

        write(LQPrintData[36]);
        setEnable(true);


        addTopLine();
        addText("│  商品名称  │    规格型号    │ 件数 │ 支／件 │ 支数 │Ｔ│  数量  │  单价  │  金额  │  备注  │\n");
        addMiddleLine();
//        addText("│镀锌方管    │40*80*1.2       │    14│      40│     0│吨│   6.923│5899.000│40838.78│        │\n");
//        addMiddleLine();
//        addText("│合作        │40*80*1.2       │    14│      40│     0│吨│   6.923│5899.000│40838.78│        │\n");
//        addMiddleLine();
        addTableRow(new String[]{"镀锌方管", "40*80*1.2", "14", "40", "0", "吨", "6.923", "5899.000", "40838.78", ""});
        addMiddleLine();
        addTableRow(new String[]{"镀锌方管", "40*80*1.2", "14", "40", "0", "吨", "6.923", "5899.000", "40838.78", ""});
        addMiddleLine();
        addTableRow(new String[]{"合计", "", "28", "", "0", "", "13.846", "", "81677.56", ""});
        addBottomLine();

//        write(LQPrintData[36]);
//        setEnable(false);

        addText("\n\n\n\n\n\n\n\n");
        addText("金额（大写）：" + "捌万壹仟陆佰柒拾柒圆伍角陆分" + "\n");

        addText("──────────────────────────────────────────────────\n");
        addText("H         ");
        addText("销售会计：薛素珍                                            ");
        addText("提货人签字：\n");
        addText("温馨提示：                                                            ");
        addText("司机电话：\n");
        addText("1.如遇雨雪天气，请及时盖好苫布；                                      ");
        addText("车牌号码：冀J9D037\n");
        addText("2.在吊运成品管时，请按照《天车安全使用说明操作》。                                   ");
        addText("H");
    }

    int[] tableLength = {12, 16, 6, 8, 6, 2, 8, 8, 8, 8};

    private void addText(String text) {
        try {
            write(text.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void addTableRow(String[] data) {
        for (int i = 0; i < tableLength.length; i++) {
            boolean isAtLeft = true;
            switch (i) {
                case 0:
                case 1:
                    isAtLeft = true;
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    isAtLeft = false;
                    break;
            }

            byte[] oldBytes = new byte[0];
            try {
                oldBytes = data[i].getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //如果超了 就截掉
            if (oldBytes.length > tableLength[i]) {
                byte[] bytes = new byte[tableLength[i]];
                for (int j = 0; j < bytes.length; j++) {
                    bytes[j] = oldBytes[j];
                }

                data[i] = new String(bytes);
            }

            //如果不足 就空格补齐
            if (oldBytes.length < tableLength[i]) {
                while (oldBytes.length < tableLength[i]) {
                    if (isAtLeft) {
                        data[i] = data[i] + " ";
                    } else {
                        data[i] = " " + data[i];
                    }
                    try {
                        oldBytes = data[i].getBytes("GBK");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (String text : data) {
            addText("│");
            addText(text);
        }
        addText("│\n");
    }

    private void setEnable(boolean enable) {
        write(ENABLE[enable ? 1 : 0]);
    }

    private void addTopLine() {
        addText("┌──────┬────────┬───┬────┬───┬─┬────┬────┬────┬────┐\n");
    }

    private void addMiddleLine() {
        addText("├──────┼────────┼───┼────┼───┼─┼────┼────┼────┼────┤\n");
    }

    private void addBottomLine() {
        addText("└──────┴────────┴───┴────┴───┴─┴────┴────┴────┴────┘\n");
    }


}
