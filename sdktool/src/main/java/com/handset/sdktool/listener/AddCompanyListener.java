package com.handset.sdktool.listener;

import com.handset.sdktool.net.base.BaseBean;
import com.handset.sdktool.net.base.ModleListBean;

import java.util.List;

/**
 * @ClassName: GetAllTemplateListener
 * @author: wr
 * @date: 2022/11/16 15:00
 * @Description:作用描述
 */
public interface AddCompanyListener {
    public void onSuccess(BaseBean listBaseBean);

    public void onError(Throwable e);
}
