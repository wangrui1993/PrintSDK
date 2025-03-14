package com.handset.sdktool.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gainscha.sdk2.ConnectType;
import com.gainscha.sdk2.ConnectionListener;
import com.gainscha.sdk2.Printer;
import com.gainscha.sdk2.PrinterConfig;
import com.gainscha.sdk2.PrinterFinder;
import com.gainscha.sdk2.PrinterResponse;
import com.gainscha.sdk2.command.Cmd;
import com.gainscha.sdk2.f;
import com.gainscha.sdk2.model.PaperType;
import com.gainscha.sdk2.model.UsbPrinterDevice;
import com.google.gson.Gson;
import com.handset.sdktool.R;
import com.handset.sdktool.data.BusinessData;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.event.LabelBoard;
import com.handset.sdktool.event.LabelItem;
import com.handset.sdktool.listener.GetTemplateByBusinessCode;
import com.handset.sdktool.listener.OnRecycleViewItemClickListener;
import com.handset.sdktool.printutil.MyPrintUtil;
import com.handset.sdktool.util.Bluetooth;
import com.handset.sdktool.util.CalculationUtil;
import com.handset.sdktool.util.DeviceUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kotlin.jvm.internal.Intrinsics;

/**
 * @ClassName: ConnectActivity
 * @author: wr
 * @date: 2022/11/17 9:33
 * @Description:作用描述
 */
public class ConnectUSBActivity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private BlueToothDeviseAdapter mBlueToothDeviseAdapter;
    private List<BluetoothDevice> mList = new ArrayList<>();
    public BluetoothAdapter myBluetoothAdapter;
    private Bluetooth bluetooth;
    private ProgressDialog progressDialog;
    private TextView jbprint;
    private TextView tv_location;
    private ProgressBar progress_bar;
    private RelativeLayout rl_search;
    private LinearLayout ll_location;
    protected BasePopupView popupView;
    private ImageView iv_image;
    private RelativeLayout rl_pre;
    private TextView tv_pre;
    private ConnectionListener listener = new ConnectionListener() {
        @Override
        public void onPrinterConnected(Printer printer) {
            //连接成功
        }

        @Override
        public void onPrinterConnectFail(Printer printer) {
            //连接失败
        }

        @Override
        public void onPrinterDisconnect(Printer printer) {
            //断开连接
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_usb);


        recycler_view = findViewById(R.id.recycler_view_usb);
        jbprint = findViewById(R.id.jbprint);
        tv_location = findViewById(R.id.tv_location);
        progress_bar = findViewById(R.id.progress_bar);
        rl_search = findViewById(R.id.rl_search);
        ll_location = findViewById(R.id.ll_location);
        iv_image = findViewById(R.id.iv_image);
        rl_pre = findViewById(R.id.rl_pre);
        tv_pre = findViewById(R.id.tv_pre);
        mBlueToothDeviseAdapter = new BlueToothDeviseAdapter(this, mList);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(mBlueToothDeviseAdapter);
        recycler_view.addOnItemTouchListener(new OnRecycleViewItemClickListener(this, recycler_view) {
            @SuppressLint("MissingPermission")
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {

            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i) {

            }
        });
        rl_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_pre.setVisibility(View.GONE);
            }
        });
        tv_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog("请求打印数据..");
                DataUtil.getInstance().getTemplateByBusinessCode(getIntent().getStringExtra("id"), new GetTemplateByBusinessCode() {
                    @Override
                    public void onSuccess(ModleDTO listBaseBean) {
                        for (ModleDTO.ComponentsBean componentsBean : listBaseBean.getComponents()) {
                            if (componentsBean.getComponentTypeId().equals("5")) {
                                LabelBoard labelBoard = new Gson().fromJson(componentsBean.getComponentContent(), LabelBoard.class);


                                for (LabelItem labelItem2 : labelBoard.getLabelItems()) {
                                    LabelBoard labelBoard2 = new Gson().fromJson(labelItem2.getDataJson(), LabelBoard.class);

                                    for (LabelItem labelItem3 : labelBoard2.getLabelItems()) {
                                        LabelBoard labelBoard3 = new Gson().fromJson(labelItem3.getDataJson(), LabelBoard.class);
                                        Log.e("3cha---", labelItem3.getDataJson());
                                    }

                                    Log.e("2cha---", labelItem2.getDataJson());
                                }
                                Log.e("1cha---", componentsBean.getComponentContent());
                            }
                        }
                        dismissLoadingDialog();
                        Log.e("MyPrintUtil---", "2");
                        MyPrintUtil printUtil = new MyPrintUtil(listBaseBean, getResources().getDisplayMetrics());
                        rl_pre.setVisibility(View.VISIBLE);
//                        printUtil.preview(iv_image,"TSC");

                        List<Map<String, Object>> d = new ArrayList<>();
                        d.add(BusinessData.getInstance().getMap());
                        iv_image.setImageBitmap(printUtil.dataGenerationImage(d, "TSC").get(0));

//                        Log.e("mapmap==",getIntent().getStringExtra("id"));
//                        Log.e("mapmap==",new Gson().toJson(listBaseBean));
//                        for (ModleDTO.ComponentsBean componentsBean : listBaseBean.getComponents()) {
//                            if (componentsBean.getComponentTypeId().equals("5")) {
//                                LabelBoard labelBoard=new Gson().fromJson(componentsBean.getComponentContent(),LabelBoard.class);
//
//
//                                for (LabelItem labelItem2 : labelBoard.getLabelItems()) {
//                                    LabelBoard labelBoard2=new Gson().fromJson(labelItem2.getDataJson(),LabelBoard.class);
//
//                                    for (LabelItem labelItem3 : labelBoard2.getLabelItems()) {
//                                        LabelBoard labelBoard3=new Gson().fromJson(labelItem3.getDataJson(),LabelBoard.class);
//                                        Log.e("3cha---", labelItem3.getDataJson());
//                                    }
//
//                                    Log.e("2cha---", labelItem2.getDataJson());
//                                }
//                                Log.e("1cha---", componentsBean.getComponentContent());
//                            }
//                        }
//                        dismissLoadingDialog();
//                        Log.e("MyPrintUtil---","2");
//                        MyPrintUtil printUtil = new MyPrintUtil(listBaseBean,getResources().getDisplayMetrics());
//                        rl_pre.setVisibility(View.VISIBLE);
//                        printUtil.preview(iv_image);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoadingDialog();
                        Toast.makeText(ConnectUSBActivity.this, "打印模板获取失败!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        jbprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog("获取启用模板..");


                DataUtil.getInstance().getTemplateByBusinessCode(getIntent().getStringExtra("id"), new GetTemplateByBusinessCode() {
                    @Override
                    public void onSuccess(ModleDTO listBaseBean) {
                        dismissLoadingDialog();
                        MyPrintUtil printUtil = new MyPrintUtil(listBaseBean, getResources().getDisplayMetrics());
//                        printUtil.printTag(1, BusinessData.getInstance().getMaps());

                        rl_pre.setVisibility(View.VISIBLE);
                        List<Map<String, Object>> d = new ArrayList<>();
                        d.add(BusinessData.getInstance().getMap());
                        Bitmap bitmap = printUtil.dataGenerationImage(d, "TSC").get(0);
                        iv_image.setImageBitmap(bitmap);


                        List<Printer> printers = Printer.getConnectedPrinters();
                        if (printers.size() > 0) {
                            toPrint(printers.get(0), bitmap, 1);
                        }
//                        for (Printer p : printers) {
//                            getESCChinese(p,bitmap);

//                            Log.e("1MyPrintUtil---", p.getPrinterDevice().getPrinterName());
//                            byte[] b = ZplPrinter.tytPrint410Bitmap(null, 0, 0, bitmap).getBytes(StandardCharsets.UTF_8);
//                            Log.e("2MyPrintUtil---",  b.toString());
//                            try {
//                                p.print(b);
//                                Log.e("3MyPrintUtil---",  b.toString());
//                            } catch (IOException e) {
//                                Log.e("4MyPrintUtil---",  e.getMessage());
//                                e.printStackTrace();
//                            }
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoadingDialog();
                        Toast.makeText(ConnectUSBActivity.this, "业务对应有多个启用模板或为设置启用模板,请调整模板启用状态", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progress_bar.getVisibility() == View.GONE) {
//                    searchBluePrint();
                }
            }
        });
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String device = DeviceUtil.deviceInit(ConnectUSBActivity.this);
                jbprint.setText("打印（" + device + ")");
            }
        });
        // 搜索USB打印机
        new PrinterFinder().searchPrinters(
                ConnectType.USB_DEVICE,
                new PrinterFinder.SimpleSearchPrinterResultListener() {
                    @Override
                    public void onSearchUsbPrinter(UsbPrinterDevice usbPrinterDevice) {
                        super.onSearchUsbPrinter(usbPrinterDevice);
                        // 搜索到USB打印机设备
                        Log.e("usbji==", usbPrinterDevice.getPrinterName() == null ? "null" : usbPrinterDevice.getPrinterName());
                        if (usbPrinterDevice != null) {
                            ll_location.setVisibility(View.VISIBLE);
                            tv_location.setText(usbPrinterDevice.getUsbDevice().getDeviceName());
                            jbprint.setText("打印（" + usbPrinterDevice.getUsbDevice().getDeviceName() + "）");
                        } else {
                            ll_location.setVisibility(View.GONE);
                        }
                        Printer.connect(usbPrinterDevice);
                    }
                });
        Printer.addConnectionListener(listener);
    }

    public void toPrint(Printer printer, Bitmap bitmap, int count) {
        try {
            Log.e("aksj==", "宽：" + bitmap.getWidth() + "高：" + bitmap.getHeight());
            PrinterConfig p = new PrinterConfig();//默认宽60mm 高40mm
            p.setDensity(5);
            p.setPaperType(PaperType.PAPER_TYPE_CONTINUOUS);
//            p.setLabelHeight((bitmap.getHeight() - DeviceUtil.TOPPADING) / 12);
            p.setLabelHeight(getHeight(bitmap));
//            p.setLabelHeight(105);
            Log.e("2aksj==", new Gson().toJson(p));
            printer.print(bitmap, count, p);
            printer.print(getByte(bitmap,count,p), new PrinterResponse<byte[]>() {
                @Override
                public void onPrinterResponse(byte[] bytes) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getByte( @Nullable Bitmap var1, int var2, @Nullable PrinterConfig var3) throws IOException {
        if (var1 != null && !var1.isRecycled() && var1.getWidth() >= 1 && var1.getHeight() >= 1) {
            List var10000 = f.a(var3, var1, var2);
            StringBuilder var5;
            var5 = new StringBuilder();
            Iterator var6 = var10000.iterator();

            while (var6.hasNext()) {
                byte[] var7 = ((Cmd) var6.next()).getBytes();
                String var4;
                var4 = new String(var7);
                var5.append(var4);
            }
            return var5.toString().getBytes();
        } else {
            return null;
        }
    }

//    public byte[] print(Printer printer, @Nullable Bitmap var1, int var2, @Nullable PrinterConfig var3) throws IOException {
//        if (var1 != null && !var1.isRecycled() && var1.getWidth() >= 1 && var1.getHeight() >= 1) {
//            if (var3 == null) {
//                var3 = new PrinterConfig ();
//            }
//
//            if (printer.isConnected()) {
//                List var10000 = f.a(var3, var1, var2);
//                StringBuilder var5;
//                var5 = new StringBuilder ();
//                Iterator var6 = var10000.iterator();
//
//                while(var6.hasNext()) {
//                    byte[] var7 = ((Cmd)var6.next()).getBytes();
//                    if (printer.connection.b(var7)) {
//                        String var4;
//                        var4 = new String (var7);
//                        var5.append(var4);
//                    }
//                }
//
//                return var5.toString().getBytes();
//            } else {
//                throw new IOException("Printer not connected");
//            }
//        } else {
//            return null;
//        }
//    }


    private int getHeight(Bitmap bitmap) {
        double proportion = CalculationUtil.multiplication(bitmap.getHeight(), 60);
        int h = (int) CalculationUtil.div(proportion, bitmap.getWidth(), 2);
        return h;
    }

    class BlueToothDeviseAdapter extends RecyclerView.Adapter<BlueToothDeviseAdapter.Holder> {

        private List<BluetoothDevice> list;
        private Context context;

        public BlueToothDeviseAdapter(Context context, List<BluetoothDevice> list) {
            this.list = list;
            this.context = context;
        }

        private int selectPosition = -1;//

        public int getSelectPosition() {
            return selectPosition;
        }

        public void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
            notifyDataSetChanged();
        }


        public final class Holder extends RecyclerView.ViewHolder {
            private final ViewGroup parent;
            private final TextView textView;
            final BlueToothDeviseAdapter this$0;

            public Holder(BlueToothDeviseAdapter labelEditMenuAdapter, View view, TextView drawableTextView,
                          ViewGroup viewGroup) {
                super(view);
                this.this$0 = labelEditMenuAdapter;
                this.textView = drawableTextView;
                this.parent = viewGroup;
            }

            public ViewGroup getParent() {
                return this.parent;
            }

            public TextView getTextView() {
                return this.textView;
            }

        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Intrinsics.checkNotNullParameter(viewGroup, "parent");
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bluetooth_deveice, viewGroup, false);
            Intrinsics.checkNotNullExpressionValue(inflate, "view");
            TextView tv_name = inflate.findViewById(R.id.tv_name);

            return new Holder(this, inflate, (TextView) tv_name, viewGroup);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.list.size();
        }

        @SuppressLint("MissingPermission")
        public void onBindViewHolder(Holder holder, int i) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            int height = holder.getParent().getHeight();
            int width = holder.getParent().getWidth();
            if (holder.itemView.getContext().getResources().getConfiguration().orientation == 2) {
                holder.itemView.setLayoutParams(new AbsListView.LayoutParams(width / 2, height / 4));
            }
            holder.getTextView().setText(list.get(i).getName());
            if (selectPosition == i) {
                holder.getTextView().setTextColor(this.context.getResources().getColor(R.color.theme));
            } else {
                holder.getTextView().setTextColor(this.context.getResources().getColor(R.color.text_title));
            }

        }
    }

    /**
     * 显示加载框带文字
     *
     * @param message
     */
    public void showLoadingDialog(String message) {
        if (popupView == null) {
            popupView = new XPopup.Builder(this)
                    .dismissOnBackPressed(false)
                    .dismissOnTouchOutside(false)
                    .asLoading(message)
                    .show();
        } else {
            popupView.show();
        }
    }

    /**
     * 关闭加载框
     */
    public void dismissLoadingDialog() {
        if (popupView != null) {
            popupView.smartDismiss();
        }
    }
}
