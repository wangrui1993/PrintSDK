package com.handset.sdktool.listener;

import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.dto.ModleDTO;

import java.util.List;

/**
 * @ClassName: GetTemplateByBusinessCode
 * @author: wr
 * @date: 2022/11/18 10:47
 * @Description:作用描述
 */
public interface GetTemplateByBusinessCode {
    public void onSuccess(ModleDTO listBaseBean);

    public void onError(Throwable e);
}
