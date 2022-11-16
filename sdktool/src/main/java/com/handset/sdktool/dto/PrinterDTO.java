package com.handset.sdktool.dto;

/**
 * @ClassName: PrinterDTO
 * @author: wr
 * @date: 2022/11/4 17:23
 * @Description:作用描述
 */
public class PrinterDTO {


    /**
     * id : 2
     * printerName : 打印机2
     * printerType : 针式打印机
     * connectionType : 蓝牙连接
     * supportWidth : 80.1
     */

    private String id;
    private String printerName;
    private String printerType;
    private String connectionType;
    private double supportWidth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterType() {
        return printerType;
    }

    public void setPrinterType(String printerType) {
        this.printerType = printerType;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public double getSupportWidth() {
        return supportWidth;
    }

    public void setSupportWidth(double supportWidth) {
        this.supportWidth = supportWidth;
    }

    @Override
    public String toString() {
        return "PrinterDTO{" +
                "id='" + id + '\'' +
                ", printerName='" + printerName + '\'' +
                ", printerType='" + printerType + '\'' +
                ", connectionType='" + connectionType + '\'' +
                ", supportWidth=" + supportWidth +
                '}';
    }
}
