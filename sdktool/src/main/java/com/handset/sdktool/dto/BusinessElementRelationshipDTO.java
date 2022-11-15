package com.handset.sdktool.dto;

import java.util.List;

/**
 * @ClassName: PrinterPaperRelationshipDTO
 * @author: wr
 * @date: 2022/11/4 17:40
 * @Description:作用描述
 */
public class BusinessElementRelationshipDTO {

    /**
     * printerId : 1
     * paperIdList : ["1","2"]
     */

    private String serviceId;
    private List<String> elementIdList;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public List<String> getElementIdList() {
        return elementIdList;
    }

    public void setElementIdList(List<String> elementIdList) {
        this.elementIdList = elementIdList;
    }
}
