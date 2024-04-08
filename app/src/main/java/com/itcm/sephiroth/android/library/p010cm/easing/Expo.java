package com.itcm.sephiroth.android.library.p010cm.easing;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/* renamed from: itcm.sephiroth.android.library.cm.easing.Expo */
/* loaded from: classes3.dex */
public class Expo implements Easing {
    @Override // itcm.sephiroth.android.library.p010cm.easing.Easing
    public double easeOut(double d, double d2, double d3, double d4) {
        return d == d4 ? d2 + d3 : d2 + (d3 * ((-Math.pow(2.0d, (d * (-10.0d)) / d4)) + 1.0d));
    }

    @Override // itcm.sephiroth.android.library.p010cm.easing.Easing
    public double easeIn(double d, double d2, double d3, double d4) {
        return d == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? d2 : d2 + (d3 * Math.pow(2.0d, ((d / d4) - 1.0d) * 10.0d));
    }

    @Override // itcm.sephiroth.android.library.p010cm.easing.Easing
    public double easeInOut(double d, double d2, double d3, double d4) {
        double d5;
        double d6;
        if (d == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            return d2;
        }
        if (d == d4) {
            return d2 + d3;
        }
        double d7 = d / (d4 / 2.0d);
        if (d7 < 1.0d) {
            d5 = d3 / 2.0d;
            d6 = Math.pow(2.0d, (d7 - 1.0d) * 10.0d);
        } else {
            d5 = d3 / 2.0d;
            d6 = (-Math.pow(2.0d, (d7 - 1.0d) * (-10.0d))) + 2.0d;
        }
        return (d5 * d6) + d2;
    }
}
