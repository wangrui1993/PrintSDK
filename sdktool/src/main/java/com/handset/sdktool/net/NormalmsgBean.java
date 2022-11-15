package com.handset.sdktool.net;

import java.util.List;

public class NormalmsgBean<T> {
    private T data;
    private String updateflag;
    private String pushmsgnum;
    private List<PushMsgBean> pushmsg;

    public List<PushMsgBean> getPushmsg() {
        return pushmsg;
    }

    public void setPushmsg(List<PushMsgBean> pushmsg) {
        this.pushmsg = pushmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUpdateflag() {
        return updateflag;
    }

    public void setUpdateflag(String updateflag) {
        this.updateflag = updateflag;
    }

    public String getPushmsgnum() {
        return pushmsgnum;
    }

    public void setPushmsgnum(String pushmsgnum) {
        this.pushmsgnum = pushmsgnum;
    }

    @Override
    public String toString() {
        return "NormalmsgListBean{" +
                "data=" + data +
                ", updateflag='" + updateflag + '\'' +
                ", pushmsgnum='" + pushmsgnum + '\'' +
                '}';
    }
}
