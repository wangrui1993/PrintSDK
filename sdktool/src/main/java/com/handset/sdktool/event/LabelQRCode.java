package com.handset.sdktool.event;

//import com.example.printbulider.data.DataRepository;
//import com.handset.data.DataRepository;
//import com.handset.data.entity.db.ExcelRow;

import io.reactivex.Single;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class LabelQRCode extends Label {
    public static final Companion Companion = new Companion(null);
    public static final String ENCODE_TYPE_DATA_Matrix = "DATA Matrix";
    public static final String ENCODE_TYPE_GS1_DATA_Matrix = "GS1 DATA Matrix";
    public static final String ENCODE_TYPE_PDF417 = "PDF417";
    public static final String ENCODE_TYPE_QRCODE = "QRcode";
    private String content = "";
    private String encodeType = ENCODE_TYPE_QRCODE;
    private int excelCol = -1;
    private int increment = 1;
    private boolean isBindExcel;
    private boolean isIncrease;

    private String encodingType= ENCODE_TYPE_QRCODE;//编码方式

    private String contentSource = "";//内容来源(固定值：固定文字、元素字段、序号)



    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }


    public LabelQRCode() {
        setWidth(250.0f);
        setHeight(250.0f);
    }

    public final String getEncodeType() {
        return this.encodeType;
    }

    public final void setEncodeType(String str) {
        Intrinsics.checkNotNullParameter(str, "encodeType");
        if (!Intrinsics.areEqual(ENCODE_TYPE_QRCODE, str) && !Intrinsics.areEqual(ENCODE_TYPE_PDF417, str) && !Intrinsics.areEqual(ENCODE_TYPE_DATA_Matrix, str) && !Intrinsics.areEqual(ENCODE_TYPE_GS1_DATA_Matrix, str)) {
            str = ENCODE_TYPE_QRCODE;
        }
        this.encodeType = str;
    }

    public final String getContent() {
        return this.content;
    }

    public final void setContent(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.content = str;
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

    public final int getExcelCol() {
        return this.excelCol;
    }

    public final void setExcelCol(int i) {
        this.excelCol = i;
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
//                /* class com.handset.data.entity.$$Lambda$LabelQRCode$QFEhD6bKqLLop5LsuLqvfWiWoU */
//
//                @Override // io.reactivex.functions.Function
//                public final Object apply(Object obj) {
//                    return LabelQRCode.m66nextContent$lambda0(LabelQRCode.this, (ExcelRow) obj);
//                }
//            });
//            Intrinsics.checkNotNullExpressionValue(map, "DataRepository.getExcelRow(excel.id, excel.r + 1)\n                .map { it.cells[excelCol] }");
//            return map;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: nextContent$lambda-0  reason: not valid java name */
//    public static final String m66nextContent$lambda0(LabelQRCode labelQRCode, ExcelRow excelRow) {
//        Intrinsics.checkNotNullParameter(labelQRCode, "this$0");
//        Intrinsics.checkNotNullParameter(excelRow, "it");
//        return excelRow.getCells().get(labelQRCode.getExcelCol());
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
//                /* class com.handset.data.entity.$$Lambda$LabelQRCode$hKfi4oWKeCnNuYk9Yaa7ho_TRxs */
//
//                @Override // io.reactivex.functions.Function
//                public final Object apply(Object obj) {
//                    return LabelQRCode.m67preContent$lambda1(LabelQRCode.this, (ExcelRow) obj);
//                }
//            });
//            Intrinsics.checkNotNullExpressionValue(map, "DataRepository.getExcelRow(excel.id, excel.r - 1)\n                .map { it.cells[excelCol] }");
//            return map;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: preContent$lambda-1  reason: not valid java name */
//    public static final String m67preContent$lambda1(LabelQRCode labelQRCode, ExcelRow excelRow) {
//        Intrinsics.checkNotNullParameter(labelQRCode, "this$0");
//        Intrinsics.checkNotNullParameter(excelRow, "it");
//        return excelRow.getCells().get(labelQRCode.getExcelCol());
//    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/handset/data/entity/LabelQRCode$Companion;", "", "()V", "ENCODE_TYPE_DATA_Matrix", "", "ENCODE_TYPE_GS1_DATA_Matrix", "ENCODE_TYPE_PDF417", "ENCODE_TYPE_QRCODE", "lib-data_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* compiled from: LabelQRCode.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
