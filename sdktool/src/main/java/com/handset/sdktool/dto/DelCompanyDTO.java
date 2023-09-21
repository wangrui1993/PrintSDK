package com.handset.sdktool.dto;

import java.util.List;

/**
 * @ClassName: AddProfessionalWork
 * @author: wr
 * @date: 2022/11/4 15:52
 * @Description:添加公司
 */
public class DelCompanyDTO {
    public DelCompanyDTO() {
    }

    public DelCompanyDTO(List<String> ids) {
        this.ids = ids;
    }

    private List<String> ids;
}
