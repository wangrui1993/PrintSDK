package com.handset.sdktool.listener;

import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.net.base.BaseBean;

import java.util.List;

/**
 * @ClassName: GetAllTemplateListener
 * @author: wr
 * @date: 2022/11/16 15:00
 * @Description:作用描述
 */
public interface GetBusinessServiceByCompanyIdListener {
    public void onSuccess(List<BusinessDTO> listBaseBean);

    public void onError(Throwable e);
}
