package com.handset.sdktool.dto;

/**
 * @ClassName: AddProfessionalWork
 * @author: wr
 * @date: 2022/11/4 15:52
 * @Description:添加业务
 */
public class BusinessDTO {
    public BusinessDTO() {
    }

    /**
     * @param servicetype 业务的名字
     */
    public BusinessDTO(String servicetype) {
        this(servicetype, null);
    }


    /**
     * @param servicetype   业务的名字
     * @param servicetypeNo 业务的唯一标识
     */
    public BusinessDTO(String servicetype, String servicetypeNo) {
        this.servicetype = servicetype;
    }

    /**
     * servicetype : 入库单
     * servicetypeNo : 1
     */
    private String servicetype;
    private String servicetypeNo;

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getServicetypeNo() {
        return servicetypeNo;
    }

    public void setServicetypeNo(String servicetypeNo) {
        this.servicetypeNo = servicetypeNo;
    }

    @Override
    public String toString() {
        return "AddProfessionalWork{" +
                "servicetype='" + servicetype + '\'' +
                ", servicetypeNo='" + servicetypeNo + '\'' +
                '}';
    }
}
