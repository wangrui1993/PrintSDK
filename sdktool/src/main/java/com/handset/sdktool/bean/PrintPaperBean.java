package com.handset.sdktool.bean;

import com.handset.sdktool.dto.PrinterDTO;
import com.handset.sdktool.dto.PaperDTO;

import java.util.List;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:作用描述
 */
public class PrintPaperBean {
    public PrintPaperBean(PrinterDTO printerDTO, List<PaperDTO> paperDTOList) {
        this.printerDTO = printerDTO;
        this.paperDTOList = paperDTOList;
    }


    public PrinterDTO printerDTO;
    public List<PaperDTO> paperDTOList;

    public PrinterDTO getPrinterDTO() {
        return printerDTO;
    }

    public void setPrinterDTO(PrinterDTO printerDTO) {
        this.printerDTO = printerDTO;
    }

    public List<PaperDTO> getPaperDTOList() {
        return paperDTOList;
    }

    public void setPaperDTOList(List<PaperDTO> paperDTOList) {
        this.paperDTOList = paperDTOList;
    }

    @Override
    public String toString() {
        return "PrintPaperBean{" +
                "printerDTO=" + printerDTO +
                ", paperDTOList=" + paperDTOList +
                '}';
    }
}
