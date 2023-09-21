package com.handset.sdktool.dto;

import java.util.List;

/**
 * @ClassName: AddProfessionalWork
 * @author: wr
 * @date: 2022/11/4 15:52
 * @Description:添加公司
 */
public class CompanyAssociationDTO {
    public CompanyAssociationDTO() {
    }

    public CompanyAssociationDTO(String companyId, List<String> ids) {
        this.companyId = companyId;
        this.templId = ids;
    }

    private String companyId;
    private List<String> templId;
}
