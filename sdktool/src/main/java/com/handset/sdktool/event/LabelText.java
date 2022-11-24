package com.handset.sdktool.event;

import android.graphics.Typeface;


import io.reactivex.Single;
import kotlin.jvm.internal.Intrinsics;

public class LabelText extends Label {
    private String content = "";
    private int excelCol;
    private int fontColor = -16777216;
    private int fontId = 1;
    private int fontSize = 20;
    private float horizontalSpace;
    private int increase = 1;
    private boolean isBindExcel;
    private boolean isBold;
    private boolean isIncrease;
    private boolean isItalic;
    private boolean isThru;
    private boolean isUnderline;
    private Typeface typeface;
    private float verticalSpace = 1.0f;
    private String prefix = "";//前缀
    private String suffix = "";//后缀

    private String componentInterval = "0";//组件间隔
    private String contentSource = CONTENTSOURCE_E;//内容来源(固定值：固定文字、元素字段、序号)
    private String templateId;//模板id


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


    public LabelText() {
        setWidth(1.0f);
        setHeight(1.0f);
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

    public final int getFontSize() {
        return this.fontSize;
    }

    public final void setFontSize(int i) {
        boolean z = false;
        if (5 <= i && i <= 500) {
            z = true;
        }
        if (z) {
            this.fontSize = i;
        }
    }

    public final int getFontColor() {
        return this.fontColor;
    }

    public final void setFontColor(int i) {
        this.fontColor = i;
    }

    public final String getContent() {
        return this.content;
    }

    public final void setContent(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.content = str;
    }

    public final int getFontId() {
        return this.fontId;
    }

    public final void setFontId(int i) {
        this.fontId = i;
    }

    public final boolean isBold() {
        return this.isBold;
    }

    public final void setBold(boolean z) {
        this.isBold = z;
    }

    public final boolean isItalic() {
        return this.isItalic;
    }

    public final void setItalic(boolean z) {
        this.isItalic = z;
    }

    public final boolean isIncrease() {
        return this.isIncrease;
    }

    public final void setIncrease(boolean z) {
        this.isIncrease = z;
    }

    public final boolean isBindExcel() {
        return this.isBindExcel;
    }

    public final void setBindExcel(boolean z) {
        this.isBindExcel = z;
    }

    public final boolean isUnderline() {
        return this.isUnderline;
    }

    public final void setUnderline(boolean z) {
        this.isUnderline = z;
    }

    public final boolean isThru() {
        return this.isThru;
    }

    public final void setThru(boolean z) {
        this.isThru = z;
    }

    public final int getIncrease() {
        return this.increase;
    }

    public final void setIncrease(int i) {
        this.increase = i;
    }

    public final float getHorizontalSpace() {
        return this.horizontalSpace;
    }

    public final void setHorizontalSpace(float f) {
        this.horizontalSpace = f;
    }

    public final float getVerticalSpace() {
        return this.verticalSpace;
    }

    public final void setVerticalSpace(float f) {
        this.verticalSpace = f;
    }

    public final int getExcelCol() {
        return this.excelCol;
    }

    public final void setExcelCol(int i) {
        this.excelCol = i;
    }

    public final Typeface getTypeface() {
        return this.typeface;
    }

    public final void setTypeface(Typeface typeface2) {
        this.typeface = typeface2;
    }

    public final Single<String> nextContent() {
        if (this.isIncrease) {
            Single<String> just = Single.just(serialAdd(this.content, this.increase));
            Intrinsics.checkNotNullExpressionValue(just, "just(serialAdd(content, increase))");
            return just;
        } else if (this.excelCol < 0) {
            Single<String> just3 = Single.just(this.content);
            Intrinsics.checkNotNullExpressionValue(just3, "just(content)");
            return just3;
        } else {
            Single<String> just3 = Single.just(this.content);
            Intrinsics.checkNotNullExpressionValue(just3, "just(content)");
            return just3;
        }
    }


    public final Single<String> preContent() {
        if (this.isIncrease) {
            Single<String> just = Single.just(serialAdd(this.content, -this.increase));
            Intrinsics.checkNotNullExpressionValue(just, "just(serialAdd(content, -increase))");
            return just;
        } else if (this.excelCol < 0) {
            Single<String> just3 = Single.just(this.content);
            Intrinsics.checkNotNullExpressionValue(just3, "just(content)");
            return just3;
        } else {
            Single<String> just3 = Single.just(this.content);
            return just3;
        }

    }

}
