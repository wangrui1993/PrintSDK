package com.handset.sdktool.modle;

import java.util.List;

/**
 * @ClassName: ModleData
 * @author: wr
 * @date: 2022/11/18 15:16
 * @Description:承载业务数据的实体
 */
public class ModleData {
    public String code;//元素的字段名
    public String value;//元素的值
    public List<ModleData> list2;//列表，列表中依然是元素
    public List<List<ModleData>> list1;//列表，列表中依然是元素

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ModleData> getList2() {
        return list2;
    }

    public void setList2(List<ModleData> list2) {
        this.list2 = list2;
    }

    public List<List<ModleData>> getList1() {
        return list1;
    }

    public void setList1(List<List<ModleData>> list1) {
        this.list1 = list1;
    }
}
