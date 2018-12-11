package com.kkinfosis.gamingwallpaper4k.utilities;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class ItemOffsetDecoration extends ItemDecoration {
    private int mItemOffset;

    public ItemOffsetDecoration(int i) {
        this.mItemOffset = i;
    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int i) {
        this(context.getResources().getDimensionPixelSize(i));
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        rect.set(this.mItemOffset, this.mItemOffset, this.mItemOffset, this.mItemOffset);
    }
}
