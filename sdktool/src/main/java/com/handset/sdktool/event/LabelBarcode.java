package com.handset.sdktool.event;

//import com.example.printbulider.data.DataRepository;
//import com.handset.data.DataRepository;
//import com.handset.data.entity.db.ExcelRow;

import io.reactivex.Single;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class LabelBarcode extends Label {
    public static final Companion Companion = new Companion(null);
    public static final String ENCODE_TYPE_CODE128 = "CODE128";
    public static final String ENCODE_TYPE_CODE39 = "";
    public static final String ENCODE_TYPE_EAN13 = "";
    public static final String ENCODE_TYPE_EAN8 = "";
    public static final String ENCODE_TYPE_ITF = "";
    public static final String ENCODE_TYPE_UPC_A = "";
    public static final String ENCODE_TYPE_UPC_E = "";
    public static final String TEXT_ALIGN_CENTER = "";
    public static final String TEXT_ALIGN_JUSTIFY = "";
    public static final String TEXT_ALIGN_LEFT = "";
    public static final String TEXT_ALIGN_RIGHT = "";
    public static final String TEXT_LOCATION_BOTTOM = "";
    public static final String TEXT_LOCATION_NONE = "";
    public static final String TEXT_LOCATION_TOP = "";
    private String content = "";
    private String encodeType = ENCODE_TYPE_CODE128;
    private int excelCol = -1;
    private int fontSize = 25;
    private int increment = 1;
    private boolean isBindExcel;
    private boolean isIncrease;
    private boolean strictWidthScale;
    private String textAlignment = TEXT_ALIGN_CENTER;
    private String textLocation = TEXT_LOCATION_BOTTOM;
    private String contentSource = "";//内容来源(固定值：固定文字、元素字段、序号)

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource;
    }



    public boolean isStrictWidthScale() {
        return strictWidthScale;
    }


    public LabelBarcode() {
        setWidth(540.0f);
        setHeight(180.0f);
    }

    public final boolean isIncrease() {
        return this.isIncrease;
    }

    public final void setIncrease(boolean z) {
        this.isIncrease = z;
    }

    public final int getIncrement() {
        return this.increment;
    }

    public final void setIncrement(int i) {
        this.increment = i;
    }

    public final boolean isBindExcel() {
        return this.isBindExcel;
    }

    public final void setBindExcel(boolean z) {
        this.isBindExcel = z;
    }

    public final String getEncodeType() {
        return this.encodeType;
    }

    public final void setEncodeType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.encodeType = str;
    }

    public final String getContent() {
        return this.content;
    }

    public final String getTextLocation() {
        return this.textLocation;
    }

    public final void setTextLocation(String str) {
        Intrinsics.checkNotNullParameter(str, "textLocation");
        if (!Intrinsics.areEqual(TEXT_LOCATION_NONE, str) && !Intrinsics.areEqual(TEXT_LOCATION_TOP, str) && !Intrinsics.areEqual(TEXT_LOCATION_BOTTOM, str)) {
            str = TEXT_LOCATION_NONE;
        }
        this.textLocation = str;
    }

    public final String getTextAlignment() {
        return this.textAlignment;
    }

    public final void setTextAlignment(String str) {
        Intrinsics.checkNotNullParameter(str, "textAlignment");
        if (!Intrinsics.areEqual(TEXT_ALIGN_LEFT, str) && !Intrinsics.areEqual(TEXT_ALIGN_RIGHT, str) && !Intrinsics.areEqual(TEXT_ALIGN_CENTER, str) && !Intrinsics.areEqual(TEXT_ALIGN_JUSTIFY, str)) {
            str = TEXT_ALIGN_CENTER;
        }
        this.textAlignment = str;
    }

    public final int getFontSize() {
        return this.fontSize;
    }

    public final void setFontSize(int i) {
        this.fontSize = i;
    }

    public final int getExcelCol() {
        return this.excelCol;
    }

    public final void setExcelCol(int i) {
        this.excelCol = i;
    }

    public final boolean getStrictWidthScale() {
        return this.strictWidthScale;
    }

    public final void setStrictWidthScale(boolean z) {
        this.strictWidthScale = z;
    }

    public final void setContent(String str) {
        int i;
        Intrinsics.checkNotNullParameter(str, "content");
        try {
            String str2 = this.encodeType;
            switch (str2.hashCode()) {
                case 72827:
                    if (str2.equals(ENCODE_TYPE_ITF)) {
                        i = 13;
                        break;
                    } else {
                        i = -1;
                        break;
                    }
                case 2120518:
                    if (!str2.equals("EAN8")) {
                        i = -1;
                        break;
                    }
                    i = 7;
                    break;
                case 65735892:
                    if (str2.equals("EAN13")) {
                        i = 12;
                        break;
                    } else {
                        i = -1;
                        break;
                    }
                case 80948412:
                    if (str2.equals(ENCODE_TYPE_UPC_A)) {
                        i = 11;
                        break;
                    } else {
                        i = -1;
                        break;
                    }
                case 80948416:
                    if (!str2.equals(ENCODE_TYPE_UPC_E)) {
                        i = -1;
                        break;
                    }
                    i = 7;
                    break;
                default:
                    i = -1;
                    break;
            }
            if (i == -1) {
                this.content = str;
            } else if (str.length() == i) {
                this.content = str;
            } else if (str.length() > i) {
                String substring = str.substring(0, i + 1);
                Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                this.content = substring;
            } else {
//                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
//                StringBuilder sb = new StringBuilder();
//                sb.append('%');
//                sb.append(i);
//                sb.append('s');
//                String format = String.format(sb.toString(), Arrays.copyOf(new Object[]{str}, 1));
//                Intrinsics.checkNotNullExpressionValue(format, "java.lang.String.format(format, *args)");
//                this.content = StringsKt.replace$default(format, " ", "0", false, 4, (Object) null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final Single<String> nextContent() {
        if (this.isIncrease) {
            Single<String> just = Single.just(serialAdd(this.content, this.increment));
            Intrinsics.checkNotNullExpressionValue(just, "just(serialAdd(content, increment))");
            return just;
        }
//        else if (!this.isBindExcel || LabelBoard.Companion.getExcel() == null) {
//            Single<String> just2 = Single.just(this.content);
//            Intrinsics.checkNotNullExpressionValue(just2, "just(content)");
//            return just2;
//        }
        else if (this.excelCol < 0) {
            Single<String> just3 = Single.just(this.content);
            Intrinsics.checkNotNullExpressionValue(just3, "just(content)");
            return just3;
        } else {
            Single<String> just3 = Single.just(this.content);
            Intrinsics.checkNotNullExpressionValue(just3, "just(content)");
            return just3;
//            LabelBindExcel excel = LabelBoard.Companion.getExcel();
//            Intrinsics.checkNotNull(excel);
//            Single<R> map = DataRepository.INSTANCE.getExcelRow(excel.getId(), excel.getR() + 1).map(new Function() {
//                /* class com.handset.data.entity.$$Lambda$LabelBarcode$LWZgBT3LfN5w_Mt9Meg3Ewmm9eU */
//
//                @Override // io.reactivex.functions.Function
//                public final Object apply(Object obj) {
//                    return LabelBarcode.lambda$LWZgBT3LfN5w_Mt9Meg3Ewmm9eU(LabelBarcode.this, (ExcelRow) obj);
//                }
//            });
//            Intrinsics.checkNotNullExpressionValue(map, "DataRepository.getExcelRow(excel.id, excel.r + 1)\n                .map { it.cells[excelCol] }");
//            return map;
        }
    }

    /* renamed from: nextContent$lambda-0 */
//    public static final String m63nextContent$lambda0(LabelBarcode labelBarcode, ExcelRow excelRow) {
//        Intrinsics.checkNotNullParameter(labelBarcode, "this$0");
//        Intrinsics.checkNotNullParameter(excelRow, "it");
//        return excelRow.getCells().get(labelBarcode.getExcelCol());
//    }

    public final Single<String> preContent() {
        if (this.isIncrease) {
            Single<String> just = Single.just(serialAdd(this.content, -this.increment));
            Intrinsics.checkNotNullExpressionValue(just, "just(serialAdd(content, -increment))");
            return just;
        }
//        else if (!this.isBindExcel || LabelBoard.Companion.getExcel() == null) {
//            Single<String> just2 = Single.just(this.content);
//            Intrinsics.checkNotNullExpressionValue(just2, "just(content)");
//            return just2;
//        }
        else if (this.excelCol < 0) {
            Single<String> just3 = Single.just(this.content);
            Intrinsics.checkNotNullExpressionValue(just3, "just(content)");
            return just3;
        } else {
            Single<String> just3 = Single.just(this.content);
            Intrinsics.checkNotNullExpressionValue(just3, "just(content)");
            return just3;

//            LabelBindExcel excel = LabelBoard.Companion.getExcel();
//            Intrinsics.checkNotNull(excel);
//            Single<R> map = DataRepository.INSTANCE.getExcelRow(excel.getId(), excel.getR() - 1).map(new Function() {
//                /* class com.handset.data.entity.$$Lambda$LabelBarcode$aklaPiHPqxlZ7UqjMZes8dFA */
//
//                @Override // io.reactivex.functions.Function
//                public final Object apply(Object obj) {
//                    return LabelBarcode.m62lambda$aklaPiHPqxlZ7UqjMZes8dFA(LabelBarcode.this, (ExcelRow) obj);
//                }
//            });
//            Intrinsics.checkNotNullExpressionValue(map, "DataRepository.getExcelRow(excel.id, excel.r - 1)\n                .map { it.cells[excelCol] }");
//            return map;
        }
    }

    /* renamed from: preContent$lambda-1 */
//    public static final String m64preContent$lambda1(LabelBarcode labelBarcode, ExcelRow excelRow) {
//        Intrinsics.checkNotNullParameter(labelBarcode, "this$0");
//        Intrinsics.checkNotNullParameter(excelRow, "it");
//        return excelRow.getCells().get(labelBarcode.getExcelCol());
//    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
