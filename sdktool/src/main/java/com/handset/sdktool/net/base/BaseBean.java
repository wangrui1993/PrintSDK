package com.handset.sdktool.net.base;

/**
 * @ClassName: BaseBean
 * @Author: Lau
 * @CreateDate: 2020/9/24 8:47
 * @Description:
 */
public class BaseBean<T> {
    private String status;
    private String resultMessage;
    private final String CODE_SUCCESS = "200";
    private String code;
    private String msg;
    private String time;

    private String total;
    private String pageTotal;
    private T data;
    private T rows;

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(String pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isCodeSuccess() {
        return status.equals(CODE_SUCCESS);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
