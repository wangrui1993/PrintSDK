package com.handset.sdktool.dto;

/**
 * @ClassName: AddProfessionalWork
 * @author: wr
 * @date: 2022/11/4 15:52
 * @Description:添加公司
 */
public class CompanyDTO {
    public CompanyDTO() {
    }

    public CompanyDTO(String companyName, String ip, String domain) {
        this.companyName = companyName;
        this.ip = ip;
        this.domain = domain;
    }

    /**
     *
     */
    public CompanyDTO(String id, String companyName, String ip, String domain) {
        this.id = id;
        this.companyName = companyName;
        this.ip = ip;
        this.domain = domain;
    }

    private String id;//公司id 打印服务分配的 用于请求
    private String companyName;
    private String ip; // 独立IP的服务IP 非独立IP用户的
    private String domain;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
