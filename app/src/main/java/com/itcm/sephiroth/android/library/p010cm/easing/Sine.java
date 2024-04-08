package com.itcm.sephiroth.android.library.p010cm.easing;

/* renamed from: itcm.sephiroth.android.library.cm.easing.Sine */
/* loaded from: classes3.dex */
public class Sine implements Easing {
    @Override // itcm.sephiroth.android.library.p010cm.easing.Easing
    public double easeOut(double d, double d2, double d3, double d4) {
        return (d3 * Math.sin((d / d4) * 1.5707963267948966d)) + d2;
    }

    @Override // itcm.sephiroth.android.library.p010cm.easing.Easing
    public double easeIn(double d, double d2, double d3, double d4) {
        return ((-d3) * Math.cos((d / d4) * 1.5707963267948966d)) + d3 + d2;
    }

    @Override // itcm.sephiroth.android.library.p010cm.easing.Easing
    public double easeInOut(double d, double d2, double d3, double d4) {
        return (((-d3) / 2.0d) * (Math.cos((d * 3.141592653589793d) / d4) - 1.0d)) + d2;
    }
}
