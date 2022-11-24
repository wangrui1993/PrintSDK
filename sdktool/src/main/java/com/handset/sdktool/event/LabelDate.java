package com.handset.sdktool.event;

//import com.google.zxing.client.android.activity.BaseCaptureActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

//@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\t\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u000fX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013¨\u0006\u0018"}, d2 = {"Lcom/handset/data/entity/LabelDate;", "Lcom/handset/data/entity/LabelText;", "()V", BaseCaptureActivity.KEY_BARCODE_FORMAT, "", "getFormat", "()Ljava/lang/String;", "setFormat", "(Ljava/lang/String;)V", "isAutoUpdate", "", "()Z", "setAutoUpdate", "(Z)V", "offset", "", "getOffset", "()J", "setOffset", "(J)V", "timeStamp", "getTimeStamp", "setTimeStamp", "Companion", "lib-data_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* compiled from: LabelDate.kt */
public final class LabelDate extends LabelText {
    public static final Companion Companion = new Companion(null);
    public static final String FORMAT_dd_MMM_yyyy = "dd-MMM-yyyy";
    public static final String FORMAT_yyyy_MM = "yyyy-MM";
    public static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String FORMAT_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_yyyy_MM_dd_HH_mm_SS = "yyyy-MM-dd HH:mm:ss";
    private String format = FORMAT_yyyy_MM_dd;
    private boolean isAutoUpdate;
    private long offset;
    private long timeStamp = System.currentTimeMillis();

    public final boolean isAutoUpdate() {
        return this.isAutoUpdate;
    }

    public final void setAutoUpdate(boolean z) {
        this.isAutoUpdate = z;
    }

    public final long getOffset() {
        return this.offset;
    }

    public final void setOffset(long j) {
        this.offset = j;
    }

    public final long getTimeStamp() {
        return this.timeStamp;
    }

    public final void setTimeStamp(long j) {
        this.timeStamp = j;
    }

    public final String getFormat() {
        return this.format;
    }

    public final void setFormat(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.format = str;
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/handset/data/entity/LabelDate$Companion;", "", "()V", "FORMAT_dd_MMM_yyyy", "", "FORMAT_yyyy_MM", "FORMAT_yyyy_MM_dd", "FORMAT_yyyy_MM_dd_HH_mm", "FORMAT_yyyy_MM_dd_HH_mm_SS", "lib-data_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* compiled from: LabelDate.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
