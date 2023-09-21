package com.handset.sdktool.dto;

import java.util.List;

/**
 * @ClassName: AddProfessionalWork
 * @author: wr
 * @date: 2022/11/4 15:52
 * @Description:添加公司
 */
public class DeleteDTO {
    public DeleteDTO() {
    }

    public DeleteDTO(String sysCfgId ) {
        this.sysCfgId = sysCfgId;
    }

    private String sysCfgId;

    public String getSysCfgId() {
        return sysCfgId;
    }

    public void setSysCfgId(String sysCfgId) {
        this.sysCfgId = sysCfgId;
    }
}
