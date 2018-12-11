package com.kkinfosis.gamingwallpaper4k.utilities;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.Behavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

public class AppBarLayoutBehavior extends Behavior {
    public AppBarLayoutBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AppBarLayoutBehavior() {
        super();
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5) {
        super.onNestedScroll(coordinatorLayout, appBarLayout, view, i, i2, i3, i4, i5);
        stopNestedScrollIfNeeded(i4, appBarLayout, view, i5);
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
        super.onNestedPreScroll(coordinatorLayout, appBarLayout, view, i, i2, iArr, i3);
        stopNestedScrollIfNeeded(i2, appBarLayout, view, i3);
    }

    private void stopNestedScrollIfNeeded(int i, AppBarLayout appBarLayout, View view, int i2) {
        if (i2 == 1) {
            i2 = getTopAndBottomOffset();
            if ((i < 0 && i2 == 0) || (i > 0 && i2 == (-appBarLayout.getTotalScrollRange()))) {
                ViewCompat.stopNestedScroll(view, 1);
            }
        }
    }
}
