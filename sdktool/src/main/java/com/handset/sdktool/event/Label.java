package com.handset.sdktool.event;

//import com.gainscha.sdk.y;

import com.google.gson.Gson;

import java.math.BigInteger;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
//import org.apache.poi.ss.util.CellUtil;

public class Label implements Cloneable {

    public static final String CONTENTSOURCE_TEXT = "固定文字";//内容来源(固定值：固定文字、元素字段、序号)
    public static final String CONTENTSOURCE_E = "元素字段";//内容来源(固定值：固定文字、元素字段、序号)
    public static final String CONTENTSOURCE_S= "序号";//内容来源(固定值：固定文字、元素字段、序号)
    public static final Companion Companion = new Companion(null);
    private static long id = 1;
    private transient boolean enable = true;
    private float height = 5.0f;
    private long id$1 = Companion.generateID();
    private transient boolean labelSelected;
    private boolean locked;
    private int priority;
    private float rotation;
    private float width = 50.0f;
    private String componentId;//组件ID

    private float x;
    private float y;

    //所关联的元素信息
    private String elementId;//元素主键
    private String elementCode;//元素编码，对应字段code
    private String elementName;//元素名称，对应字段名称
    private String elementDesc;//元素描述，对应字段含义/字段描述
    private String alignType = "";//对齐方式
    private String aboveComponentId= "";//上方组件id

    public String getAlignType() {
        return alignType;
    }

    public void setAlignType(String alignType) {
        this.alignType = alignType;
    }

    public String getAboveComponentId() {
        return aboveComponentId;
    }

    public void setAboveComponentId(String aboveComponentId) {
        this.aboveComponentId = aboveComponentId;
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

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float f) {
        if (f == 0.0f) {
            f = 1.0f;
        }
        this.width = Math.abs(f);
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float f) {
        if (f == 0.0f) {
            f = 1.0f;
        }
        this.height = Math.abs(f);
    }

    public final float getX() {
        return this.x;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final float getY() {
        return this.y;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public final float getRotation() {
        return this.rotation;
    }

    public final void setRotation(float f) {
        this.rotation = f;
    }

    public final boolean getLocked() {
        return this.locked;
    }

    public final void setLocked(boolean z) {
        this.locked = z;
    }

    public final int getPriority() {
        return this.priority;
    }

    public final void setPriority(int i) {
        this.priority = i;
    }

    public final long getId() {
        return this.id$1;
    }

    public final void setId(long j) {
        this.id$1 = j;
    }

    public final boolean getEnable() {
        return this.enable;
    }

    public final void setEnable(boolean z) {
        this.enable = z;
    }

    public final boolean getLabelSelected() {
        return this.labelSelected;
    }

    public final void setLabelSelected(boolean z) {
        this.labelSelected = z;
    }

    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public long generateID() {
            long j = Label.id;
            Label.id = 1 + j;
            return j;
        }
    }

    public String toString() {
        String json = new Gson().toJson(this);
        Intrinsics.checkNotNullExpressionValue(json, "Gson().toJson(this)");
        return json;
    }

    public final String serialAdd(String str, int i) {
        String str2;
        Intrinsics.checkNotNullParameter(str, "serialContent");
        boolean z = true;
        if ((StringsKt.trim(str).toString().length() == 0) || i == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length() - 1;
        if (length >= 0) {
            while (true) {
                int i2 = length - 1;
                char charAt = str.charAt(length);
                if (!('0' <= charAt && charAt <= '9')) {
                    break;
                }
                sb.append(charAt);
                if (i2 < 0) {
                    break;
                }
                length = i2;
            }
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "barcodeSerialStr.toString()");
        if (sb2.length() > 0) {
            sb = new StringBuilder();
            int length2 = sb2.length() - 1;
            if (length2 >= 0) {
                while (true) {
                    int i3 = length2 - 1;
                    sb.append(sb2.charAt(length2));
                    if (i3 < 0) {
                        break;
                    }
                    length2 = i3;
                }
            }
        }
        if (!(sb.length() > 0)) {
            return serialAdd(Intrinsics.stringPlus(str, "1"), i);
        }
        if (!Intrinsics.areEqual(sb.toString(), str)) {
            str2 = str.substring(0, str.length() - sb.length());
            Intrinsics.checkNotNullExpressionValue(str2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        } else {
            str2 = "";
        }
        StringBuilder sb3 = new StringBuilder();
        String sb4 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb4, "barcodeSerialStr.toString()");
        char[] charArray = sb4.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "(this as java.lang.String).toCharArray()");
        int length3 = charArray.length;
        int i4 = 0;
        while (i4 < length3) {
            char c = charArray[i4];
            i4++;
            if (c != '0') {
                break;
            }
            sb3.append('0');
        }
        if (sb3.length() <= 0) {
            z = false;
        }
        if (z) {
            str2 = Intrinsics.stringPlus(str2, sb3);
        }
        BigInteger add = new BigInteger(sb.toString()).add(new BigInteger(String.valueOf(i)));
        Intrinsics.checkNotNullExpressionValue(add, "barcodeSerialInt.add(BigInteger(serialIncrease.toString()))");
        return Intrinsics.stringPlus(str2, add);
    }

    @Override
    public Label clone() {
        Label label;
        try {
            label = (Label) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            label = null;
        }
        Intrinsics.checkNotNull(label);
        return label;
    }
}
