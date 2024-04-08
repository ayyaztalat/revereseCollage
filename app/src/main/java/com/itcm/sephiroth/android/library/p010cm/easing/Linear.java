package com.itcm.sephiroth.android.library.p010cm.easing;

/* renamed from: itcm.sephiroth.android.library.cm.easing.Linear */
/* loaded from: classes3.dex */
public class Linear implements Easing {
    @Override // itcm.sephiroth.android.library.p010cm.easing.Easing
    public double easeIn(double d, double d2, double d3, double d4) {
        return ((d3 * d) / d4) + d2;
    }

    @Override // itcm.sephiroth.android.library.p010cm.easing.Easing
    public double easeInOut(double d, double d2, double d3, double d4) {
        return ((d3 * d) / d4) + d2;
    }

    public double easeNone(double d, double d2, double d3, double d4) {
        return ((d3 * d) / d4) + d2;
    }

    @Override // itcm.sephiroth.android.library.p010cm.easing.Easing
    public double easeOut(double d, double d2, double d3, double d4) {
        return ((d3 * d) / d4) + d2;
    }
}
