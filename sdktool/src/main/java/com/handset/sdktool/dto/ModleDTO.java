package com.handset.sdktool.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: AddProfessionalWork
 * @author: wr
 * @date: 2022/11/4 15:52
 * @Description:添加模板
 */
public class ModleDTO implements Serializable {

    /**
     * template : {"id":"1","templateName":"模板1","businesstypeId":"2","printerId":"1","paperId":"1","width":29,"height":21}
     * components : [{"id":"7","componentTypeId":"1","componentContent":"1","prefix":"物料名称：","suffix":null,"size":"1","alignType":"左侧对齐","coordX":100,"coordY":100,"fontBold":"1","componentWidth":200,"componentHeight":30,"componentInterval":10,"encodingType":null,"contentSource":"元素字段","templateId":"1","aboveComponentId":null,"childTemplateId":null},{"id":"8","componentTypeId":"1","componentContent":"2","prefix":"物料编码：","suffix":null,"size":"1","alignType":"左侧对齐","coordX":300,"coordY":200,"fontBold":"1","componentWidth":200,"componentHeight":30,"componentInterval":10,"encodingType":null,"contentSource":"元素字段","templateId":"1","aboveComponentId":"1","childTemplateId":null},{"id":"9","componentTypeId":"4","componentContent":"3","prefix":"","suffix":null,"size":"1","alignType":"左侧对齐","coordX":500,"coordY":300,"fontBold":"1","componentWidth":200,"componentHeight":30,"componentInterval":10,"encodingType":null,"contentSource":"元素字段","templateId":"1","aboveComponentId":"2","childTemplateId":"2"}]
     */
    private int height; //高度
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }


    private TemplateBean template;
    private List<ComponentsBean> components;

    public TemplateBean getTemplate() {
        return template;
    }

    public void setTemplate(TemplateBean template) {
        this.template = template;
    }

    public List<ComponentsBean> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentsBean> components) {
        this.components = components;
    }

    public static class TemplateBean implements Serializable {
        /**
         * id : 1
         * templateName : 模板1
         * businesstypeId : 2
         * printerId : 1
         * paperId : 1
         * width : 29.0
         * height : 21.0
         */

        public TemplateBean() {
        }

        public TemplateBean(String id, String templateName, String businesstypeId
                , String printerId, String paperId, double width, double height) {
            this.id = id;
            this.templateName = templateName;
            this.businesstypeId = businesstypeId;
            this.printerId = printerId;
            this.paperId = paperId;
            this.width = width;
            this.height = height;
        }

        private String id;
        private String templateName;
        private String businesstypeId;
        private String printerId;
        private String paperId;
        private double width;
        private double height;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTemplateName() {
            return templateName;
        }

        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        public String getBusinesstypeId() {
            return businesstypeId;
        }

        public void setBusinesstypeId(String businesstypeId) {
            this.businesstypeId = businesstypeId;
        }

        public String getPrinterId() {
            return printerId;
        }

        public void setPrinterId(String printerId) {
            this.printerId = printerId;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "TemplateBean{" +
                    "id='" + id + '\'' +
                    ", templateName='" + templateName + '\'' +
                    ", businesstypeId='" + businesstypeId + '\'' +
                    ", printerId='" + printerId + '\'' +
                    ", paperId='" + paperId + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

    public static class ComponentsBean implements Serializable {
        /**
         * id : 7
         * componentTypeId : 1
         * componentContent : 1
         * prefix : 物料名称：
         * suffix : null
         * size : 1
         * alignType : 左侧对齐
         * coordX : 100
         * coordY : 100
         * fontBold : 1
         * componentWidth : 200
         * componentHeight : 30
         * componentInterval : 10
         * encodingType : null
         * contentSource : 元素字段
         * templateId : 1
         * aboveComponentId : null
         * childTemplateId : null
         */

        private String id;
        private String componentTypeId;
        private String componentContent;//关联的元素的id   或内容（固定文字或序号）
        private String prefix;
        private String suffix;
        private String size;
        private String alignType;
        private int coordX;
        private int coordY;
        private String fontBold;
        private int componentWidth;
        private int componentHeight;
        private int componentInterval;
        private String encodingType;
        private String contentSource;
        private String templateId;
        private String aboveComponentId;
        private String childTemplateId;
        private BigDecimal paperCoordProportion;
        private ElementsBean element;

        private int bottomY=0;//作为列表底部Y轴高

        public int getBottomY() {
            return bottomY;
        }

        public void setBottomY(int bottomY) {
            this.bottomY = bottomY;
        }

        //        private String father_elementCode;//有这个值说明是列表中的元素，好让我在赋值的时候判断


//        public String getFather_elementCode() {
//            return father_elementCode;
//        }
//
//        public void setFather_elementCode(String father_elementCode) {
//            this.father_elementCode = father_elementCode;
//        }

        public BigDecimal getPaperCoordProportion() {
            return paperCoordProportion;
        }

        public void setPaperCoordProportion(BigDecimal paperCoordProportion) {
            this.paperCoordProportion = paperCoordProportion;
        }


        public ElementsBean getElement() {
            return element;
        }

        public void setElement(ElementsBean element) {
            this.element = element;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getComponentTypeId() {
            return componentTypeId;
        }

        public void setComponentTypeId(String componentTypeId) {
            this.componentTypeId = componentTypeId;
        }

        public String getComponentContent() {
            return componentContent;
        }

        public void setComponentContent(String componentContent) {
            this.componentContent = componentContent;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getAlignType() {
            return alignType;
        }

        public void setAlignType(String alignType) {
            this.alignType = alignType;
        }

        public int getCoordX() {
            return coordX;
        }

        public void setCoordX(int coordX) {
            this.coordX = coordX;
        }

        public int getCoordY() {
            return coordY;
        }

        public void setCoordY(int coordY) {
            this.coordY = coordY;
        }

        public String getFontBold() {
            return fontBold;
        }

        public void setFontBold(String fontBold) {
            this.fontBold = fontBold;
        }

        public int getComponentWidth() {
            return componentWidth;
        }

        public void setComponentWidth(int componentWidth) {
            this.componentWidth = componentWidth;
        }

        public int getComponentHeight() {
            return componentHeight;
        }

        public void setComponentHeight(int componentHeight) {
            this.componentHeight = componentHeight;
        }

        public int getComponentInterval() {
            return componentInterval;
        }

        public void setComponentInterval(int componentInterval) {
            this.componentInterval = componentInterval;
        }

        public String getEncodingType() {
            return encodingType;
        }

        public void setEncodingType(String encodingType) {
            this.encodingType = encodingType;
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

        public String getAboveComponentId() {
            return aboveComponentId;
        }

        public void setAboveComponentId(String aboveComponentId) {
            this.aboveComponentId = aboveComponentId;
        }

        public String getChildTemplateId() {
            return childTemplateId;
        }

        public void setChildTemplateId(String childTemplateId) {
            this.childTemplateId = childTemplateId;
        }

        @Override
        public String toString() {
            return "ComponentsBean{" +
                    "id='" + id + '\'' +
                    ", componentTypeId='" + componentTypeId + '\'' +
                    ", componentContent='" + componentContent + '\'' +
                    ", prefix='" + prefix + '\'' +
                    ", suffix='" + suffix + '\'' +
                    ", size='" + size + '\'' +
                    ", alignType='" + alignType + '\'' +
                    ", coordX=" + coordX +
                    ", coordY=" + coordY +
                    ", fontBold='" + fontBold + '\'' +
                    ", componentWidth=" + componentWidth +
                    ", componentHeight=" + componentHeight +
                    ", componentInterval=" + componentInterval +
                    ", encodingType='" + encodingType + '\'' +
                    ", contentSource='" + contentSource + '\'' +
                    ", templateId='" + templateId + '\'' +
                    ", aboveComponentId='" + aboveComponentId + '\'' +
                    ", childTemplateId='" + childTemplateId + '\'' +
                    '}';
        }

        public static class ElementsBean implements Serializable {

            /**
             * elementCode : 1667791189033
             * elementName : 名称7
             * elementDesc : 采购物料名称
             * elementType : 1
             * listElementCode : null
             * referenceCount : 1
             * isHistory : 0
             * elementExample : null
             */

            private String elementCode;
            private String elementName;
            private String elementDesc;
            private String elementType;
            private Object listElementCode;
            private int referenceCount;
            private String isHistory;
            private Object elementExample;

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

            public Object getListElementCode() {
                return listElementCode;
            }

            public void setListElementCode(Object listElementCode) {
                this.listElementCode = listElementCode;
            }

            public int getReferenceCount() {
                return referenceCount;
            }

            public void setReferenceCount(int referenceCount) {
                this.referenceCount = referenceCount;
            }

            public String getIsHistory() {
                return isHistory;
            }

            public void setIsHistory(String isHistory) {
                this.isHistory = isHistory;
            }

            public Object getElementExample() {
                return elementExample;
            }

            public void setElementExample(Object elementExample) {
                this.elementExample = elementExample;
            }

            @Override
            public String toString() {
                return "ElementsBean{" +
                        "elementCode='" + elementCode + '\'' +
                        ", elementName='" + elementName + '\'' +
                        ", elementDesc='" + elementDesc + '\'' +
                        ", elementType='" + elementType + '\'' +
                        ", listElementCode=" + listElementCode +
                        ", referenceCount=" + referenceCount +
                        ", isHistory='" + isHistory + '\'' +
                        ", elementExample=" + elementExample +
                        '}';
            }
        }
    }
}
