package com.handset.sdktool.event;

public class LabelItem extends Label {
    private String dataJson = "";
    private String componentInterval = "0";//组件间隔
    private String contentSource = CONTENTSOURCE_E;//内容来源(固定值：固定文字、元素字段、序号)
    private String templateId;//模板id
    private String childTemplateId;//子模板id


    public String getComponentInterval() {
        return componentInterval;
    }

    public void setComponentInterval(String componentInterval) {
        this.componentInterval = componentInterval;
    }

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }


    public String getChildTemplateId() {
        return childTemplateId;
    }

    public void setChildTemplateId(String childTemplateId) {
        this.childTemplateId = childTemplateId;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
