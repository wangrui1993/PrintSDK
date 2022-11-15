package com.handset.sdktool.net;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: ApiGo
 * @Author: wr
 * @CreateDate: 2020/10/30 13:09
 * @Description:
 */
public class ApiGo {
    public static <T> void api(Observable<T> observable, final OnResponse<T> onResponse) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        onResponse.onSubscribe(d);
                    }

                    @Override
                    public void onNext(T t) {
                        onResponse.onNext(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onResponse.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        onResponse.onComplete();
                    }
                });

    }
}
