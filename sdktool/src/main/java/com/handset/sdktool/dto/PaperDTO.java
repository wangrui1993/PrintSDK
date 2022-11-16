package com.handset.sdktool.dto;

/**
 * @ClassName: PrinterDTO
 * @author: wr
 * @date: 2022/11/4 17:23
 * @Description:作用描述
 */
public class PaperDTO {

    /**
     * id : 1
     * paperType : A4
     * paperWidth : 21.0
     * paperHeight : 29.7
     */

    private String id;
    private String paperType;
    private double paperWidth;
    private double paperHeight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public double getPaperWidth() {
        return paperWidth;
    }

    public void setPaperWidth(double paperWidth) {
        this.paperWidth = paperWidth;
    }

    public double getPaperHeight() {
        return paperHeight;
    }

    public void setPaperHeight(double paperHeight) {
        this.paperHeight = paperHeight;
    }

    @Override
    public String toString() {
        return "PaperDTO{" +
                "id='" + id + '\'' +
                ", paperType='" + paperType + '\'' +
                ", paperWidth=" + paperWidth +
                ", paperHeight=" + paperHeight +
                '}';
    }
}
