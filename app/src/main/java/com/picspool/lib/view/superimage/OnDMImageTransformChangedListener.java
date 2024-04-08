package com.picspool.lib.view.superimage;

import android.graphics.PointF;
import android.view.View;

/* loaded from: classes3.dex */
public interface OnDMImageTransformChangedListener {
    void scalePost(float f);

    void scalePost(float f, PointF pointF, View view);

    void translatePost(float f, float f2);
}
