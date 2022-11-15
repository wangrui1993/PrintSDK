package com.handset.sdktool.dto;

import java.util.List;

/**
 * @ClassName: PrinterPaperRelationshipDTO
 * @author: wr
 * @date: 2022/11/4 17:40
 * @Description:作用描述
 */
public class PrinterPaperRelationshipDTO {

    /**
     * printerId : 1
     * paperIdList : ["1","2"]
     */

    private String printerId;
    private List<String> paperIdList;

    public String getPrinterId() {
        return printerId;
    }

    public void setPrinterId(String printerId) {
        this.printerId = printerId;
    }

    public List<String> getPaperIdList() {
        return paperIdList;
    }

    public void setPaperIdList(List<String> paperIdList) {
        this.paperIdList = paperIdList;
    }
}
