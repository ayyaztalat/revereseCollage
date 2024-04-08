package com.photo.editor.square.splash.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/* loaded from: classes2.dex */
public class CSScreenInfoUtil {
    public static int dip2px(Context context, float f) {
        context.getResources();
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int screenWidthDp(Context context) {
        return px2dip(context, context.getResources().getDisplayMetrics().widthPixels);
    }

    public static int screenHeightDp(Context context) {
        return px2dip(context, context.getResources().getDisplayMetrics().heightPixels);
    }

    public static float screenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int screenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int screenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static void resizeView(View view, float f, float f2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
            layoutParams2.setMargins((int) ((layoutParams2.leftMargin * f) + 0.5d), (int) ((layoutParams2.topMargin * f2) + 0.5d), (int) ((layoutParams2.rightMargin * f) + 0.5d), (int) ((layoutParams2.bottomMargin * f2) + 0.5d));
            if (layoutParams2.width >= 0) {
                layoutParams2.width = (int) ((layoutParams2.width * f) + 0.5d);
            }
            if (layoutParams2.height >= 0) {
                layoutParams2.height = (int) ((layoutParams2.height * f2) + 0.5d);
            }
        }
        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) layoutParams;
            layoutParams3.setMargins((int) ((layoutParams3.leftMargin * f) + 0.5d), (int) ((layoutParams3.topMargin * f2) + 0.5d), (int) ((layoutParams3.rightMargin * f) + 0.5d), (int) ((layoutParams3.bottomMargin * f2) + 0.5d));
            if (layoutParams3.width >= 0) {
                layoutParams3.width = (int) ((layoutParams3.width * f) + 0.5d);
            }
            if (layoutParams3.height >= 0) {
                layoutParams3.height = (int) ((layoutParams3.height * f2) + 0.5d);
            }
        }
    }
}
