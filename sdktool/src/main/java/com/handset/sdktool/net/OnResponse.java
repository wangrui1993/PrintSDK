package com.handset.sdktool.net;

import android.widget.Toast;


import com.handset.sdktool.base.BaseBean;


import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @ClassName: OnResponse
 * @Author: wr
 * @CreateDate: 2020/10/30 11:52
 * @Description:
 */
public abstract class OnResponse<T> implements Observer<T> {

    @Override
    public void onNext(@NotNull T t) {
        if (t instanceof BaseBean) {
//            if (t != null && ((BaseBean) t).getResultMessage() != null && ((BaseBean) t).getErrormsg().getToken() != null) {
//                Config.TOKEN = ((BaseBean) t).getErrormsg().getToken();
//                DebugLog.e("请求==tokenonNext===" + Config.TOKEN);
//            }
        }
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onSubscribe(@NotNull Disposable d) {
//        if (ActivityStackManager.getActivityStackManager().currentActivity() instanceof BaseActivity) {
//            ((BaseActivity) ActivityStackManager.getActivityStackManager().currentActivity()).addDisposable(d);
//        }
    }

    @Override
    public void onComplete() {

    }
}