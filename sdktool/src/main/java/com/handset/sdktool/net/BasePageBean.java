package com.handset.sdktool.net;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BasePageBean
 * @Author: wr
 * @CreateDate: 2021/1/20 9:11
 * @Description:
 */
public class BasePageBean<T> {
    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;
    private List<T> rows = new ArrayList<>();

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
