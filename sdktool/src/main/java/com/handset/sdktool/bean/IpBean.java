package com.handset.sdktool.bean;

/**
 * @ClassName: IpBean
 * @author: wr
 * @date: 2023/9/16 17:15
 * @Description:作用描述
 */
public class IpBean {
    public String ip;
    public String name;

    public IpBean(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    public IpBean() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
