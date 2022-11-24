package com.handset.sdktool.event;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/**
 * 标签板
 */
public final class LabelBoard implements Cloneable {
    public static int HEIGHTDEFAULT = 50;
    public static int WIDTHDEFAULT = 30;
    //所在X Y轴坐标
    public int x = 0;
    public int y = 0;
    public static final Companion Companion = new Companion(null);
    //    private String backgroundUrl = "";
    private transient boolean encoded;
    public int height = 50;
    public int width = 30;
    private transient int heightOnScreen;
    private int id;
    private List<LabelBarcode> labelBarcodes = new ArrayList();
    private List<LabelDate> labelDates = new ArrayList();
    private List<LabelQRCode> labelQRCodes = new ArrayList();
    private List<LabelShape> labelShapes = new ArrayList();
    private List<LabelItem> labelItems = new ArrayList();
    private List<LabelText> labelTexts = new ArrayList();
    private String name;
    private String previewUrl = "";//对齐方式
    private int version;
    private transient int widthOnScreen;
    private String elementCode;//元素编码，对应字段code  (注意这个就是给子模板用的，因为模板类型给后台传element内容，后台存不上，所以存json里我自己取)

    public String getElementCode() {
        return elementCode;
    }

    public void setElementCode(String elementCode) {
        this.elementCode = elementCode;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isEncoded() {
        return encoded;
    }


    public LabelBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.width);
        sb.append('*');
        sb.append(this.height);
        this.name = sb.toString();
        this.version = 1;
        this.encoded = true;
        this.widthOnScreen = 480;
        this.heightOnScreen = 320;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final String getPreviewUrl() {
        return this.previewUrl;
    }

    public final void setPreviewUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.previewUrl = str;
    }

//    public final String getBackgroundUrl() {
//        return this.backgroundUrl;
//    }
//
//    public final void setBackgroundUrl(String str) {
//        Intrinsics.checkNotNullParameter(str, "<set-?>");
//        this.backgroundUrl = str;
//    }

    public final List<LabelText> getLabelTexts() {
        return this.labelTexts;
    }

    public final void setLabelTexts(List<LabelText> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.labelTexts = list;
    }

    public final List<LabelDate> getLabelDates() {
        return this.labelDates;
    }

    public final void setLabelDates(List<LabelDate> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.labelDates = list;
    }

//    public final List<LabelImage> getLabelImages() {
//        return this.labelImages;
//    }

//    public final void setLabelImages(List<LabelImage> list) {
//        Intrinsics.checkNotNullParameter(list, "<set-?>");
//        this.labelImages = list;
//    }

    public final List<LabelShape> getLabelShapes() {
        return this.labelShapes;
    }

    public final void setLabelShapes(List<LabelShape> list) {
        this.labelShapes = list;
    }

    public final List<LabelItem> getLabelItems() {
        return this.labelItems;
    }

    public final void setLabelItems(List<LabelItem> list) {
        this.labelItems = list;
    }

    public final List<LabelQRCode> getLabelQRCodes() {
        return this.labelQRCodes;
    }

    public final void setLabelQRCodes(List<LabelQRCode> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.labelQRCodes = list;
    }

//    public final List<LabelForm> getLabelForms() {
//        return this.labelForms;
//    }

//    public final void setLabelForms(List<LabelForm> list) {
//        Intrinsics.checkNotNullParameter(list, "<set-?>");
//        this.labelForms = list;
//    }

    public final List<LabelBarcode> getLabelBarcodes() {
        return this.labelBarcodes;
    }

    public final void setLabelBarcodes(List<LabelBarcode> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.labelBarcodes = list;
    }

    public final int getWidth() {
        return this.width;
    }

    public final void setWidth(int i) {
        this.width = i;
    }

    public final int getHeight() {
        return this.height;
    }

    public final void setHeight(int i) {
        this.height = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final int getVersion() {
        return this.version;
    }

    public final void setVersion(int i) {
        this.version = i;
    }

    public final boolean getEncoded() {
        return this.encoded;
    }

    public final void setEncoded(boolean z) {
        this.encoded = z;
    }

    public final int getWidthOnScreen() {
        return this.widthOnScreen;
    }

    public final void setWidthOnScreen(int i) {
        this.widthOnScreen = i;
    }

    public final int getHeightOnScreen() {
        return this.heightOnScreen;
    }

    public final void setHeightOnScreen(int i) {
        this.heightOnScreen = i;
    }

//    public final LabelBindExcel getExcel() {
//        return this.excel$1;
//    }

//    public final void setExcel(LabelBindExcel labelBindExcel) {
//        this.excel$1 = labelBindExcel;
//    }

    //    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/handset/data/entity/LabelBoard$Companion;", "", "()V", "excel", "Lcom/handset/data/entity/LabelBindExcel;", "getExcel", "()Lcom/handset/data/entity/LabelBindExcel;", "setExcel", "(Lcom/handset/data/entity/LabelBindExcel;)V", "lib-data_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* compiled from: LabelBoard.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

//        public final LabelBindExcel getExcel() {
//            return LabelBoard.excel;
//        }

//        public final void setExcel(LabelBindExcel labelBindExcel) {
//            LabelBoard.excel = labelBindExcel;
//        }
    }

    public final void clear() {
        this.labelTexts.clear();
        this.labelDates.clear();
//        this.labelImages.clear();
        this.labelShapes.clear();
        this.labelItems.clear();
        this.labelQRCodes.clear();
//        this.labelForms.clear();
        this.labelBarcodes.clear();
    }

    public final void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public String toString() {
        String json = new Gson().toJson(this);
        Intrinsics.checkNotNullExpressionValue(json, "Gson().toJson(this)");
        return json;
    }

    @Override // java.lang.Object
    public LabelBoard clone() {
        LabelBoard labelBoard;
        CloneNotSupportedException e;
        try {
            labelBoard = (LabelBoard) super.clone();
//            try {
            labelBoard.labelTexts = new ArrayList();
            labelBoard.labelDates = new ArrayList();
//                labelBoard.labelImages = new ArrayList();
            labelBoard.labelShapes = new ArrayList();
            labelBoard.labelItems = new ArrayList();
            labelBoard.labelQRCodes = new ArrayList();
//                labelBoard.labelForms = new ArrayList();
            labelBoard.labelBarcodes = new ArrayList();
            labelBoard.name = Intrinsics.stringPlus("", this.name);
            labelBoard.version = this.version;
//            } catch (CloneNotSupportedException e2) {
//                e = e2;
//            }
        } catch (CloneNotSupportedException e3) {
            labelBoard = null;
            e = e3;
            e.printStackTrace();
            Intrinsics.checkNotNull(labelBoard);
            return labelBoard;
        }
        Intrinsics.checkNotNull(labelBoard);
        return labelBoard;
    }
}
