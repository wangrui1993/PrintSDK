package com.handset.sdktool.bean;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handset.sdktool.Config;
import com.handset.sdktool.dto.BusinessDTO;
import com.handset.sdktool.dto.ElementDTO;
import com.handset.sdktool.util.SharedPreferenceUtil;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: DataUtil
 * @author: wr
 * @date: 2022/11/15 16:07
 * @Description:作用描述
 */
public class BusinessElementBean {
    public BusinessElementBean(BusinessDTO businessDTO, List<ElementDTO> elementDTOList) {
        this.businessDTO = businessDTO;
        this.elementDTOList = elementDTOList;
    }


    public BusinessDTO businessDTO;
    public List<ElementDTO> elementDTOList;

    public BusinessDTO getBusinessDTO() {
        return businessDTO;
    }

    public void setBusinessDTO(BusinessDTO businessDTO) {
        this.businessDTO = businessDTO;
    }

    public List<ElementDTO> getElementDTOList() {
        return elementDTOList;
    }

    public void setElementDTOList(List<ElementDTO> elementDTOList) {
        this.elementDTOList = elementDTOList;
    }
}
