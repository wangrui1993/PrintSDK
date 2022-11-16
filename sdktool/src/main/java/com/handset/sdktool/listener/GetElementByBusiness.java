package com.handset.sdktool.listener;

import com.handset.sdktool.dto.ElementDTO;

import java.util.List;

/**
 * @ClassName: GetElementByBusiness
 * @author: wr
 * @date: 2022/11/16 15:15
 * @Description:作用描述
 */
public interface GetElementByBusiness {
    public void onSuccess(List<ElementDTO> listBaseBean);

    public void onError(Throwable e);
}
