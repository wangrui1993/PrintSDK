package com.handset.sdktool.base;


import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.ModleDTO;

import java.util.List;

/**
 * @ClassName: ModleListBean
 * @author: wr
 * @date: 2022/11/8 14:25
 * @Description:作用描述
 */
public class ModleListBean {

    private BusinessDTO service;
    private List<ModleDTO> templateList;

    public BusinessDTO getService() {
        return service;
    }

    public void setService(BusinessDTO service) {
        this.service = service;
    }

    public List<ModleDTO> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<ModleDTO> templateList) {
        this.templateList = templateList;
    }

    @Override
    public String toString() {
        return "ModleListBean{" +
                "service=" + service +
                ", templateList=" + templateList +
                '}';
    }
}
