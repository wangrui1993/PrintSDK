package com.handset.sdktool.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

public abstract class OnRecycleViewItemClickListener implements RecyclerView.OnItemTouchListener {
    private final GestureDetectorCompat mGestureDetector;
    private final RecyclerView rlView;

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder, int i);

    public abstract void onItemLongClick(RecyclerView.ViewHolder viewHolder, int i);

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    public OnRecycleViewItemClickListener(Context context, RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "rlView");
        this.rlView = recyclerView;
        this.mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener(this));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(recyclerView, "rv");
        Intrinsics.checkNotNullParameter(motionEvent, "e");
        this.mGestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(recyclerView, "rv");
        Intrinsics.checkNotNullParameter(motionEvent, "e");
        this.mGestureDetector.onTouchEvent(motionEvent);
    }

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\t"}, d2 = {"Lcom/handset/print/common/OnRecycleViewItemClickListener$ItemTouchHelperGestureListener;", "Landroid/view/GestureDetector$SimpleOnGestureListener;", "(Lcom/handset/print/common/OnRecycleViewItemClickListener;)V", "onLongPress", "", "e", "Landroid/view/MotionEvent;", "onSingleTapUp", "", "app-print_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* compiled from: OnRecycleViewItemClickListener.kt */
    private final class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        final /* synthetic */ OnRecycleViewItemClickListener this$0;

        /* JADX WARN: Incorrect args count in method signature: ()V */
        public ItemTouchHelperGestureListener(OnRecycleViewItemClickListener onRecycleViewItemClickListener) {
            Intrinsics.checkNotNullParameter(onRecycleViewItemClickListener, "this$0");
            this.this$0 = onRecycleViewItemClickListener;
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            Intrinsics.checkNotNullParameter(motionEvent, "e");
            View findChildViewUnder = this.this$0.rlView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (findChildViewUnder == null) {
                return true;
            }
            this.this$0.onItemClick(this.this$0.rlView.getChildViewHolder(findChildViewUnder), this.this$0.rlView.getChildLayoutPosition(findChildViewUnder));
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            Intrinsics.checkNotNullParameter(motionEvent, "e");
            super.onLongPress(motionEvent);
            View findChildViewUnder = this.this$0.rlView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (findChildViewUnder != null) {
                this.this$0.onItemLongClick(this.this$0.rlView.getChildViewHolder(findChildViewUnder), this.this$0.rlView.getChildLayoutPosition(findChildViewUnder));
            }
        }
    }
}
