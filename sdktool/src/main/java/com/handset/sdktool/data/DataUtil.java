package com.handset.sdktool.data;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.handset.sdktool.dto.PrinterDTO;
import com.handset.sdktool.net.NetUtil;
import com.handset.sdktool.net.OnResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:作用描述
 */
public class DataUtil {
    private DataUtil() {

    }

    ;
    private static DataUtil dataUtil = new DataUtil();

    public static DataUtil getInstance() {
        return dataUtil;
    }

    /**
     * 获取所有打印机
     */
    public List<PrinterDTO> getPrits(Context context) {
        List<PrinterDTO> list = new ArrayList<>();
        NetUtil.getInstance().api().getAllPrinter()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnResponse<List<PrinterDTO>>() {
                    @Override
                    public void onNext(List<PrinterDTO> listBaseBean) {
                        list.addAll(listBaseBean);
                        Log.e("sdfdfd===sss1", list.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError===", e.getMessage() + "===" + e.getLocalizedMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return list;
    }

}
