package com.touch.android.library.imagezoom.easing;

/* loaded from: classes3.dex */
public class Circ implements Easing {
    @Override // touch.android.library.imagezoom.easing.Easing
    public double easeOut(double d, double d2, double d3, double d4) {
        double d5 = (d / d4) - 1.0d;
        return (d3 * Math.sqrt(1.0d - (d5 * d5))) + d2;
    }

    @Override // touch.android.library.imagezoom.easing.Easing
    public double easeIn(double d, double d2, double d3, double d4) {
        double d5 = d / d4;
        return ((-d3) * (Math.sqrt(1.0d - (d5 * d5)) - 1.0d)) + d2;
    }

    @Override // touch.android.library.imagezoom.easing.Easing
    public double easeInOut(double d, double d2, double d3, double d4) {
        double d5;
        double sqrt;
        double d6 = d / (d4 / 2.0d);
        if (d6 < 1.0d) {
            d5 = (-d3) / 2.0d;
            sqrt = Math.sqrt(1.0d - (d6 * d6)) - 1.0d;
        } else {
            d5 = d3 / 2.0d;
            double d7 = d6 - 2.0d;
            sqrt = Math.sqrt(1.0d - (d7 * d7)) + 1.0d;
        }
        return (d5 * sqrt) + d2;
    }
}
