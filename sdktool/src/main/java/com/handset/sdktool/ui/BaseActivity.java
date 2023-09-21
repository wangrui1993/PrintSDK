package com.handset.sdktool.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.LoadingPopupView;

/**
 * @ClassName: BaseActivity
 * @author: wr
 * @date: 2023/9/14 13:49
 * @Description:作用描述
 */
public class BaseActivity  extends AppCompatActivity {

    protected BasePopupView popupView;
    protected LoadingPopupView loadingPopupView;

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
        popupView = null;
        loadingPopupView = null;
        loadingPopupView = new XPopup.Builder(BaseActivity.this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asLoading(message);
        popupView = loadingPopupView.show();
    }

    /**
     * 更新加载框文字
     *
     * @param message
     */
    public void updateLoadingDialog(String message) {
        if (popupView != null && loadingPopupView != null) {
            loadingPopupView.setTitle(message);
        }
    }

    /**
     * 显示/更新加载框文字（如没有显示就显示。已显示就更新）
     *
     * @param message
     */
    public void showOrUpdateLoadingDialog(String message) {
        if (loadingPopupView == null) {
            showLoadingDialog(message);
        } else {
            updateLoadingDialog(message);
        }
    }

    /**
     * 关闭加载框
     */
    public void dismissLoadingDialog() {
        if (popupView != null) {
            popupView.smartDismiss();
//            popupView.delayDismiss(300);
            popupView = null;
            loadingPopupView = null;
        }
    }

    public void dismissLoadingDialog(int delayTime) {
        if (popupView != null) {
            popupView.delayDismiss(delayTime);
            popupView = null;
            loadingPopupView = null;
        }
    }
}
