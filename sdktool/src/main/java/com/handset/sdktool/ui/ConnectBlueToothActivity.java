package com.handset.sdktool.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.handset.sdktool.R;
import com.handset.sdktool.data.BusinessData;
import com.handset.sdktool.data.DataUtil;
import com.handset.sdktool.dto.ModleDTO;
import com.handset.sdktool.event.LabelBoard;
import com.handset.sdktool.event.LabelItem;
import com.handset.sdktool.listener.GetTemplateByBusinessCode;
import com.handset.sdktool.listener.OnRecycleViewItemClickListener;
import com.handset.sdktool.printer.sunmi.SunmiPrintHelper;
import com.handset.sdktool.printutil.MyPrintUtil;
import com.handset.sdktool.util.Bluetooth;
import com.handset.sdktool.util.DeviceUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import cpcl.PrinterHelper;
import kotlin.jvm.internal.Intrinsics;
import rx.functions.Action1;

/**
 * @ClassName: ConnectActivity
 * @author: wr
 * @date: 2022/11/17 9:33
 * @Description:作用描述
 */
public class ConnectBlueToothActivity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private BlueToothDeviseAdapter mBlueToothDeviseAdapter;
    private List<BluetoothDevice> mList = new ArrayList<>();
    public BluetoothAdapter myBluetoothAdapter;
    private Bluetooth bluetooth;
    private ProgressDialog progressDialog;
    private TextView print;
    private TextView tv_location;
    private ProgressBar progress_bar;
    private RelativeLayout rl_search;
    private LinearLayout ll_location;
    protected BasePopupView popupView;
    private ImageView iv_image;
    private RelativeLayout rl_pre;
    private TextView tv_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_bluetooth);
        recycler_view = findViewById(R.id.recycler_view_blue);
        print = findViewById(R.id.print);
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
                Bluetooth.setOnBondState(mList.get(position), new Bluetooth.OnBondState() {
                    @Override
                    public void bondSuccess() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Intent intent = new Intent();
                        intent.putExtra("SelectedBDAddress", mList.get(position).getAddress());
                        finish();
                    }
                });
                if (mList.get(position).getBondState() == BluetoothDevice.BOND_BONDED) {
                    Intent intent = new Intent();
                    intent.putExtra("SelectedBDAddress", mList.get(position).getAddress());
                    connectBT(ConnectBlueToothActivity.this, mList.get(position));//TODO 从这对比看
                } else {
                    progressDialog = new ProgressDialog(ConnectBlueToothActivity.this);
                    progressDialog.setMessage(getString(R.string.hello_second_fragment));
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mList.get(position).createBond();
                        }
                    }).start();
                }
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
                                LabelBoard labelBoard=new Gson().fromJson(componentsBean.getComponentContent(),LabelBoard.class);


                                for (LabelItem labelItem2 : labelBoard.getLabelItems()) {
                                    LabelBoard labelBoard2=new Gson().fromJson(labelItem2.getDataJson(),LabelBoard.class);

                                    for (LabelItem labelItem3 : labelBoard2.getLabelItems()) {
                                        LabelBoard labelBoard3=new Gson().fromJson(labelItem3.getDataJson(),LabelBoard.class);
                                        Log.e("3cha---", labelItem3.getDataJson());
                                    }

                                    Log.e("2cha---", labelItem2.getDataJson());
                                }
                                Log.e("1cha---", componentsBean.getComponentContent());
                            }
                        }
                        dismissLoadingDialog();
                        Log.e("MyPrintUtil---","2");
                        MyPrintUtil printUtil = new MyPrintUtil(listBaseBean,getResources().getDisplayMetrics());
                        rl_pre.setVisibility(View.VISIBLE);
                        printUtil.preview(iv_image,"TSC");
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
                        Toast.makeText(ConnectBlueToothActivity.this, "打印模板获取失败!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog("获取启用模板..");
                DataUtil.getInstance().getTemplateByBusinessCode(getIntent().getStringExtra("id"), new GetTemplateByBusinessCode() {
                    @Override
                    public void onSuccess(ModleDTO listBaseBean) {
                        dismissLoadingDialog();
                        Log.e("MyPrintUtil---","3");
                        MyPrintUtil printUtil = new MyPrintUtil(listBaseBean,getResources().getDisplayMetrics());
//                        printUtil.printTag(1, BusinessData.getInstance().getMaps());
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoadingDialog();
                        Toast.makeText(ConnectBlueToothActivity.this, "业务对应有多个启用模板或为设置启用模板,请调整模板启用状态", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progress_bar.getVisibility() == View.GONE) {
                    searchBluePrint();
                }
            }
        });
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String device = DeviceUtil.deviceInit(ConnectBlueToothActivity.this);
                print.setText("打印（" + device + ")");
            }
        });
        //搜索本地（如果是手持打印一体机）
        searchLocationPrint();
        searchBluePrint();
    }

    private void searchLocationPrint() {
        SunmiPrintHelper.getInstance().initPrinter();
        String device = DeviceUtil.deviceInit(this);
        if (device != null && device.length() > 0) {
            ll_location.setVisibility(View.VISIBLE);
            tv_location.setText(device);
            print.setText("打印（" + device + "）");
        } else {
            ll_location.setVisibility(View.GONE);
        }
    }

    /**
     * 搜索打印机
     */
    public void searchBluePrint() {
        Log.e("rl_search===3", "");
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Action1<Boolean>() {
            @SuppressLint("MissingPermission")
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    Log.e("dfdccc===2", "");
                    if ((myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()) == null) {
                        Toast.makeText(ConnectBlueToothActivity.this, "没有找到蓝牙适配器", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!myBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, 2);
                    }
                    Log.e("dfdccc===3", "");
                    if (!myBluetoothAdapter.isEnabled()) {
                        return;
                    }
                    Log.e("dfdccc===4", "");
                    bluetooth = Bluetooth.getBluetooth(ConnectBlueToothActivity.this);
                    initBT(ConnectBlueToothActivity.this);

                }
            }
        });

    }

    private void initBT(Context context) {
        progress_bar.setVisibility(View.VISIBLE);
        mList.clear();
        bluetooth.doDiscovery();
        bluetooth.getData(new Bluetooth.toData() {
            @SuppressLint("MissingPermission")
            @Override
            public void succeed(BluetoothDevice bluetoothDevice) {
                Log.e("dfdccc===6", "");
                for (BluetoothDevice printBT : mList) {
                    Log.e("dfdccc===7", "");
                    if (bluetoothDevice.getAddress().equals(printBT.getAddress())) {
                        Log.e("dfdccc===8", "");
                        return;
                    }
                }
                Log.e("dfdccc===9", bluetoothDevice.getName());
                //XiangYinBao_X3,ATOL1
                mList.add(bluetoothDevice);
                mBlueToothDeviseAdapter.notifyDataSetChanged();
                Log.e("dfdccc===10", bluetoothDevice.getName());
            }

            @Override
            public void discoveryFinished() {
                Log.e("dfdccc===11", "");
                progress_bar.setVisibility(View.GONE);
                if (mList.size() > 0) {

                }
            }
        });
    }

    private void connectBT(Context context, final BluetoothDevice bluetoothDevice) {
        if (TextUtils.isEmpty(bluetoothDevice.getAddress()))
            return;
        Log.e("dfdccc===connectBT", bluetoothDevice.getAddress());
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.activity_devicelist_connect));
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    final int result = PrinterHelper.portOpenBT(context.getApplicationContext(), bluetoothDevice.getAddress());
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void run() {
                            if (result == 0) {
                                print.setText("打印（" + bluetoothDevice.getName() + "）");
                                DeviceUtil.setDevive(bluetoothDevice.getName());
                                Toast.makeText(context, context.getString(R.string.activity_main_connected), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.activity_main_connecterr), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    progressDialog.dismiss();
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }
        }.start();
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
        public BlueToothDeviseAdapter.Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Intrinsics.checkNotNullParameter(viewGroup, "parent");
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bluetooth_deveice, viewGroup, false);
            Intrinsics.checkNotNullExpressionValue(inflate, "view");
            TextView tv_name = inflate.findViewById(R.id.tv_name);

            return new BlueToothDeviseAdapter.Holder(this, inflate, (TextView) tv_name, viewGroup);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.list.size();
        }

        @SuppressLint("MissingPermission")
        public void onBindViewHolder(BlueToothDeviseAdapter.Holder holder, int i) {
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
