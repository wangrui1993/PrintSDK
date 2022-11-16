package com.handset.sdktool.dto;

/**
 * @ClassName: ElementDTO
 * @author: wr
 * @date: 2022/11/6 10:14
 * @Description:元素
 */
public class ElementDTO {
    /**
     * id : 1
     * elementCode : name
     * elementName : 名称
     * elementDesc : 采购物料名称
     * elementType : 1
     */

    public ElementDTO() {
    }

    public ElementDTO(String id) {
        this.id = id;
    }

    public ElementDTO(String id, String elementName, String elementDesc, String elementType) {
        this.id = id;
        this.elementName = elementName;
        this.elementDesc = elementDesc;
        this.elementType = elementType;
    }

    private String id;
    private String elementCode;//元素编码，对应字段code
    private String elementName;//元素名称，对应字段名称
    private String elementDesc;//元素描述，对应字段含义/字段描述
    private String elementType;//（“1”表示文字，“2”表示列表）
    private String listElementCode;//元素为对象列表时，此处为列表对象的code



    private String referenceCount;//引用次数，元素被组件引用的次数
    private String isHistory;//是否为历史版本（“0”表示现行版本，“1”表示历史版本）

    public String getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(String referenceCount) {
        this.referenceCount = referenceCount;
    }

    public String getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(String isHistory) {
        this.isHistory = isHistory;
    }

    public String getListElementCode() {
        return listElementCode;
    }

    public void setListElementCode(String listElementCode) {
        this.listElementCode = listElementCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getElementCode() {
        return elementCode;
    }

    public void setElementCode(String elementCode) {
        this.elementCode = elementCode;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementDesc() {
        return elementDesc;
    }

    public void setElementDesc(String elementDesc) {
        this.elementDesc = elementDesc;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }
}
