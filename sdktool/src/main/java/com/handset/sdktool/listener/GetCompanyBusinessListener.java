package com.handset.sdktool.listener;

import com.handset.sdktool.dto.BusinessDTO;

import java.util.List;

/**
 * @ClassName: GetAllTemplateListener
 * @author: wr
 * @date: 2022/11/16 15:00
 * @Description:作用描述
 */
public interface GetCompanyBusinessListener {
    public void onSuccess(List<BusinessDTO> business);

    public void onError(Throwable e);
}
