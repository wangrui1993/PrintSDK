package com.handset.printsdk.base;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.handset.sdktool.net.ApiGo;
import com.handset.sdktool.net.ApiService;
import com.handset.sdktool.net.ApiStore;
import com.handset.sdktool.net.OnResponse;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @ClassName: BaseActivity
 * @Author: WR
 * @CreateDate: 2020/10/16 9:31
 * @Description:
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseViewInterface {
    protected Context mContext;
    protected Bundle bundle;
    protected BasePopupView popupView;
    private Unbinder unbinder;
    protected View rootView;


    final String TAG = this.getClass().getSimpleName(); //获取当前activity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityStackManager.getActivityStackManager().pushActivity(this);
        onBeforeSetContentView();
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            rootView = LayoutInflater.from(mContext).inflate(layoutId, null);
            setContentView(rootView);
        }
        Log.e(TAG, "--->OnCreate");

        unbinder = ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        initView(savedInstanceState);
        initEvent();
    }


    protected boolean isShowFloatWindow() {
        return true;
    }

    /**
     * 在setContentView()前执行的方法，需要可重写
     */
    protected void onBeforeSetContentView() {

    }

    /************************************************************** Retrofit *************************************************************/

    /**
     * Retrofit请求
     * 传入接口配置文件，可传入不同的接口配置文件
     *
     * @return
     */
    protected <T> T api(Class<T> service) {
        return ApiStore.createApi(service);
    }

    /**
     * @return
     */
    protected ApiService api() {
        return ApiStore.createApi(ApiService.class);
    }


    /*************************************************************** 界面跳转 **************************************************************/

    /**
     * 跳转界面
     *
     * @param descClass
     */
    public void goActivity(Class<?> descClass) {
        goActivity(descClass, null);
    }

    /**
     * 跳转界面带Bundle
     *
     * @param descClass
     * @param bundle
     */
    public void goActivity(Class<?> descClass, Bundle bundle) {
        Intent intent = new Intent(mContext, descClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转界面ForResult
     *
     * @param descClass
     * @param requestCode
     */
    public void goActivityForResult(Class<?> descClass, int requestCode) {
        goActivityForResult(descClass, null, requestCode);
    }

    /**
     * 跳转界面ForResult带Bundle
     *
     * @param descClass
     * @param bundle
     * @param requestCode
     */
    public void goActivityForResult(Class<?> descClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mContext, descClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * Toast
     *
     * @param toast
     */
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    /**
     * 统一的网络请求失败Toast
     */
    public void showServerErrorToast() {
        Toast.makeText(mContext, "网络请求失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示加载框
     */
    public void showLoadingDialog() {
        showLoadingDialog("");
    }

    /**
     * 显示加载框带文字
     *
     * @param message
     */
    public void showLoadingDialog(String message) {
        if (popupView == null) {
            popupView = new XPopup.Builder(mContext)
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

    public void dismissLoadingDialog(int delayTime) {
        if (popupView != null) {
            popupView.delayDismiss(delayTime);
        }
    }

    public void dismissLoadingDialog(Runnable runnable) {
        dismissLoadingDialog(0, runnable);
    }

    public void dismissLoadingDialog(int delayTime, Runnable runnable) {
        if (popupView != null) {
            popupView.delayDismissWith(delayTime, runnable);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    /**
     * 开启屏幕常量
     */
    private void keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 清除常量模式
     */
    private void clearScreenOn() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    /**
     * 判断网络连接是否可用
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conn.getActiveNetworkInfo();
        if (net != null && net.isConnected()) {
            return true;
        } else {
            showToast("请检查网络！");
            return false;
        }
    }
}
